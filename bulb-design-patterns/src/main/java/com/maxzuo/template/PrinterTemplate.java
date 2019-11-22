package com.maxzuo.template;

/**
 * 打印模板
 * <p>
 * Created by zfh on 2019/11/22
 */
public abstract class PrinterTemplate {

    /**
     * 设置名称
     */
    abstract void settingName();

    /**
     * 定义组件
     */
    abstract void defineModule();

    /**
     * 保存
     */
    abstract void save();

    /**
     * 获取模板
     */
    public final void getTemplate () {
        settingName();
        defineModule();
        save();
    }
}
