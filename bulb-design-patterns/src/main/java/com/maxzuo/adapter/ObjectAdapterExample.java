package com.maxzuo.adapter;

/**
 * 对象适配器模式
 * Created by zfh on 2019/03/11
 */
public class ObjectAdapterExample {

    public static void main(String[] args) {
        Ps3 ps3 = new Adapter3(new Usber());
        ps3.isPs3();
    }
}

/**
 * 源接口
 */
interface Ps3 {

    void isPs3();
}

/**
 * 目标接口
 */
interface Usb3 {
    void isUsb3();
}

/**
 * 实现类
 */
class Usber3 implements Usb3 {

    @Override
    public void isUsb3() {
        System.out.println("is Usb3");
    }
}

/**
 * 适配器
 */

class Adapter3 implements Ps3 {

    private Usb usb;

    Adapter3(Usb usb) {
        this.usb = usb;
    }

    @Override
    public void isPs3() {
        usb.isUsb();
    }
}
