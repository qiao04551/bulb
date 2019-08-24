package com.maxzuo.dubbo.common.exception;

/**
 * 哨兵-阻塞异常
 * Created by zfh on 2019/07/20
 */
public class SentinelBlockException extends RuntimeException {

    private static final long serialVersionUID = 8964853814147775642L;

    public SentinelBlockException() {
        super("哨兵-阻塞异常");
    }
}
