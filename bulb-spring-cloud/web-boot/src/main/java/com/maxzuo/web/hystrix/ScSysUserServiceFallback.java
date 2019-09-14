package com.maxzuo.web.hystrix;

import com.maxzuo.web.service.IScSysUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建 ScSysUserServiceFallback 类继承与 IScSysUserService 实现回调的方法
 * <p>
 * Created by zfh on 2019/09/14
 */
@Component
public class ScSysUserServiceFallback implements IScSysUserService {

    @Override
    public String hello(@RequestParam("name") String name) {
        return "hello" + name + ", this messge send failed";
    }
}
