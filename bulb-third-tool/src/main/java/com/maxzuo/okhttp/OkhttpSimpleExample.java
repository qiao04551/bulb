package com.maxzuo.okhttp;

import com.alibaba.fastjson.JSONObject;
import cucumber.api.java.gl.E;
import okhttp3.*;
import org.fusesource.hawtbuf.BufferInputStream;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/**
 * okhttp3 简单使用
 * <pre>
 *  官网：https://square.github.io/okhttp/works_with_okhttp/
 * </pre>
 * Created by zfh on 2019/08/23
 */
public class OkhttpSimpleExample {

    /**
     * 测试Get请求
     */
    @Test
    public void testGetRequest () throws InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url("http://www.baidu.com")
                .addHeader("Host", "www.baidu.com")
                .addHeader("User-Agent", "curl/7.54.0")
                .addHeader("Accept", "*/*")
                .build();

        long start = System.currentTimeMillis();
        /// 同步调用
        // try (Response response = client.newCall(request).execute()) {
        //     ResponseBody body = response.body();
        //     if (body != null) {
        //         System.out.println(response.code());
        //         System.out.println(new String(body.bytes()));
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        // 异步调用
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    System.out.println(response.code());
                    System.out.println(new String(body.bytes()));
                }
            }
        });

        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
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

    /**
     * 使用URLConnection 下载 文件
     */
    @Test
    public void testDownloadFile () {
        String fileUrl = "file:///Users/dazuo/Pictures/09CE6F152ECE5D0B2FB1619D42364A61.jpg";
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);

            int cLength = connection.getContentLength();
            if (cLength > 1048576) {
                System.out.println("文件大小限制：1M");
                return;
            }

            // 下载文件
            InputStream is = connection.getInputStream();
            BufferedImage img = ImageIO.read(is);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                System.out.println("资源不是图片！");
                return;
            }
            if (img.getWidth(null) > 1334 || img.getHeight(null) > 750) {
                System.out.println("图片尺寸不超过 750px x 1334px");
                return;
            }

            // 将输出流转换为字节数组
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img,"png", baos);

            baos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Okhttp3 上传文件，小程序图片安全检查
     */
    @Test
    public void testUploadFile () {
        String wxUrl = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + "27_fUn8wdYWFsi4-MRv8xlHzxcpc0apl3bxF1Z8M9CZCkaxWj_PtYPXdPwnPvlZq3rd_r4DVmbBnHhmdz5bkSj_BaV4N9QlM_t2QgY6bhaiFdp2bqVJ4vNhrj23YeS56N8khMa8btaX8EL6yPKdEDJaABANMS";
        OkHttpClient client = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), new File("/Users/dazuo/Pictures/BD2C26EA2292FB60EF8041E149F0FB00.gif"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("media", "testImage.png", fileBody)
                .build();

        Request request = new Request.Builder()
                .url(wxUrl)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                System.out.println("response：" + new String(response.body().bytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
