package com.maxzuo.thrift;

import org.apache.thrift.TException;

/**
 * Created by zfh on 2019/08/11
 */
public class HelloWorldImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "hello " + username;
    }
}
