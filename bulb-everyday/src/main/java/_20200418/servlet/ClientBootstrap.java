package _20200418.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 长轮询-客户端
 * <p>
 * Created by zfh on 2020/04/18
 */
public class ClientBootstrap {

    private static final String SYNC_URL = "http://localhost:8080/long-polling";

    private final AtomicLong sequence = new AtomicLong();

    void poll() {
        // 循环执行，保证每次longpolling结束，再次发起longpolling
        while (!Thread.interrupted()) {
            doPoll();
        }
    }

    private void doPoll() {
        System.out.println("第" + (sequence.incrementAndGet()) + "次 longpolling");

        long startMillis = System.currentTimeMillis();

        HttpURLConnection connection = null;
        try {
            URL getUrl = new URL(SYNC_URL);
            connection = (HttpURLConnection) getUrl.openConnection();

            // 50s作为长轮询超时时间
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");
            connection.connect();

            if (200 == connection.getResponseCode()) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder result = new StringBuilder(256);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("结果 " + result);
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("request failed");
        } finally {
            long elapsed = (System.currentTimeMillis() - startMillis) / 1000;
            System.out.println("connection close" + "     " + "elapse " + elapsed + "s");
            if (connection != null) {
                connection.disconnect();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ClientBootstrap bootstrap = new ClientBootstrap();
        bootstrap.poll();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
