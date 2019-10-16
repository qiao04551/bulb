package com.maxzuo.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jetty 简单示例
 * <p>
 * Created by zfh on 2019/08/17
 */
public class JettyWebServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyWebServer.class);

    private Server server;

    private int port;

    private String host;

    public static void main(String[] args) {
        try {
            logger.info("服务运行在：http://127.0.0.1:8080");
            JettyWebServer server = new JettyWebServer(8080, "0.0.0.0");
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JettyWebServer (int port, String host) {
        this.port = port;
        this.host = host;
    }

    private void start () throws Exception {
        server = new Server(createThreadPool());
        server.addConnector(createConnector());
        server.setHandler(createHandlers());
        server.start();
        // 阻塞，直到线程池停止
        server.join();
    }

    private ThreadPool createThreadPool () {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        return threadPool;
    }

    private NetworkConnector createConnector () {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        return connector;
    }

    private HandlerCollection createHandlers () {
        // 静态资源访问
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setBaseResource(Resource.newResource(JettyWebServer.class.getClassLoader().getResource("dist")));
        ContextHandler context = new ContextHandler("/");
        context.setHandler(resourceHandler);

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                logger.info("服务端收到请求！");
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("<h1>Hello World</h1>");
            }
        }), "/hello");

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[] {servletHandler});
        return handlerCollection;
    }
}
