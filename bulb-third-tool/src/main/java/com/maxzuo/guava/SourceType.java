package com.maxzuo.guava;

/**
 * 应用来源类型
 * <p>
 * Created by zfh on 2019/09/16
 */
public enum SourceType {

    APP(100, "移动APP"),
    MINI_APP(200, "小程序"),
    PC(300, "PC端");

    private Integer code;

    private String desc;

    SourceType (Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SourceType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
