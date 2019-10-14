## Spring-Security + JWT 单点登录，权限校验

使用 Spring Security 实现 JWT 身份认证的 Demo

### Spring Security介绍

> **1.简介**

一个能够为基于Spring的企业应用系统提供声明式的安全訪问控制解决方式的安全框架（简单说是对访问权限进行控制嘛），应用的安全性包括用户
认证（Authentication）和用户授权（Authorization）两个部分。用户认证指的是验证某个用户是否为系统中的合法主体，也就是说用户能否访
问该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认证过程。用户授权指的是验证某个用户是否有权限执行某
个操作。在一个系统中，不同用户所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以进行修改。一般来说，系
统会为不同的用户分配不同的角色，而每个角色则对应一系列的权限。   spring security的主要核心功能为 认证和授权，所有的架构也是基于
这两个核心功能去实现的。

> **2.框架原理**

众所周知 想要对对Web资源进行保护，最好的办法莫过于Filter，要想对方法调用进行保护，最好的办法莫过于AOP。所以springSecurity在我们
进行用户认证以及授予权限的时候，通过各种各样的拦截器来控制权限的访问，从而实现安全。

主要的过滤器
```
WebAsyncManagerIntegrationFilter 
SecurityContextPersistenceFilter 
HeaderWriterFilter 
CorsFilter 
LogoutFilter
RequestCacheAwareFilter
SecurityContextHolderAwareRequestFilter
AnonymousAuthenticationFilter
SessionManagementFilter
ExceptionTranslationFilter
FilterSecurityInterceptor
UsernamePasswordAuthenticationFilter
BasicAuthenticationFilter
```

> **3.框架的核心组件**

```
SecurityContextHolder：  提供对SecurityContext的访问
SecurityContext,：       持有Authentication对象和其他可能需要的信息
AuthenticationManager：  其中可以包含多个AuthenticationProvider
ProviderManager：        对象为AuthenticationManager接口的实现类
AuthenticationProvider： 主要用来进行认证操作的类 调用其中的authenticate()方法去进行认证操作
Authentication：         Spring Security方式的认证主体
GrantedAuthority：       对认证主题的应用层面的授权，含当前用户的权限信息，通常使用角色表示
UserDetails：            构建Authentication对象必须的信息，可以自定义，可能需要访问DB得到
UserDetailsService：     通过username构建UserDetails对象，通过loadUserByUsername根据userName获取UserDetail对象 
                        （可以在这里基于自身业务进行自定义的实现  如通过数据库，xml,缓存获取等）     
```

### JWT 的原理

JWT 的原理是，服务器认证以后，生成一个 JSON 对象，发回给用户，就像下面这样。

```
{
  "姓名": "张三",
  "角色": "管理员",
  "到期时间": "2018年7月1日0点0分"
}
```

以后，用户与服务端通信的时候，都要发回这个 JSON 对象。服务器完全只靠这个对象认定用户身份。为了防止用户篡改数据，服务器在生成这个对象
的时候，会加上签名（详见后文）。

服务器就不保存任何 session 数据了，也就是说，服务器变成无状态了，从而比较容易实现扩展。


### JWT的数据结构

一个 JWT token 是一个字符串，它由三部分组成，头部、负载与签名，中间用 . 分隔，例如：`xxxxx.yyyyy.zzzzz`

- **header**

Header 部分是一个 JSON 对象，描述 JWT 的元数据，通常是下面的样子。

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```
上面代码中，`alg`属性表示签名的算法（algorithm），默认是 HMAC SHA256（写成 HS256）；`typ`属性表示这个令牌（token）的类型（type），
JWT 令牌统一写为`JWT`。

最后，将上面的 JSON 对象使用 Base64URL 算法（详见后文）转成字符串，即 xxxx

- **载荷（Payload）**

Payload 部分也是一个 JSON 对象，用来存放实际需要传递的数据。JWT 规定了7个官方字段，供选用。

```
{
  "sub": "1",                                   # 主题
  "iss": "http://localhost:8000/auth/login",    # 签发人
  "iat": 1451888119,                            # 签发时间
  "exp": 1454516119,                            # 过期时间
  "nbf": 1451888119,                            # 生效时间，在此之前是无效的
  "jti": "37c107e4609ddbcc9c096ea5ee76c667",    # 编号
  "aud": "dev"                                  # 受众
}
```

除了官方字段，你还可以在这个部分定义私有字段，下面就是一个例子。

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```
注意，JWT 默认是不加密的，任何人都可以读到，所以不要把秘密信息放在这个部分。

这个 JSON 对象也要使用 Base64URL 算法转成字符串，即 yyyy

> **Signature**

Signature 部分是对前两部分的签名，防止数据篡改。

首先，需要指定一个密钥（secret）。这个密钥只有服务器才知道，不能泄露给用户。然后，使用 Header 里面指定的签名算法（默认是 HMAC SHA256），
按照下面的公式产生签名。

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```

算出签名以后，把 Header、Payload、Signature 三个部分拼成一个字符串，每个部分之间用"点"（`.`）分隔，就可以返回给用户。

> **Base64URL**

前面提到，Header 和 Payload 串型化的算法是 Base64URL。这个算法跟 Base64 算法基本类似，但有一些小的不同。

JWT 作为一个令牌（token），有些场合可能会放到 URL（比如 api.example.com/?token=xxx）。Base64 有三个字符`+`、`/`和`=`，
在 URL 里面有特殊含义，所以要被替换掉：`=`被省略、`+`替换成`-`，`/`替换成`_`。这就是 Base64URL 算法。


### JWT 的使用方式

客户端收到服务器返回的 JWT，可以储存在 Cookie 里面，也可以储存在 localStorage。

此后，客户端每次与服务器通信，都要带上这个 JWT。你可以把它放在 Cookie 里面自动发送，但是这样不能跨域，所以更好的做法是放在 HTTP 
请求的头信息`Authorization`字段里面。

```
Authorization: Bearer <token>
```

另一种做法是，跨域的时候，JWT 就放在 POST 请求的数据体里面。


### Token 认证常见问题以及解决办法

> **1.注销登录等场景下 token 还有效**

与之类似的具体相关场景有：

1.退出登录;

2.修改密码;

3.服务端修改了某个用户具有的权限或者角色；

4.用户的帐户被删除/暂停。

5.用户由管理员注销；

这个问题不存在于 Session 认证方式中，因为在 Session 认证方式中，遇到这种情况的话服务端删除对应的 Session 记录即可。但是，使用 
token 认证的方式就不好解决了。我们也说过了，token 一旦派发出去，如果后端不增加其他逻辑的话，它在失效之前都是有效的。那么，我们
如何解决这个问题呢？查阅了很多资料，总结了下面几种方案：

- **将 token 存入内存数据库**：将 token 存入 DB 中，redis 内存数据库在这里是是不错的选择。如果需要让某个 token 失效就直接从 
redis 中删除这个 token 即可。但是，这样会导致每次使用 token 发送请求都要先从 DB 中查询 token 是否存在的步骤，而且违背了 JWT 
的无状态原则。

- **黑名单机制**：和上面的方式类似，使用内存数据库比如 redis 维护一个黑名单，如果想让某个 token 失效的话就直接将这个 token 加
入到 黑名单 即可。然后，每次使用 token 进行请求的话都会先判断这个 token 是否存在于黑名单中。

- **修改密钥**: 我们为每个用户都创建一个专属密钥，如果我们想让某个 token 失效，我们直接修改对应用户的密钥即可。但是，
这样相比于前两种引入内存数据库带来了危害更大，比如：️1）如果服务是分布式的，则每次发出新的 token 时都必须在多台机器同步密钥。为此，
你需要将必须将机密存储在数据库或其他外部服务中，这样和 Session 认证就没太大区别了。2）如果用户同时在两个浏览器打开系统，或者在
手机端也打开了系统，如果它从一个地方将账号退出，那么其他地方都要重新进行登录，这是不可取的。

- **保持令牌的有效期限短并经常轮换**：很简单的一种方式。但是，会导致用户登录状态不会被持久记录，而且需要用户经常登录。

对于修改密码后 token 还有效问题的解决还是比较容易的，说一种我觉得比较好的方式：使用用户的密码的哈希值对 token 进行签名。因此，
如果密码更改，则任何先前的令牌将自动无法验证。

> **2.token 的续签问题**

token 有效期一般都建议设置的不太长，那么 token 过期后如何认证，如何实现动态刷新 token，避免用户经常需要重新登录？

我们先来看看在 Session 认证中一般的做法：假如 session 的有效期30分钟，如果 30 分钟内用户有访问，就把 session 有效期被延长30分钟。

1.**类似于 Session 认证中的做法**：这种方案满足于大部分场景。假设服务端给的 token 有效期设置为30分钟，服务端每次进行校验时，
如果发现 token 的有效期马上快过期了，服务端就重新生成 token 给客户端。客户端每次请求都检查新旧token，如果不一致，则更新本地
的token。这种做法的问题是仅仅在快过期的时候请求才会更新 token ,对客户端不是很友好。

2.**每次请求都返回新 token**: 这种方案的的思路很简单，但是，很明显，开销会比较大。

3.**token 有效期设置到半夜**：这种方案是一种折衷的方案，保证了大部分用户白天可以正常登录，适用于对安全性要求不高的系统。

4.**用户登录返回两个 token**：第一个是 acessToken ，它的过期时间 token 本身的过期时间比如半个小时，另外一个是 refreshToken 
它的过期时间更长一点比如为1天。客户端登录后，将 accessToken和refreshToken 保存在本地，每次访问将 accessToken 传给服务端。
服务端校验 accessToken 的有效性，如果过期的话，就将 refreshToken 传给服务端。如果有效，服务端就生成新的 accessToken 给客户端。
否则，客户端就重新登录即可。该方案的不足是：1）️需要客户端来配合；2）用户注销的时候需要同时保证两个 token 都无效；3）重新请求
获取 token 的过程中会有短暂 token 不可用的情况（可以通过在客户端设置定时器，当accessToken 快过期的时候，提前去通过 refreshToken 
获取新的accessToken）

### 参考文献

1.[JSON Web Token 入门教程](https://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)
