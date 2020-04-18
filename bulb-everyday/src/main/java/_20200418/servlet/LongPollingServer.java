package _20200418.servlet;

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
 * 长轮询服务端-同步阻塞
 * <p>
 * Created by zfh on 2020/04/18
 */
public class LongPollingServer {

    private final Random random = new Random();

    private final AtomicLong sequence = new AtomicLong();

    private final AtomicLong value = new AtomicLong();

    private void start() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.createContext("/long-polling", httpExchange -> {
            System.out.println();
            final long currentSequence = sequence.incrementAndGet();
            System.out.println("第" + (currentSequence) + "次 longpolling");

            // 由于客户端设置的超时时间是50s，
            // 为了更好的展示长轮询，这边random 100，模拟服务端hold住大于50和小于50的情况。
            // 再具体场景中，这块在具体实现上，
            // 对于同步servlet，首先这里必须阻塞，因为一旦doGet方法走完，容器就认为可以结束这次请求，返回结果给客户端。
            // 所以一般实现如下：
            // while(结束){ //结束条件，超时或者拿到数据
            //    data = fetchData();
            //    if(data == null){
            //       sleep();
            //    }
            // }

            try {
                // 模拟阻塞
                int sleepSecends = random.nextInt(100);
                System.out.println(currentSequence + " wait " + sleepSecends + " second");
                TimeUnit.SECONDS.sleep(sleepSecends);
            } catch (InterruptedException ignored) {
            }

            byte[] data = Long.toString(value.getAndIncrement()).getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, data.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            outputStream.close();
            httpExchange.close();
        });
        httpServer.start();
    }

    public static void main(String[] args) throws IOException {
        LongPollingServer server = new LongPollingServer();
        server.start();
    }
}
