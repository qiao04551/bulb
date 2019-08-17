package com.maxzuo.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * JDBC 使用详解
 * <p>
 * Created by zfh on 2019/08/17
 */
public class JDBCSyntaxExample {

    private Connection conn;

    @Before
    public void initConnect () {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/mooc?useUnicode=true";
            conn = DriverManager.getConnection(url, "root", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Statement
     */
    @Test
    public void testStatement () {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sc_user WHERE id = 1");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建一个PreparedStatement对象，用于向数据库发送参数化SQL语句。
     *
     * 带或不带参数的SQL语句可以预编译并存储在PreparedStatement对象中。然后可以使用此对象有效地多次执行此语句。
     */
    @Test
    public void testPreparedStatement () {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sc_user WHERE id = ?");
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            // 光标下移一行（遍历结果集）
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批处理
     */
    @Test
    public void testStatementBatch () {
        try {
            Statement statement = conn.createStatement();
            String sql1 = "INSERT INTO sc_user(username, password) VALUES('dazuo', '1234')";
            String sql2 = "INSERT INTO sc_user(username, password) VALUES('dazuo', '12345')";

            statement.addBatch(sql1);
            statement.addBatch(sql2);

            statement.executeBatch();
            statement.clearBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用事务
     */
    @Test
    public void testTransaction () {
        try {
            // 开启事务
            conn.setAutoCommit(false);

            String sql = "UPDATE sc_user SET password = '222' WHERE id = 1";
            Statement statement = conn.createStatement();
            statement.execute(sql);

            conn.commit();
            // conn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试InnoDB 行锁
     * <pre>
     *   共享锁（S）：又称读锁。允许一个事务去读一行，阻塞其他事务获得相同数据集的排他锁。
     * 	 排他锁（X）：又称写锁。允许获取排他锁的事务更新数据，阻塞其他事务取得相同的数据集共享读锁和排他写锁。
     *
     *   意向锁是InnoDB自动加的，不需用户干预。对于UPDATE、DELETE和INSERT语句，InnoDB会自动给涉及及数据集加排他锁(X)，对于普通SELECT语句，InnoDB不会加任何锁。
     * </pre>
     */
    @Test
    public void testInnoDBLock () {
        try {
            conn.setAutoCommit(false);

            /// 共享锁
            // String sql = "SELECT * FROM sc_user WHERE id = 1 LOCK IN SHARE MODE";

            // 排他锁
            String sql = "SELECT * FROM sc_user WHERE id = 1 FOR UPDATE";
            Statement statement = conn.createStatement();
            statement.execute(sql);

            Thread.sleep(10000);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置事务隔离级别
     * <pre>
     *   READ_UNCOMMITTED：最低的隔离等级，允许别的事务去读取这个事务未提交之前的数据
     *   READ_COMMITTED：被读取的数据可以被其他事务修改，这样可能导致不可重复读。也就是说，事务读取的时候获取读锁，但是在读完
     *                   之后立即释放(不需要等事务结束)，而写锁则是事务提交之后才释放，释放读锁之后，就可能被其他事务修改数据。
     *   REPEATABLE_READ：所有被 select 获取的数据都不能被修改，这样就可以避免一个事务前后读取数据不一致的情况。
     *   SERIALIZABLE：最高的隔离等级，所有事务一个接着一个的执行。顾名思义是对于同一行记录，“写”会加“写锁”，“读”会加“读锁”。
     *                 当出现读写锁冲突的时候，后访问的事务必须等前一个事务执行完成，才能继续执行。
     * </pre>
     */
    @Test
    public void testTxIsolationRepeatableRead () {
        try {
            // 没有加锁
            // 对当前事务有效
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM sc_user WHERE id = 1";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("读第一次：" + resultSet.getString("password"));
            }

            Thread.sleep(5000);

            // 读第二次
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("读第二次：" + resultSet.getString("password"));
            }

            conn.commit();

            Thread.sleep(5000);

            // 读第三次
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("读第三次：" + resultSet.getString("password"));
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTxIsolationsSerializable () {
        try {
            // 加了锁
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            String sql = "SELECT * FROM sc_user WHERE id = 1";
            Statement statement = conn.createStatement();
            statement.executeQuery(sql);

            Thread.sleep(5000);

            conn.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 使用数据库连接池（druid）
     */
    @Test
    public void testDatabaseConnectPool () {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mooc?useUnicode=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        try {
            DruidPooledConnection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM sc_user WHERE id = 1");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
