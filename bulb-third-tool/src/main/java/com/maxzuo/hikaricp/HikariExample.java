package com.maxzuo.hikaricp;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * HikariCP 使用示例
 * <p>
 *   项目地址：https://github.com/brettwooldridge/HikariCP
 *
 * Created by zfh on 2019/12/01
 */
public class HikariExample {

    public static void main(String[] args) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mooc");
        ds.setUsername("root");
        ds.setPassword("123456");

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sc_user WHERE id = 1");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                System.out.println(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
