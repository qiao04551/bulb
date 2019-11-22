package com.maxzuo.template;

/**
 * 办公打印模板
 * <p>
 * Created by zfh on 2019/11/22
 */
public class OfficePrinterTemplate extends PrinterTemplate {

    @Override
    void settingName() {
        System.out.println("OfficePrinterTemplate");
    }

    @Override
    void defineModule() {
        System.out.println("container 8, 9, 10");
    }

    @Override
    void save() {
        System.out.println("save OfficePrinterTemplate");
    }
}
