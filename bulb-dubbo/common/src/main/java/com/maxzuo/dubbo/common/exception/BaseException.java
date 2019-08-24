package com.maxzuo.dubbo.common.exception;

/**
 * 统一异常处理，异常基类
 * <pre>
 * 1.异常的抛与接，需要严格的对等传递异常信息传递机制。要使捕获的异常与被抛出的异常是完全匹配的，
 *   或者捕获的异常是抛出异常的父类。
 * 2.避免单一的错误响应，不利于排查异常信息；推荐对外提供的开发接口使用错误码，提高开发效率。
 * 3.公司内部跨应用服务调用（RPC）优先考虑使用Result对象封装错误码、错误描述信息；而应用内部
 *   则推荐直接抛出异常对象。
 * </pre>
 * Created by zfh on 2018/12/28
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1015884204096063763L;

    private String            errCode;

    private String            errMessage;

    public BaseException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public BaseException(String errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
