## Spring DeferredResult

DeferredResult字面意思就是推迟结果，是在servlet3.0以后引入了异步请求之后，spring封装了一下提供了相应的支持，也是一个很老的特性了。
DeferredResult可以允许容器线程快速释放以便可以接受更多的请求提升吞吐量，让真正的业务逻辑在其他的工作线程中去完成。

### Demo体验

通过postman工具发送请求http://localhost:8080/watch/mynamespace，请求会挂起，60秒后，DeferredResult超时，客户端正常收到了
304状态码，表明在这个期间配置没有变更过。

然后我们在模拟配置变更的情况，再次发起请求http://localhost:8080/watch/mynamespace，等待个10秒钟（不要超过60秒），然后调用
http://localhost:8080/publish/mynamespace,发布配置变更。

### 补充：

这里我们用了一个MultiMap来存放所有轮训的请求，Key对应的是namespace,value对应的是所有watch这个namespace变更的异步请求DeferredResult，
需要注意的是：在DeferredResult完成的时候记得移除MultiMap中相应的key,避免内存溢出请求。

采用这种长轮询的好处是，相比一直循环请求服务器，实例一多的话会对服务器产生很大的压力，http长轮询的方式会在服务器变更的时候主动推送给
客户端，其他时间客户端是挂起请求的，这样同时满足了性能和实时性。
