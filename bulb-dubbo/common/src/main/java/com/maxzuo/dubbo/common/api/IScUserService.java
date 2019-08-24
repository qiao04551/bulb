package com.maxzuo.dubbo.common.api;

import com.maxzuo.dubbo.common.model.ScUser;

/**
 * 用户表相关Service
 * <p>
 * Created by zfh on 2019/06/10
 */
public interface IScUserService {

    /**
     * 新增用户
     */
    void save(ScUser user);

    /**
     * 通过主键查询用户
     */
    ScUser queryUserByPrimaryKey(Integer id);
}
