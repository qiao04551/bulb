package com.maxzuo.template;

/**
 * 模板方法模式
 * <p>
 *     一次性实现一个算法的不变的部分，并将可变的行为留给子类来实现；
 *     各子类中公共的行为应被提取出来并集中到一个公共父类中以避免代码重复；
 *     控制子类的扩展
 * </p>
 * Created by zfh on 2019/11/22
 */
public class Main {

    public static void main(String[] args) {
        PrinterTemplate caterTemp = new CaterPrinterTemplate();
        caterTemp.getTemplate();

        PrinterTemplate officeTemp = new OfficePrinterTemplate();
        officeTemp.getTemplate();
    }
}
