package com.maxzuo.sharding.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Sharding-jdbc数据分片
 * <p>
 * Created by zfh on 2019/04/24
 */
public class ShardingJDBCExample {
    /*
        Maven依赖：
            <dependency>
                <groupId>org.apache.shardingsphere</groupId>
                <artifactId>sharding-jdbc-core</artifactId>
                <version>4.0.0-RC1</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.1.10</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.46</version>
            </dependency>
     */

    public static void main(String[] args) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(16);

        // 数据源一
        DruidDataSource dataSource0 = new DruidDataSource();
        dataSource0.setUrl("jdbc:mysql://192.168.3.192:3306/ds0?useUnicode=true&characterEncoding=utf8");
        dataSource0.setUsername("root");
        dataSource0.setPassword("123456");
        dataSourceMap.put("ds0", dataSource0);

        // 数据源二
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setUrl("jdbc:mysql://192.168.3.192:3306/ds1?useUnicode=true&characterEncoding=utf8");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("ds1", dataSource1);

        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "ds${0..1}.t_order${0..1}");

        // 配置分库 + 分表策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        try {
            // 获取数据源对象
            DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
            Connection connection = dataSource.getConnection();

            //writeTable(connection);

            readTable(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写数据
     */
    private static void writeTable (Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_order (id, user_id, order_id) VALUES (?, ?, ?)");

            // 0号库0号表
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);
            preparedStatement.execute();

            // 0号库1号表
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 1);
            preparedStatement.execute();

            // 1号库0号表
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 0);
            preparedStatement.execute();

            // 1号库1号表
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 1);
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("写表异常");
            e.printStackTrace();
        }
    }

    /**
     * 读数据
     */
    private static void readTable (Connection connection) {
        try {
            // 分页修正：https://shardingsphere.apache.org/document/current/cn/features/sharding/principle/rewrite/
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_order WHERE user_id = ? ORDER BY id DESC LIMIT 1, 2");
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            // sharding-jdbc不支持结果集指针位置判断
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Map<String, Object>> dataList = new ArrayList<>(10);
            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    rowData.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                dataList.add(rowData);
            }
            System.out.println("dataList: " + dataList);
        } catch (Exception e) {
            System.out.println("读数据异常");
            e.printStackTrace();
        }
    }
}
