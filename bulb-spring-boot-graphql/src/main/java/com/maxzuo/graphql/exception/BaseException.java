package com.maxzuo.graphql.exception;

/**
 * 异常基类
 * <p>
 * Created by zfh on 2018/09/14
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 5555388202156871321L;

    private String errCode;

    private String errMessage;

    public BaseException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
