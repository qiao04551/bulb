package com.maxzuo.jpa;

import com.maxzuo.jpa.entity.Person;
import com.maxzuo.jpa.repositorys.PersonRepository;
import com.maxzuo.jpa.service.PersonService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * SpringData-JPA使用示例
 * <p>
 * Created by zfh on 2019/04/21
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        // 通过Dao层
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        Integer result = personRepository.getPersonByNaviteQuery(1);
        System.err.println(result);

        // 通过Service层
        PersonService personService = context.getBean(PersonService.class);
        personService.updateByPrimaryKey(1, "dazuo2");

        // 插入数据
        Person person = new Person();
        person.setUsername("li");
        person.setPassword("li.123");
        person.setCreateTime(new Date());
        person.setUpdateTime(new Date());
        personService.savePerson(person);
    }
}
