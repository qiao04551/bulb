package com.maxzuo.facade;

/**
 * Java外观（Facade）模式
 * <pre>
 *   所谓外观模式就是要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行交互。外观模式提供一个高层次的接口使得子系统更易于使用。
 *
 *   相关角色：
 *     1.外观（Facade）角色：客户端可以调用这个角色的方法。此角色知晓相关的子系统的功能和责任。
 *     2.子系统角色：可以同时有一个或者多个子系统。每一个子系统都不是一个单独的类，而是一个类的集合。每一个子系统都可以被客户端直接调用，或者被外观角色调用。
 *
 *   适用情况：
 *     1.为复杂的子系统提供一个简单的接口；
 *     2.客户程序与抽象类的实现部分之间存在着很大的依赖性；
 *     3.构建一个层次结构的子系统时，适用外观模式定义子系统中每层的入口点。
 * </pre>
 * Created by zfh on 2020/04/14
 */
public class FacadeExample {

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.startup();
        computer.shutdown();
    }
}
