## Netty学习笔记

Netty是业界最流行的NIO框架之一，它的健壮性、功能、性能、可定制性和可扩展性在同类框架中都是首屈一指的。

> **Netty的优点**

- API使用简单，开发门槛低
- 功能强大，预置了多种编解码功能，支持多种主流协议；
- 定制能力强，可以通过ChannelHandler对通信框架进行灵活地扩展；
- 性能高，通过与其他业界主流的NIO框架相比，Netty的综合性能最优；
- 成熟、稳定，Netty修复了已经发现的所有JDK NIO BUG，业务开发人员不需要再为NIO的BUG而烦恼；

> **TCP粘包/拆包**

TCP是个"流"协议，所谓流，就是没有界限的一串数据。它们都是连成一片的，其间没有分界线，TCP底层并不了解上层业务数据的具体含义，
它会根据TCP缓冲区的实际情况进行包的划分，所以在业务上认为，一个完整的包可能会被TCP拆分成多个包进行发送，也有可能把多个小的
包封装成一个大的数据包发送，这就是所谓的TCP粘包和拆包问题。


> **TCP粘包/拆包问题说明**

假设客户端分别发送了两个数据包 D1 和 D2 给服务端，由于服务端一次读取到的字节数是不确定的，故可能存在以下4中情况：

1）服务端分两次读取到了两个独立的数据包，分别是 D1 和 D2，没有粘包和拆包。

2）服务端一次接收到了两个数据包，D1 和 D2 粘合在一起，被称为TCP粘包；

3）服务端分两次读取到了两个数据包，第一次读取到了完整的 D1 和 D2 包的部分内容，第二次读取到了 D2 包的剩余内容，这被称为TCP拆包。

4）服务端分两次读取到了两个数据包，第一次读取到了 D1 包的部分内容 D1_1，第二次读取到了 D1 包的剩余内容 D1_2 和 D2包的整包。

如果此时服务端TCP接收滑窗非常小，而数据包 D1 和 D2 比较大，很有可能会发生第5种可能，即服务端分多次才能将 D1 和 D2
包接收完全，期间发生多次拆包。

> **TCP粘包/拆包发生的原因**

1）应用程序write写入的字节大小大于套接字接口发送缓冲区大小；

2）进行MSS大小的TCP分段；

3）以太网帧的payload大于MTU进行IP分片。


> **粘包问题的解决策略**

由于底层的TCP无法理解上层的业务数据，所以在底层是无法保证数据包不被拆分和重组的，这个问题只能通过上层的应用协议栈设计来解决，
根据业界的主流协议的解决方案，可以归纳如下：

1）消息定长，例如每个报文的大小为固定长度200字节，如果不够，空位补空格；

2）在包尾增加回车换行符进行分割，例如FTP协议；

3）将消息分为消息头和消息体，消息头包含表示消息总长度（或者消息长度）的字段，通常设计思路为消息头的第一个字段使用int32来表示
消息的总长度；

4）更复杂的应用层协议。

> **Netty解析TCP粘包问题**

- LineBasedFrameDecoder：其工作原理是一次遍历ByteBuf中的可读字节，判断看是否有 "\n" 或者 "\r\n"，如果有，就以此位置为
结束位置，从可读索引到结束位置区间的字节就组成了一行。它是以换行符为结束标志的解码器，支持携带结束符或者不携带结束符两种解码方
式，同时支持配置单行的最大长度。如果连续读取到最大长度后仍然没有发现换行符，就会抛出异常，同时忽略掉之前读到的异常码流。

- DelimiterBaseFrameDecoder：自动完成以指定分隔符作为码流结束标识的消息的解码。

- FixedLengthFrameDecoder：是固定长度解码器，它能够按照指定的长度对消息进行自动解码。

> **Google Protobuf编解码**

Protobuf是一个灵活、高效、结构化的数据序列化框架，相比XML等传统的序列化工具，它更小、更快、更简单。Protobuf支持数据结构化
一次可以到处使用，甚至跨语言使用，通过代码生成工具可以生成不同语言版本的源代码，甚至可以在使用不同版本的数据结构进行间进行数
据传递，实现数据结构的向前兼容。

- ProtobufVarint32FrameDecoder：可以处理半包消息
- ProtobufVarint32LengthFieldPrepender：半包解码器
- ProtobufDecoder：仅仅负责解码，它不支持读半包