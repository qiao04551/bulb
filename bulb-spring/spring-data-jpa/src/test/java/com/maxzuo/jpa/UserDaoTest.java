package com.maxzuo.jpa;

import com.maxzuo.jpa.dto.OrderInfoDTO;
import com.maxzuo.jpa.entity.User;
import com.maxzuo.jpa.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfh on 2019/10/17
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsert () {
        User user = new User();
        user.setUsername("dazuo23");
        user.setPassword("12321");
        User save = userRepository.save(user);
        System.out.println(save);
    }

    @Test
    public void testQuery () {
        List<User> userList = userRepository.findByUsername("dazuo");
        System.out.println(userList);

        User user = userRepository.findByUserId(1);
        System.out.println(user);
    }

    /**
     * 查询条件分页
     */
    @Test
    public void testPageQuery () {
        String username = "dazuo";
        Specification<User> specification = new Specification<User>() {

            private static final long serialVersionUID = -6028951320672912685L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 模糊匹配
                Predicate pi = cb.like(root.get("username").as(String.class), username);
                return cb.and(pi);
            }
        };

        int page = 1;
        int rows = 2;
        Pageable pageable = PageRequest.of(page - 1, rows);
        Page<User> pageInfo = userRepository.findAll(specification, pageable);
        List<User> userList = pageInfo.getContent();
        System.out.println(userList);
        System.out.println("总条数：" + pageInfo.getTotalElements());
    }

    /**
     * 原生查询分页
     */
    @Test
    public void testNativePageQuery () {
        Page<User> pageInfo = userRepository.findUserByUsernameAndPage("dazuo", PageRequest.of(0, 2));
        List<User> userList = pageInfo.getContent();
        System.out.println(userList);
        System.out.println("总条数：" + pageInfo.getTotalElements());
    }

    /**
     * 多表JOIN查询（hql语句）
     */
    @Test
    public void testJoinQuery () {
        List<OrderInfoDTO> orderList = userRepository.findOrderListByUserId(1);
        System.out.println(orderList);
    }

    /**
     * 多表JOIN查询（原生语句）
     */
    @Test
    public void testJoinQueryNative () throws Exception {
        List<Object[]> list = userRepository.findOrderByUserIdNative(1);
        System.out.println(castEntity(list, OrderInfoDTO.class));
    }

    /**
     * 转换实体类
     */
    private static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return returnList;
        }
        Object[] co = list.get(0);
        Class[] c2 = new Class[co.length];
        for (int i = 0; i < co.length; i++) {
            if(co[i]!=null){
                c2[i] = co[i].getClass();
            }else {
                c2[i]=String.class;
            }
        }
        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }
        return returnList;
    }

    @Test
    public void testUpdate () {
        int affectedRow = userRepository.updateUsernameById("dazuo23423", 6);
        System.out.println(affectedRow);
    }

    @Test
    public void testDelete () {
        userRepository.deleteById(7);
    }
}
