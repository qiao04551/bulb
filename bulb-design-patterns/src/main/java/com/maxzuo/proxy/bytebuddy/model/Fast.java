package com.maxzuo.proxy.bytebuddy.model;

/**
 * Created by zfh on 2019/01/28
 */
public class Fast {

    public String m() {
        return "fast";
    }

    public String m(String var1) {
        return var1 + " fast";
    }
}
