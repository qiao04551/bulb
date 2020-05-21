package com.maxzuo.hessian;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Hessian服务提供者，通过sevlet提供服务
 * <p>
 * Created by zfh on 2020/05/21
 */
public class HessianServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new TokenServiceImpl());
        context.addServlet(servletHolder, "/token");

        server.start();
        server.join();
    }
}
