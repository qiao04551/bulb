package com.maxzuo.commonpool.example2;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现PooledObjectFactory，定义了如何makeObject创建、destroyObject销毁、validateObject校验、activateObject激活PooledObject对象，
 * <p>
 * Created by zfh on 2019/09/19
 */
public class PooledObjectFactoryExample implements PooledObjectFactory<UserObject> {

    private static final Logger logger = LoggerFactory.getLogger(PooledObjectFactoryExample.class);

    @Override
    public PooledObject<UserObject> makeObject() throws Exception {
        UserObject userObject = new UserObject();
        userObject.setName("dazuo");
        userObject.setAge(23);
        userObject.setTimestamp(System.currentTimeMillis());
        logger.info("【makeObject】UserObject = {}", userObject.toString());
        return new DefaultPooledObject<>(userObject);
    }

    @Override
    public void destroyObject(PooledObject<UserObject> p) throws Exception {
        logger.info("【destroyObject】UserObject = {}", p.getObject().toString());
        UserObject userObject = p.getObject();
        userObject.setName(null);
        userObject.setAge(null);
        userObject.setTimestamp(null);
    }

    @Override
    public boolean validateObject(PooledObject<UserObject> p) {
        logger.info("【validateObject】UserObject = {}", p.getObject().toString());
        return p.getObject() != null;
    }

    @Override
    public void activateObject(PooledObject<UserObject> p) throws Exception {
        logger.info("【activateObject】UserObject = {}", p.getObject().toString());
        UserObject object = p.getObject();
        object.setTimestamp(System.currentTimeMillis());
    }

    @Override
    public void passivateObject(PooledObject<UserObject> p) throws Exception {
        logger.info("【passivateObject】UserObject = {}", p.getObject().toString());
    }
}
