package com.maxzuo.bulb.service;

import com.maxzuo.bulb.api.IScUserService;
import com.maxzuo.bulb.dao.ScUserMapper;
import com.maxzuo.bulb.model.ScUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关Service实现类
 * <p>
 * Created by zfh on 2019/06/10
 */
@Service("scUserService")
public class ScUserService implements IScUserService {

    @Autowired
    private ScUserMapper scUserMapper;

    @Override
    public void save(ScUser user) {
        scUserMapper.insert(user);
    }

    @Override
    public ScUser queryUserByPrimaryKey(Integer id) {
        return scUserMapper.selectUserByPrimaryKey(id);
    }
}
