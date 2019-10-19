package com.maxzuo.mybatis;

import org.apache.ibatis.session.SqlSessionManager;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

/**
 * SqlSessionManager的使用
 * <p>
 * Created by zfh on 2019/10/19
 */
public class SqlSessionManagerTest {

    public static void main(String[] args) {
        InputStream inputStream = SqlSessionManagerTest.class.getResourceAsStream("/mybatis-config.xml");
        SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(inputStream);

        sqlSessionManager.startManagedSession();
        Connection connection = sqlSessionManager.getConnection();

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("UPDATE sc_user SET username = 'dazuo' WHERE id = 1");

            sqlSessionManager.rollback(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSessionManager.close();
        }
    }
}
