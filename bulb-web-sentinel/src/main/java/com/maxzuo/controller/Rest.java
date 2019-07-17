package com.maxzuo.controller;

import com.maxzuo.form.Param;
import com.maxzuo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求统一入口
 * Created by zfh on 2018/12/30
 */
@RestController
public class Rest {

    private static final Logger logger = LoggerFactory.getLogger(Rest.class);

    @PostMapping("/zxcity_restful/ws/rest")
    public ModelAndView restMain(HttpServletRequest request) {
        Param param = new Param();
        param.setCmd(request.getParameter("cmd"));
        param.setData(request.getParameter("data"));
        param.setVersion(request.getParameter("version"));
        request.setAttribute("param", param);
        return new ModelAndView("forward:/ws/" + param.getCmd());
    }
}
