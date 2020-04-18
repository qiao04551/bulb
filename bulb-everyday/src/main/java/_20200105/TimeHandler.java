package _20200105;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Created by zfh on 2020/01/05
 */
public class TimeHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        byte[] data = ("time " + LocalDateTime.now().toString()).getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, data.length);
        OutputStream out = httpExchange.getResponseBody();
        out.write(data);
        out.close();
        httpExchange.close();
    }
}
