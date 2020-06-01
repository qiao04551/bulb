package _20200418.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 长轮询服务端
 * <p>
 * Created by zfh on 2020/04/18
 */
public class server {

    private final AtomicLong value = new AtomicLong();

    private void start() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.createContext("/long-polling", httpExchange -> {

            byte[] data = fetchData();
            httpExchange.sendResponseHeaders(200, data.length);

            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            outputStream.close();
            httpExchange.close();
        });
        httpServer.start();
    }

    private byte[] fetchData() {
        try {
            // 由于客户端设置的超时时间是50s，
            // 为了更好的展示长轮询，这边random 100，模拟服务端hold住大于50和小于50的情况。
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(100));
        } catch (InterruptedException ignored) {
        }
        return Long.toString(value.getAndIncrement()).getBytes(StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        server server = new server();
        server.start();
    }
}
