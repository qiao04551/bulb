package com.maxzuo.guice;

import com.google.inject.AbstractModule;

/**
 * 定义依赖绑定的基本单元
 * <p>
 * Created by zfh on 2020/04/26
 */
public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        // 将 Communicator 绑定了到一个具体的实现 DefaultCommunicatorImpl
        bind(Communicator.class).to(DefaultCommunicatorImpl.class);
    }
}
