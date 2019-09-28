package com.maxzuo.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * tomcat-embed 容器 （ TODO: 当前 tomcat-embed 版本 8.5.37）
 * <p>
 * Created by zfh on 2019/09/27
 */
public class TomcatContainerExample {

    public static void main(String[] args) {
        Tomcat tomcatServer =  new Tomcat();
        tomcatServer.setPort(8089);
        tomcatServer.setBaseDir("/Users/dazuo");
        tomcatServer.getHost().setAutoDeploy(false);

        // 创建上下文
        String contextPath = "/web";
        StandardContext standardContext = new StandardContext();
        standardContext.setPath(contextPath);
        //监听上下文
        standardContext.addLifecycleListener(new Tomcat.FixContextListener());
        // tomcat容器添加standardContext 添加整个context
        tomcatServer.getHost().addChild(standardContext);

        // 创建servlet
        tomcatServer.addServlet(contextPath, "indexServlet",  new IndexServlet());
        // 添加servleturl映射
        standardContext.addServletMappingDecoded("/index", "indexServlet");
        try {
            tomcatServer.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        tomcatServer.getServer().await();
    }
}
