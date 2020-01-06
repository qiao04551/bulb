package com.maxzuo.schema;

import com.maxzuo.support.BulbSpringPeople;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * 定义解析器
 * <p>
 * Created by zfh on 2020/01/06
 */
public class BulbBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return BulbSpringPeople.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");

        if (StringUtils.hasText(id)) {
            builder.addPropertyValue("id", id);
        }
        if (StringUtils.hasText("name")) {
            builder.addPropertyValue("name", name);
        }
        if (StringUtils.hasText("age")) {
            builder.addPropertyValue("age", Integer.valueOf(age));
        }
    }
}
