package com.maxzuo.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Druid 数据库连接池 使用
 * Created by zfh on 2019/09/30
 */
public class DruidUseExample {

    public static void main(String[] args) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://139.xx.xx.xx:8066/mooc?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true");
        dataSource.setPassword("xx");
        dataSource.setUsername("xx");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(33);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(25200000);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);
        /// 监控数据库
        // dataSource.setFilters("mergeStat");

        try {
            dataSource.init();

            DruidPooledConnection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sc_base_coupon WHERE id = 100");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
