/*
Navicat MySQL Data Transfer

Source Server         : 192.168.3.192（物理库 root）
Source Server Version : 50717
Source Host           : 192.168.3.192:3306
Source Database       : ds0,db1

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-04-25 18:20:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- 数据库1
CREATE DATABASE `ds0`;

-- ----------------------------
-- Table structure for t_order0
-- ----------------------------
DROP TABLE IF EXISTS `t_order0`;
CREATE TABLE `t_order0` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order0
-- ----------------------------
INSERT INTO `t_order0` VALUES ('0', '0', '0');
INSERT INTO `t_order0` VALUES ('1', '0', '2');
INSERT INTO `t_order0` VALUES ('2', '0', '4');
INSERT INTO `t_order0` VALUES ('3', '0', '6');
INSERT INTO `t_order0` VALUES ('4', '0', '8');
INSERT INTO `t_order0` VALUES ('5', '0', '10');

-- ----------------------------
-- Table structure for t_order1
-- ----------------------------
DROP TABLE IF EXISTS `t_order1`;
CREATE TABLE `t_order1` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order1
-- ----------------------------
INSERT INTO `t_order1` VALUES ('0', '0', '1');
INSERT INTO `t_order1` VALUES ('1', '0', '3');
INSERT INTO `t_order1` VALUES ('2', '0', '5');
INSERT INTO `t_order1` VALUES ('3', '0', '7');
INSERT INTO `t_order1` VALUES ('4', '0', '9');

DROP TABLE IF EXISTS `t_order0`;
CREATE TABLE `t_order0` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order0
-- ----------------------------
INSERT INTO `t_order0` VALUES ('0', '1', '0');
INSERT INTO `t_order0` VALUES ('1', '1', '2');
INSERT INTO `t_order0` VALUES ('2', '1', '4');
INSERT INTO `t_order0` VALUES ('3', '1', '6');
INSERT INTO `t_order0` VALUES ('4', '1', '8');
INSERT INTO `t_order0` VALUES ('5', '1', '10');

-- 数据库2
CREATE DATABASE `ds1`;

-- ----------------------------
-- Table structure for t_order1
-- ----------------------------
DROP TABLE IF EXISTS `t_order1`;
CREATE TABLE `t_order1` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order1
-- ----------------------------
INSERT INTO `t_order1` VALUES ('0', '1', '1');
INSERT INTO `t_order1` VALUES ('1', '1', '3');
INSERT INTO `t_order1` VALUES ('2', '1', '5');
INSERT INTO `t_order1` VALUES ('3', '1', '7');
INSERT INTO `t_order1` VALUES ('4', '1', '9');

SET FOREIGN_KEY_CHECKS=1;
