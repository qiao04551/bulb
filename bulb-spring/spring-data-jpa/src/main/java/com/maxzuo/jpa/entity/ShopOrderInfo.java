package com.maxzuo.jpa.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zfh on 2019/10/17
 */
@Data
@ToString
@Entity
@Table(name = "shop_order_info")
public class ShopOrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "count")
    private Integer count;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "create_time")
    private Date createTime;
}
