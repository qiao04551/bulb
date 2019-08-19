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

查询变量

```json
{
	"cardId": 3
}
```

查询结果

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