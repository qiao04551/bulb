package com.maxzuo.graphql.vo;

/**
 * 统一返回类型
 */
public class Result {

    /**
     * 调用成功
     */
    public static final Integer RESULT_SUCCESS = 1;

    /**
     * 无权限访问
     */
    public static final Integer RESULT_NO_AUTHORITY = 8;

    /**
     * 调用失败
     */
    public static final Integer RESULT_FAILURE = 9;


    public static final String RESULT_SUCCESS_MSG = "操作成功!";

    public static final String RESULT_FAILURE_MSG = "系统繁忙，请稍后再试!";

    /**
     * 总数
     */
    private Long total;

    /**
     * 1:成功; 8:无权限; 9:失败
     */
    private int code;

    /**
     * 成功或者失败返回的消息
     */
    private String msg;

    /**
     * 成功后返回的数据
     */
    private Object data;

    public Result() {

    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "total=" + total +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
