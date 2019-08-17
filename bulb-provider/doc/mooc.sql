# 建库
CREATE DATABASE IF NOT EXISTS `mooc` CHARACTER SET = utf8mb4;

# 用户表
CREATE TABLE `sc_user` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `username` varchar(20) NOT NULL COMMENT '用户名',
   `password` varchar(32) NOT NULL COMMENT '密码',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

# 用户职业
CREATE TABLE `sc_user_profession` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL COMMENT '用户ID',
    `profession_named` varchar(20) NOT NULL COMMENT '职业称呼',
    `feature` varchar(20) NOT NULL COMMENT '职业特征',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '用户职业';

# 订单表
CREATE TABLE `shop_order_info` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL COMMENT '用户ID',
   `username` varchar(20) NOT NULL COMMENT '用户名',
   `count` int(11) NOT NULL COMMENT '数量',
   `order_no` varchar(30) NOT NULL COMMENT '订单编号',
   `create_time` datetime DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';