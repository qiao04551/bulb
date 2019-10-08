## 使用binlog恢复数据

> 一、首先要确认你的数据库日志格式是row，root用户登陆数据库后，执行命令：

```
show variables like '%binlog_format%'; 
```
结果：binlog_format = ROW

mysql数据库日志格式有三种；Statement，Mixed，Row，这里我不做过多介绍，这里需要通过日志恢复数据的日志格式必须是Row。

> 二、查看是否开启了binlog

```
show variables like '%log_bin%';
```
我们可以看到log_bin的值为ON，开启状态，OK，我们数据库可以还原。

> 三、查看log文件

```
show  master logs;
```

看到末尾数值最大那个文件就是了，OK，我们先来找到它，如果你不知道文件位置，简单粗暴点:

```
find  / -name 'mysqld-bin.000057';
```

> 四、mysqlbinlog

- 1.查看binlog内容，需要先将二进制文件转换成文本文件再查看(也可以加入start-pos、end-pos、start-date、end-date条件)

```
mysqlbinlog /usrl/local/mysql/data/mysql-bin.000001 > /tmp/log.txt
```

- 2.针对单库导出文件

```
mysqlbinlog --base64-output=decode-rows -d sc_settle --start-datetime='2019-10-08 16:21:16' --stop-datetime='2019-10-08 16:21:18' ./mysql-bin.019582 > ./recover_ivr.sql
```

- 3.找到误操作的update 语句，记录下sql上面 # at 开头后面的数字(这个标记应该是事务的行号吧)，OK，继续执行命令

```
mysqlbinlog --no-defaults -v -v --base64-output=DECODE-ROWS ./mysql-bin.019582 | sed -n '/# at 364855768/,/COMMIT/p' > ./update.sql
```

- 4.至此，我们已经拿到了需要还原的sql语句，根据导出的sql语句进行sed命令替换，还原到修改之前sql语句，命令如下：

```
sed '/WHERE/{:a;N;/SET/!ba;s/\([^\n]*\)\n\(.*\)\n\(.*\)/\3\n\2\n\1/}' update.sql | sed 's/### //g;s/\/\*.*/,/g' | sed  /@3/s/,//g | sed '/WHERE/{:a;N;/@3/!ba;s/,/AND/g};s/#.*//g;s/COMMIT,//g' | sed '/^$/d'  >  rollback.sql
```

### 参考资料

- [MySQL执行update 语句忘加where条件后使用mysqlbinlog搭配sed命令完美还原](https://my.oschina.net/u/3496194/blog/1532177)

- [MySQL的biglog文件操作](https://blog.csdn.net/Stubborn_Cow/article/details/48159291)