package com.maxzuo.dubbo.common.exception.common;

import com.maxzuo.dubbo.common.exception.BaseException;

/**
 * 参数异常
 * Created by zfh on 2019/3/7
 */
public class ParamterException extends BaseException {

    private static final long serialVersionUID = -7902303991992499548L;

    public ParamterException() {
        super("00001", "缺少参数！");
    }
}
