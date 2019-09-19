package com.maxzuo.jedis;

import org.junit.Test;
import redis.clients.jedis.*;

/**
 * Jedis三种请求模式
 * <pre>
 *  1.Jedis的主要模块，Jedis,JedisCluster,JedisSentinel和ShardedJedis对应了Redis的四种工作模式：Redis Standalone（单节点模式）,
 *    Redis Cluster（集群模式）,Redis Sentinel（哨兵模式）和Redis Sharding（分片模式）。
 *  2.每个Jedis实例对应一个Redis节点，我们对Jedis实例的每个操作，都相当于使用redis-cli启动客户端的直接操作。
 *  3.无论是集群模式，哨兵模式，还是分片模式，内部均为对Jedis实例的操作。
 * </pre>
 * Created by zfh on 2019/03/20
 */
public class JedisClientExample {

    /**
     * Client模式
     * <pre>
     *  Client模式就是常用的“所见即所得”，客户端发一个命令，阻塞等待服务端执行，然后读取返回结果。优点是确保每次处理都有结果，
     *  一旦发现返回结果中有Error,就可以立即处理。
     * </pre>
     */
    @Test
    public void clientMode() {
        Jedis jedis = new Jedis("47.98.199.80", 6379);
        jedis.auth("myredis");
        jedis.select(1);

        String setResult = jedis.set("name", "dazuo");
        System.out.println("setResult: " + setResult);

        jedis.close();
    }

    @Test
    public void clientBinaryClient() {
        BinaryJedis binaryJedis = new BinaryJedis("192.168.3.183", 6379);
        binaryJedis.auth("myredis");
        binaryJedis.select(1);

        String setResult = binaryJedis.set("name".getBytes(), "dazuo".getBytes());
        System.out.println("setResult：" + setResult);

        binaryJedis.close();
    }

    /**
     * Pipeline模式
     * <pre>
     *  Pipeline模式则是一次性发送多个命令，最后一次取回所有的返回结果，这种模式通过减少网络的往返时间和IO的读写次数，
     *  大幅度提高通信性能，但Pipeline不支持原子性，如果想保证原子性，可同时开启事务模式。
     * </pre>
     */
    @Test
    public void pipelineMode() {
        Jedis jedis = new Jedis("192.168.3.183", 6379);
        jedis.auth("myredis");
        jedis.select(1);

        Pipeline pipe = jedis.pipelined();
        // 只发送命令，不读取结果，此时的Response<T>没有数据
        Response<String> setResponse = pipe.set("name", "dazuo");
        Response<String> getResponse = pipe.get("name");
        Response<Long> delResponse = pipe.del("name");
        // 一次性读取所有的Response
        pipe.sync();
        System.out.println("setResponse: " + setResponse.get());
        System.out.println("getResponse: " + getResponse.get());
        System.out.println("delResponse: " + delResponse.get());

        try {
            pipe.close();
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Transaction模式
     * <pre>
     * ​Transaction模式即开启Redis的事务管理，Pipeline可以在事务中，也可以不在事务中。事务模式开启后，所有的命令（除了 EXEC 、DISCARD 、
     *  MULTI 和 WATCH ）到达服务端以后，不会立即执行，会进入一个等待队列，等到收到下述四个命令时执行不同操作：
     *
     *  - EXEC命令执行时， 服务器以先进先出（FIFO）的方式执行事务队列中的命令,当事务队列里的所有命令被执行完之后， 将回复队列
     *    作为自己的执行结果返回给客户端， 客户端从事务状态返回到非事务状态， 至此， 事务执行完毕。
     *  - DISCARD命令用于取消一个事务， 它清空客户端的整个事务队列， 然后将客户端从事务状态调整回非事务状态， 最后返回字符串 OK
     *    给客户端， 说明事务已被取消。
     *  - Redis 的事务是不可嵌套的， 当客户端已经处于事务状态， 而客户端又再向服务器发送MULTI时， 服务器只是简单地向客户端发送
     *    一个错误， 然后继续等待其他命令的入队。 MULTI命令的发送不会造成整个事务失败， 也不会修改事务队列中已有的数据。
     *  - WATCH只能在客户端进入事务状态之前执行， 在事务状态下发送 WATCH命令会引发一个错误， 但它不会造成整个事务失败， 也不会
     *    修改事务队列中已有的数据（和前面处理 MULTI的情况一样）。
     * </pre>
     */
    @Test
    public void transactionMode() {
        Jedis jedis = new Jedis("192.168.3.183", 6379);
        jedis.auth("myredis");
        jedis.select(1);

        // 开启事务
        Transaction multi = jedis.multi();
        // 命令进入服务端的执行队列
        Response<String> response1 = multi.set("name", "dazuo");
        Response<Long> response2 = multi.del("name");
        // 执行所有命令
        multi.exec();
        System.out.println("response1: " + response1.get());
        System.out.println("response2: " + response2.get());

        try {
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
