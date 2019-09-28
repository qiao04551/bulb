package com.maxzuo.tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zfh on 2019/09/27
 */
public class IndexServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("IndexServlet init!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("this is IndexServlet");
        PrintWriter writer = resp.getWriter();
        writer.print("this is doGet!");
    }
}
