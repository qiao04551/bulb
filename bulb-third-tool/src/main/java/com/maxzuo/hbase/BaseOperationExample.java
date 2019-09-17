package com.maxzuo.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HBase 基本操作
 * <p>
 * Created by zfh on 2019/09/16
 */
public class BaseOperationExample {

    private Connection connection;

    /**
     * 初始化连接
     */
    @Before
    public void initConnect () {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "47.98.199.80");
        try {
            connection = ConnectionFactory.createConnection(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 集群管理的管理实例
     */
    @Test
    public void testAdminInstance () {
        // 返回用于集群管理的管理实例
        // 检索管理实现来管理HBase集群。返回的Admin不能保证是线程安全的。应该使用thread为每个线程创建一个新实例。
        // 这是一个轻量级操作。不建议使用返回的Admin的池或缓存。调用者负责在返回的Admin实例上调用Admin.close()。
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            // 删除表
            // admin.disableTable(TableName.valueOf("user"));
            // admin.deleteTable(TableName.valueOf("user"));

            // 创建表
            TableDescriptorBuilder.ModifyableTableDescriptor table = new TableDescriptorBuilder.ModifyableTableDescriptor(TableName.valueOf("user"));
            ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor columnFamily = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(Bytes.toBytes("info"));
            table.setColumnFamily(columnFamily);
            admin.createTable(table);

            TableName[] tableNames = admin.listTableNames();
            System.out.println(Arrays.toString(tableNames));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过Rowkey 查询一条数据
     */
    @Test
    public void testQueryDataByRowKey () {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("user"));
            Get get = new Get(Bytes.toBytes("row1"));
            Result result = table.get(get);
            Cell[] cells = result.rawCells();
            for (Cell c : cells) {
                String columnName = Bytes.toString(c.getQualifierArray(), c.getQualifierOffset(), c.getQualifierLength());
                String columnValue = Bytes.toString(c.getValueArray(), c.getValueOffset(), c.getValueLength());
                System.out.println(columnName + ":" + columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过Rowkey 查询多条数据
     */
    @Test
    public void testQueryMultipleData () {
        List<Get> gets = new ArrayList<>();
        gets.add(new Get(Bytes.toBytes("row1")));
        gets.add(new Get(Bytes.toBytes("row2")));
        gets.add(new Get(Bytes.toBytes("row3")));
        // table.get(gets);
    }

    /**
     * 扫描表—设置起始位置/结束位置（通过字符串匹配）
     */
    @Test
    public void testScanTableBetween () {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("user"));
            Scan scan = new Scan();
            // 开区间
            scan.withStartRow(Bytes.toBytes("row0"));
            scan.withStopRow(Bytes.toBytes("row4"));
            ResultScanner scanner = table.getScanner(scan);
            for (Result rs : scanner) {
                System.out.println("rowKey:" + Bytes.toString(rs.getRow()));
                Cell[] cells = rs.rawCells();
                for (Cell c : cells) {
                    String columnName = Bytes.toString(c.getQualifierArray(), c.getQualifierOffset(), c.getQualifierLength());
                    String columnValue = Bytes.toString(c.getValueArray(), c.getValueOffset(), c.getValueLength());
                    System.out.println(columnName + ":" + columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过Rowkey 删除一条数据
     */
    @Test
    public void testDeleteRowData () {
        Delete delete = new Delete("row1".getBytes());
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("user"));
            table.delete(delete);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 添加数据
     */
    @Test
    public void testPutRowData () {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("user"));
            Put put = new Put(Bytes.toBytes("row3"));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("YUAN"));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(22));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("time"), Bytes.toBytes(System.currentTimeMillis()));

            table.put(put);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取多个版本的数据
     */
    @Test
    public void testQueryMutipleVersionData () {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf("user"));
            Get get = new Get(Bytes.toBytes("row1"));
            get.readVersions(2);
            Result result = table.get(get);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                String columnName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                String columnValue = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(columnName + ":" + columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
