package com.maxzuo.mongodb;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Date;

/**
 * Mongodb 使用最新高级别API，完成增删改查操作。（version：3.2.6）
 * <p>
 * Created by zfh on 2019/05/14
 */
public class MongodbExample {

    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://admin:123456@[127.0.0.1:27017]/test?authSource=admin");
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("user");

        //saveRecord(collection);
        queryRecord(collection);
    }

    /**
     * 插入一条文档
     */
    private static void saveRecord(MongoCollection<Document> collection) {
        Document doc = new Document("name", "dazuo")
                .append("age", 22)
                .append("gender", 1)
                .append("createTime", new Date());
        // 插入一个文档
        collection.insertOne(doc);
    }

    /**
     * 查询文档
     */
    private static void queryRecord(MongoCollection<Document> collection) {
        Document doc = new Document("name", "mars");
        FindIterable<Document> documents = collection.find(doc);
        for (Document document : documents) {
            System.out.println(document);

            System.out.println("+++++++++++++++++++++++++++++");

            String name = document.getString("name");
            System.out.println("name: " + name);
        }
    }
}
