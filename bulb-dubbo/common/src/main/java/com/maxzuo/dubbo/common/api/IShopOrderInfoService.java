package com.maxzuo.dubbo.common.api;

import com.maxzuo.dubbo.common.model.ShopOrderInfo;

/**
 * 订单相关Service
 * Created by zfh on 2019/01/19
 */
public interface IShopOrderInfoService {

    /**
     * 添加记录
     * @param shopOrderInfo {@link ShopOrderInfo}
     * @return 自增主键
     */
    Integer save(ShopOrderInfo shopOrderInfo);

    /**
     * 通过主键查询记录
     * @param id 主键
     * @return {@link ShopOrderInfo}
     */
    ShopOrderInfo getShopOrderInfoByPrimaryKey(Integer id);

    /**
     * 更新记录
     * @param orderInfo {@link ShopOrderInfo}
     * @return 受影响的条数
     */
    Integer updateByPrimarySeletive(ShopOrderInfo orderInfo);

    /**
     * 删除记录
     * @param id 主键
     * @return 受影响的条数
     */
    Integer deleteByPrimaryKey(Integer id);
}
