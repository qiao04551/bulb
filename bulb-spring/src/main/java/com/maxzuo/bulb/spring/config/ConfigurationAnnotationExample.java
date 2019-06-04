package com.maxzuo.bulb.spring.config;

import com.maxzuo.bulb.spring.model.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 测试@Configuration注解
 * <p>
 * Created by zfh on 2019/04/04
 */
@Configuration
public class ConfigurationAnnotationExample {

    /**
     * <p> @Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>
     * <p> 和Spring-context.xml中配置的Bean没有冲突，可以互相调用。
     */
    public ConfigurationAnnotationExample () {
        System.out.println("ConfigurationAnnotationExample 初始化 ...");
    }

    /**
     * 方法名 对应着 <bean>标签的 id属性；默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域；
     * <pre>
     *  示例：
     *    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
     *    Token token = context.getBean("getToken", Token.class);
     * </pre>
     */
    @Bean
    @Scope("singleton")
    public Token getToken () {
        return new Token();
    }
}
