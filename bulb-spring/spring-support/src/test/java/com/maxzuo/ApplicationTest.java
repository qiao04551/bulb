package com.maxzuo;

import com.maxzuo.support.BulbSpringPeople;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Schema扩展机制
 * <pre>
 *   1.创建一个XML Schema文件,描述自定义的合法构建模块,也就是xsd文件。
 *   2.自定义处理器类,并实现NamespaceHandler接口。
 *   3.自定义一个或者多个解析器,实现BeanDefinitionParser接口(关键部分)。
 *   4.注册上面的组建到Spring IOC容器。
 * </pre>
 * Created by zfh on 2020/01/06
 */
public class ApplicationTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        BulbSpringPeople people = (BulbSpringPeople) context.getBean("beanId");
        System.out.println(people.getId());
    }
}
