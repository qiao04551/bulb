## 餐饮打印模板相关接口

#1:查询打印模板票据类型
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/queryType
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{}
```

- [x] 出参示例说明

```
{
    "total": 6,                             -- Integer 模板类型数量
    "code": 1,                              -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功",                      -- String  描述信息
    "data": [                               -- [] 列表
        {
            "id": 1,                        -- Integer 模板类型主键
            "name": "结账单"                -- Integer 模板类型名称
        },
        {
            "id": 2,
            "name": "客看单"
        },
        {
            "id": 3,
            "name": "预结单"
        },
        {
            "id": 4,
            "name": "消费清单"
        },
        {
            "id": 5,
            "name": "厨总单"
        },
        {
            "id": 6,
            "name": "堂口单"
        }
    ]
}
```

#2:查询店铺 某种票据类型模板列表
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/query
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId": 288,                      -- Integer 店铺id
    "documentType": 1                   -- Integer 文档类型
}
```

- [x] 出参示例说明

```
{
    "total": 1,                         -- Integer 无效字段
    "code": 1,                          -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功",                  -- String  描述信息
    "data": [                           -- [] 模板列表
        {
            "id": 1,                    -- Integer 模板id
            "documentType": 1,          -- Integer 票据类型
            "name": "系统模板-结账单",  -- String  模板名称
            "status": 1,                -- Integer 1-启用 2-禁用
            "defaultTemplet": 1,        -- Integer 0-自定义模板 1-默认模板
            "modules": []               -- 模板组件
        }
    ]
}
```

#3:票据样式-获取系统组件
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/getSystemComponents
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "templateId": 1,                -- Integer 模板id
    "documentType": 1               -- Integer 票据类型
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功",              -- Integer 描述信息
    "data": {                       
        "modules": []               -- [] 模块列表
    }
}
```

#4:查询预览组件
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/getPreviewComponents
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "templateId": 1,                  -- Integer 模板id
    "documentType": 1                 -- Integer 票据类型
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功",              -- String  描述信息
    "data": [                       -- [] 模块列表
        {
            "moduleName": "标题",           -- String  模块名称
            "moduleDescribe": "票据名称",   -- String  模块描述
            "sort": 1,                      -- Integer 排序
            "moduleId": 1,                  -- Integer 模块id
            "rows": []                      -- [] rows
        }
    ]
}
```

#5:检查模板是否应用门店（默认模板不能应用于门店）
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/checkIsSwitched
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "templateId": 1                 -- Integer 模板id
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功",              -- String  描述信息
    "data": [                       -- [] 模块列表
        {
            "isSwitched": 1         -- Integer 模板状态 1-启用 2-禁用
        }
    ]
}
```

#6:检查模板名称是否唯一
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/checkName
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "templateId": 1,                -- Integer 模板id（新建模板 模板id传 0）
    "name": "123"                   -- String  模板名称
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功",              -- String  描述信息
    "data": []
}
```

or

```
{
    "total": 0,                                 -- Integer 无效字段
    "code": 9,                                  -- Integer 状态码 1-成功 9-失败
    "msg": "模板名称已存在，请更换模板名称！",  -- String  描述信息
    "data": []
}
```

#7:新增模板
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/save
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId": 288,                  -- Integer 店铺id
    "operatorId": 17,               -- Integer 平台用户id
    "name": "1234",                 -- String  模板名称
    "documentType": 1,              -- Integer 模板类型
    "url": "http://img.png",        -- String  预览图
    "modules": []                   -- [] 模板组件
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "添加成功",              -- String  描述信息
    "data": [
        "templateId": 10            -- Integer 新模板ID
    ]
}
```

#8:更新模板
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/update
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId": 288,                  -- Integer 店铺ID
    "name": "1234",                 -- String  模板名称
    "templateId": 1,                -- Integer 模板ID
    "url": "http://img.png",        -- String  预览图
    "modules": []                   -- [] 组件
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "保存成功",              -- String  描述信息
    "data": []
}
```

#9:应用模板
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/enable
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId": 288,                  -- Integer 店铺id
    "templateId": 1,                -- Integer 模板id
    "documentType": 1               -- Integer 票据类型
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "操作成功",              -- String  描述信息
    "data": null
}
```

#10:删除模板
- [x] java类：PrinterTicketRest.java
- [x] 访问地址： printerTicket/remove
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId": 288,                  -- Integer 店铺id
    "templateId": 1                 -- Integer 模板id
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "删除成功",              -- String  描述信息
    "data": null
}
```

or

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 9,                      -- Integer 状态码 1-成功 9-失败
    "msg": "模板不存在",            -- String  描述信息
    "data": null
}
```

or

```
{
    "total": 0,                                         -- Integer 无效字段
    "code": 9,                                          -- Integer 状态码 1-成功 9-失败
    "msg": "该模板为默认模板或者使用中，不允许删除",    -- String  描述信息
    "data": null
}
```

#11:新增 打印机
- [x] java类：PrinterDeviceRest.java
- [x] 访问地址： printerDevice/save
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "deviceName": "一号机",         -- String  打印机名称
    "printerDeviceType": 1,         -- Integer 打印机类型：1-服务器 2-网络打印机
    "address": "127.0.0.1",         -- String  IP地址
    "shopId": 288,                  -- Integer 店铺ID
    "operatorId": 1                 -- Integer 平台用户ID
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "添加成功",              -- String  描述信息
    "data": null
}
```

#12:打印机列表
- [x] java类：PrinterDeviceRest.java
- [x] 访问地址： printerDevice/query
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId"：288,                  -- Integer 店铺ID
    "page"：1,                      -- Integer 页码
    "rows"：10                      -- Integer 单页条数
}
```

- [x] 出参示例说明

```
{
    "total": 1,                     -- Integer 总数量
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "添加成功",              -- String  描述信息
    "data": [
        {
            "id": 1,                -- Integer 出票口ID
            "deviceName": "一号机", -- String  打印机名称
            "printerDeviceType": 1, -- Integer 打印机类型：1-服务器 2-网络打印机
            "address": "127.0.0.1", -- String  IP地址
            "creatorName": "大左",  -- String  创建人姓名
            "updatorName": "大左"   -- String  更新人姓名
            "createTime": "xxx"     -- String  添加时间，格式 yyyy-MM-dd HH:mm:ss
        }
    ]
}
```

#13:编辑打印机
- [x] java类：PrinterDeviceRest.java
- [x] 访问地址： printerDevice/edit
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId"：288,                  -- Integer 店铺ID
    "printerDeviceId"：1            -- Integer 打印机ID
}
```

- [x] 出参示例说明

```
{
    "total": 10,                    -- Integer 总数量
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "添加成功",              -- String  描述信息
    "data":{
        "id": 1,                -- Integer 出票口ID
        "deviceName": "一号机", -- String  打印机名称
        "printerDeviceType": 1, -- Integer 打印机类型：1-服务器 2-网络打印机
        "address": "127.0.0.1", -- String  IP地址
        "creatorName": "大左",  -- String  创建人姓名
        "updatorName": "大左"   -- String  更新人姓名
        "createTime": "xxx"     -- String  添加时间，格式 yyyy-MM-dd HH:mm:ss
    }
}
```

#14:更新打印机
- [x] java类：PrinterDeviceRest.java
- [x] 访问地址： printerDevice/update
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "id": 1,                        -- Integer 打印机ID
    "deviceName": "一号机",         -- String  打印机名称
    "printerDeviceType": 1,         -- Integer 打印机类型：1-服务器 2-网络打印机
    "address": "127.0.0.1",         -- String  IP地址
    "shopId": 288,                  -- Integer 店铺ID
    "operatorId": 1                 -- Integer 平台用户ID
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "更新成功",              -- String  描述信息
    "data": null
}
```

#15:删除打印机
- [x] java类：PrinterDeviceRest.java
- [x] 访问地址： printerDevice/delete
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId"：288,                  -- Integer 店铺ID
    "printerDeviceId"：1            -- Integer 打印机ID
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "删除成功",              -- String  描述信息
    "data": null
}
```

#16:新增 出票口
- [x] java类：PrinterKitchenRest.java
- [x] 访问地址： printerKitchen/save
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "name": "出票口",               -- String  出票口名称
    "printerDeviceId": 0,           -- Integer 打印机ID
    "shopId": 288,                  -- Integer 店铺ID
    "operatorId": 1,                -- Integer 平台用户ID
    "documentTypeRules": [          -- [] 出票口票据类型
        {
            "documentTypeId": 1,    -- Integer 票据类型ID
            "number": 1,            -- Integer 打印份数
            "printerType": 1        -- Integer 打印方式：1-一纸多菜 2-一纸一菜 3-各打一张
        }
    ]
    "table": 1,                     -- Integer 是否配置桌台区域 0-未配置 1-配置
    "goods": 1,                     -- Integer 是否配置打印菜品 0-未配置 1-配置
    "tableRules": [                 -- [] 桌台区域，没有配置传空数组
        {
            "tableId": 1            -- Integer 桌台号
        }
    ],
    "goodsRules": [                 -- [] 打印菜品，没有配置传空数组
        {
            "goodsId": 1,           -- Integer 商品ID
            "stockId": 0            -- Integer 商品规格，没有就传 0
        }
    ]
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "添加成功",              -- String  描述信息
    "data": null
}
```

#17:出票口列表
- [x] java类：PrinterKitchenRest.java
- [x] 访问地址： printerKitchen/query
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId"：288,                  -- Integer 店铺id
    "page"：1,                      -- Integer 页码
    "rows"：10                      -- Integer 每页条数
}
```

- [x] 出参示例说明

```
{
    "total": 10,                    -- Integer 列表数量
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "添加成功",              -- String  描述信息
    "data": [
        {
            "id": 1,                -- Integer 出票口ID
            "name": "一号口",       -- String  出票口名称
            "printerDeviceId": 1,   -- Integer 打印机ID
            "creatorName": "大左",  -- String  创建人姓名
            "updatorName": "大左",  -- String  更新人姓名
            "createTime": "xx"      -- String  添加时间，格式 yyyy-MM-dd HH:mm:ss
        }
    ]
}
```

#18:编辑出票口
- [x] java类：PrinterKitchenRest.java
- [x] 访问地址： printerKitchen/edit
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId": 288,                  -- Integer 店铺ID
    "printerKitchenId": 4           -- Integer 出票口ID
}
```

- [x] 出参示例说明

```
{
    "total": 0,                         -- Integer 无效字段
    "code": 1,                          -- Integer 状态码 1-成功 9-失败
    "msg": "查询成功！",                -- String  描述信息
    "data": {
        "id": 1,                        -- Integer 出票口ID
        "name": "出票口",               -- String  出票口名称
        "printerDeviceId": 0,           -- Integer 打印机ID
        "shopId": 288,                  -- Integer 店铺ID
        "documentTypeRules": [          -- [] 出票口票据类型
            {
                "documentTypeId": 0,    -- Integer 票据类型ID
                "number": 1,            -- Integer 打印份数
                "printerType": 1        -- Integer 打印方式：1-一纸多菜 2-一纸一菜 3-各打一张
            }
        ],
        "table": 0,                     -- Integer 是否配置桌台区域 0-未配置 1-配置
        "goods": 0,                     -- Integer 是否配置打印菜品 0-未配置 1-配置
        "tableRules": [                 -- [] 桌台区域，没有配置传空数组
            {
                "tableId": 0            -- Integer 桌台号
            }
        ],
        "goodsRules": [                 -- [] 打印菜品，没有配置传空数组
            {
                "goodsId": 1,           -- Integer 商品ID
                "stockId": 0            -- Integer 商品规格，没有就传 0
            }
        ]
    }
}
```

#19:更新 出票口
- [x] java类：PrinterKitchenRest.java
- [x] 访问地址： printerKitchen/update
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```

{
    "id": 1,                        -- Integer 出票口ID
    "name": "出票口",               -- String  出票口名称
    "printerDeviceId": 1,           -- Integer 打印机ID
    "shopId": 288,                  -- Integer 店铺ID
    "operatorId": 0,                -- Integer 平台用户ID
    "documentTypeRules": [          -- [] 出票口票据类型
        {
            "documentTypeId": 1,    -- Integer 票据类型ID
            "number": 2,            -- Integer 打印份数
            "printerType": 3        -- Integer 打印方式：1-一纸多菜 2-一纸一菜 3-各打一张
        }
    ],
    "table": 0,                     -- Integer 是否配置桌台区域 0-未配置 1-配置
    "goods": 0,                     -- Integer 是否配置打印菜品 0-未配置 1-配置
    "tableRules": [                 -- [] 桌台区域，没有配置传空数组
        {
            "tableId": 1            -- Integer 桌台号
        }
    ],
    "goodsRules": [                 -- [] 打印菜品，没有配置传空数组
        {
            "goodsId": 1,           -- Integer 商品ID
            "stockId": 1            -- Integer 商品规格，没有就传 0
        }
    ]
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "更新成功",              -- String  描述信息
    "data": null
}
```

#20:删除 出票口
- [x] java类：PrinterKitchenRest.java
- [x] 访问地址： printerKitchen/delete
- [x] 公司接口地址： 本地,本机
- [x] 入参示例说明

```
{
    "shopId"：288,                  -- Integer 店铺ID
    "printerKitchenId"：1           -- Integer 出票口ID
}
```

- [x] 出参示例说明

```
{
    "total": 0,                     -- Integer 无效字段
    "code": 1,                      -- Integer 状态码 1-成功 9-失败
    "msg": "删除成功",              -- String  描述信息
    "data": null
}
```