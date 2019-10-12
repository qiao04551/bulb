package com.maxzuo.bulb;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

/**
 * Spring-Data MongoDB 使用示例
 * <p>
 * Created by zfh on 2019/05/25
 */
public class SpringDataMongodbExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-mongo.xml");
        MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);

        UserEntity user = new UserEntity();
        user.setId(1);
        user.setName("dazuo");
        user.setAge(23);
        user.setCreateTime(new Date());
        // 添加数据
        mongoTemplate.save(user);

        // 查询数据
        UserEntity userInfo = mongoTemplate.findOne(Query.query(Criteria.where("name").is("dazuo")), UserEntity.class);
        System.out.println("userInfo: " + userInfo);
    }
}
