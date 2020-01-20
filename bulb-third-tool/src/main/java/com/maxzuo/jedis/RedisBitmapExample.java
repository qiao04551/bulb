package com.maxzuo.jedis;

import redis.clients.jedis.Jedis;

/**
 * Redis BitMap（位图）
 * <pre>
 *   就是通过一个bit位来表示某个元素对应的值或者状态,其中的key就是对应元素本身。我们知道8个bit可以组成一个Byte，所以bitmap
 *   本身会极大的节省储存空间。
 * </pre>
 * Created by zfh on 2020/01/20
 */
public class RedisBitmapExample {

    public static void main (String[] args) {
        Jedis jedis = new Jedis("192.168.3.183", 6379);
        jedis.auth("myredis");

        /*
            SETBIT key offset value
            特性：对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
                 位的设置或清除取决于 value 参数，可以是 0 也可以是 1。
                 当 key 不存在时，自动生成一个新的字符串值。
                 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
                 offset 参数必须大于或等于 0，小于 2^32 (bit 映射被限制在 512 MB 之内)。
            时间复杂度：O(1)
            返回值：指定偏移量原来储存的位。

            业务场景：
              1.视频网站，某个用户是否观看了某个视频。
              2.统计多天bitmap就可以实现统计活跃用户。
         */
        String key = "akey";
        jedis.setbit(key, 0, "1");
        jedis.setbit(key, 1, "1");
        jedis.setbit(key, 2, "1");

        System.out.println(jedis.getbit(key, 0));
        System.out.println(jedis.getbit(key, 1));
        System.out.println(jedis.getbit(key, 2));

        System.out.println(jedis.setbit(key, 3, "1"));
        System.out.println(jedis.setbit(key, 3, "1"));

        /*
            'a'：ascll码是97，二进制表示：01100001
            'b'：ascll码是98，二进制表示：01100010
            使用Redis SETBIT命令将 'a' 变成 'b'，需要将 'a' 中的offset 6从0变成1，将offset 7 从1变成0 。
         */
        System.out.println("a Ascll: " + (int)'a');
        System.out.println("b Ascll: " + (int)'b');
        // 若最高的几位为0则不输出这几位，从为1的那一位开始输出
        System.out.println("a binaryString: " + Integer.toBinaryString('a'));
        System.out.println("b binaryString: " + Integer.toBinaryString('b'));

        jedis.set(key, "a");
        jedis.setbit(key, 6, "1");
        jedis.setbit(key, 7, "0");
        System.out.println("akey value：" + jedis.get(key));

        /*
            BITCOUNT 就是统计字符串的二级制码中，有多少个'1'
         */
        System.out.println("b bitCount：" + jedis.bitcount(key));

        /*
            Getbit 命令用于对 key 所储存的字符串值，获取指定偏移量上的位(bit)

            'b' 转换为二进制 01100010，二进制中的每一位就是offset值，offset是从左往右计数的，也就是从高位往低位。
         */
        System.out.println("b Getbit offset = 0：" + jedis.getbit(key, 0));
        System.out.println("b Getbit offset = 1：" + jedis.getbit(key, 1));
        System.out.println("b Getbit offset = 1：" + jedis.getbit(key, 2));
        System.out.println("b Getbit offset = 1：" + jedis.getbit(key, 3));

        jedis.close();
    }
}
