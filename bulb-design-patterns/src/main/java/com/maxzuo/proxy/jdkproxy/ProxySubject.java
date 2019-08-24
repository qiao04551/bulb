package com.maxzuo.proxy.jdkproxy;

/**
 * 代理类
 * <p>
 * Created by zfh on 2019/04/03
 */
public class ProxySubject implements Subject {

    private Subject subject;

    public ProxySubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void visit() {
        subject.visit();
    }

    @Override
    public String getSubject() {
        return "proxySubject";
    }
}
