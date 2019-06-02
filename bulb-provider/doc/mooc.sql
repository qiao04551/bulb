# 订单表
CREATE TABLE `shop_order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `count` int(11) NOT NULL COMMENT '数量',
  `order_no` varchar(30) NOT NULL COMMENT '订单编号',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';