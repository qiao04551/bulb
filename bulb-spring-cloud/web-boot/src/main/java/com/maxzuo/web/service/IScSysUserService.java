package com.maxzuo.web.service;

import com.maxzuo.web.hystrix.ScSysUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过Feign 进行远程服务调用；添加fallback属性
 * <p>
 * Created by zfh on 2019/09/14
 */
@FeignClient(name = "spring-cloud-producer", fallback = ScSysUserServiceFallback.class)
public interface IScSysUserService {

    @PostMapping("hello")
    String hello(@RequestParam(value = "name") String name);
}
