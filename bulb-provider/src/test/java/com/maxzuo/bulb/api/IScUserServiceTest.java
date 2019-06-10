package com.maxzuo.bulb.api;

import com.maxzuo.bulb.model.ScUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class IScUserServiceTest {

    @Autowired
    private IScUserService scUserService;

    @Test
    public void testSaveUser () {
        ScUser user = new ScUser();
        user.setUsername("dazuo");
        user.setPassword("123456");
        scUserService.save(user);
    }

    @Test
    public void testQueryUserByPrimaryKey () {
        ScUser user = scUserService.queryUserByPrimaryKey(10);
        Assert.assertEquals(Long.valueOf(10).longValue(), user.getId().longValue());
    }
}
