package com.maxzuo.adapter;

/**
 * 接口适配器
 * Created by zfh on 2019/03/11
 */
public class InterfaceAdapterExample {

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        Car car = new MercedesCar();
        car.color();
    }
}

/**
 * 目标接口
 */
interface Car {

    void color();

    void brand();

    void price();
}

/**
 * 适配器
 */
abstract class AbstractCar implements Car {

    @Override
    public void color() {

    }

    @Override
    public void brand() {

    }

    @Override
    public void price() {

    }
}

/**
 * 实现类
 */
class MercedesCar extends AbstractCar {

    @Override
    public void color() {
        System.out.println("red");
    }
}
