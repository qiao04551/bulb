## Protocol Buffer

### 一、Protocol Buffer 与 XML、JSON 的区别

- Protocol Buffer 序列化之后得到的数据不是可读的字符串，而是二进制流

- XML 和 JSON 格式的数据信息都包含在了序列化之后的数据中，不需要任何其它信息就能还原序列化之后的数据；但使用 Protocol Buffer
需要事先定义数据的格式(.proto 协议文件)，还原一个序列化之后的数据需要使用到这个定义好的数据格式

- 最后，在传输数据量较大的需求场景下，Protocol Buffer 比 XML、JSON 更小（3到10倍）、更快（20到100倍）、使用 & 维护更简单；
而且 Protocol Buffer 可以跨平台、跨语言使用

### 二、Protocol Buffer 的作用

通过将结构化的数据（拥有多种属性）进行序列化，从而实现（内存与硬盘之间）数据存储和交换的功能

- 序列化：按照`.proto`协议文件将数据结构或对象转换成二进制流的过程

- 反序列化：将在序列化过程中所生成的二进制流转换成数据结构或对象的过程

### 三、构建 Protocol Buffer 消息对象模型

**1.通过 Protocol Buffer 语法描述需要存储的数据结构**

- Protocol Buffer 定义数据格式的文件一般保存在`.proto`文件中，每一个`message`代表了一类结构化的数据，`message`里面定义了每一个属性的类型和名字。

- 一个消息对象可以将其他消息对象类型用作字段类型

- [proto3](https://developers.google.cn/protocol-buffers/docs/proto3)语法文档

```proto
syntax = "proto3";

import "google/protobuf/any.proto";

// 可以添加一个可选的包说明符，以防止协议消息类型之间的名称冲突。
package common;

// option 选项
option java_package = "com.maxzuo.proto";
option java_outer_classname = "NetworkProtocol";
option java_multiple_files = true;

// 定义消息类型
message Person {
    /*
      分配标识号：
        1）每个字段都有唯一的一个数字标识符。
        2）这些字段号用于以消息二进制格式标识字段，在使用消息类型后不应更改。
        3）注意，范围[1,15]之内的标识号在编码的时候会占用一个字节进行编码，字段号和字段类型(您可以在协议缓冲区编码中了解更多相关信息)。
           [16,2047]范围之内的字段号需要2个字节。因此那些频繁出现的消息元素保留 [1,15]之内的标识号。
    */
    string name = 1;
    int32 id = 2;
    string email = 3;

    enum PhoneType {
        MOBILE = 0;
        HOME = 1;
        WORK = 2;
    }

    message PhoneNumber {
        string number = 1;
        PhoneType type = 2;
    }

    /*
      指定字段规则：
        1）singular: 格式良好的消息可以有零个或一个该字段(但不能多于一个)。这是proto3语法的默认字段规则。
        2）repeated：在一个格式良好的消息中，这种字段可以重复任意多次（包括0次）。重复的值的顺序会被保留在协议缓冲区中。
    */
    repeated PhoneNumber phone = 4;
    
    /*
      保留字段：
        如果通过完全删除字段或将其注释掉来更新消息类型，未来的用户可以在对该类型进行更新时重用字段号。如果以后加载相同.proto的旧版本，
        这可能会导致严重的问题，包括数据损坏、隐私bug等等。确保不发生这种情况的一种方法是指定保留已删除字段的字段号(和/或名称，这也可
        能导致JSON序列化问题)。如果将来有用户试图使用这些字段标识符，协议缓冲区编译器将发出抱怨。
        1）指定保留 已删除 字段的字段号
        2）指定保留 已删除 字段的字段名
    */
    reserved 5;
    reserved "age";
    
    /*
      Any
        Any消息类型允许您将消息作为嵌入类型使用，而不需要它们的.proto定义。Any包含作为字节的任意序列化消息，以及充当全局惟一标识符并解析为该消息类型的URL。
        需要导入 google/protobuf/any.proto 文件。
    */
    message ErrorStatus {
      string message = 1;
      repeated google.protobuf.Any details = 2;
    }
}

message AddressBook {
    // 使用其他消息类型作为字段类型，可以将消息嵌套到任意深度
    Person person = 1;
}

message SearchRequest {
    string greeting = 1;
}

message SearchResponse {
    string reply = 1;
}

/*
  定义服务：
    1）如果希望将消息类型与RPC(远程过程调用)系统一起使用，可以在.proto文件中定义RPC服务接口，协议缓冲区编译器将用您选择的语言生成服务接口代码和存根。
    2）举例来说，如果您想定义一个RPC服务，它的方法接受您的SearchRequest并返回一个SearchResponse。
*/
service SearchService {
  rpc Search (SearchRequest) returns (SearchResponse);
}

```

**2.通过 Protocol Buffer 编译器编译 .proto 文件**

安装Protocol Buffer文件编译器

```
$ brew install automake
$ brew install libtool
$ brew install protobuf

# 检查安装
$ protoc --version
```

编译（将 .proto 文件 转换成对应平台（python、C++、Java）的代码文件）

```
#  在终端输入下列命令进行编译
protoc -I=$SRC_DIR --xxx_out=$DST_DIR $SRC_DIR/common.proto

# 参数说明
# 1. $SRC_DIR：指定需要编译的.proto文件目录 (如没有提供则使用当前目录)
# 2. --xxx_out：xxx根据需要生成代码的类型进行设置
	"""
	对于 Java ，xxx =  java ，即 -- java_out
	对于 C++ ，xxx =  cpp ，即 --cpp_out
	对于 Python，xxx =  python，即 --python_out
	"""

# 3. $DST_DIR ：编译后代码生成的目录 (通常设置与$SRC_DIR相同)
# 4. 最后的路径参数：需要编译的.proto 文件的具体路径
```

### 四、应用场景

传输数据量大 & 网络环境不稳定 的数据存储、RPC 数据交换 的需求场景

- 如 即时IM （QQ、微信）的需求场景

- 在 传输数据量较大的需求场景下，Protocol Buffer比XML、Json 更小、更快、使用 & 维护更简单！



