package com.maxzuo.elastic.model;

/**
 * ElasticSearch记录对象
 * Created by zfh on 2019/01/25
 */
public class RecordDTO {

    /** 索引名 */
    private String index;

    /** 类型 */
    private String type;

    /** 文档 */
    private String document;

    /** 记录ID */
    private String id;

    public RecordDTO() {
    }

    public RecordDTO(String index, String type, String document, String id) {
        this.index = index;
        this.type = type;
        this.document = document;
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RecordDTO{" + "index='" + index + '\'' + ", type='" + type + '\'' + ", document='" + document + '\''
               + ", id='" + id + '\'' + '}';
    }
}
