## graphql schema和类型

### 对象类型和字段（Object Types and Fields） 

一个 GraphQL schema 中的最基本的组件是对象类型，它就表示你可以从服务上获取到什么类型的对象，以及这个对象有什么字段。

```
type Character {
  name: String!
  appearsIn: [Episode!]!
}
```

- `Character` 是一个 GraphQL 对象类型，表示其是一个拥有一些字段的类型
- `name` 和 appearsIn 是 Character 类型上的字段
- `String` 是内置的标量类型之一 —— 标量类型是解析到单个标量对象的类型
- `String!` 表示这个字段是非空的，GraphQL 服务保证当你查询这个字段后总会给你返回一个值。
- `[Episode!]!` 表示一个 Episode 数组。因为它也是非空的，所以当你查询 appearsIn 字段的时候，你也总能得到一个数组（零个或者多个元素）。且由于 `Episode!` 也是非空的，你总是可以预期到数组中的每个项目都是一个 Episode 对象。

> 参考示例 查询数组和对象

定义schema

```
type Query {
  bookById(id: ID): Book
}

type Book {
  id: ID
  name: String
  pageCount: Int
  author: Author
  authors: [Author]
}

type Author {
  id: ID
  firstName: String
  lastName: String
}
```

query 参数

```
query queryBookInfo {
    bookById(id: "book-1") {
        id
        # 查询对象
        author {
            id
        }
        # 查询数组
        authors {
            id
            lastName
        }
    }
}
```

查询结果

```
{
    "data": {
        "bookById": {
            "id": "book-1",
            "author": {
                "id": "author-1"
            },
            "authors": [
                {
                    "id": "author-1",
                    "lastName": "Rowling"
                }
            ]
        }
    }
}
```

### 参数（Arguments）

GraphQL 对象类型上的每一个字段都可能有零个或者多个参数，例如下面的 length 字段：

```
type Starship {
  id: ID!
  name: String!
  length(unit: LengthUnit = METER): Float
}
```

- 所有参数都是具名的，不像 JavaScript 或者 Python 之类的语言，函数接受一个有序参数列表，而在 GraphQL 中，所有参数必须具名传递。本例中，length 字段定义了一个参数，unit。

- 参数可能是必选或者可选的，当一个参数是可选的，我们可以定义一个默认值 —— 如果 unit 参数没有传递，那么它将会被默认设置为 METER。


### 查询和变更类型（The Query and Mutation Types） 

schema 中大部分的类型都是普通对象类型，但是一个 schema 内有两个特殊类型

```
schema {
  query: Query
  mutation: Mutation
}
```

- 每一个 GraphQL 服务都有一个 query 类型，可能有一个 mutation 类型。这两个类型和常规对象类型无差，但是它们之所以特殊，是因为它们定义了每一个 GraphQL 查询的入口。


### 标量类型（Scalar Types） 

一个对象类型有自己的名字和字段，而某些时候，这些字段必然会解析到具体数据。

> GraphQL 自带一组默认标量类型：

- Int：有符号 32 位整数。
- Float：有符号双精度浮点值。
- String：UTF‐8 字符序列。
- Boolean：true 或者 false。
- ID：ID 标量类型表示一个唯一标识符，通常用以重新获取对象或者作为缓存中的键。ID 类型使用和 String 一样的方式序列化；然而将其定义为 ID 意味着并不需要人类可读型。


> 自定义标量类型：

```
scalar Date
```

### 枚举类型（Enumeration Types） 

枚举类型是一种特殊的标量，它限制在一个特殊的可选值集合内。这让你能够：

- 1.验证这个类型的任何参数是可选值的的某一个
- 2.与类型系统沟通，一个字段总是一个有限值集合的其中一个值。

> 示例：

```
enum Episode {
  NEWHOPE
  EMPIRE
  JEDI
}
```

### 列表和非空（Lists and Non-Null）

对象类型、标量以及枚举是 GraphQL 中你唯一可以定义的类型种类。但是当你在 schema 的其他部分使用这些类型时，或者在你的查询变量声明处使用时，你可以给它们应用额外的类型修饰符来影响这些值的验证。

```
type Character {
  name: String!
  appearsIn: [Episode]!
}
```

- 我们使用了一个 String 类型，并通过在类型名后面添加一个感叹号!将其标注为非空。这表示我们的服务器对于这个字段，总是会返回一个非空值，如果它结果得到了一个空值，那么事实上将会触发一个 GraphQL 执行错误，以让客户端知道发生了错误。

- 非空和列表修饰符可以组合使用。

### 接口（Interfaces）

跟许多类型系统一样，GraphQL 支持接口。一个接口是一个抽象类型，它包含某些字段，而对象类型必须包含这些字段，才能算实现了这个接口。

```
interface Character {
  id: ID!
  name: String!
  friends: [Character]
  appearsIn: [Episode]!
}
```

> 这意味着任何实现 Character 的类型都要具有这些字段，并有对应参数和返回类型。

```
type Human implements Character {
  id: ID!
  name: String!
  friends: [Character]
  appearsIn: [Episode]!
  starships: [Starship]
  totalCredits: Int
}
```

### 联合类型（Union Types）

联合类型和接口十分相似，但是它并不指定类型之间的任何共同字段。

```
union SearchResult = Human | Droid | Starship
```

### 输入类型（Input Types）

目前为止，我们只讨论过将例如枚举和字符串等标量值作为参数传递给字段，但是你也能很容易地传递复杂对象。这在变更（mutation）中特别有用，因为有时候你需要传递一整个对象作为新建对象。在 GraphQL schema language 中，输入对象看上去和常规对象一模一样，除了关键字是 input 而不是 type：

```
input ReviewInput {
  stars: Int!
  commentary: String
}
```

> 你可以像这样在变更（mutation）中使用输入对象类型：

```
mutation CreateReviewForEpisode($ep: Episode!, $review: ReviewInput!) {
  createReview(episode: $ep, review: $review) {
    stars
    commentary
  }
}
```







