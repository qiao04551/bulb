package com.maxzuo.controller;

import com.maxzuo.bulb.api.IShopOrderInfoService;
import com.maxzuo.bulb.model.ShopOrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zfh on 2019/05/30
 */
@RestController
public class Rest {

    private final static Logger logger = LoggerFactory.getLogger(Rest.class);

    private final AtomicInteger count = new AtomicInteger(1);

    @Autowired
    private IShopOrderInfoService shopOrderInfoService;

    @GetMapping("queryShopInfo")
    public String queryShopInfo () {
        try {
            shopOrderInfoService.getShopOrderInfoByPrimaryKey(10);
            System.out.println("count: " + count.getAndIncrement());
        } catch (Exception e) {
            logger.info("发生异常：{}", e.getMessage());
            return "error";
        }
        return "ok";
    }
}
