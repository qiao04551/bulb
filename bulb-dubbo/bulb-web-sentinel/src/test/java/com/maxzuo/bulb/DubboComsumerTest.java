//package com.maxzuo.bulb;
//
//import com.maxzuo.bulb.api.IShopOrderInfoService;
//import com.maxzuo.bulb.api.IUserService;
//import com.maxzuo.bulb.model.ShopOrderInfo;
//import com.maxzuo.bulb.model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Dubbo comsumer测试类
// * Created by zfh on 2018/10/12
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-context.xml")
//public class DubboComsumerTest {
//
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private IShopOrderInfoService shopOrderInfoService;
//
//    @Test
//    public void testFescarDistributedTransaction () {
//        User user = new User();
//        user.setUsername("大左");
//        user.setPassword("dazuo.123");
//        userService.save(user);
//
//        ShopOrderInfo shopOrderInfo = new ShopOrderInfo();
//        shopOrderInfo.setOrderNo("12345");
//        shopOrderInfo.setUsername("大左");
//        shopOrderInfo.setCount(1);
//        shopOrderInfoService.save(shopOrderInfo);
//    }
//}
