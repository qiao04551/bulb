## 使用binlog 恢复 where 语句数据（以下恢复方式，日志格式必须是Row）

### 提取还原SQL

> 一、首先要确认你的数据库日志格式是row，root用户登陆数据库后，执行命令：

```
show variables like '%binlog_format%'; 
```
结果：binlog_format = ROW

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

- 1.查看binlog内容，将二进制文件转换成文本文件查看

```
mysqlbinlog --base64-output=decode-rows -v -d mooc --start-datetime='2019-10-09 10:57:00' --stop-datetime='2019-10-09 10:57:59' ./mysql-bin.000001 > ./recover_ivr.sql
```

- 2.找到误操作的update 语句，记录下sql上面 # at 开头后面的数字（通过上一步获取）

```
mysqlbinlog --no-defaults -v -v --base64-output=DECODE-ROWS ./mysql-bin.019582 | sed -n '/# at 364855768/,/COMMIT/p' > ./update.sql
```

- 3.至此，我们已经拿到了需要还原的sql语句，根据导出的sql语句进行sed命令替换，还原到修改之前sql语句，命令如下：

```
sed '/WHERE/{:a;N;/SET/!ba;s/\([^\n]*\)\n\(.*\)\n\(.*\)/\3\n\2\n\1/}' update.sql | sed 's/### //g;s/\/\*.*/,/g' | sed  /@3/s/,//g | sed '/WHERE/{:a;N;/@3/!ba;s/,/AND/g};s/#.*//g;s/COMMIT,//g' | sed '/^$/d'  >  rollback.sql
```

### 参考资料

- [MySQL执行update 语句忘加where条件后使用mysqlbinlog搭配sed命令完美还原](https://my.oschina.net/u/3496194/blog/1532177)

- [MySQL - binlog日志简介及数据恢复](https://docs.qq.com/doc/DWkl4bk9QZGZyV3Rk)