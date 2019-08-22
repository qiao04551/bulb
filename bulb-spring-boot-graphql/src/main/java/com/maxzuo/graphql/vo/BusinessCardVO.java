package com.maxzuo.graphql.vo;

import lombok.Data;

/**
 * 名片信息 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
@Data
public class BusinessCardVO {

    /**
     * 名片ID
     */
    private Integer cardId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 职位
     */
    private String position;

    /**
     * 公司
     */
    private String company;

    /**
     * 兼任的职位列表，json
     */
    private String otherJobs;

    /**
     * 行业ID
     */
    private Integer tradeId;

    /**
     * 行业名称
     */
    private String tradeName;

    /**
     * 业务
     */
    private String business;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;
}
