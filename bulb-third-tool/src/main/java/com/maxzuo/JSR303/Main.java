package com.maxzuo.JSR303;

/**
 * Created by zfh on 2019/10/19
 */
public class Main {

    public static void main(String[] args) {
        RequestForm requestForm = new RequestForm(1, null);
        requestForm.validateParam();
    }
}
