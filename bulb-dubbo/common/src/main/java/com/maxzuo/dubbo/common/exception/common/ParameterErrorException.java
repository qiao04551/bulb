package com.maxzuo.dubbo.common.exception.common;

import com.maxzuo.dubbo.common.exception.BaseException;

/**
 * 参数错误异常
 * Created by zfh on 2019/3/7
 */
public class ParameterErrorException extends BaseException {

    private static final long serialVersionUID = -630472067126208283L;

    public ParameterErrorException() {
        super("00002", "参数错误！");
    }
}
