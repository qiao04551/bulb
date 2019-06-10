package com.maxzuo.bulb.dao;

import com.maxzuo.bulb.model.ScUser;

/**
 * 用户表相关Mapper
 * <p>
 * Created by zfh on 2019/06/10
 */
public interface ScUserMapper {

    /**
     * 新增用户
     */
    void insert (ScUser user);

    /**
     * 通过主键查询用户
     */
    ScUser selectUserByPrimaryKey (Integer id);
}
