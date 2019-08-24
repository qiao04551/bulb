package com.maxzuo.adapter;

/**
 * 类适配器模式
 * Created by zfh on 2019/03/11
 */
public class ClassAdapterExample {

    public static void main(String[] args) {
        Ps2 ps2 = new Adapter();
        ps2.isPs2();
    }
}

/**
 * 源接口
 */
interface Ps2 {

    void isPs2();
}

/**
 * 目标接口
 */
interface Usb {

    void isUsb();
}

/**
 * 实现类
 */
class Usber implements Usb {

    @Override
    public void isUsb() {
        System.out.println("usb 接口");
    }
}

/**
 * 适配器
 */
class Adapter extends Usber implements Ps2 {
    @Override
    public void isPs2() {
        isUsb();
    }
}