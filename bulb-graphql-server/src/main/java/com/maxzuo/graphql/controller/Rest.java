package com.maxzuo.graphql.controller;


import com.maxzuo.graphql.form.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 入口Rest
 */
@RestController
public class Rest {
    @RequestMapping(method = RequestMethod.POST,value = "/zxcity_restful/ws/rest")
    public ModelAndView restmain(HttpServletRequest request) {
		Param param = new Param();
		param.setCmd(request.getParameter("cmd"));
		param.setData(request.getParameter("data"));
		param.setVersion(request.getParameter("version"));
		request.setAttribute("param", param);
		return new ModelAndView("forward:/" + param.getCmd());
    }
}
