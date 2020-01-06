package com.maxzuo.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 自定义处理器类
 * <p>
 * Created by zfh on 2020/01/06
 */
public class BulbNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("people", new BulbBeanDefinitionParser());
    }
}
