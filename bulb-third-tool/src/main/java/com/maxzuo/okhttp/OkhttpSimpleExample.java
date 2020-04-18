package com.maxzuo.okhttp;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.junit.Test;

/**
 * okhttp3 简单使用
 * <p>
 * Created by zfh on 2019/08/23
 */
public class OkhttpSimpleExample {

    /**
     * 测试Get请求
     */
    @Test
    public void testGetRequest () {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url("http://www.baidu.com")
                .addHeader("Host", "www.baidu.com")
                .addHeader("User-Agent", "curl/7.54.0")
                .addHeader("Accept", "*/*")
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body != null) {
                System.out.println(response.code());
                System.out.println(new String(body.bytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Post请求：application/x-www-form-urlencoded
     */
    @Test
    public void testPostRequest () {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("name", "dazuo");

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/hello")
                .post(formBody.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                byte[] bytes = response.body().bytes();
                System.out.println(new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Post请求：application/json
     */
    @Test
    public void testPostRequestAndJson () {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        JSONObject postData = new JSONObject(10);
        postData.put("id", 2);
        postData.put("name", "dazuo");

        RequestBody requestBody = RequestBody.create(mediaType, postData.toString());
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8080/hello")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                byte[] bytes = response.body().bytes();
                System.out.println(new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
