package com.maxzuo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 试验H2内存数据库
 * <p>
 * Created by zfh on 2019/08/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testH2() {
        try {
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
