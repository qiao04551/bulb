package com.maxzuo.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户职业信息 实体
 * Created by zfh on 2019/08/17
 */
@Data
public class ScUserProfessionDO implements Serializable {

    private Integer id;

    private Integer userId;

    private String professionNamed;

    private String feature;
}
