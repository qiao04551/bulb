## WX-miniprogram Query Protocol
The Query protocol of WX-miniprogram in GraphQL format.

### 查询示例

> POST http://127.0.0.1:8080/faceCircle/graphql

Query

```graphql
query businessCard($cardId: Int = 0) {
    queryBusinessCard(cardId: $cardId) {
        cardId
        name
    }
    querySubordinateShop(cardId: $cardId) {
        shopId
    }
}
```

Variables

```json
{
	"cardId": 3
}
```

Response

```json
{
    "total": null,
    "code": 1,
    "msg": "查询成功！",
    "data": {
        "queryBusinessCard": {
            "cardId": "1",
            "name": "dazuo"
        },
        "querySubordinateShop": [
            {
                "shopId": 288
            }
        ]
    }
}
```

### 变更示例

Matation

```graphql
mutation ($cardId: Int!, $diary: DiaryInput!) {
    publishDiary(cardId: $cardId, diary: $diary) {
        cardId
        summary
    }
}
```

Variables

```json
{
	"cardId": 3,
	"diary": {
		"summary": "summary123",
		"richText": "richText456"
	}
}
```

Response

```
{
    "total": null,
    "code": 1,
    "msg": "查询成功！",
    "data": {
        "publishDiary": {
            "cardId": "12",
            "summary": "summary"
        }
    }
}
```

### 内联片段

当查询的字段返回的是接口或者联合类型，那么你可能需要使用内联片段来取出下层具体类型的数据

Query

```graphql
query {
    queryMonkey(id: 1) {
        __typename
        name
        ... on Monkey {
            height
        }
    }
    queryTiger(id: 1) {
        __typename
        name
        ... on Tiger {
            weight
        }
    }
}
```

Response

```json
{
    "total": null,
    "code": 1,
    "msg": "查询成功！",
    "data": {
        "queryMonkey": {
            "__typename": "Monkey",
            "name": "monkey",
            "height": 120
        },
        "queryTiger": {
            "__typename": "Tiger",
            "name": "tiger",
            "weight": 100
        }
    }
}
```

### 联合类型

- 如果需要查询一个返回 SearchResult 联合类型的字段，那么你得使用条件片段才能查询任意字段。
- 联合类型的成员需要是具体对象类型；你不能使用接口或者其他联合类型来创造一个联合类型。
- 由于 Monkey 和 Tiger 共享一个公共接口（Animal），你可以在一个地方查询它们的公共字段

Query

```graphql
query {
    searchUnion(id: 2) {
        __typename
        ... on Animal {
            name
        }
    }
}
```

Response

```json
{
    "total": null,
    "code": 1,
    "msg": "查询成功！",
    "data": {
        "searchUnion": {
            "__typename": "Tiger",
            "name": "tiger"
        }
    }
}
```