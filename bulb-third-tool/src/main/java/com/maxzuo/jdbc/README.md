## Mysql JDBC

JDBC 全称Java Database Connectivity（java数据库连接），提供了一种与平台无关的用于执行SQL语句的标准Java API，可以
方便实现多种关系型数据库的统一操作。

> **使用示例**

```
// 1.加载MySQL驱动程序（反射机制）
Class.forName("com.mysql.jdbc.Driver");
// 2.获得数据库连接
String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8&useSSL=false";
// &字符转义后：&amp;
Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
// 3.通过连接，创建Statement对象
Statement stmt = conn.createStatement();
// 4.使用Statement执行SQL语句，返回ResultSet结果集对象
ResultSet rs = stmt.executeQuery("select * from product");
// 5.从结果集中提取数据
while (rs.next()){
  System.out.println(rs.getString("name"));
}
// 6.清理环境，关闭所有数据库资源，先打开的后关闭。
rs.close();
stmt.close();
conn.close();
```


> **数据库连接池**

dbcp（spring组织推荐使用），c3p0（Hibernate推荐使用）、druid（阿里巴巴开源的连接池）
