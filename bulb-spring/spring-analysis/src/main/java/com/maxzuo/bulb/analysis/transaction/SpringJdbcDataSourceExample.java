package com.maxzuo.bulb.analysis.transaction;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring事务管理
 * <p>
 * Created by zfh on 2019/09/19
 */
public class SpringJdbcDataSourceExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-jdbc-datasource.xml");
        try {
            // DataSource dataSource = applicationContext.getBean(DataSource.class);
            // Connection connection = dataSource.getConnection();
            // Statement statement = connection.createStatement();
            // ResultSet resultSet = statement.executeQuery("SELECT * FROM sc_user WHERE id = 1");
            // System.out.println(resultSet);

            UserService userService = applicationContext.getBean(UserService.class);
            userService.updateByPrimary();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
