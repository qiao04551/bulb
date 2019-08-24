package com.maxzuo.dubbo.shop.service;

import com.maxzuo.dubbo.common.api.IShopOrderInfoService;
import com.maxzuo.dubbo.common.model.ShopOrderInfo;
import com.maxzuo.dubbo.shop.dao.ShopOrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单表相关Service实现类
 * Created by zfh on 2019/01/19
 */
@Service("shopOrderInfoService")
public class ShopOrderInfoServiceImpl implements IShopOrderInfoService {

    @Autowired
    private ShopOrderInfoMapper shopOrderInfoMapper;

    @Override
    public Integer save(ShopOrderInfo shopOrderInfo) {
        shopOrderInfoMapper.insert(shopOrderInfo);
        return shopOrderInfo.getId();
    }

    @Override
    public ShopOrderInfo getShopOrderInfoByPrimaryKey(Integer id) {
        return shopOrderInfoMapper.selectShopOrderInfoByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimarySeletive(ShopOrderInfo orderInfo) {
        return shopOrderInfoMapper.updateByPrimarySeletive(orderInfo);
    }

    @Override
    public Integer deleteByPrimaryKey(Integer id) {
        return shopOrderInfoMapper.deleteByPrimaryKey(id);
    }
}
