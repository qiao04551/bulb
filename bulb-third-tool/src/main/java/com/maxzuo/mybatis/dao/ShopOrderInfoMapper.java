package com.maxzuo.mybatis.dao;

import com.maxzuo.mybatis.entity.ScUserProfessionDO;
import com.maxzuo.mybatis.entity.ShopOrderInfoDO;
import com.maxzuo.mybatis.entity.UserAndOrderInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表相关Service
 * Created by zfh on 2019/01/18
 */
public interface ShopOrderInfoMapper {

    /**
     * 添加记录
     * @param shopOrderInfo {@link ShopOrderInfoDO}
     * @return 自增主键
     */
    Integer insert(ShopOrderInfoDO shopOrderInfo);

    /**
     * 通过主键查询记录
     */
    ShopOrderInfoDO selectShopOrderInfoByPrimaryKey(Integer id);

    /**
     * 更新订单信息
     */
    void updateShopOrderInfoByPrimaryKey(@Param("id") Integer id, @Param("count") Integer count);

    /**
     * 查询用户的全部订单
     */
    List<ShopOrderInfoDO> selectShopOrderInfoByUserId(Integer userId);

    /**
     * 查询用户及其订单（复合查询：方式一）
     */
    UserAndOrderInfoDTO selectUserAndOrderInfo(Integer userId);

    /**
     * 查询用户及其订单（复合查询：方式二）
     */
    UserAndOrderInfoDTO selectUserAndOrderInfoTwo(Integer userId);

    /**
     * 查询用户职业信息
     */
    ScUserProfessionDO selectUserProfessionByUserId(Integer userId);
}
