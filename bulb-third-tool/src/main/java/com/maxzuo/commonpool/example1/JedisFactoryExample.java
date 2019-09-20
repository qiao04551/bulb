package com.maxzuo.commonpool.example1;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 自定义 JedisFactory，生成Jedis对象
 * <p>
 * Created by zfh on 2019/09/20
 */
public class JedisFactoryExample implements PooledObjectFactory<Jedis> {

    private String host;

    private Integer port;

    private String password;

    public JedisFactoryExample (String host, Integer port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Override
    public PooledObject<Jedis> makeObject() throws Exception {
        Jedis jedis = new Jedis(host, port);
        try {
            jedis.connect();
            jedis.auth(password);
            jedis.select(0);
            jedis.clientSetname("test_bulb");
        } catch (JedisException je) {
            jedis.close();
            throw je;
        }
        return new DefaultPooledObject<>(jedis);
    }

    @Override
    public void destroyObject(PooledObject<Jedis> pooledJedis) throws Exception {
        final BinaryJedis jedis = pooledJedis.getObject();
        if (jedis.isConnected()) {
            try {
                try {
                    jedis.quit();
                } catch (Exception e) {
                }
                jedis.disconnect();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<Jedis> pooledJedis) {
        final BinaryJedis jedis = pooledJedis.getObject();
        try {
            return jedis.isConnected() && jedis.ping().equals("PONG");
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<Jedis> pooledJedis) throws Exception {
        final BinaryJedis jedis = pooledJedis.getObject();
        if (jedis.getDB() != 0) {
            jedis.select(0);
        }
    }

    @Override
    public void passivateObject(PooledObject<Jedis> p) throws Exception {
        // TODO maybe should select db 0? Not sure right now.
    }
}
