package com.maxzuo.jpa.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 订单信息，实体传输对象
 */
@Data
@ToString
public class OrderInfoDTO {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单ID
     */
    private Integer orderId;

    public OrderInfoDTO (Integer userId, Integer orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }
}
