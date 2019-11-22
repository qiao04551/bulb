package com.maxzuo.template;

/**
 * 餐饮模板
 * <p>
 * Created by zfh on 2019/11/22
 */
class CaterPrinterTemplate extends PrinterTemplate {

    @Override
    void settingName() {
        System.out.println("cater printerTemplate");
    }

    @Override
    void defineModule() {
        System.out.println("container 1, 2, 3");
    }

    @Override
    void save() {
        System.out.println("save ...");
    }
}
