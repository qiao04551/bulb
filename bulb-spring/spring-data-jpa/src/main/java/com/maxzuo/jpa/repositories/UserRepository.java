package com.maxzuo.jpa.repositories;

import com.maxzuo.jpa.dto.OrderInfoDTO;
import com.maxzuo.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定义查询方法
 * <p>
 * Created by zfh on 2019/10/17
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * 通过用户名查询
     */
    List<User> findByUsername(String username);

    /**
     * 执行原生SQL
     */
    @Query(value = "SELECT * FROM sc_user WHERE id = ?1", nativeQuery = true)
    User findByUserId(Integer userId);

    /**
     * 更新数据的语法（update/delete必须在一个事务里）
     */
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.username = :username WHERE u.id = :id")
    int updateUsernameById(@Param("username") String username, @Param("id") Integer id);

    /**
     * 分页查询
     */
    @Query(value = "SELECT * FROM sc_user WHERE username = ?1",
            nativeQuery = true,
            countQuery = "SELECT COUNT(*) FROM sc_user WHERE username = ?1")
    Page<User> findUserByUsernameAndPage (String username, Pageable pageable);

    /**
     * 多表关联查询（hql语句）
     */
    @Query(value = "SELECT new com.maxzuo.jpa.dto.OrderInfoDTO(u.id, i.id)\n" +
            "FROM User u\n" +
            "INNER JOIN ShopOrderInfo i ON i.userId = u.id \n" +
            "WHERE u.id = :userId")
    List<OrderInfoDTO> findOrderListByUserId(@Param("userId") Integer userId);

    /**
     * 多表关联查询（原生语句）
     */
    @Query(value = "SELECT u.id AS uid, i.id AS oid \n" +
        "FROM sc_user u \n" +
        "INNER JOIN shop_order_info i ON i.user_id = u.id \n" +
        "WHERE u.id = ?1", nativeQuery = true)
    List<Object[]> findOrderByUserIdNative (Integer userId);

    /**
     * 使用@Modifying和@Query注解完成删除操作
     */
    @Modifying
    @Query(value = "DELETE FROM sc_user WHERE id = ?", nativeQuery = true)
    void deleteById(Integer id);
}
