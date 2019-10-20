## Apache Shiro 

Apache Shiro是Java的一个安全框架。目前，使用Apache Shiro的人越来越多，因为它相当简单，对比Spring Security，可能没有
Spring Security做的功能强大，但是在实际工作时可能并不需要那么复杂的东西，所以使用小而简单的Shiro就足够了。

### 基本功能点

![基本功能点](./doc/feature.png)

- **Authentication**：身份认证/登录，验证用户是不是拥有相应的身份；

- **Authorization**：授权，即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情，常见的如：验证某个
用户是否拥有某个角色。或者细粒度的验证某个用户对某个资源是否具有某个权限；

- **Session Manager**：会话管理，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中。

- **Cryptography**：加密，保护数据的安全性，如密码加密存储到数据库，而不是明文存储；

- **Web Support**：Web支持，可以非常容易的集成到Web环境；

- **Caching**：缓存，比如用户登录后，其用户信息、拥有的角色/权限不必每次去查，这样可以提高效率；

- **Concurrency：shiro**：shiro支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去；

- **Testing**：提供测试支持；

- **Run As**：允许一个用户假装为另一个用户（如果他们允许）的身份进行访问；

- **Remember Me**：记住我，这个是非常常见的功能，即一次登录后，下次再来的话不用登录了。


### Shiro核心架构概念

![Shiro核心架构](./doc/architecture.png)

- **Subject**：主体，可以看到主体可以是任何可以与应用交互的“用户”

- **SecurityManager**：所有具体的交互都通过SecurityManager进行控制；它管理着所有Subject、且负责进行认证和授权、
及会话、缓存的管理。

- **Authenticator**：认证器，负责主体认证的，这是一个扩展点，如果用户觉得Shiro默认的不好，可以自定义实现；其需要
认证策略（Authentication Strategy），即什么情况下算用户认证通过了；

- **Authrizer**：授权器，或者访问控制器，用来决定主体是否有权限进行相应的操作；即控制着用户能访问应用中的哪些功能。

- **SessionManager**：如果写过Servlet就应该知道Session的概念，Session呢需要有人去管理它的生命周期，这个组件就是
SessionManager；而Shiro并不仅仅可以用在Web环境，也可以用在如普通的JavaSE环境、EJB等环境；所有呢，Shiro就抽象了一个
自己的Session来管理主体与应用之间交互的数据；这样的话，就可以实现自己的分布式会话（如把数据放到Redis服务器）

- **SessionDAO**：DAO大家都用过，数据访问对象，用于会话的CRUD，比如我们想把Session保存到数据库，那么可以实现自己的
SessionDAO，通过如JDBC写到数据库；比如想把Session放到Memcached中，可以实现自己的Memcached SessionDAO；另外
SessionDAO中可以使用Cache进行缓存，以提高性能。

- **CacheManager**：缓存控制器，来管理如用户、角色、权限等的缓存的；因为这些数据基本上很少去改变，放到缓存中后可以提
高访问的性能。

- **Cryptography**：密码模块，Shiro提高了一些常见的加密组件用于如密码加密/解密的。

- **Realm**：可以有1个或多个Realm，可以认为是安全实体数据源，即用于获取安全实体的；可以是JDBC实现，也可以是LDAP实现，
或者内存实现等等；由用户提供；注意：Shiro不知道你的用户/权限存储在哪及以何种格式存储；所以我们一般在应用中都需要实现自
己的Realm。

### 参考文献

1.[Shiro 官方文档](http://shiro.apache.org/reference.html)