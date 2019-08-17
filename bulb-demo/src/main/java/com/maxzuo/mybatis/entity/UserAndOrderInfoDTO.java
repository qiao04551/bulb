package com.maxzuo.mybatis.entity;

import lombok.Data;

import java.util.List;

/**
 * 结果映射：collection 一对多
 * <p>
 * Created by zfh on 2019/08/16
 */
@Data
public class UserAndOrderInfoDTO {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 职业信息
     */
    private ScUserProfessionDO userProfession;

    /**
     * 订单列表
     */
    private List<ShopOrderInfoDO> orderInfoList;
}
