package com.maxzuo.basic;

/**
 * 枚举实现接口
 * Created by zfh on 2019/02/12
 */
public enum EnumAndInterface implements ConstructorStrategy {

    NO_CONSTRUCTOR(1, "DAZUO"), OVERWRITE_CONSTRUCTOR(2, "wang") {
        @Override
        public void inject(String name) {
            System.out.println("hello " + name);
        }
    };

    private Integer code;

    private String  name;

    EnumAndInterface(Integer code, String name) {
        this.name = name;
        this.code = code;
    }

    @Override
    public void inject(String name) {
        System.out.println("hello name");
    }
}

interface ConstructorStrategy {
    void inject(String name);
}

class Main {
    public static void main(String[] args) {
        EnumAndInterface.NO_CONSTRUCTOR.inject("dazuo");
        EnumAndInterface.OVERWRITE_CONSTRUCTOR.inject("dazuo");
    }
}
