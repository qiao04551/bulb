package com.maxzuo.bulb.analysis.annotation;

import com.maxzuo.bulb.spring.annotation.bean.MiniContainer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring注解使用示例
 * <p>
 * Created by zfh on 2019/09/19
 */
public class AnnotationUseExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.maxzuo.bulb.spring.annotation");
        MiniContainer container = applicationContext.getBean(MiniContainer.class);
        container.init();
        container.destroy();
    }
}
