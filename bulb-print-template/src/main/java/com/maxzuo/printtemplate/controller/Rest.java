package com.maxzuo.printtemplate.controller;

import com.maxzuo.printtemplate.dto.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求统一入口
 * Created by zfh on 2018/12/30
 */
@RestController
public class Rest {

    @PostMapping("/zxcity_restful/ws/rest")
    public ModelAndView restMain (HttpServletRequest request) {
        Param param = new Param();
        param.setCmd(request.getParameter("cmd"));
        param.setData(request.getParameter("data"));
        param.setVersion(request.getParameter("version"));
        request.setAttribute("param", param);
        return new ModelAndView("forward:/zxcity_restful/ws/" + param.getCmd());
    }
}
