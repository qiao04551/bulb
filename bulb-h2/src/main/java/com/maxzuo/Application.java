package com.maxzuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 使用H2数据库
 * <p>
 * Created by zfh on 2019/08/01
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        try {
            DataSource dataSource = context.getBean(DataSource.class);
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            // 建表
            statement.execute("CREATE TABLE IF NOT EXISTS sc_user(id INTEGER AUTO_INCREMENT PRIMARY KEY,name VARCHAR(30), age INTEGER)");

            // 新增数据
            PreparedStatement savePreparedStatement = connection.prepareStatement("INSERT INTO sc_user(`name`, age) VALUES(?, ?)");
            savePreparedStatement.setString(1, "dazuo");
            savePreparedStatement.setInt(2, 23);
            savePreparedStatement.executeUpdate();

            // 查询数据
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sc_user WHERE id = ?");
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
