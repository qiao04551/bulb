## Apache Httpclient

[Commons HttpClient](http://hc.apache.org/httpclient-3.x/userguide.html)项目现在已经结束，不再开发了。[Apache HttpComponents](http://hc.apache.org/)
项目的HttpClient和HttpCore模块已经取代了它，这两个模块提供了更好的性能和更大的灵活性。

### Apache HttpComponents

- `HttpCore`是一组低层HTTP传输组件，可用于以最小的占用空间构建自定义客户机和服务器端HTTP服务。

HttpCore支持两种I/O模型:基于经典Java I/O的阻塞I/O模型和基于Java NIO的非阻塞事件驱动I/O模型。

阻塞I/O模型可能更适合于数据密集型、低延迟的场景，而非阻塞模型可能更适合于高延迟的场景，在这些场景中，原始数据吞吐量没有以资源效率的方式处理数千个并发HTTP连接的能力重要。

- `HttpClient`是一个基于HttpCore的HTTP/1.1兼容的HTTP代理实现。它还为客户端身份验证、HTTP状态管理和HTTP连接管理提供了可重用组件。
HttpComponents Client是通用HttpClient 3.x的继承者和替代品。

- `Asynch HttpClient`是一个基于HttpCore NIO和HttpClient组件的HTTP/1.1兼容的HTTP代理实现。它是Apache HttpClient的一个补充模块，
适用于处理大量并发连接的能力比原始数据吞吐量的性能更重要的特殊情况。

### HttpClient 

- 基于HttpCore的客户端HTTP传输库

- 基于经典的(阻塞)I/O

- 内容无关的

文档地址：http://hc.apache.org/httpcomponents-client-ga/tutorial/html/
