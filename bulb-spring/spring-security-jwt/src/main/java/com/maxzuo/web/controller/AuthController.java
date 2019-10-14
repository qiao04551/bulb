package com.maxzuo.web.controller;

import com.maxzuo.web.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    /**
     * 查询用户信息，需要Admin权限
     */
    @PostMapping("queryShop")
    public Result queryShop () {
        Map<String, Object> shopInfo = new HashMap<>(10);
        shopInfo.put("shopId", 1);
        shopInfo.put("shopName", "测试店铺");

        Result result = Result.ok();
        result.setData(shopInfo);
        return result;
    }
}
