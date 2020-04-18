package _20200105;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

/**
 * JDK内置的轻量级Http服务器
 * <p>
 * Created by zfh on 2020/01/05
 */
public class HttpServerExample {

    public static void main(String[] args) throws IOException {
        createHttpServer();
    }

    private static void createHttpServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                byte[] data = "hello world" .getBytes(StandardCharsets.UTF_8);
                httpExchange.sendResponseHeaders(200, data.length);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(data);
                outputStream.close();
                httpExchange.close();
            }
        });
        httpServer.createContext("/time", new TimeHandler());
        httpServer.start();

        // httpServer.stop(0);
    }
}
