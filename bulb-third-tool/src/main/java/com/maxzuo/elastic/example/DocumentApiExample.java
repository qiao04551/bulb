package com.maxzuo.elastic.example;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.elastic.model.Person;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * ElasticSearch Document API的使用
 * <p>
 * Created by zfh on 2019/01/24
 */
public class DocumentApiExample extends ElasticApiBase {

    /**
     * 通过ID查询数据
     * <pre>
     *  通过RESTful API 查询所有数据
     *  $ curl http://192.168.1.1:9200/zxcity_elk/file/_search
     * </pre>
     */
    @Test
    public void testGetRecordById() {
        String index = "zxcity_elk";
        String type = "file";
        String id = "AW0-QrI4_bcdcI2POWJX";
        // 如果设置为true，将开启一个操作线程
        GetResponse response = client.prepareGet(index, type, id).setOperationThreaded(false).get();
        System.out.println("index：" + new String(response.getIndex().getBytes()));
        System.out.println("type：" + new String(response.getType().getBytes()));
        System.out.println("version： " + response.getVersion());

        if (response.isExists()) {
            String sourceAsString = response.getSourceAsString();
            System.out.println(sourceAsString);
        }

        /// 方式二
        // GetRequest getRequest = new GetRequest();
    }

    /**
     * 通过ID删除记录
     */
    @Test
    public void testDeleteRecordById() {
        String index = "zxcity_elk";
        String type = "file";
        String id = "AW09qZ9lq01uG26cjLNN";
        client.prepareDelete(index, type, id).get();

        /// 方式二
        // DeleteRequest deleteRequest = new DeleteRequest();
    }

    /**
     * 新增一条记录（如果索引不存在，则创建索引）
     */
    @Test
    public void testSaveRecord() {
        String index = "test_dazuo";
        String type = "file";
        String document = JSONObject.toJSONString(new Person("wang", "web工程师"));
        client.prepareIndex(index, type).setSource(document).get().isCreated();

        // 方式二
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("test_dazuo");
        indexRequest.type("file");
        indexRequest.source(JSONObject.toJSONString(new Person("dazuo88", "java888")));
        try {
            client.index(indexRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     */
    @Test
    public void testUpdateById() {
        String index = "zxcity_elk";
        String type = "file";
        String id = "AW0-CC5W_bcdcI2POWJB";
        client.prepareUpdate(index, type, id)
                .setDoc(JSONObject.toJSONString(new Person("dazuo", "java")))
                .get();

        // 方式二：
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index);
        updateRequest.type(type);
        updateRequest.id(id);
        updateRequest.doc(JSONObject.toJSONString(new Person("dazuo123", "java123")));
        try {
            client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询多条记录
     */
    @Test
    public void testGetMutipleRecord() {
        String index = "zxcity_elk";
        String type = "file";
        MultiGetResponse responses = client.prepareMultiGet()
                .add(index, type, "AW0-DDI__bcdcI2POWJC")
                .add(index, type, "AW0-DGuI_bcdcI2POWJD", "AW0-CC5W_bcdcI2POWJB")
                .get();

        for (MultiGetItemResponse itemResponse : responses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }
    }

    /**
     * Bulk API允许在一个请求中索引和删除多个文档
     */
    @Test
    public void testBulkAPI() {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        try {
            String index = "zxcity_elk";
            String type = "file";
            bulkRequest.add(client.prepareIndex(index, type)
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("name", "kimchy")
                            .field("profession", "hadoop")
                            .field("time", new Date())
                            .endObject()
                    )
            );

            bulkRequest.add(client.prepareIndex(index, type)
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("name", "bobo")
                            .field("profession", "hbase")
                            .field("time", new Date())
                            .endObject()
                    )
            );
            BulkResponse response = bulkRequest.get();
            if (response.hasFailures()) {
                System.out.println("操作失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量入库
     * <p>
     * BulkProcessor类提供了一个简单的接口，可以根据请求的数量或大小，或者在给定的时间段之后，自动刷新批量操作。
     */
    @Test
    public void testUseBulkProcessor() {
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {

                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        System.out.println("请求失败回调！");
                        failure.printStackTrace();
                    }
                })
                // 每10,000个请求执行一次批量
                .setBulkActions(10000)
                // 每1gb刷新一次内存
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // 无论请求的数量是多少，每5秒刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 设置并发请求的数量。值为0意味着只允许执行一个请求。值1表示允许在累积新批量请求时执行1个并发请求。
                .setConcurrentRequests(1)
                // 设置一个自定义回退策略，该策略最初将等待100毫秒，然后成倍增长，重试最多三次。
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        try {
            int i = 0;
            while (++i < 10) {
                System.out.println(i);
                IndexRequest indexRequest = new IndexRequest();
                indexRequest.index("zxcity_elk");
                indexRequest.type("file");
                indexRequest.source(JSONObject.toJSONString(new Person("dazuo" + i, "java888")));
                bulkProcessor.add(indexRequest);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                /*
                    这两种方法都会刷新任何剩余的文档，如果通过设置flushInterval来调度其他所有调度刷新，则禁用它们。
                    如果启用了并发请求，那么awaitClose方法将等待所有大容量请求完成指定的超时，然后返回true;如果指
                    定的等待时间在所有大容量请求完成之前过期，则返回false。close方法不等待任何剩余的批量请求完成并
                    立即退出。
                 */
                bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
                // bulkProcessor.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
