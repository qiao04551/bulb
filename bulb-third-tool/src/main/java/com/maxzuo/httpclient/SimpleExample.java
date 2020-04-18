package com.maxzuo.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Apache Components httpclient 简单使用
 * <p>
 * Created by zfh on 2019/08/23
 */
public class SimpleExample {

    /**
     * Get请求
     */
    @Test
    public void testGetRequest () {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        httpGet.addHeader("User-Agent", "curl/7.54.0");
        httpGet.addHeader("Host", "www.baidu.com");
        httpGet.addHeader("Accept", "*/*");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            System.err.println("statusLine: " + statusLine);

            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = content.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, count));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试Post请求：application/x-www-form-urlencoded
     */
    @Test
    public void testPostRequest () {
        List<NameValuePair> formparams = new ArrayList<>(10);
        formparams.add(new BasicNameValuePair("name", "dazuo"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        HttpPost httpPost = new HttpPost("http://charles.com:8080/hello");
        httpPost.setEntity(formEntity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = content.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, count ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试Post请求：application/json
     */
    @Test
    public void testPostRequestAndJson () {
        JSONObject postData = new JSONObject(10);
        postData.put("id", 2);
        postData.put("name", "dazuo");

        StringEntity postEntity = new StringEntity(postData.toString(), "UTF-8");
        postEntity.setContentEncoding("UTF-8");
        postEntity.setContentType("application/json");

        HttpPost httpPost = new HttpPost("http://charles.com:8080/hello");
        httpPost.setEntity(postEntity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = content.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, count ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
