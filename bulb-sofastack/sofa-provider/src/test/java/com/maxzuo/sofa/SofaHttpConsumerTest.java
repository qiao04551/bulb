package com.maxzuo.sofa;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.rpc.common.RemotingConstants;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.io.IOException;

/**
 * Sofa-Rpc http调用
 * <p>
 * Created by zfh on 2019/12/15
 */
public class SofaHttpConsumerTest {

    public static void main(String[] args) throws IOException {
        HttpClient httpclient = HttpClientBuilder.create().build();
        // POST 正常请求
        String url = "http://localhost:12200/com.maxzuo.sofa.IHelloSyncService/saySync";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(RemotingConstants.HEAD_SERIALIZE_TYPE, "json");

        String postData = "hello sofa http";
        ByteArrayEntity entity = new ByteArrayEntity(JSONObject.toJSONBytes(postData),
                ContentType.create("application/json"));
        httpPost.setEntity(entity);

        HttpResponse httpResponse = httpclient.execute(httpPost);
        Assert.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
        System.out.println(new String(data));
    }
}
