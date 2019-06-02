package com.maxzuo.bulb.service;

import com.maxzuo.bulb.api.IShopOrderInfoService;
import com.maxzuo.bulb.dao.ShopOrderInfoMapper;
import com.maxzuo.bulb.model.ShopOrderInfo;
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
