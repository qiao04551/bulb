package com.maxzuo.socket.server;

import com.maxzuo.juc.threadpool.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

/**
 * 简单的Http Server
 * <p>
 * Created by zfh on 2019/09/20
 */
public class SimpleHttpServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final int DEFAULT_SERVER_SO_TIMEOUT = 3000;

    private ExecutorService bizExecutor;

    private ServerSocket socketReference;

    public static void main(String[] args) {
        new SimpleHttpServer().start(8089);
    }

    /**
     * 启动Http Server
     */
    public void start (int serverPort) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        this.bizExecutor = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                new NamedThreadFactory("sentinel-command-center-service-executor"),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        logger.info("EventTask rejected");
                        throw new RejectedExecutionException();
                    }
                });

        Runnable serverInitTask = new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = getServerSocketFromBasePort(serverPort);

                if (serverSocket != null) {
                    logger.info("[SimpleHttpServer] Begin listening at port " + serverSocket.getLocalPort());
                    socketReference = serverSocket;
                    executor.submit(new ServerThread(serverSocket));
                } else {
                    logger.info("[SimpleHttpServer] chooses port fail, http command center will not work");
                }
                executor.shutdown();
            }
        };

        new Thread(serverInitTask).start();
    }

    /**
     * 关闭Http Server
     */
    public void stop() throws Exception {
        if (socketReference != null) {
            try {
                socketReference.close();
            } catch (IOException e) {
                logger.warn("Error when releasing the server socket", e);
            }
        }
        bizExecutor.shutdownNow();
        executor.shutdownNow();
    }

    /**
     * 从基础端口获取可用端口的服务器套接字。当端口已被使用时，端口号将增加（自旋重试，直到成功）
     *
     * Get a server socket from an available port from a base port.<br>
     * Increasing on port number will occur when the port has already been used.
     *
     * @param basePort base port to start
     * @return new socket with available port
     */
    private static ServerSocket getServerSocketFromBasePort(int basePort) {
        int tryCount = 0;
        while (true) {
            try {
                ServerSocket server = new ServerSocket(basePort + tryCount / 3, 100);
                server.setReuseAddress(true);
                return server;
            } catch (IOException e) {
                tryCount++;
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException e1) {
                    break;
                }
            }
        }
        return null;
    }

    /**
     * 请求处理线程，响应Response
     */
    class ServerThread extends Thread {

        private ServerSocket serverSocket;

        ServerThread(ServerSocket s) {
            this.serverSocket = s;
            setName("sentinel-courier-server-accept-thread");
        }

        @Override
        public void run() {
            while (true) {
                Socket socket = null;
                try {
                    // 接收连接
                    socket = this.serverSocket.accept();
                    setSocketSoTimeout(socket);
                    // 交给处理线程
                    HttpEventTask eventTask = new HttpEventTask(socket);
                    bizExecutor.submit(eventTask);
                } catch (Exception e) {
                    logger.info("Server error", e);
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (Exception e1) {
                            logger.info("Error when closing an opened socket", e1);
                        }
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Avoid server thread hang, 3 seconds timeout by default.
     */
    private void setSocketSoTimeout(Socket socket) throws SocketException {
        if (socket != null) {
            socket.setSoTimeout(DEFAULT_SERVER_SO_TIMEOUT);
        }
    }
}
