package com.maxzuo.bulb.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Created by zfh on 2019/09/20
 */
@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void updateByPrimary () {
        try {
            jdbcTemplate.execute("UPDATE sc_user SET username = 'dazuo' WHERE id = 1");

            // 干扰异常
            throw new RuntimeException("Disturb exception！");
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
