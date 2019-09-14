package com.maxzuo.elastic.model;

import lombok.Data;

/**
 * ElasticSearch记录对象
 * Created by zfh on 2019/01/25
 */
@Data
public class RecordDTO {

    /** 索引名 */
    private String index;

    /** 类型 */
    private String type;

    /** 文档 */
    private String document;

    /** 记录ID */
    private String id;
}
