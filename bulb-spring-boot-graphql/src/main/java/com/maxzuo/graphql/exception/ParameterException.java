package com.maxzuo.graphql.exception;

/**
 * 参数异常
 * <p>
 * Created by zfh on 2018/09/14
 */
public class ParameterException extends BaseException {

    private static final long serialVersionUID = 4118870983589945134L;

    public ParameterException() {
        super("10001", "缺少参数！");
    }

    public ParameterException(String errMessage) {
        super("10001", errMessage);
    }
}
