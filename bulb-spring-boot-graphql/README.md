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
