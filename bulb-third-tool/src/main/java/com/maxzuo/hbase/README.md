## HBase Client API的使用

> **数据库命令**

```
$ ./hbase shell						                        # 启动Shell
$ create 'user', 'info'					                    # 创建名为 user 的表，含一个列蔟 info
$ put 'user', 'row1', 'info:name' , 'dazuo' 			    # 在 user 表的 row1 行中的'info.name'列对应的单元格插入字节数组'dazuo'
$ get 'user', 'row1'						                # 读取 user 表 row1 行的内容
$ deleteall 'user', 'row1'					                # 删除 user 表 row1 行的内容
$ delete 'user', 'row1', 'info:name'				        # 删除 user 表 row1 行中的'info:name'列的内容
$ scan 'user'						                        # 读取 user 表全部内容
$ count 'user'						                        # 统计表总行数
$ incr 'user', 'row2', 'info:age', 2				        # 计数器，incr填充整型，put 操作会转换成Bytes.toBytes()
$ get 'user', 'row1', {COLUMN=> 'info:name', VERSIONS => 2}	# 获取user表 row1行 info:name列，最近两个版本的数据（VERSIONS 表示版本数）
```

> **命令空间**

```
$ list_namespace				            # 列出所有命名空间
$ create_namespace 'ns1'			        # 新建命名空间
$ drop_namespace 'ns1'			            # 删除命名空间
$ alter_namespace 'ns', {METHOD => 'set', 'PROPERTY_NAME' => 'PROPERTY_VALUE'} 	# 修改命名空间

其中，有两个特殊的命名空间：
  hbase - 系统命名空间，用于包含hbase内部表
  default - 没有显式指定名称空间的表将自动归入此名称空间
``` 

> **表命令**

```
$ list					                    # 列出所有表
$ list_namespace_tables 'ns1'		        # 列出命名空间下所有的表
$ create 'ns1:t1', 'cf1'			        # 在'ns1'命名空间下建表
$ exists 'user'				                # 检查表存在
$ disable 'ns1:t1'				            # 禁用表
$ drop 'ns1:t1'				                # 删除表（删除之前，需要先disable表）
$ enable 'ns1:t1'				            # 恢复表可用
$ describe 'user'				            # 查看表结构
$ truncate 'user'				            # 重新创建指定的表
$ alter 't1', 'info'				        # 添加列族
$ alter 't1', NAME => 'f1', VERSIONS => 3 	# 修改列族
$ alter 'user', 'delete' => 'info'		    # 添加列蔟
```

> **建表语句**

```hbh
create_namespace 'pb'

create 'pb:user', {NAME => 'b', VERSIONS => '3', TTL => '2147483647', 'BLOOMFILTER' => 'ROW'}, {NAME => 'o', VERSIONS => '3', TTL => '2147483647', 'BLOOMFILTER' => 'ROW'}
create 'pb:pass', {NAME => 'i', VERSIONS => '3', TTL => '2147483647', 'BLOOMFILTER' => 'ROW'}
create 'pb:passtemplate', {NAME => 'b', VERSIONS => '3', TTL => '2147483647', 'BLOOMFILTER' => 'ROW'}, {NAME => 'c', VERSIONS => '3', TTL => '2147483647', 'BLOOMFILTER' => 'ROW'}
create 'pb:feedback', {NAME => 'i', VERSIONS => '3', TTL => '2147483647', 'BLOOMFILTER' => 'ROW'}
```