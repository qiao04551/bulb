package com.maxzuo.bulb.api;

import com.maxzuo.bulb.model.ShopOrderInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class IShopOrderInfoServiceTest {

    @Autowired
    private IShopOrderInfoService shopOrderInfoService;

    @Test
    public void save() {
        ShopOrderInfo shopOrderInfo = new ShopOrderInfo();
        shopOrderInfo.setOrderNo("12345");
        shopOrderInfo.setUsername("dazuo");
        shopOrderInfo.setCount(1);
        Integer id = shopOrderInfoService.save(shopOrderInfo);
        Assert.assertTrue(id > 0);
    }

    @Test
    public void getShopOrderInfoByPrimaryKey() {
        ShopOrderInfo shopOrderInfo = shopOrderInfoService.getShopOrderInfoByPrimaryKey(10);
        Assert.assertEquals(shopOrderInfo.getId().longValue(), 10);
    }

    @Test
    public void updateByPrimarySeletive() {
        ShopOrderInfo shopOrderInfo = shopOrderInfoService.getShopOrderInfoByPrimaryKey(1);
        Integer affected = shopOrderInfoService.updateByPrimarySeletive(shopOrderInfo);
        Assert.assertEquals(affected.longValue(), 1);
    }

    @Test
    public void deleteByPrimaryKey() {
        Integer affected = shopOrderInfoService.deleteByPrimaryKey(1);
        Assert.assertEquals(affected.longValue(), 1);
    }
}
