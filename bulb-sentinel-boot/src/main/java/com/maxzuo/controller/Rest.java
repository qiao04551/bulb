package com.maxzuo.controller;

import com.maxzuo.bulb.api.IShopOrderInfoService;
import com.maxzuo.bulb.model.ShopOrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zfh on 2019/05/30
 */
@RestController
public class Rest {

    private final static Logger logger = LoggerFactory.getLogger(Rest.class);

    @Autowired
    private IShopOrderInfoService shopOrderInfoService;

    @GetMapping("queryShopInfo")
    public void queryShopInfo () {
        ShopOrderInfo orderInfo = shopOrderInfoService.getShopOrderInfoByPrimaryKey(10);
        System.out.println("orderInfo: " + orderInfo);
    }
}
