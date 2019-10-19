## JPA

JPA全称Java Persistence API，可以通过注解或者XML描述【对象-关系表】之间的映射关系，并将实体对象持久化到数据库中。

- JPA是规范：JPA本质上就是一种ORM规范，不是ORM框架——因为JPA并未提供ORM实现，它只是制定了一些规范，
  提供了一些编程的API接口，但具体实现则由ORM厂商提供实现。
  
- Hibernate是实现：Hibernate除了作为ORM框架之外，他也是一种JPA实现。

- Spring data jpa是spring提供的一套简化JPA开发的框架，可以理解为JPA规范的再次封装对象，底层还是使用了
  Hibernate的JPA技术实现。
  
### Spring Data

Spring的一个子项目。用于简化数据库的访问，支持NoSQL和关系数据存储。

> Spring Data项目支持的NoSQL存储：

- MongoDB（文档数据库）
- Neo4j（图数据库）
- Redis（键/值存储）
- Hbase（列族数据库）
- SpringData所支持的关系数据存储技术：
  - JDBC
  - JPA

> Spring Data JPA

致力于减少数据访问层 (DAO) 的开发量. 开发者唯一要做的，就只是声明持久层的接口，其他都交给 Spring Data JPA 来完成！

