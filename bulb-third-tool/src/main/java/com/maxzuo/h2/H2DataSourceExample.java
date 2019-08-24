package com.maxzuo.h2;

import java.sql.*;

/**
 * H2 内存数据库
 * <p>
 * Created by zfh on 2019/08/24
 */
public class H2DataSourceExample {

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:file:./mooc-db", "sa", "sa");
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
