**示例**

```
query queryBookInfo {
    bookById(id: "book-1") {
        id
        author {
            id
        }
    }
}
```

**字段（Fields**

简单而言，GraphQL 是关于请求对象上的特定字段

```
query {
    book: bookById(id: "book-1") {
        id
    }
}
```

**参数（Arguments）**

参数可以是多种不同的类型。

```
query {
    book: bookById(id: "book-1") {
        id
    }
}
```


**别名（Aliases）**

```
query {
    # 别名
    book: bookById(id: "book-1") {
        id
        # 别名
        bookname: name
    }
}

```


**片段（Fragments）**

```
query {
    bookById(id: "book-1") {
        id
        author {
            ... authorFragment
        }
    }
}
```

**定义片段（基于Author对象的）**

```
fragment authorFragment on Author {
    id
    lastName
}
```

**操作类型**

* 操作类型可以是 query、mutation 或 subscription
* 操作类型是必需的，除非你使用查询简写语法，可以省略 query

```
{
    bookById(id: "book-1") {
        id
    }
}
```

**操作名称**

```
query queryBookInfo {
    bookById(id: "book-1") {
        id
        name
    }
}
```

**变量（Variables）**
```
# 使用变量的步骤：
#   1.声明 $variableName 为查询接受的变量之一
#   2.使用 $bookId 替代查询中的静态值。
#   3.将 variableName: value 通过传输专用（通常是 JSON）的分离的变量字典中
#     {
#	     "bookId": "book-1"
#     }
# 定义变量：
#   1.其工作方式跟类型语言中函数的参数定义一样。它以列出所有变量，变量前缀必须为 $，后跟其类型
#   2.所有声明的变量都必须是标量、枚举型或者输入对象类型。所以如果想要传递一个复杂对象到一个字段上，你必须知道服务器上其匹配的类型。可以从Schema页面了解更多关于输入对象类型的信息。
#   3.变量定义可以是可选的或者必要的。(通过schema中的 ! 表示必选)
# 变量默认值
#   1.可以通过在查询中的类型定义后面附带默认值的方式，将默认值赋给变量
#   2.当所有变量都有默认值的时候，你可以不传变量直接调用查询。如果任何变量作为变量字典的部分传递了，它将覆盖其默认值。
#
```

```
query queryBookInfo($bookId: ID = "book-2"){
    bookById(id: $bookId) {
        id
    }
}
```


**指令（Directives）**

* GraphQL 的核心规范包含两个指令，其必须被任何规范兼容的 GraphQL 服务器实现所支持：
* @include(if: Boolean) 仅在参数为 true 时，包含此字段。
* @skip(if: Boolean) 如果参数为 true，跳过此字段。  

```
# 变量定义：
#  {
#	  "withAuthor": false
#  }

query queryBookInfo($withAuthor: Boolean!) {
    bookById(id: "book-1") {
        id
        # 仅在参数为 true 时，包含此字段。
        author @include(if: $withAuthor) {
            lastName
        }
    }
}
```

**变更（Mutations）**

```
mutation {
}
```

**内联片段（Inline Fragments）**

**元字段**






