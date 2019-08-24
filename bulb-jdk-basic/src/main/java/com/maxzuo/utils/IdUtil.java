package com.maxzuo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分布式唯一ID生成器
 * <pre>
 *   方案一：利用数据库的自增ID，从1开始，基本可以做到连续递增。Oracle可以用SEQUENCE，MySQL可以用主键的AUTO_INCREMENT，
 *          虽然不能保证全局唯一，但每个表唯一，也基本满足需求。
 *   方案二：采用一个集中式ID生成器，它可以是Redis，也可以是ZooKeeper，也可以利用数据库的表记录最后分配的ID。缺点：复杂性太高，
 *          需要严重依赖第三方服务，而且代码配置繁琐。
 *   方案三：类似Twitter的Snowflake算法（https://www.cnblogs.com/relucent/p/4955340.html）
 *          Snowflake算法采用41bit毫秒时间戳，加上10bit机器ID，加上12bit序列号，理论上最多支持1024台机器每秒生成4096000个序列号，
 *          对于Twitter的规模来说够用了。但是对于绝大部分普通应用程序来说，根本不需要每秒超过400万的ID。
 *   方案四：知乎-廖雪峰分布式ID生成器：https://zhuanlan.zhihu.com/p/65095562
 *
 *   其中：Leaf——美团点评分布式ID生成系统（https://tech.meituan.com/2017/04/21/mt-leaf.html）
 * </pre>
 * Created by zfh on 2019/08/03
 */
public class IdUtil {

    private static final Logger logger = LoggerFactory.getLogger(IdUtil.class);

    private static final Pattern PATTERN_LONG_ID = Pattern.compile("^([0-9]{15})([0-9a-f]{32})([0-9a-f]{3})$");

    private static final Pattern PATTERN_HOSTNAME = Pattern.compile("^.*\\D+([0-9]+)$");

    private static final long OFFSET = LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.of("Z")).toEpochSecond();

    private static final long MAX_NEXT = 0b11111_11111111_111L;

    private static final long SHARD_ID = getServerIdAsLong();

    private static long offset = 0;

    private static long lastEpoch = 0;

    public static long nextId() {
        return nextId(System.currentTimeMillis() / 1000);
    }

    private static synchronized long nextId(long epochSecond) {
        if (epochSecond < lastEpoch) {
            // warning: clock is turn back:
            logger.warn("clock is back: " + epochSecond + " from previous:" + lastEpoch);
            epochSecond = lastEpoch;
        }
        if (lastEpoch != epochSecond) {
            lastEpoch = epochSecond;
            reset();
        }
        offset++;
        long next = offset & MAX_NEXT;
        if (next == 0) {
            logger.warn("maximum id reached in 1 second in epoch: " + epochSecond);
            return nextId(epochSecond + 1);
        }
        return generateId(epochSecond, next, SHARD_ID);
    }

    private static void reset() {
        offset = 0;
    }

    private static long generateId(long epochSecond, long next, long shardId) {
        return ((epochSecond - OFFSET) << 21) | (next << 5) | shardId;
    }

    private static long getServerIdAsLong() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            Matcher matcher = PATTERN_HOSTNAME.matcher(hostname);
            if (matcher.matches()) {
                long n = Long.parseLong(matcher.group(1));
                if (n >= 0 && n < 8) {
                    logger.info("detect server id from host name {}: {}.", hostname, n);
                    return n;
                }
            }
        } catch (UnknownHostException e) {
            logger.warn("unable to get host name. set server id = 0.");
        }
        return 0;
    }
}
