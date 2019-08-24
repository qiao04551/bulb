-- CREATE DATABASE IF NOT EXISTS `sc_operation` CHARACTER SET = utf8mb4;

SET NAMES utf8mb4;

-- -----------------------------------------------
-- Table structure for sc_operation_printer_device
-- -----------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_device` (
  `id` int(11) UNSIGNED AUTO_INCREMENT,
  `device_name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `printer_device_type` tinyint NOT NULL DEFAULT 0 COMMENT '打印机类型：1-服务器 2-网络打印机',
  `address` varchar(20) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否状态：0-未删除 1-删除',
  `shop_id` int(11) NOT NULL DEFAULT 0 COMMENT '店铺id',
  `creator_id` int(11) DEFAULT 0 COMMENT '创建人',
  `creator_name` varchar(30) DEFAULT '' COMMENT '创建人姓名',
  `updator_id` int(11) DEFAULT 0 COMMENT '修改人',
  `updator_name` varchar(30) DEFAULT '' COMMENT '修改人姓名',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_printer_device_shop_id`(`shop_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '打印机表';


-- ------------------------------------------------
-- Table structure for sc_operation_printer_kitchen
-- ------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_kitchen` (
  `id` int(11) UNSIGNED AUTO_INCREMENT,
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '出票口名称',
  `printer_device_id` int NOT NULL DEFAULT 0 COMMENT '打印机id',
  `is_table` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否配置桌台区域：0-未配置 1-配置',
  `is_goods` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否配置打印菜品：0-未配置 1-配置',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-删除',
  `shop_id` int(11) NOT NULL DEFAULT 0 COMMENT '店铺id',
  `creator_id` int DEFAULT 0 COMMENT '创建人',
  `creator_name` varchar(30) DEFAULT '' COMMENT '创建人姓名',
  `updator_id` int DEFAULT 0 COMMENT '修改人',
  `updator_name` varchar(30) DEFAULT '' COMMENT '修改人姓名',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `idx_printer_kitchen_shop_id`(`shop_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '出票口';


-- --------------------------------------------------------------------
-- Table structure for sc_operation_printer_kitchen_document_type_rules
-- --------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_kitchen_document_type_rules` (
  `id` int(11) UNSIGNED AUTO_INCREMENT,
  `printer_kitchen_id` int(11) NOT NULL DEFAULT 0 COMMENT '出票口id',
  `document_type_id` int NOT NULL DEFAULT 0 COMMENT '票据类型',
  `number` tinyint NOT NULL DEFAULT 1 COMMENT '打印份数',
  `printer_type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '打印方式：1-一纸多菜 2-一纸一菜 3-各打一张',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `idx_kitchen_document_type_printer_kitchen_id`(`printer_kitchen_id`),
  KEY `idx_kitchen_document_type_document_type_id`(`document_type_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '出票口票据类型';


-- ------------------------------------------------------------
-- Table structure for sc_operation_printer_kitchen_table_rules
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_kitchen_table_rules` (
  `id` int(11) UNSIGNED AUTO_INCREMENT,
  `printer_kitchen_id` int(11) NOT NULL DEFAULT 0 COMMENT '出票口id',
  `table_id` int(11) NOT NULL DEFAULT 0 COMMENT '桌台号',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `idx_kitchen_table_rules_printer_kitchen_id`(`printer_kitchen_id`),
  KEY `idx_kitchen_table_rules_table_id`(`table_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '出票口配置桌台区域';


-- ------------------------------------------------------------
-- Table structure for sc_operation_printer_kitchen_goods_rules
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_kitchen_goods_rules` (
  `id` int(11) UNSIGNED AUTO_INCREMENT,
  `printer_kitchen_id` int(11) NOT NULL DEFAULT 0 COMMENT '出票口id',
  `goods_id` int(11) NOT NULL DEFAULT 0 COMMENT '商品id',
  `stock_id` int(11) NOT NULL DEFAULT 0 COMMENT '规格id',
  `is_delete` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `idx_kitchen_goods_rules_printer_kitchen_id`(`printer_kitchen_id`),
  KEY `idx_kitchen_goods_rules_goods_id`(`goods_id`),
  KEY `idx_kitchen_goods_rules_stock_id`(`stock_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '出票口配置打印菜品';


-- ------------------------------------------------------
-- Table structure for sc_operation_printer_document_type
-- ------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_document_type` (
  `id` INT UNSIGNED AUTO_INCREMENT,
  `name` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '类型名称',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-禁用 1-启用',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '打印模板类型表';

-- ---------------------------------------------
-- Record for sc_operation_printer_document_type
-- ---------------------------------------------
INSERT INTO `sc_operation_printer_document_type` (id, `name`, status, create_time, update_time) VALUES(1, '结账单', 1, now(), now());
INSERT INTO `sc_operation_printer_document_type` (id, `name`, status, create_time, update_time) VALUES(2, '客看单', 1, now(), now());
INSERT INTO `sc_operation_printer_document_type` (id, `name`, status, create_time, update_time) VALUES(3, '预结单', 1, now(), now());
INSERT INTO `sc_operation_printer_document_type` (id, `name`, status, create_time, update_time) VALUES(4, '消费清单', 1, now(), now());
INSERT INTO `sc_operation_printer_document_type` (id, `name`, status, create_time, update_time) VALUES(5, '厨总单', 1, now(), now());
INSERT INTO `sc_operation_printer_document_type` (id, `name`, status, create_time, update_time) VALUES(6, '堂口单', 1, now(), now());

-- --------------------------------------------------------
-- Table structure for sc_operation_printer_template_module
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_template_module` (
  `id` INT UNSIGNED AUTO_INCREMENT,
  `module_name` VARCHAR(10) NOT NULL COMMENT '模块名称',
  `module_describe` VARCHAR(30) NOT NULL COMMENT '模块描述',
  `document_type` INT NOT NULL COMMENT '模板类型',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '打印模板模块表';

-- -----------------------------------------------
-- Record for sc_operation_printer_template_module
-- -----------------------------------------------
-- 结账单模块
INSERT INTO `sc_operation_printer_template_module`(module_name, module_describe, document_type, sort, create_time, update_time) VALUES
('标题', '定义商户LOGO & 票据名称', 1, 1, now(), now()),
('基础信息', '配置桌台号、流水号、订单号等', 1, 2, now(), now()),
('订单明细', '配置商品、单价、数量、总价等', 1, 3, now(), now()),
('结算信息', '配置原价合计、优惠合计、汇总', 1, 4, now(), now()),
('支付信息', '配置付款方式、支付状态等', 1, 5, now(), now()),
('退货信息', '展示作废商品、反结账退货商品等', 1, 6, now(), now()),
('二维码', '配置二维码', 1, 7, now(), now()),
('底栏', '配置服务员、时间、商户信息等', 1, 8, now(), now());
-- 客看单模块
INSERT INTO `sc_operation_printer_template_module`(module_name, module_describe, document_type, sort, create_time, update_time) VALUES
('标题', '定义商户LOGO & 票据名称', 2, 1, now(), now()),
('基础信息', '配置桌台号、流水号、订单号等', 2, 2, now(), now()),
('订单明细', '配置商品、单价、数量、总价等', 2, 3, now(), now()),
('二维码', '配置二维码', 2, 4, now(), now()),
('底栏', '配置服务员、时间、商户信息等', 2, 5, now(), now());
-- 预结单模块
INSERT INTO `sc_operation_printer_template_module`(module_name, module_describe, document_type, sort, create_time, update_time) VALUES
('标题', '定义商户LOGO & 票据名称', 3, 1, now(), now()),
('基础信息', '配置桌台号、流水号、订单号等', 3, 2, now(), now()),
('订单明细', '配置商品、单价、数量、总价等', 3, 3, now(), now()),
('结算信息', '配置原价合计、优惠合计、汇总', 3, 4, now(), now()),
('作废信息', '展示作废商品', 3, 5, now(), now()),
('二维码', '配置二维码', 3, 6, now(), now()),
('底栏', '配置服务员、时间、商户信息等', 3, 7, now(), now());
-- 消费清单模块
INSERT INTO `sc_operation_printer_template_module`(module_name, module_describe, document_type, sort, create_time, update_time) VALUES
('标题', '定义商户LOGO & 票据名称', 4, 1, now(), now()),
('基础信息', '配置桌台号、流水号、订单号等', 4, 2, now(), now()),
('订单明细', '配置商品、单价、数量、总价等', 4, 3, now(), now()),
('结算信息', '配置原价合计、优惠合计、汇总', 4, 4, now(), now()),
('支付信息', '配置付款方式、支付状态、发票信息等', 4, 5, now(), now()),
('退货信息', '展示反结账退货商品等', 4, 6, now(), now()),
('二维码', '配置二维码', 4, 7, now(), now()),
('底栏', '配置服务员、时间、商户信息等', 4, 8, now(), now());
-- 厨总单模块
INSERT INTO `sc_operation_printer_template_module`(module_name, module_describe, document_type, sort, create_time, update_time) VALUES
('标题', '定义商户LOGO & 票据名称', 5, 1, now(), now()),
('基础信息', '配置桌台号、流水号、订单号等', 5, 2, now(), now()),
('订单明细', '配置商品、单价、数量、总价等', 5, 3, now(), now()),
('底栏', '配置服务员、时间、商户信息等', 5, 4, now(), now());
-- 堂口单
INSERT INTO `sc_operation_printer_template_module`(module_name, module_describe, document_type, sort, create_time, update_time) VALUES
('标题', '定义商户LOGO & 票据名称', 6, 1, now(), now()),
('基本信息', '配置桌台号、流水号、订单号等', 6, 2, now(), now()),
('订单明细', '配置商品、单价、数量、总价等', 6, 3, now(), now()),
('底栏', '配置服务员、时间、商户信息等', 6, 4, now(), now());

-- ---------------------------------------------------------
-- Table structure for sc_operation_printer_system_component
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_system_component`(
  `id` INTEGER UNSIGNED AUTO_INCREMENT,
  `module_id` INT NOT NULL DEFAULT 0 COMMENT '模块id',
  `parent_id` INT NOT NULL DEFAULT 0 COMMENT '父组件id',
  `ref_id` INT NOT NULL DEFAULT 0 COMMENT '引用id',
  `label` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '组件名称',
  `value` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '组件值',
  `value_style` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '组件值排版属性',
  `placeholder` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '占位符，例如：#{orderSource}',
  `type` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '组件类型：hidden-隐藏（refId） text-文本 grid-网格 title-表格标题 category-分类 head-表格头',
  `row` INT NOT NULL DEFAULT 0 COMMENT '行号',
  `column` INT NOT NULL DEFAULT 0 COMMENT '列号',
  `width` INT NOT NULL DEFAULT 0 COMMENT '宽度，百分比',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '序号',
  `is_enable` tinyint NOT NULL DEFAULT 1 COMMENT '启用状态：0 未启用 1已启用',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY(`id`),
  UNIQUE KEY `idx_temponent_component_id`(`id`),
  KEY `idx_template_component_module_id`(`module_id`),
  KEY `idx_template_component_parent_id`(`parent_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '打印模板系统组件表';


-- ------------------------------------------------
-- Record for sc_operation_printer_system_component
-- ------------------------------------------------
-- 结账单：moduleId = 1
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (101001, 1, 0, 0, '商户logo', '商户logo', '{"fontSize": 4,"textAlign":"center"}', '#{shopLogo}', 'text', 1, 1, 100, 1, now(), now()),
(101002, 1, 0, 0, '店铺名称', '店铺名称', '{"fontSize": 4,"textAlign":"center"}', '#{shopName}', 'text', 2, 1, 100, 2, now(), now()),
(101003, 1, 0, 0, '票据名称', '结账单', '{"fontSize": 4,"textAlign":"center"}', '#{ticketType}', 'text', 3, 1, 100, 3, now(), now()),
(101004, 1, 0, 0, '发票号', '发票号:xxxxxxxxx', '{"fontSize": 1,"textAlign":"left"}', '发票号:#{taxNumber}', 'text', 4, 1, 100, 4, now(), now());
-- 结账单：moduleId = 2
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (102001, 2, 0, 0, '桌台号', '桌台号:1', '{"fontSize": 1,"textAlign":"left"}', '桌台号:#{tableNum}', 'text', 1, 1, 70, 1, now(), now()),
(102002, 2, 0, 0, '流水号', '流水号:1', '{"fontSize": 1,"textAlign":"right"}', '流水号:#{serialNum}', 'text', 1, 2, 30, 2, now(), now()),
(102003, 2, 0, 0, '订单号', '订单号:201608221001', '{"fontSize": 1,"textAlign":"left"}', '订单号:#{orderNum}', 'text', 2, 1, 80, 3, now(), now()),
(102004, 2, 0, 0, '人数', '人数:2', '{"fontSize": 1,"textAlign":"right"}', '人数:#{peopleNum}', 'text', 2, 2, 20, 4, now(), now()),
(102005, 2, 0, 0, '税号', '税号:123232323', '{"fontSize": 1,"textAlign":"left"}', '税号:#{taxGSTRegNo}', 'text', 3, 1, 100, 5, now(), now()),
(102006, 2, 0, 0, '会员', '会员:张三(150XXXXXXXX)', '{"fontSize": 1,"textAlign":"left"}', '会员:#{memberNum}', 'text', 4, 1, 100, 6, now(), now()),
(102007, 2, 0, 0, '反结账原订单流水号', '反结账原订单流水号:2', '{"fontSize": 1,"textAlign":"left"}', '反结账原订单流水号:#{reverseAccountOldOrderNum}', 'text', 5, 1, 100, 7, now(), now()),
(102008, 2, 0, 0, '就餐人数详情', '成人2位，儿童1位，老人1位', '{"fontSize": 1,"textAlign":"left"}', '#{mealPeopleDetail}', 'text', 6, 1, 100, 8, now(), now());
-- 结账单：moduleId = 3
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (103001, 3, 0, 0, '商品表格', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(103002, 3, 103001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 2, 1, 100, 2, now(), now()),
-- 商品表格头
(103003, 3, 103001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 3, 1, 40, 3, now(), now()),
(103004, 3, 103001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 3, 2, 20, 4, now(), now()),
(103005, 3, 103001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 3, 3, 20, 5, now(), now()),
(103006, 3, 103001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 3, 4, 20, 6, now(), now()),
-- 商品表格体，用于占位
(103007, 3, 103001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 4, 1, 40, 7, now(), now()),
(103008, 3, 103001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 4, 2, 20, 8, now(), now()),
(103009, 3, 103001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 4, 3, 20, 9, now(), now()),
(103010, 3, 103001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 4, 4, 20, 10, now(), now()),
(103011, 3, 103001, 103014, '商品备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 5, 1, 100, 11, now(), now()),
(103012, 3, 103001, 0, '商品优惠名称', '【单品5折】', '{"fontSize": 1,"textAlign":"left"}', '#{dishDiscountName}', 'text', 6, 1, 60, 12, now(), now()),
(103013, 3, 103001, 0, '商品优惠价格', '-10.00', '{"fontSize": 1,"textAlign":"right"}', '#{dishDiscountPrice}', 'text', 6, 1, 40, 13, now(), now()),
(103014, 3, 0, 0, '单品备注', '控制商品里面的单品备注', '', '', 'hidden', 7, 1, 100, 14, now(), now()),
-- 合计
(103015, 3, 0, 0, '合计', '', '', '', 'grid', 7, 1, 100, 15, now(), now()),
(103016, 3, 103015, 0, '合计', '合计', '{"fontSize": 1,"textAlign":"left"}', '合计', 'text', 7, 1, 60, 16, now(), now()),
(103017, 3, 103015, 0, '合计数量', '1', '{"fontSize": 1,"textAlign":"right"}', '#{goodNumber}', 'text', 7, 2, 20, 17, now(), now()),
(103018, 3, 103015, 0, '合计价格', '18.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodPrice}', 'text', 7, 3, 20, 18, now(), now()),
(103019, 3, 0, 0, '整单备注', '整单备注:不要太辣', '{"fontSize": 1,"textAlign":"left"}', '整单备注:#{orderMemo}', 'text', 8, 1, 100, 19, now(), now()),
-- 优惠活动
(103020, 3, 0, 0, '优惠活动', '', '', '', 'grid', 9, 1, 100, 20, now(), now()),
(103021, 3, 103020, 0, '优惠大类', '手动折扣', '{"fontSize": 2,"textAlign":"left"}', '#{privilegeType}', 'title', 10, 1, 100, 21, now(), now()),
(103022, 3, 103020, 0, '优惠名称', '单品5折', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeTypeDetailName}', 'text', 11, 1, 70, 22, now(), now()),
(103023, 3, 103020, 0, '优惠数量', '1/份', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeTypeDetailNumber}', 'text', 11, 2, 10, 23, now(), now()),
(103024, 3, 103020, 0, '优惠价格', '-8.00', '{"fontSize": 2,"textAlign":"right"}', '#{privilegeTypeDetailNameAmount}', 'text', 11, 3, 20, 24, now(), now()),
-- 可参与手动折扣合计
(103025, 3, 0, 0, '可参与手动折扣合计', '', '', '#{privilegeType}', 'grid', 12, 1, 100, 25, now(), now()),
(103026, 3, 103025, 0, '参与折扣统计', '参与折扣统计', '{"fontSize": 2,"textAlign":"right"}', '参与折扣统计', 'title', 13, 1, 100, 26, now(), now()),
(103027, 3, 103025, 0, '不参与手动折扣', '[不参与手动折扣金额]', '{"fontSize": 1,"textAlign":"left"}', '[不参与手动折扣金额]', 'text', 14, 1, 60, 27, now(), now()),
(103028, 3, 103025, 0, '不参与手动折扣金额', '100.00', '{"fontSize": 1,"textAlign":"right"}', '#{noManualPrivilegeNumber}', 'text', 14, 2, 40, 28, now(), now()),
(103029, 3, 103025, 0, '可参与手动折扣', '[可参与手动折扣金额]', '{"fontSize": 1,"textAlign":"left"}', '[可参与手动折扣金额]', 'text', 15, 1, 60, 29, now(), now()),
(103030, 3, 103025, 0, '可参与手动折扣金额', '100.00', '{"fontSize": 1,"textAlign":"right"}', '#{manualPrivilegeNumber}', 'text', 15, 2, 40, 30, now(), now()),
-- 附加费
(103031, 3, 0, 0, '附加费', '', '', '', 'grid', 16, 1, 100, 31, now(), now()),
(103032, 3, 103031, 0, '附加费名称', '包间费', '{"fontSize": 1,"textAlign":"left"}', '#{surchargeName}', 'text', 17, 1, 70, 32, now(), now()),
(103033, 3, 103031, 0, '附加费金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{surchargeAmount}', 'text', 17, 2, 30, 33, now(), now()),
-- 消费税
(103034, 3, 0, 0, '消费税', '', '', '', 'grid', 18, 1, 100, 34, now(), now()),
(103035, 3, 103034, 0, '消费税税率', '消费税5%', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxName}', 'text', 19, 1, 70, 35, now(), now()),
(103036, 3, 103034, 0, '消费税金额', '6.00', '{"fontSize": 1,"textAlign":"right"}', '#{consumptionTaxAmount}', 'text', 19, 2, 30, 36, now(), now());
-- 结账单：moduleId = 4
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (104001, 4, 0, 0, '结算明细', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(104002, 4, 104001, 0, '商品原价合计', '商品原价合计', '{"fontSize": 1,"textAlign":"left"}', '商品原价合计', 'text', 2, 1, 70, 2, now(), now()),
(104003, 4, 104001, 0, '商品原价合计金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmount}', 'text', 2, 2, 30, 3, now(), now()),
(104004, 4, 104001, 0, '原始押金', '原始押金', '{"fontSize": 1,"textAlign":"left"}', '原始押金', 'text', 3, 1, 70, 4, now(), now()),
(104005, 4, 104001, 0, '原始押金金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{depositPay}', 'text', 3, 2, 30, 5, now(), now()),
(104006, 4, 104001, 0, '已退押金', '已退押金', '{"fontSize": 1,"textAlign":"left"}', '已退押金', 'text', 4, 1, 70, 6, now(), now()),
(104007, 4, 104001, 0, '已退押金金额', '-16.00', '{"fontSize": 1,"textAlign":"right"}', '#{depositRefund}', 'text', 4, 2, 30, 7, now(), now()),
(104008, 4, 104001, 0, '优惠合计', '优惠合计', '{"fontSize": 1,"textAlign":"left"}', '优惠合计', 'text', 5, 1, 70, 8, now(), now()),
(104009, 4, 104001, 0, '优惠合计金额', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{privilegeTotalAmount}', 'text', 5, 2, 30, 9, now(), now()),
(104010, 4, 104001, 0, '附加费合计', '附加费合计', '{"fontSize": 1,"textAlign":"left"}', '附加费合计', 'text', 6, 1, 70, 10, now(), now()),
(104011, 4, 104001, 0, '附加费合计金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{surchargeTotal}', 'text', 6, 2, 30, 11, now(), now()),
(104012, 4, 104001, 0, '四舍五入', '四舍五入', '{"fontSize": 1,"textAlign":"left"}', '四舍五入', 'text', 7, 1, 70, 12, now(), now()),
(104013, 4, 104001, 0, '四舍五入金额', '-0.50', '{"fontSize": 1,"textAlign":"right"}', '#{roundAmount}', 'text', 7, 2, 30, 13, now(), now()),
(104014, 4, 0, 0, '预付金', '', '', '', 'grid', 8, 1, 100, 14, now(), now()),
(104015, 4, 104014, 0, '预付金', '预付金', '{"fontSize": 4,"textAlign":"left"}', '#{prePayName}', 'text', 9, 1, 70, 15, now(), now()),
(104016, 4, 104014, 0, '预付金额', '20.00', '{"fontSize": 1,"textAlign":"right"}', '#{prePayCost}', 'text', 9, 2, 30, 16, now(), now()),
(104017, 4, 0, 0, '应付汇总', '', '', '', 'grid', 10, 1, 100, 17, now(), now()),
(104018, 4, 104017, 0, '应付金额', '应付金额', '{"fontSize": 4,"textAlign":"left"}', '应付金额', 'text', 11, 1, 70, 18, now(), now()),
(104019, 4, 104017, 0, '应付金额', '216.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmountPay}', 'text', 11, 2, 30, 19, now(), now());
-- 结账单：moduleId = 5
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (105001, 5, 0, 0, '财务处理', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(105002, 5, 105001, 0, '抹零', '抹零', '{"fontSize": 1,"textAlign":"left"}', '抹零', 'text', 2, 1, 70, 2, now(), now()),
(105003, 5, 105001, 0, '抹零金额', '-6.00', '{"fontSize": 1,"textAlign":"right"}', '#{quiteZero}', 'text', 2, 2, 30, 3, now(), now()),
(105004, 5, 105001, 0, '溢收', '溢收', '{"fontSize": 1,"textAlign":"left"}', '溢收', 'text', 3, 1, 70, 4, now(), now()),
(105005, 5, 105001, 0, '溢收金额', '5.00', '{"fontSize": 1,"textAlign":"right"}', '#{overflow}', 'text', 3, 2, 30, 5, now(), now()),
-- 支付优惠
(105006, 5, 0, 0, '支付优惠', '', '', '', 'grid', 4, 1, 100, 6, now(), now()),
(105007, 5, 105006, 0, '闪惠优惠', '闪惠优惠', '{"fontSize": 1,"textAlign":"left"}', '#{payDiscountName}', 'text', 5, 1, 70, 7, now(), now()),
(105008, 5, 105006, 0, '闪惠优惠金额', '-6.00', '{"fontSize": 1,"textAlign":"right"}', '#{payDiscountNumber}', 'text', 5, 2, 30, 8, now(), now()),
-- 实付汇总
(105009, 5, 0, 0, '实付汇总', '', '', '', 'grid', 6, 1, 100, 9, now(), now()),
(105010, 5, 105009, 0, '实付金额', '实付金额', '{"fontSize": 1,"textAlign":"left"}', '实付金额', 'text', 7, 1, 70, 10, now(), now()),
(105011, 5, 105009, 0, '实付金额', '210.00', '{"fontSize": 4,"textAlign":"right"}', '#{goodTotalAmountActual}', 'text', 7, 2, 30, 11, now(), now()),
-- 付款方式
(105012, 5, 0, 0, '付款方式', '', '', '', 'grid', 8, 1, 100, 12, now(), now()),
(105013, 5, 105012, 0, '付款方式', '现金', '{"fontSize": 1,"textAlign":"left"}', '#{paymentName}', 'text', 9, 1, 70, 13, now(), now()),
(105014, 5, 105012, 0, '付款金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{paymentAmount}', 'text', 9, 2, 30, 14, now(), now()),
-- 找零实收
(105015, 5, 0, 0, '找零实收', '', '', '', 'grid', 10, 1, 100, 15, now(), now()),
(105016, 5, 105015, 0, '付款合计', '付款合计', '{"fontSize": 1,"textAlign":"right"}', '付款合计', 'text', 11, 1, 70, 16, now(), now()),
(105017, 5, 105015, 0, '付款合计金额', '220.00', '{"fontSize": 1,"textAlign":"right"}', '#{payTotalAmount}', 'text', 11, 2, 30, 17, now(), now()),
(105018, 5, 105015, 0, '找零', '找零', '{"fontSize": 1,"textAlign":"right"}', '找零', 'text', 12, 1, 70, 18, now(), now()),
(105019, 5, 105015, 0, '找零金额', '-4.00', '{"fontSize": 1,"textAlign":"right"}', '#{changeAmount}', 'text', 12, 2, 30, 19, now(), now()),
(105020, 5, 105015, 0, '现金实收', '现金实收', '{"fontSize": 1,"textAlign":"right"}', '现金实收', 'text', 13, 1, 70, 20, now(), now()),
(105021, 5, 105015, 0, '现金实收金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{cashAmount}', 'text', 13, 2, 30, 21, now(), now()),
-- 消费税明细
(105022, 5, 0, 0, '消费税明细', '', '', '', 'grid', 14, 1, 100, 22, now(), now()),
(105023, 5, 105022, 0, '消费税明细', '消费税明细', '{"fontSize": 1,"textAlign":"left"}', '消费税明细', 'head', 15, 1, 40, 23, now(), now()),
(105024, 5, 105022, 0, '总额(计税)', '总额(计税)', '{"fontSize": 1,"textAlign":"left"}', '总额(计税)', 'head', 15, 2, 30, 24, now(), now()),
(105025, 5, 105022, 0, '税额', '税额', '{"fontSize": 1,"textAlign":"right"}', '税额', 'head', 15, 3, 30, 25, now(), now()),
(105026, 5, 105022, 0, '消费税明细', 'S=6%', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxDetail}', 'text', 16, 1, 40, 26, now(), now()),
(105027, 5, 105022, 0, '总额(计税)', '总额(计税)', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxTotal}', 'text', 16, 2, 30, 27, now(), now()),
(105028, 5, 105022, 0, '税额', '税额', '{"fontSize": 1,"textAlign":"right"}', '#{consumptionTaxDuty}', 'text', 16, 3, 30, 28, now(), now()),
-- 储值卡余额
(105029, 5, 0, 0, '储值卡余额', '', '', '', 'grid', 17, 1, 100, 29, now(), now()),
(105030, 5, 105029, 0, '储值卡余额', '储值卡余额', '{"fontSize": 2,"textAlign":"left"}', '储值卡余额', 'title', 18, 1, 100, 30, now(), now()),
(105031, 5, 105029, 0, '消费前', '消费前:300', '{"fontSize": 1,"textAlign":"left"}', '消费前:#{valueCardBefore}', 'text', 19, 1, 50, 31, now(), now()),
(105032, 5, 105029, 0, '消费后', '消费后:100', '{"fontSize": 1,"textAlign":"right"}', '消费后:#{valueCardAfter}', 'text', 19, 2, 50, 32, now(), now()),
(105033, 5, 0, 0, '签字区', '签字区', '{"fontSize": 2,"textAlign":"left"}', '签字区', 'text', 20, 1, 100, 33, now(), now());
-- 结账单：moduleId = 6
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (106001, 6, 0, 0, '作废商品', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(106002, 6, 106001, 0, '作废商品', '作废商品', '{"fontSize": 2,"textAlign":"right"}', '作废商品', 'title', 2, 1, 100, 2, now(), now()),
(106003, 6, 106001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryNameDiscard}', 'category', 3, 1, 100, 3, now(), now()),
(106004, 6, 106001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 4, 1, 40, 4, now(), now()),
(106005, 6, 106001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 4, 2, 20, 5, now(), now()),
(106006, 6, 106001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 4, 3, 20, 6, now(), now()),
(106007, 6, 106001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 4, 4, 20, 7, now(), now()),
(106008, 6, 106001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{goodDiscard}', 'text', 5, 1, 40, 8, now(), now()),
(106009, 6, 106001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{priceDiscard}', 'text', 5, 2, 20, 9, now(), now()),
(106010, 6, 106001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{numberDiscard}', 'text', 5, 3, 20, 10, now(), now()),
(106011, 6, 106001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amountDiscard}', 'text', 5, 4, 20, 11, now(), now()),
(106012, 6, 106001, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"right"}', '#{memoDiscard}', 'text', 6, 1, 100, 12, now(), now()),
(106013, 6, 106001, 0, '作废理由', '作废理由:不想要了', '{"fontSize": 1,"textAlign":"right"}', '#{reasonDiscard}', 'text', 7, 1, 100, 13, now(), now()),
-- 反结账原订单信息
(106014, 6, 0, 0, '反结账原订单信息', '', '', '', 'grid', 8, 1, 100, 14, now(), now()),
(106015, 6, 106014, 0, '反结账原订单信息', '反结账原订单信息', '{"fontSize": 2,"textAlign":"left"}', '反结账原订单信息', 'title', 9, 1, 100, 15, now(), now()),
(106016, 6, 106014, 0, '原单商品原价合计', '原单商品原价合计', '{"fontSize": 1,"textAlign":"left"}', '原单商品原价合计', 'text', 10, 1, 70, 16, now(), now()),
(106017, 6, 106014, 0, '原单商品原价合计金额', '216.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderGoodTotalAmount}', 'text', 10, 2, 30, 17, now(), now()),
(106018, 6, 106014, 0, '原单优惠合计', '原单优惠合计', '{"fontSize": 1,"textAlign":"left"}', '原单优惠合计', 'text', 11, 1, 70, 18, now(), now()),
(106019, 6, 106014, 0, '原单优惠合计金额', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderPrivilegeTotalAmount}', 'text', 11, 2, 30, 19, now(), now()),
(106020, 6, 106014, 0, '原单附加费合计', '原单附加费合计', '{"fontSize": 1,"textAlign":"left"}', '原单附加费合计', 'text', 12, 1, 70, 20, now(), now()),
(106021, 6, 106014, 0, '原单附加费合计金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderSurchargeTotal}', 'text', 12, 2, 30, 21, now(), now()),
(106022, 6, 106014, 0, '原单四舍五入', '原单四舍五入', '{"fontSize": 1,"textAlign":"left"}', '原单四舍五入', 'text', 13, 1, 70, 22, now(), now()),
(106023, 6, 106014, 0, '原单四舍五入金额', '-0.50', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderRoundAmount}', 'text', 13, 2, 30, 23, now(), now()),
(106024, 6, 106014, 0, '原单应付金额', '原单应付金额', '{"fontSize": 4,"textAlign":"left"}', '原单应付金额', 'text', 14, 1, 70, 24, now(), now()),
(106025, 6, 106014, 0, '原单应付金额', '216.00', '{"fontSize": 4,"textAlign":"right"}', '#{oldOrderGoodTotalAmountPay}', 'text', 14, 2, 30, 25, now(), now()),
-- 反结账退货商品
(106026, 6, 0, 0, '反结账退货商品', '', '', '', 'grid', 15, 1, 100, 26, now(), now()),
(106027, 6, 106026, 0, '反结账退货商品', '反结账退货商品', '{"fontSize": 1,"textAlign":"center"}', '反结账退货商品', 'title', 16, 1, 100, 27, now(), now()),
(106028, 6, 106026, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{reverseaccountGoodMiddleCategoryName}', 'category', 17, 1, 100, 28, now(), now()),
(106029, 6, 106026, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 18, 1, 40, 29, now(), now()),
(106030, 6, 106026, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 18, 2, 20, 30, now(), now()),
(106031, 6, 106026, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 18, 3, 20, 31, now(), now()),
(106032, 6, 106026, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 18, 4, 20, 32, now(), now()),
(106033, 6, 106026, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{goodDiscard}', 'text', 19, 1, 40, 33, now(), now()),
(106034, 6, 106026, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{priceDiscard}', 'text', 19, 2, 20, 34, now(), now()),
(106035, 6, 106026, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{numberDiscard}', 'text', 19, 3, 20, 35, now(), now()),
(106036, 6, 106026, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amountDiscard}', 'text', 19, 4, 20, 36, now(), now()),
(106037, 6, 106026, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"right"}', '#{memoDiscard}', 'text', 20, 1, 100, 37, now(), now()),
(106038, 6, 106026, 0, '作废理由', '作废理由:不想要了', '{"fontSize": 1,"textAlign":"right"}', '#{reasonDiscard}', 'text', 21, 1, 100, 38, now(), now()),
-- 反结账理由
(106039, 6, 0, 0, '反结账理由', '', '', '', 'grid', 22, 1, 100, 39, now(), now()),
(106040, 6, 106039, 0, '反结账理由', '反结账理由', '{"fontSize": 2,"textAlign":"left"}', '反结账理由', 'title', 23, 1, 100, 40, now(), now()),
(106041, 6, 106039, 0, '反结账理由', '收款错误需要进行反结账', '{"fontSize": 1,"textAlign":"left"}', '#{reverseAccountReason}', 'text', 24, 1, 100, 41, now(), now());
-- 结账单：moduleId = 7
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (107001, 7, 0, 0, '自定义二维码', '文案内容', '{"fontSize": 1,"textAlign":"center"}', '文案内容', 'text', 1, 1, 100, 1, now(), now());
-- 结账单：moduleId = 8
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES (108001, 8, 0, 0, '服务员', '服务员:李四', '{"fontSize": 1,"textAlign":"left"}', '服务员:#{waiter}', 'text', 1, 1, 50, 1, now(), now()),
(108002, 8, 0, 0, '操作员', '操作员:王五', '{"fontSize": 1,"textAlign":"right"}', '操作员:#{cashier}', 'text', 1, 2, 50, 2, now(), now()),
(108003, 8, 0, 0, '开单', '开单:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '开单:#{openBillingTime}', 'text', 2, 1, 100, 3, now(), now()),
(108004, 8, 0, 0, '打印', '打印:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '打印:#{printTime}', 'text', 3, 1, 100, 4, now(), now()),
(108005, 8, 0, 0, '操作时间', '操作:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '操作:#{operationTime}', 'text', 4, 1, 100, 5, now(), now()),
-- 商户信息
(108006, 8, 0, 0, '商户信息', '', '', '', 'grid', 5, 1, 100, 6, now(), now()),
(108007, 8, 108006, 0, '商户地址', 'XX市XX地址', '{"fontSize": 1,"textAlign":"center"}', '#{shopAddress}', 'text', 6, 1, 100, 7, now(), now()),
(108008, 8, 108006, 0, '商户电话号码', '15XXXXXXXXX', '{"fontSize": 1,"textAlign":"center"}', '#{shopPhone}', 'text', 7, 1, 100, 8, now(), now()),
(108009, 8, 108006, 0, '商户自定义内容', '欢迎下次光临', '{"fontSize": 1,"textAlign":"center"}', '欢迎下次光临', 'text', 8, 1, 100, 9, now(), now());

-- 客看单：module= 9
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(209001, 9, 0, 0, '商户logo', '', '{"fontSize": 1,"textAlign":"center"}', '#{shopLogo}', 'text', 1, 1, 100, 1, now(), now()),
(209002, 9, 0, 0, '店铺名称', '店铺名称', '{"fontSize": 1,"textAlign":"center"}', '#{shopName}', 'text', 2, 1, 100, 2, now(), now()),
(209003, 9, 0, 0, '票据名称', '客看单', '{"fontSize": 1,"textAlign":"center"}', '#{ticketType}', 'text', 3, 1, 100, 3, now(), now()),
-- module= 10
(210001, 10, 0, 0, '桌台号', '桌台号:1', '{"fontSize": 1,"textAlign":"left"}', '桌台号:#{tableNum}', 'text', 1, 1, 70, 1, now(), now()),
(210002, 10, 0, 0, '流水号', '流水号:1', '{"fontSize": 1,"textAlign":"right"}', '流水号:#{serialNum}', 'text', 1, 2, 30, 2, now(), now()),
(210003, 10, 0, 0, '订单号', '订单号:201608221001', '{"fontSize": 1,"textAlign":"left"}', '订单号:#{orderNum}', 'text', 2, 1, 80, 3, now(), now()),
(210004, 10, 0, 0, '人数', '人数:2', '{"fontSize": 1,"textAlign":"right"}', '人数:#{peopleNum}', 'text', 2, 2, 20, 4, now(), now()),
(210005, 10, 0, 0, '会员', '会员:张三(150XXXXXXXX)', '{"fontSize": 1,"textAlign":"left"}', '会员:#{memberNum}', 'text', 3, 1, 100, 5, now(), now()),
(210006, 10, 0, 0, '就餐人数详情', '成人2位，儿童1位，老人1位', '{"fontSize": 1,"textAlign":"left"}', '#{mealPeopleDetail}', 'text', 4, 1, 100, 6, now(), now());
-- module= 11
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(211001, 11, 0, 0, '商品表格', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(211002, 11, 211001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 2, 1, 100, 2, now(), now()),
(211003, 11, 211001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 3, 1, 40, 3, now(), now()),
(211004, 11, 211001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 3, 2, 20, 4, now(), now()),
(211005, 11, 211001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 3, 3, 20, 5, now(), now()),
(211006, 11, 211001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 3, 4, 20, 6, now(), now()),
(211007, 11, 211001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 4, 1, 40, 7, now(), now()),
(211008, 11, 211001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 4, 2, 20, 8, now(), now()),
(211009, 11, 211001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 4, 3, 20, 9, now(), now()),
(211010, 11, 211001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 4, 4, 20, 10, now(), now()),
(211011, 11, 211001, 211012, '商品备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 5, 1, 100, 11, now(), now()),
(211012, 11, 0, 0, '单品备注', '', '', '', 'hidden', 6, 1, 100, 12, now(), now()),
(211013, 11, 0, 0, '合计', '', '', '', 'grid', 7, 1, 100, 13, now(), now()),
(211014, 11, 211013, 0, '合计', '合计', '{"fontSize": 1,"textAlign":"left"}', '合计', 'text', 8, 1, 60, 14, now(), now()),
(211015, 11, 211013, 0, '合计数量', '1', '{"fontSize": 1,"textAlign":"right"}', '#{goodNumber}', 'text', 8, 2, 20, 15, now(), now()),
(211016, 11, 211013, 0, '合计价格', '18.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodPrice}', 'text', 8, 3, 20, 16, now(), now()),
(211017, 11, 0, 0, '改单前商品信息', '', '', '', 'grid', 9, 1, 100, 17, now(), now()),
(211018, 11, 211017, 0, '改单前商品信息', '改单前商品信息', '{"fontSize": 1,"textAlign":"left"}', '改单前商品信息', 'title', 10, 1, 100, 18, now(), now()),
(211019, 11, 211017, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 11, 1, 100, 19, now(), now()),
(211020, 11, 211017, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 12, 1, 100, 40, now(), now()),
(211021, 11, 211017, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 12, 2, 20, 21, now(), now()),
(211022, 11, 211017, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 12, 3, 20, 22, now(), now()),
(211023, 11, 211017, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 12, 4, 20, 23, now(), now()),
(211024, 11, 211017, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 13, 1, 40, 24, now(), now()),
(211025, 11, 211017, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 13, 2, 20, 25, now(), now()),
(211026, 11, 211017, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 13, 3, 20, 26, now(), now()),
(211027, 11, 211017, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 13, 4, 20, 27, now(), now()),
(211028, 11, 211017, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 14, 1, 100, 28, now(), now()),
(211029, 11, 0, 0, '整单备注', '整单备注:不要太辣', '{"fontSize": 1,"textAlign":"left"}', '整单备注:#{orderMemo}', 'text', 15, 1, 100, 29, now(), now());
-- module= 12
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(212001, 12, 0, 0, '自定义二维码', '文案内容', '{"fontSize": 1,"textAlign":"center"}', '文案内容', 'text', 1, 1, 100, 1, now(), now()),
-- module= 13
(213001, 13, 0, 0, '服务员', '服务员:李四', '{"fontSize": 1,"textAlign":"left"}', '服务员:#{waiter}', 'text', 1, 1, 50, 1, now(), now()),
(213002, 13, 0, 0, '操作员', '操作员:王五', '{"fontSize": 1,"textAlign":"right"}', '操作员:#{cashier}', 'text', 1, 2, 50, 2, now(), now()),
(213003, 13, 0, 0, '开单', '开单:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '开单:#{openBillingTime}', 'text', 2, 1, 100, 3, now(), now()),
(213004, 13, 0, 0, '打印', '打印:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '打印:#{printTime}', 'text', 3, 1, 100, 4, now(), now()),
(213005, 13, 0, 0, '操作时间', '操作:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '操作:#{operationTime}', 'text', 4, 1, 100, 5, now(), now()),
(213006, 13, 0, 0, '商户信息', '', '', '', 'grid', 5, 1, 100, 6, now(), now()),
(213007, 13, 213006, 0, '商户地址', 'XX市XX地址', '{"fontSize": 1,"textAlign":"center"}', '#{shopAddress}', 'text', 6, 1, 100, 7, now(), now()),
(213008, 13, 213006, 0, '商户电话号码', '15XXXXXXXXX', '{"fontSize": 1,"textAlign":"center"}', '#{shopPhone}', 'text', 7, 1, 100, 8, now(), now()),
(213009, 13, 213006, 0, '商户自定义内容', '欢迎下次光临', '{"fontSize": 1,"textAlign":"center"}', '欢迎下次光临', 'text', 8, 1, 100, 9, now(), now());

-- 预结单 moduleId = 14
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(314001, 14, 0, 0, '商户logo', '', '{"fontSize": 1,"textAlign":"center"}', '#{shopLogo}', 'text', 1, 1, 100, 1, now(), now()),
(314002, 14, 0, 0, '店铺名称', '店铺名称', '{"fontSize": 1,"textAlign":"center"}', '#{shopName}', 'text', 2, 1, 100, 2, now(), now()),
(314003, 14, 0, 0, '票据名称', '预结单', '{"fontSize": 1,"textAlign":"center"}', '#{ticketType}', 'text', 3, 1, 100, 3, now(), now()),
-- moduleId = 15
(315001, 15, 0, 0, '桌台号', '桌台号:1', '{"fontSize": 1,"textAlign":"left"}', '桌台号:#{tableNum}', 'text', 1, 1, 70, 1, now(), now()),
(315002, 15, 0, 0, '流水号', '流水号:1', '{"fontSize": 1,"textAlign":"right"}', '流水号:#{serialNum}', 'text', 1, 2, 30, 2, now(), now()),
(315003, 15, 0, 0, '订单号', '订单号:201608221001', '{"fontSize": 1,"textAlign":"left"}', '订单号:#{orderNum}', 'text', 2, 1, 80, 3, now(), now()),
(315004, 15, 0, 0, '人数', '人数:2', '{"fontSize": 1,"textAlign":"right"}', '人数:#{peopleNum}', 'text', 2, 2, 20, 5, now(), now()),
(315005, 15, 0, 0, '会员', '会员:张三(150XXXXXXXX)', '{"fontSize": 1,"textAlign":"left"}', '会员:#{memberNum}', 'text', 3, 1, 100, 5, now(), now()),
(315006, 15, 0, 0, '反结账原订单流水号', '反结账原订单流水号:2', '{"fontSize": 1,"textAlign":"left"}', '反结账原订单流水号:#{reverseAccountOldOrderNum}', 'text', 4, 1, 100, 6, now(), now()),
(315007, 15, 0, 0, '就餐人数详情', '成人2位，儿童1位，老人1位', '{"fontSize": 1,"textAlign":"left"}', '#{mealPeopleDetail}', 'text', 5, 1, 100, 7, now(), now());
-- moduleId = 16
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(316001, 16, 0, 0, '商品表格', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(316002, 16, 316001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 2, 1, 100, 2, now(), now()),
(316003, 16, 316001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 3, 1, 40, 3, now(), now()),
(316004, 16, 316001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 3, 2, 20, 4, now(), now()),
(316005, 16, 316001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 3, 3, 20, 5, now(), now()),
(316006, 16, 316001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 3, 4, 20, 6, now(), now()),
(316007, 16, 316001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 4, 1, 40, 7, now(), now()),
(316008, 16, 316001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 4, 2, 20, 8, now(), now()),
(316009, 16, 316001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 4, 3, 20, 9, now(), now()),
(316010, 16, 316001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 4, 4, 20, 10, now(), now()),
(316011, 16, 316001, 316014, '商品备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 5, 1, 100, 11, now(), now()),
(316012, 16, 316001, 0, '商品优惠名称', '【单品5折】', '{"fontSize": 1,"textAlign":"left"}', '#{dishDiscountName}', 'text', 6, 1, 60, 12, now(), now()),
(316013, 16, 316001, 0, '商品优惠价格', '-10.00', '{"fontSize": 1,"textAlign":"right"}', '#{dishDiscountPrice}', 'text', 6, 2, 40, 13, now(), now()),
(316014, 16, 0, 0, '单品备注', '控制商品里面的单品备注', '', '', 'hidden', 7, 1, 100, 14, now(), now()),
(316015, 16, 0, 0, '合计', '', '', '', 'grid', 8, 1, 100, 15, now(), now()),
(316016, 16, 316015, 0, '合计', '合计', '{"fontSize": 1,"textAlign":"left"}', '合计', 'text', 9, 1, 60, 16, now(), now()),
(316017, 16, 316015, 0, '合计数量', '1', '{"fontSize": 1,"textAlign":"right"}', '#{goodNumber}', 'text', 9, 2, 20, 17, now(), now()),
(316018, 16, 316015, 0, '合计价格', '18.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodPrice}', 'text', 9, 3, 20, 18, now(), now()),
(316019, 16, 0, 0, '整单备注', '整单备注:不要太辣', '{"fontSize": 1,"textAlign":"left"}', '整单备注:#{orderMemo}', 'text', 10, 1, 100, 19, now(), now()),
(316020, 16, 0, 0, '优惠活动', '', '', '', 'grid', 11, 1, 100, 20, now(), now()),
(316021, 16, 316020, 0, '优惠大类', '手动折扣', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeType}', 'title', 12, 1, 100, 21, now(), now()),
(316022, 16, 316020, 0, '优惠名称', '单品5折', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeTypeDetailName}', 'text', 13, 1, 70, 22, now(), now()),
(316023, 16, 316020, 0, '优惠数量', '1/份', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeTypeDetailNumber}', 'text', 13, 2, 10, 23, now(), now()),
(316024, 16, 316020, 0, '优惠价格', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{privilegeTypeDetailNameAmount}', 'text', 13, 3, 20, 24, now(), now()),
(316025, 16, 0, 0, '可参与手动折扣合计', '', '', '', 'grid', 14, 1, 100, 25, now(), now()),
(316026, 16, 316025, 0, '参与折扣统计', '参与折扣统计', '{"fontSize": 1,"textAlign":"left"}', '参与折扣统计', 'title', 15, 1, 100, 26, now(), now()),
(316027, 16, 316025, 0, '不参与手动折扣', '[不参与手动折扣金额]', '{"fontSize": 1,"textAlign":"left"}', '[不参与手动折扣金额]', 'text', 16, 1, 60, 27, now(), now()),
(316028, 16, 316025, 0, '不参与手动折扣金额', '100.00', '{"fontSize": 1,"textAlign":"right"}', '#{noManualPrivilegeNumber}', 'text', 16, 2, 40, 28, now(), now()),
(316029, 16, 0, 0, '附加费', '', '', '', 'grid', 17, 1, 100, 29, now(), now()),
(316030, 16, 316029, 0, '附加费名称', '包间费', '{"fontSize": 1,"textAlign":"left"}', '#{surchargeName}', 'text', 18, 1, 70, 30, now(), now()),
(316031, 16, 316029, 0, '附加费金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{surchargeAmount}', 'text', 18, 2, 30, 31, now(), now()),
(316032, 16, 0, 0, '消费税', '', '', '', 'grid', 19, 1, 100, 32, now(), now()),
(316033, 16, 316032, 0, '消费税税率', '消费税5%', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxName}', 'text', 20, 1, 70, 33, now(), now()),
(316034, 16, 316032, 0, '消费税金额', '6.00', '{"fontSize": 1,"textAlign":"right"}', '#{consumptionTaxAmount}', 'text', 20, 2, 30, 34, now(), now());
-- moduleId = 17
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(317001, 17, 0, 0, '结算明细', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(317002, 17, 317001, 0, '商品原价合计', '商品原价合计', '{"fontSize": 1,"textAlign":"left"}', '商品原价合计', 'text', 2, 1, 70, 2, now(), now()),
(317003, 17, 317001, 0, '商品原价合计金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmount}', 'text', 2, 2, 30, 3, now(), now()),
(317004, 17, 317001, 0, '原始押金', '原始押金', '{"fontSize": 1,"textAlign":"left"}', '原始押金', 'text', 3, 1, 70, 4, now(), now()),
(317005, 17, 317001, 0, '原始押金金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{depositPay}', 'text', 3, 2, 30, 5, now(), now()),
(317006, 17, 317001, 0, '已退押金', '已退押金', '{"fontSize": 1,"textAlign":"left"}', '已退押金', 'text', 4, 1, 70, 6, now(), now()),
(317007, 17, 317001, 0, '已退押金金额', '-16.00', '{"fontSize": 1,"textAlign":"right"}', '#{depositRefund}', 'text', 4, 2, 30, 7, now(), now()),
(317008, 17, 317001, 0, '优惠合计', '优惠合计', '{"fontSize": 1,"textAlign":"left"}', '优惠合计', 'text', 5, 1, 70, 8, now(), now()),
(317009, 17, 317001, 0, '优惠合计金额', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{privilegeTotalAmount}', 'text', 5, 2, 30, 9, now(), now()),
(317010, 17, 317001, 0, '附加费合计', '附加费合计', '{"fontSize": 1,"textAlign":"left"}', '附加费合计', 'text', 6, 1, 70, 10, now(), now()),
(317011, 17, 317001, 0, '附加费合计金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{surchargeTotal}', 'text', 6, 2, 30, 11, now(), now()),
(317012, 17, 317001, 0, '四舍五入', '四舍五入', '{"fontSize": 1,"textAlign":"left"}', '四舍五入', 'text', 7, 1, 70, 12, now(), now()),
(317013, 17, 317001, 0, '四舍五入金额', '-0.50', '{"fontSize": 1,"textAlign":"right"}', '#{roundAmount}', 'text', 7, 2, 30, 13, now(), now()),
(317014, 17, 0, 0, '应付汇总', '', '', '', 'grid', 8, 1, 100, 14, now(), now()),
(317015, 17, 317014, 0, '应付金额', '应付金额', '{"fontSize": 1,"textAlign":"left"}', '应付金额', 'text', 9, 1, 70, 15, now(), now()),
(317016, 17, 317014, 0, '应付金额', '216.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmountPay}', 'text', 9, 2, 30, 16, now(), now());
-- moduleId = 18
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(318001, 18, 0, 0, '作废商品', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(318002, 18, 318001, 0, '作废商品', '作废商品', '{"fontSize": 1,"textAlign":"left"}', '作废商品', 'title', 2, 1, 100, 2, now(), now()),
(318003, 18, 318001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryNameDiscard}', 'category', 3, 1, 100, 3, now(), now()),
(318004, 18, 318001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 4, 1, 40, 4, now(), now()),
(318005, 18, 318001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 4, 2, 20, 5, now(), now()),
(318006, 18, 318001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 4, 3, 20, 6, now(), now()),
(318007, 18, 318001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 4, 4, 20, 7, now(), now()),
(318008, 18, 318001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{goodDiscard}', 'text', 5, 1, 40, 8, now(), now()),
(318009, 18, 318001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{priceDiscard}', 'text', 5, 2, 20, 9, now(), now()),
(318010, 18, 318001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{numberDiscard}', 'text', 5, 3, 20, 10, now(), now()),
(318011, 18, 318001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amountDiscard}', 'text', 5, 4, 20, 11, now(), now()),
(318012, 18, 318001, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memoDiscard}', 'text', 6, 1, 100, 12, now(), now()),
(318013, 18, 318001, 0, '作废理由', '作废理由:不想要了', '{"fontSize": 1,"textAlign":"left"}', '#{reasonDiscard}', 'text', 7, 1, 100, 13, now(), now()),
-- moduleId = 19
(319001, 19, 0, 0, '支付二维码', '扫我买单有惊喜哦~', '{"fontSize": 1,"textAlign":"center"}', '扫我买单有惊喜哦~', 'text', 8, 1, 100, 1, now(), now()),
(319002, 19, 0, 0, '自定义二维码', '文案内容', '{"fontSize": 1,"textAlign":"center"}', '文案内容', 'text', 1, 1, 100, 2, now(), now()),
-- moduleId = 20
(320001, 20, 0, 0, '服务员', '服务员:李四', '{"fontSize": 1,"textAlign":"left"}', '服务员:#{waiter}', 'text', 1, 1, 50, 1, now(), now()),
(320002, 20, 0, 0, '操作员', '操作员:王五', '{"fontSize": 1,"textAlign":"right"}', '操作员:#{cashier}', 'text', 1, 2, 50, 2, now(), now()),
(320003, 20, 0, 0, '开单', '开单:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '开单:#{openBillingTime}', 'text', 2, 1, 100, 3, now(), now()),
(320004, 20, 0, 0, '打印', '打印:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '打印:#{printTime}', 'text', 3, 1, 100, 4, now(), now()),
(320005, 20, 0, 0, '操作时间', '操作:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '操作:#{operationTime}', 'text', 4, 1, 100, 5, now(), now()),
(320006, 20, 0, 0, '商户信息', '', '', '', 'grid', 5, 1, 100, 6, now(), now()),
(320007, 20, 0, 0, '商户地址', 'XX市XX地址', '{"fontSize": 1,"textAlign":"center"}', '#{shopAddress}', 'text', 6, 1, 100, 7, now(), now()),
(320008, 20, 0, 0, '商户电话号码', '15XXXXXXXXX', '{"fontSize": 1,"textAlign":"center"}', '#{shopPhone}', 'text', 7, 1, 100, 8, now(), now()),
(320009, 20, 0, 0, '商户自定义内容', '欢迎下次光临', '{"fontSize": 1,"textAlign":"center"}', '欢迎下次光临', 'text', 8, 1, 100, 9, now(), now());


-- 消费清单
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(321001, 21, 0, 0, '商户logo', '商户logo', '{"fontSize": 1,"textAlign":"center"}', '#{shopLogo}', 'text', 1, 1, 100, 1, now(), now()),
(321002, 21, 0, 0, '店铺名称', '店铺名称', '{"fontSize": 1,"textAlign":"center"}', '#{shopName}', 'text', 2, 1, 100, 2, now(), now()),
(321003, 21, 0, 0, '票据名称', '消费清单', '{"fontSize": 1,"textAlign":"center"}', '#{ticketType}', 'text', 3, 1, 100, 3, now(), now()),
(321004, 21, 0, 0, '发票号', '发票号:xxxxxxxxx', '{"fontSize": 1,"textAlign":"center"}', '发票号:#{taxNumber}', 'text', 4, 1, 100, 4, now(), now()),
-- moduleId = 22
(322001, 22, 0, 0, '付款状态', '订单已付款', '{"fontSize": 1,"textAlign":"center"}', '#{orderStatus}', 'text', 1, 1, 100, 1, now(), now()),
(322002, 22, 0, 0, '取餐号/桌台号', '桌台号:001', '{"fontSize": 1,"textAlign":"left"}', '#{numType}:#{tableNum}', 'text', 2, 1, 70, 2, now(), now()),
(322003, 22, 0, 0, '流水号', '流水号:1', '{"fontSize": 1,"textAlign":"right"}', '流水号:#{serialNum}', 'text', 2, 2, 30, 3, now(), now()),
(322004, 22, 0, 0, '第三方流水号', '&lt;第三方&gt;流水号:001', '{"fontSize": 1,"textAlign":"center"}', '#{thirdClient}#{thirdSerialNum}', 'text', 3, 1, 100, 4, now(), now()),
(322005, 22, 0, 0, '第三方订单号', '<第三方>订单号:001', '{"fontSize": 1,"textAlign":"left"}', '#{thirdClient}订单号:#{thirdOrderNum}', 'text', 4, 1, 100, 5, now(), now()),
(322006, 22, 0, 0, '订单来源', '外送-收银终端', '{"fontSize": 1,"textAlign":"left"}', '#{orderSource}', 'text', 5, 1, 80, 6, now(), now()),
(322007, 22, 0, 0, '人数', '人数:2', '{"fontSize": 1,"textAlign":"right"}', '人数:#{peopleNum}', 'text', 5, 2, 20, 7, now(), now()),
(322008, 22, 0, 0, '订单号', '订单号:201608221001', '{"fontSize": 1,"textAlign":"left"}', '订单号:#{orderNum}', 'text', 6, 1, 100, 8, now(), now()),
(322009, 22, 0, 0, '税号', '税号:123232323', '{"fontSize": 1,"textAlign":"left"}', '税号:#{taxGSTRegNo}', 'text', 7, 1, 100, 9, now(), now()),
(322010, 22, 0, 0, '顾客', '顾客:张三(155****5555)', '{"fontSize": 1,"textAlign":"left"}', '顾客:#{customerInfo}', 'text', 8, 1, 100, 10, now(), now()),
(322011, 22, 0, 0, '反结账原订单号', '反结账原订单号:201608221002', '{"fontSize": 1,"textAlign":"left"}', '反结账原订单号:#{refundOrderNumber}', 'text', 9, 1, 100, 11, now(), now()),
(322012, 22, 0, 0, '收货地址', 'XX省XX市XX地方', '{"fontSize": 1,"textAlign":"left"}', '#{deliveryAddress}', 'text', 10, 1, 100, 12, now(), now()),
(322013, 22, 0, 0, '收货人姓名', '张三', '{"fontSize": 1,"textAlign":"left"}', '#{receiverName}', 'text', 11, 1, 100, 13, now(), now()),
(322014, 22, 0, 0, '收货人电话', '155****5555', '{"fontSize": 1,"textAlign":"left"}', '#{receiverPhone}', 'text', 12, 1, 100, 14, now(), now()),
(322015, 22, 0, 0, '送达信息', 'MM-DD HH:mm送达', '{"fontSize": 1,"textAlign":"left"}', '#{transitTime}', 'text', 13, 1, 100, 15, now(), now());
-- moduleId = 23
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(323001, 23, 0, 0, '商品表格', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(323002, 23, 323001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 2, 1, 100, 2, now(), now()),
(323003, 23, 323001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 3, 1, 40, 3, now(), now()),
(323004, 23, 323001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 3, 1, 20, 4, now(), now()),
(323005, 23, 323001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 3, 1, 20, 5, now(), now()),
(323006, 23, 323001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 3, 1, 20, 6, now(), now()),
(323007, 23, 323001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 4, 1, 40, 7, now(), now()),
(323008, 23, 323001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 4, 2, 20, 8, now(), now()),
(323009, 23, 323001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 4, 3, 20, 9, now(), now()),
(323010, 23, 323001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 4, 4, 20, 10, now(), now()),
(323011, 23, 323001, 323014, '商品备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 5, 1, 100, 11, now(), now()),
(323012, 23, 323001, 0, '商品优惠名称', '【单品5折】', '{"fontSize": 1,"textAlign":"left"}', '#{dishDiscountName}', 'text', 6, 1, 60, 12, now(), now()),
(323013, 23, 323001, 0, '商品优惠价格', '-10.00', '{"fontSize": 1,"textAlign":"right"}', '#{dishDiscountPrice}', 'text', 6, 2, 40, 13, now(), now()),
(323014, 23, 0, 0, '单品备注', '控制商品里面的单品备注', '', '', 'hidden', 7, 1, 100, 14, now(), now()),
(323015, 23, 0, 0, '合计', '', '', '', 'grid', 8, 1, 100, 15, now(), now()),
(323016, 23, 323015, 0, '合计', '合计', '{"fontSize": 1,"textAlign":"left"}', '合计', 'text', 9, 1, 60, 16, now(), now()),
(323017, 23, 323015, 0, '合计数量', '1', '{"fontSize": 1,"textAlign":"right"}', '#{goodNumber}', 'text', 9, 2, 20, 17, now(), now()),
(323018, 23, 323015, 0, '合计价格', '18.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodPrice}', 'text', 9, 3, 20, 18, now(), now()),
(323019, 23, 0, 0, '整单备注', '整单备注:不要太辣', '{"fontSize": 1,"textAlign":"left"}', '整单备注:#{orderMemo}', 'text', 10, 1, 100, 19, now(), now()),
(323020, 23, 0, 0, '优惠活动', '', '', '', 'grid', 11, 1, 100, 20, now(), now()),
(323021, 23, 323020, 0, '优惠大类', '手动折扣', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeType}', 'title', 12, 1, 100, 21, now(), now()),
(323022, 23, 323020, 0, '优惠名称', '单品5折', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeTypeDetailName}', 'text', 13, 1, 50, 22, now(), now()),
(323023, 23, 323020, 0, '优惠数量', '1/份', '{"fontSize": 1,"textAlign":"left"}', '#{privilegeTypeDetailNumber}', 'text', 13, 2, 20, 23, now(), now()),
(323024, 23, 323020, 0, '优惠价格', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{privilegeTypeDetailNameAmount}', 'text', 13, 3, 30, 24, now(), now()),
(323025, 23, 0, 0, '可参与手动折扣合计', '', '', '', 'grid', 14, 1, 100, 25, now(), now()),
(323026, 23, 323025, 0, '参与折扣统计', '参与折扣统计', '{"fontSize": 1,"textAlign":"center"}', '参与折扣统计', 'title', 15, 1, 100, 26, now(), now()),
(323027, 23, 323025, 0, '不参与手动折扣', '[不参与手动折扣金额]', '{"fontSize": 1,"textAlign":"left"}', '[不参与手动折扣金额]', 'text', 16, 1, 60, 27, now(), now()),
(323028, 23, 323025, 0, '不参与手动折扣金额', '100.00', '{"fontSize": 1,"textAlign":"right"}', '#{noManualPrivilegeNumber}', 'text', 16, 2, 40, 28, now(), now()),
(323029, 23, 323025, 0, '可参与手动折扣', '[可参与手动折扣金额]', '{"fontSize": 1,"textAlign":"left"}', '[可参与手动折扣金额]', 'text', 17, 1, 60, 29, now(), now()),
(323030, 23, 323025, 0, '可参与手动折扣金额', '100.00', '{"fontSize": 1,"textAlign":"right"}', '#{manualPrivilegeNumber}', 'text', 17, 2, 40, 30, now(), now()),
(323031, 23, 0, 0, '附加费', '', '', '', 'grid', 18, 1, 100, 31, now(), now()),
(323032, 23, 323031, 0, '附加费名称', '包间费', '{"fontSize": 1,"textAlign":"left"}', '#{surchargeName}', 'text', 19, 1, 60, 32, now(), now()),
(323033, 23, 323031, 0, '附加费金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{surchargeAmount}', 'text', 19, 2, 40, 33, now(), now()),
(323034, 23, 0, 0, '消费税', '', '', '', 'grid', 20, 1, 100, 34, now(), now()),
(323035, 23, 323034, 0, '消费税税率', '消费税5%', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxName}', 'text', 21, 1, 70, 35, now(), now()),
(323036, 23, 323034, 0, '消费税金额', '6.00', '{"fontSize": 1,"textAlign":"right"}', '#{consumptionTaxAmount}', 'text', 21, 2, 30, 36, now(), now());
-- moduleId = 24
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(324001, 24, 0, 0, '结算明细', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(324002, 24, 324001, 0, '商品原价合计', '商品原价合计', '{"fontSize": 1,"textAlign":"left"}', '商品原价合计', 'text', 2, 1, 50, 2, now(), now()),
(324003, 24, 324001, 0, '商品原价合计金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmount}', 'text', 2, 2, 50, 3, now(), now()),
(324004, 24, 324001, 0, '原始押金', '原始押金', '{"fontSize": 1,"textAlign":"left"}', '原始押金', 'text', 3, 1, 50, 4, now(), now()),
(324005, 24, 324001, 0, '原始押金金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{depositPay}', 'text', 3, 2, 50, 5, now(), now()),
(324006, 24, 324001, 0, '已退押金', '已退押金', '{"fontSize": 1,"textAlign":"left"}', '已退押金', 'text', 4, 1, 50, 6, now(), now()),
(324007, 24, 324001, 0, '已退押金金额', '-16.00', '{"fontSize": 1,"textAlign":"right"}', '#{depositRefund}', 'text', 4, 2, 50, 7, now(), now()),
(324008, 24, 324001, 0, '优惠合计', '优惠合计', '{"fontSize": 1,"textAlign":"left"}', '优惠合计', 'text', 5, 1, 50, 8, now(), now()),
(324009, 24, 324001, 0, '优惠合计金额', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{privilegeTotalAmount}', 'text', 5, 2, 50, 9, now(), now()),
(324010, 24, 324001, 0, '附加费合计', '附加费合计', '{"fontSize": 1,"textAlign":"left"}', '附加费合计', 'text', 6, 1, 50, 10, now(), now()),
(324011, 24, 324001, 0, '附加费合计金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{surchargeTotal}', 'text', 6, 2, 50, 11, now(), now()),
(324012, 24, 324001, 0, '四舍五入', '四舍五入', '{"fontSize": 1,"textAlign":"left"}', '四舍五入', 'text', 7, 1, 50, 12, now(), now()),
(324013, 24, 324001, 0, '四舍五入金额', '-0.50', '{"fontSize": 1,"textAlign":"right"}', '#{roundAmount}', 'text', 7, 2, 50, 13, now(), now()),
(324014, 24, 0, 0, '预付金', '', '', '', 'grid', 8, 1, 100, 14, now(), now()),
(324015, 24, 324014, 0, '预付金', '预付金', '{"fontSize": 1,"textAlign":"left"}', '#{prePayName}', 'text', 9, 1, 70, 15, now(), now()),
(324016, 24, 324014, 0, '预付金额', '20.00', '{"fontSize": 1,"textAlign":"center"}', '#{prePayCost}', 'text', 9, 2, 30, 16, now(), now()),
(324017, 24, 0, 0, '应付汇总', '', '', '', 'grid', 10, 1, 100, 17, now(), now()),
(324018, 24, 324017, 0, '应付金额', '应付金额', '{"fontSize": 1,"textAlign":"left"}', '应付金额', 'text', 11, 1, 50, 18, now(), now()),
(324019, 24, 324017, 0, '应付金额', '216.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmountPay}', 'text', 11, 2, 50, 19, now(), now());
-- moduleId = 25
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(325001, 25, 0, 0, '财务处理', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(325002, 25, 325001, 0, '抹零', '抹零', '{"fontSize": 1,"textAlign":"left"}', '抹零', 'text', 2, 1, 50, 2, now(), now()),
(325003, 25, 325001, 0, '抹零金额', '-6.00', '{"fontSize": 1,"textAlign":"right"}', '#{quiteZero}', 'text', 2, 2, 50, 3, now(), now()),
(325004, 25, 325001, 0, '溢收', '溢收', '{"fontSize": 1,"textAlign":"left"}', '溢收', 'text', 3, 1, 50, 4, now(), now()),
(325005, 25, 325001, 0, '溢收金额', '5.00', '{"fontSize": 1,"textAlign":"right"}', '#{overflow}', 'text', 4, 2, 50, 5, now(), now()),
(325006, 25, 0, 0, '支付优惠', '', '', '', 'grid', 5, 1, 100, 6, now(), now()),
(325007, 25, 325006, 0, '闪惠优惠', '闪惠优惠', '{"fontSize": 1,"textAlign":"left"}', '#{payDiscountName}', 'text', 6, 1, 50, 7, now(), now()),
(325008, 25, 325006, 0, '闪惠优惠金额', '-6.00', '{"fontSize": 1,"textAlign":"right"}', '#{payDiscountNumber}', 'text', 6, 2, 50, 8, now(), now()),
(325009, 25, 0, 0, '实付汇总', '', '', '', 'grid', 7, 1, 100, 9, now(), now()),
(325010, 25, 325009, 0, '实付金额', '实付金额', '{"fontSize": 1,"textAlign":"left"}', '实付金额', 'text', 8, 1, 50, 10, now(), now()),
(325011, 25, 325009, 0, '实付金额', '210.00', '{"fontSize": 1,"textAlign":"right"}', '#{goodTotalAmountActual}', 'text', 8, 2, 50, 11, now(), now()),
(325012, 25, 0, 0, '付款方式', '', '', '', 'grid', 9, 1, 100, 12, now(), now()),
(325013, 25, 325012, 0, '付款方式', '现金', '{"fontSize": 1,"textAlign":"left"}', '#{paymentName}', 'text', 10, 1, 50, 13, now(), now()),
(325014, 25, 325012, 0, '付款金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{paymentAmount}', 'text', 10, 2, 50, 14, now(), now()),
(325015, 25, 0, 0, '找零实收', '', '', '', 'grid', 11, 1, 100, 15, now(), now()),
(325016, 25, 325015, 0, '付款合计', '付款合计', '{"fontSize": 1,"textAlign":"left"}', '付款合计', 'text', 12, 1, 50, 16, now(), now()),
(325017, 25, 325015, 0, '付款合计金额', '220.00', '{"fontSize": 1,"textAlign":"right"}', '#{payTotalAmount}', 'text', 12, 2, 50, 17, now(), now()),
(325018, 25, 325015, 0, '找零', '找零', '{"fontSize": 1,"textAlign":"left"}', '找零', 'text', 13, 1, 50, 18, now(), now()),
(325019, 25, 325015, 0, '找零金额', '-4.00', '{"fontSize": 1,"textAlign":"right"}', '#{changeAmount}', 'text', 13, 2, 50, 19, now(), now()),
(325020, 25, 325015, 0, '现金实收', '现金实收', '{"fontSize": 1,"textAlign":"left"}', '现金实收', 'text', 14, 1, 50, 20, now(), now()),
(325021, 25, 325015, 0, '现金实收金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{cashAmount}', 'text', 14, 2, 50, 21, now(), now()),
(325022, 25, 0, 0, '消费税明细', '', '', '', 'grid', 15, 1, 100, 22, now(), now()),
(325023, 25, 325022, 0, '消费税明细', '消费税明细', '{"fontSize": 1,"textAlign":"left"}', '消费税明细', 'head', 16, 1, 40, 23, now(), now()),
(325024, 25, 325022, 0, '总额(计税)', '总额(计税)', '{"fontSize": 1,"textAlign":"left"}', '总额(计税)', 'head', 16, 2, 30, 24, now(), now()),
(325025, 25, 325022, 0, '税额', '税额', '{"fontSize": 1,"textAlign":"right"}', '#{shopLogo}', 'head', 16, 3, 30, 25, now(), now()),
(325026, 25, 325022, 0, '消费税明细', 'S=6%', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxDetail}', 'text', 17, 1, 40, 26, now(), now()),
(325027, 25, 325022, 0, '总额(计税)', '200', '{"fontSize": 1,"textAlign":"left"}', '#{consumptionTaxTotal}', 'text', 17, 2, 30, 27, now(), now()),
(325028, 25, 325022, 0, '税额', '12', '{"fontSize": 1,"textAlign":"right"}', '#{consumptionTaxDuty}', 'text', 17, 3, 30, 28, now(), now()),
(325029, 25, 0, 0, '第三方交易号(比格专用)', '', '', '', 'grid', 18, 1, 100, 29, now(), now()),
(325030, 25, 325029, 0, '交易号', '微信交易号:1234567890', '{"fontSize": 1,"textAlign":"left"}', '#{transactionNo}', 'text', 19, 1, 100, 30, now(), now()),
(325031, 25, 325029, 0, '交易号状态', '交易成功', '{"fontSize": 1,"textAlign":"left"}', '#{transactionState}', 'text', 20, 1, 100, 30, now(), now()),
(325032, 25, 0, 0, '储值卡余额', '', '', '', 'grid', 21, 1, 100, 31, now(), now()),
(325033, 25, 325032, 0, '储值卡余额', '储值卡余额', '{"fontSize": 1,"textAlign":"left"}', '储值卡余额', 'title', 22, 1, 100, 32, now(), now()),
(325034, 25, 325032, 0, '消费前', '消费前:300', '{"fontSize": 1,"textAlign":"left"}', '消费前:#{valueCardBefore}', 'text', 23, 1, 50, 33, now(), now()),
(325035, 25, 325032, 0, '消费后', '消费后:100', '{"fontSize": 1,"textAlign":"right"}', '消费后:#{valueCardAfter}', 'text', 23, 2, 50, 34, now(), now()),
(325036, 25, 0, 0, '发票信息', '发票:公司抬头', '{"fontSize": 1,"textAlign":"left"}', '发票:#{invoice}', 'text', 24, 1, 100, 35, now(), now()),
(325037, 25, 0, 0, '签字区', '签字区', '{"fontSize": 1,"textAlign":"left"}', '签字区', 'text', 25, 1, 100, 36, now(), now());
-- moduleId = 26
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(326001, 26, 0, 0, '反结账原订单信息', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(326002, 26, 326001, 0, '反结账原订单信息', '反结账原订单信息', '{"fontSize": 1,"textAlign":"left"}', '反结账原订单信息', 'title', 2, 1, 100, 2, now(), now()),
(326003, 26, 326001, 0, '原单商品原价合计', '原单商品原价合计', '{"fontSize": 1,"textAlign":"left"}', '原单商品原价合计', 'text', 3, 1, 70, 3, now(), now()),
(326004, 26, 326001, 0, '原单商品原价合计金额', '216.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderGoodTotalAmount}', 'text', 3, 2, 30, 4, now(), now()),
(326005, 26, 326001, 0, '原单原始押金', '原单原始押金', '{"fontSize": 1,"textAlign":"center"}', '原单原始押金', 'text', 4, 1, 50, 5, now(), now()),
(326006, 26, 326001, 0, '原单原始押金金额', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldDepositPay}', 'text', 4, 2, 50, 6, now(), now()),
(326007, 26, 326001, 0, '原单已退押金', '原单已退押金', '{"fontSize": 1,"textAlign":"left"}', '原单已退押金', 'text', 5, 1, 50, 7, now(), now()),
(326008, 26, 326001, 0, '原单已退押金金额', '-16.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldDepositRefund}', 'text', 5, 2, 50, 8, now(), now()),
(326009, 26, 326001, 0, '原单优惠合计', '原单优惠合计', '{"fontSize": 1,"textAlign":"left"}', '原单优惠合计', 'text', 6, 1, 70, 9, now(), now()),
(326010, 26, 326001, 0, '原单优惠合计金额', '-8.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderPrivilegeTotalAmount}', 'text', 6, 2, 30, 10, now(), now()),
(326011, 26, 326001, 0, '原单附加费合计', '原单附加费合计', '{"fontSize": 1,"textAlign":"left"}', '原单附加费合计', 'text', 7, 1, 70, 11, now(), now()),
(326012, 26, 326001, 0, '原单附加费合计金额', '200.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderSurchargeTotal}', 'text', 7, 2, 30, 12, now(), now()),
(326013, 26, 326001, 0, '原单四舍五入', '原单四舍五入', '{"fontSize": 1,"textAlign":"left"}', '原单四舍五入', 'text', 8, 1, 70, 13, now(), now()),
(326014, 26, 326001, 0, '原单四舍五入金额', '-0.50', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderRoundAmount}', 'text', 8, 2, 30, 14, now(), now()),
(326015, 26, 326001, 0, '原单应付金额', '原单应付金额', '{"fontSize": 1,"textAlign":"left"}', '原单应付金额', 'text', 9, 1, 70, 15, now(), now()),
(326016, 26, 326001, 0, '原单应付金额', '216.00', '{"fontSize": 1,"textAlign":"right"}', '#{oldOrderGoodTotalAmountPay}', 'text', 9, 2, 30, 16, now(), now()),
(326017, 26, 0, 0, '反结账退货商品', '', '', '', 'grid', 10, 1, 100, 17, now(), now()),
(326018, 26, 326017, 0, '反结账退货商品', '反结账退货商品', '{"fontSize": 1,"textAlign":"left"}', '反结账退货商品', 'title', 10, 1, 100, 18, now(), now()),
(326019, 26, 326017, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{reverseaccountGoodMiddleCategoryName}', 'category', 11, 1, 100, 19, now(), now()),
(326020, 26, 326017, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 12, 1, 40, 20, now(), now()),
(326021, 26, 326017, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 12, 2, 20, 21, now(), now()),
(326022, 26, 326017, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 12, 3, 20, 22, now(), now()),
(326023, 26, 326017, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 12, 4, 20, 23, now(), now()),
(326024, 26, 326017, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{reverseaccountGood}', 'text', 13, 1, 40, 24, now(), now()),
(326025, 26, 326017, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{reverseaccountPrice}', 'text', 13, 2, 20, 25, now(), now()),
(326026, 26, 326017, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{reverseaccountNumber}', 'text', 13, 3, 20, 26, now(), now()),
(326027, 26, 326017, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{reverseaccountAmount}', 'text', 13, 4, 20, 27, now(), now()),
(326028, 26, 326017, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{reverseaccountMemo}', 'text', 14, 1, 100, 28, now(), now()),
(326029, 26, 326017, 0, '作废理由', '作废理由:不想要了', '{"fontSize": 1,"textAlign":"left"}', '#{reverseaccountReason}', 'text', 15, 1, 100, 29, now(), now()),
(326030, 26, 0, 0, '反结账理由', '', '', '', 'grid', 16, 1, 100, 30, now(), now()),
(326031, 26, 326030, 0, '反结账理由', '反结账理由', '{"fontSize": 1,"textAlign":"left"}', '反结账理由', 'title', 17, 1, 100, 31, now(), now()),
(326032, 26, 326030, 0, '反结账理由', '收款错误需要进行反结账', '{"fontSize": 1,"textAlign":"left"}', '#{reverseAccountReason}', 'text', 18, 1, 100, 32, now(), now());
-- moduleId = 27
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(327001, 27, 0, 0, 'On Moblie配送二维码', '二维码ID', '{"fontSize": 1,"textAlign":"center"}', '#{codeId}', 'text', 1, 1, 100, 1, now(), now()),
(327002, 27, 0, 0, '自定义二维码', '扫码关注公众号,有惊喜哟~', '{"fontSize": 1,"textAlign":"center"}', '扫码关注公众号,有惊喜哟~', 'text', 2, 1, 100, 2, now(), now()),
(327003, 27, 0, 0, '顺丰派送二维码', '文案内容', '{"fontSize": 1,"textAlign":"center"}', '文案内容', 'text', 3, 1, 100, 3, now(), now()),
(327004, 27, 0, 0, '开柜条形码', '扫码开柜', '{"fontSize": 1,"textAlign":"center"}', '扫码开柜', 'text', 4, 1, 100, 4, now(), now()),
(327005, 27, 0, 0, '京东到家取货码', '京东配送取货扫码', '{"fontSize": 1,"textAlign":"center"}', '京东配送取货扫码', 'text', 5, 1, 100, 5, now(), now()),
-- moduleId = 28
(328001, 28, 0, 0, '服务员', '服务员:李四', '{"fontSize": 1,"textAlign":"left"}', '服务员:#{waiter}', 'text', 1, 1, 50, 1, now(), now()),
(328002, 28, 0, 0, '操作员', '操作员:王五', '{"fontSize": 1,"textAlign":"right"}', '操作员:#{cashier}', 'text', 1, 2, 50, 2, now(), now()),
(328003, 28, 0, 0, '开单', '开单:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '开单:#{openBillingTime}', 'text', 2, 1, 100, 3, now(), now()),
(328004, 28, 0, 0, '打印', '打印:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"right"}', '打印:#{printTime}', 'text', 3, 1, 100, 4, now(), now()),
(328005, 28, 0, 0, '操作时间', '操作:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '操作:#{operationTime}', 'text', 4, 1, 100, 5, now(), now()),
(328006, 28, 0, 0, '商户信息', '', '', '', 'grid', 5, 1, 100, 6, now(), now()),
(328007, 28, 328006, 0, '商户地址', 'XX市XX地址', '{"fontSize": 1,"textAlign":"center"}', '#{shopAddress}', 'text', 6, 1, 100, 7, now(), now()),
(328008, 28, 328006, 0, '商户电话号码', '15XXXXXXXXX', '{"fontSize": 1,"textAlign":"center"}', '#{shopPhone}', 'text', 7, 1, 100, 8, now(), now()),
(328009, 28, 328006, 0, '商户自定义内容', '欢迎下次光临', '{"fontSize": 1,"textAlign":"center"}', '欢迎下次光临', 'text', 8, 1, 100, 9, now(), now());

-- 厨总单
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(329001, 29, 0, 0, '票据名称', '厨总单', '{"fontSize": 1,"textAlign":"center"}', '#{ticketType}', 'text', 1, 1, 100, 1, now(), now()),
-- moduleId = 30
(330001, 30, 0, 0, '取餐号/桌台号', '桌台号:001', '{"fontSize": 1,"textAlign":"left"}', '#{numType}:#{tableNum}', 'text', 1, 1, 100, 1, now(), now()),
(330002, 30, 0, 0, '订单来源', '外送-商户收银终端', '{"fontSize": 1,"textAlign":"left"}', '#{orderSource}', 'text', 2, 1, 100, 2, now(), now()),
(330003, 30, 0, 0, '第三方流水号', '&lt;第三方&gt;流水号:001', '{"fontSize": 1,"textAlign":"left"}', '#{thirdClient}流水号:#{thirdSerialNum}', 'text', 3, 1, 100, 3, now(), now()),
(330004, 30, 0, 0, '流水号', '流水号:1', '{"fontSize": 1,"textAlign":"left"}', '流水号:#{serialNum}', 'text', 4, 1, 50, 4, now(), now()),
(330005, 30, 0, 0, '人数', '人数:2', '{"fontSize": 1,"textAlign":"right"}', '人数:#{peopleNum}', 'text', 4, 2, 50, 5, now(), now()),
(330006, 30, 0, 0, '送达时间', '送达时间:MM-DD HH:mm送达', '{"fontSize": 1,"textAlign":"left"}', '#{transitTime}', 'text', 5, 1, 100, 6, now(), now()),
(330007, 30, 0, 0, '出单序号', '出单序号:1', '{"fontSize": 1,"textAlign":"left"}', '出单序号:#{orderSerialNumber}', 'text', 6, 1, 100, 7, now(), now()),
-- moduleId = 31
(331001, 31, 0, 0, '整单备注', '整单备注:不要太辣', '{"fontSize": 1,"textAlign":"left"}', '整单备注:#{orderMemo}', 'text', 1, 1, 100, 1, now(), now()),
(331002, 31, 0, 0, '整单操作理由', '退货理由:上菜太慢,要求退菜', '{"fontSize": 1,"textAlign":"left"}', '#{orderOperationReason}', 'text', 2, 1, 100, 2, now(), now()),
(331003, 31, 0, 0, '商品表格', '', '', '', 'grid', 3, 1, 100, 3, now(), now()),
(331004, 31, 331003, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 4, 1, 100, 4, now(), now()),
(331005, 31, 331003, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 5, 1, 40, 5, now(), now()),
(331006, 31, 331003, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 5, 2, 20, 6, now(), now()),
(331007, 31, 331003, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 5, 3, 20, 7, now(), now()),
(331008, 31, 331003, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 5, 4, 20, 8, now(), now()),
(331009, 31, 331003, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 6, 1, 40, 9, now(), now()),
(331010, 31, 331003, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 6, 2, 20, 10, now(), now()),
(331011, 31, 331003, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 6, 3, 20, 11, now(), now()),
(331012, 31, 331003, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 6, 4, 20, 12, now(), now()),
(331013, 31, 331003, 331015, '商品备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 7, 1, 100, 13, now(), now()),
(331014, 31, 331003, 331016, '商品操作理由', '【作废理由:太甜了】', '{"fontSize": 1,"textAlign":"left"}', '#{dishOperationReason}', 'text', 8, 1, 100, 14, now(), now()),
(331015, 31, 0, 0, '单品备注', '控制商品里面的单品备注', '', '', 'hidden', 9, 1, 100, 15, now(), now()),
(331016, 31, 0, 0, '单品操作理由', '控制商品里面的单品操作理由', '', '', 'hidden', 10, 1, 100, 16, now(), now()),
(331017, 31, 0, 0, '合计', '', '', '', 'grid', 11, 1, 100, 17, now(), now()),
(331018, 31, 331017, 0, '合计', '合计', '{"fontSize": 1,"textAlign":"left"}', '合计', 'text', 12, 1, 70, 18, now(), now()),
(331019, 31, 331017, 0, '合计数量', '1', '{"fontSize": 1,"textAlign":"right"}', '#{goodNumber}', 'text', 12, 2, 30, 19, now(), now()),
(331020, 31, 0, 0, '改单前商品信息', '', '', '', 'grid', 13, 1, 100, 20, now(), now()),
(331021, 31, 331020, 0, '改单前商品信息', '改单前商品信息', '{"fontSize": 1,"textAlign":"left"}', '改单前商品信息', 'title', 14, 1, 100, 21, now(), now()),
(331022, 31, 331020, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 15, 1, 100, 22, now(), now()),
(331023, 31, 331020, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 16, 1, 80, 23, now(), now()),
(331024, 31, 331020, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 16, 2, 20, 24, now(), now()),
(331025, 31, 331020, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 17, 1, 80, 25, now(), now()),
(331026, 31, 331020, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 17, 2, 20, 26, now(), now()),
(331027, 31, 331020, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 18, 1, 100, 27, now(), now()),
-- moduleId = 32
(332001, 32, 0, 0, '服务员', '服务员:李四', '{"fontSize": 1,"textAlign":"left"}', '服务员:#{waiter}', 'text', 1, 1, 50, 1, now(), now()),
(332002, 32, 0, 0, '操作员', '操作员:王五', '{"fontSize": 1,"textAlign":"right"}', '操作员:#{cashier}', 'text', 1, 2, 50, 2, now(), now()),
(332003, 32, 0, 0, '开单', '开单:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '开单:#{openBillingTime}', 'text', 2, 1, 100, 3, now(), now()),
(332004, 32, 0, 0, '打印', '打印:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"right"}', '打印:#{printTime}', 'text', 3, 2, 100, 4, now(), now()),
(332005, 32, 0, 0, '操作时间', '操作:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '操作:#{operationTime}', 'text', 4, 1, 100, 5, now(), now());

-- 堂口单
INSERT INTO `sc_operation_printer_system_component`(id, module_id, parent_id, ref_id, label, `value`, value_style, placeholder, `type`, row, `column`, width, sort, create_time, update_time)
VALUES
(333001, 33, 0, 0, '票据名称', '堂口单', '{"fontSize": 1,"textAlign":"center"}', '#{ticketType}', 'text', 1, 1, 100, 1, now(), now()),
-- moduleId = 34
(334001, 34, 0, 0, '桌台号', '桌台号:001', '{"fontSize": 1,"textAlign":"left"}', '桌台号:#{tableNum}', 'text', 1, 1, 100, 1, now(), now()),
(334002, 34, 0, 0, '人数', '人数:2', '{"fontSize": 1,"textAlign":"left"}', '人数:#{peopleNum}', 'text', 2, 1, 50, 2, now(), now()),
(334003, 34, 0, 0, '订单来源', '外送-商户收银终端', '{"fontSize": 1,"textAlign":"left"}', '#{orderSource}', 'text', 3, 1, 100, 3, now(), now()),
(334004, 34, 0, 0, '第三方流水号', '第三方流水号:001', '{"fontSize": 1,"textAlign":"left"}', '#{thirdClient}流水号:#{thirdSerialNum}', 'text', 4, 1, 100, 4, now(), now()),
(334005, 34, 0, 0, '流水号', '流水号:1', '{"fontSize": 1,"textAlign":"left"}', '流水号:#{serialNum}', 'text', 5, 1, 100, 5, now(), now()),
(334006, 34, 0, 0, '送达时间', '送达时间:MM-DD HH:mm送达', '{"fontSize": 1,"textAlign":"center"}', '#{transitTime}', 'text', 6, 1, 100, 6, now(), now()),
(334007, 34, 0, 0, '出单序号', '出单序号:1', '{"fontSize": 1,"textAlign":"left"}', '出单序号:#{orderSerialNumber}', 'text', 7, 1, 50, 7, now(), now()),
(334008, 34, 0, 0, '出单页码', '页码:1/5', '{"fontSize": 1,"textAlign":"right"}', '页码:#{orderPageNumber}', 'text', 8, 2, 50, 8, now(), now()),
-- moduleId = 35
(335001, 35, 0, 0, '商品表格', '', '', '', 'grid', 1, 1, 100, 1, now(), now()),
(335002, 35, 335001, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 2, 1, 100, 2, now(), now()),
(335003, 35, 335001, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 3, 1, 40, 3, now(), now()),
(335004, 35, 335001, 0, '单价', '单价', '{"fontSize": 1,"textAlign":"right"}', '单价', 'head', 3, 2, 20, 4, now(), now()),
(335005, 35, 335001, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 3, 3, 20, 5, now(), now()),
(335006, 35, 335001, 0, '价格', '价格', '{"fontSize": 1,"textAlign":"right"}', '价格', 'head', 3, 4, 20, 6, now(), now()),
(335007, 35, 335001, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 4, 1, 40, 7, now(), now()),
(335008, 35, 335001, 0, '单价', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{price}', 'text', 4, 2, 20, 8, now(), now()),
(335009, 35, 335001, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 4, 3, 20, 9, now(), now()),
(335010, 35, 335001, 0, '价格', '16.00', '{"fontSize": 1,"textAlign":"right"}', '#{amount}', 'text', 4, 4, 20, 10, now(), now()),
(335011, 35, 335001, 335013, '商品备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 5, 1, 100, 11, now(), now()),
(335012, 35, 335001, 335014, '商品操作理由', '【作废理由:太甜了】', '{"fontSize": 1,"textAlign":"center"}', '#{dishOperationReason}', 'text', 6, 1, 100, 12, now(), now()),
(335013, 35, 0, 0, '单品备注', '控制商品里面的单品备注', '', '', 'hidden', 7, 1, 100, 13, now(), now()),
(335014, 35, 0, 0, '单品操作理由', '控制商品里面的单品操作理由', '', '', 'hidden', 8, 1, 100, 14, now(), now()),
(335015, 35, 0, 0, '整单备注', '整单备注:不要太辣', '{"fontSize": 1,"textAlign":"left"}', '整单备注:#{orderMemo}', 'text', 9, 1, 100, 15, now(), now()),
(335016, 35, 0, 0, '整单操作理由', '退货理由:上菜太慢,要求退菜', '{"fontSize": 1,"textAlign":"left"}', '#{orderOperationReason}', 'text', 10, 1, 100, 16, now(), now()),
(335017, 35, 0, 0, '改单前商品信息', '', '', '', 'grid', 11, 1, 100, 17, now(), now()),
(335018, 35, 335017, 0, '改单前商品信息', '改单前商品信息', '{"fontSize": 1,"textAlign":"left"}', '改单前商品信息', 'title', 12, 1, 100, 18, now(), now()),
(335019, 35, 335017, 0, '中类名称', '中类(价格)', '{"fontSize": 1,"textAlign":"center"}', '#{goodMiddleCategoryName}', 'category', 13, 1, 100, 19, now(), now()),
(335020, 35, 335017, 0, '商品', '商品', '{"fontSize": 1,"textAlign":"left"}', '商品', 'head', 14, 1, 80, 20, now(), now()),
(335021, 35, 335017, 0, '数量', '数量', '{"fontSize": 1,"textAlign":"right"}', '数量', 'head', 14, 2, 20, 21, now(), now()),
(335022, 35, 335017, 0, '商品', '1.回锅肉', '{"fontSize": 1,"textAlign":"left"}', '#{good}', 'text', 15, 1, 80, 22, now(), now()),
(335023, 35, 335017, 0, '数量', '1/份', '{"fontSize": 1,"textAlign":"right"}', '#{number}', 'text', 15, 2, 20, 23, now(), now()),
(335024, 35, 335017, 0, '备注', '【备注:多放肉】', '{"fontSize": 1,"textAlign":"left"}', '#{memo}', 'text', 16, 1, 100, 24, now(), now()),
(335025, 35, 0, 0, '传菜码(堂口单一菜一纸)', '传菜码', '{"fontSize": 1,"textAlign":"center"}', '传菜码', 'text', 17, 1, 100, 25, now(), now()),
-- moduleId = 36
(336001, 36, 0, 0, '服务员', '服务员:李四', '{"fontSize": 1,"textAlign":"left"}', '服务员:#{waiter}', 'text', 1, 1, 50, 1, now(), now()),
(336002, 36, 0, 0, '操作员', '操作员:王五', '{"fontSize": 1,"textAlign":"right"}', '操作员:#{cashier}', 'text', 1, 2, 50, 2, now(), now()),
(336003, 36, 0, 0, '开单', '开单:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '开单:#{openBillingTime}', 'text', 2, 1, 100, 3, now(), now()),
(336004, 36, 0, 0, '打印', '打印:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"right"}', '打印:#{printTime}', 'text', 3, 1, 100, 4, now(), now()),
(336005, 36, 0, 0, '操作时间', '操作:YYYY-MM-DD HH:MM:SS', '{"fontSize": 1,"textAlign":"left"}', '操作:#{operationTime}', 'text', 4, 1, 100, 5, now(), now());


-- ----------------------------------------------------------
-- Table structrue for sc_operation_printer_template_document
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_template_document` (
  `id` INT UNSIGNED AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '模板名称',
  `document_type` INT NOT NULL DEFAULT 0 COMMENT '文档类型',
  `url` VARCHAR(100) DEFAULT '' COMMENT '文档预览图',
  `status` INT NOT NULL DEFAULT 0 COMMENT '模板状态：0-删除 1-启用 2-禁用',
  `shop_id` INT NOT NULL DEFAULT 0 COMMENT '店铺id',
  `creator_id` INT NOT NULL DEFAULT 0 COMMENT '添加者id',
  `creator_name` VARCHAR(20) DEFAULT '' COMMENT '添加者名称',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY(`id`),
  KEY `idx_template_document_shop_id`(`shop_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8mb4 COMMENT '打印模板表';


-- -------------------------------------------------
-- Record for sc_operation_printer_template_document
-- -------------------------------------------------
INSERT INTO `sc_operation_printer_template_document`(id, `name`, document_type, url, status, shop_id, creator_id, creator_name, create_time, update_time)
VALUES
(1, '系统模板-结账单', 1, '', 1, 0, 0, 'system', now(), now()),
(2, '系统模板-客看单', 2, '', 1, 0, 0, 'system', now(), now()),
(3, '系统模板-预结单', 3, '', 1, 0, 0, 'system', now(), now()),
(4, '系统模板-消费清单', 4, '', 1, 0, 0, 'system', now(), now()),
(5, '系统模板-厨总单', 5, '', 1, 0, 0, 'system', now(), now()),
(6, '系统模板-堂口单', 6, '', 1, 0, 0, 'system', now(), now());


-- ---------------------------------------------------------
-- Table structure for sc_operation_printer_custom_component
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sc_operation_printer_custom_component` (
  `id` int(11) UNSIGNED AUTO_INCREMENT,
  `document_template_id` int(11) NOT NULL DEFAULT 0 COMMENT '模板id',
  `system_component_id` int(11) NOT NULL NULL DEFAULT 0 COMMENT '系统组件id',
  `value_style` varchar(50) NOT NULL DEFAULT '' COMMENT '自定义组件属性',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-删除',
  PRIMARY KEY(`id`),
  KEY `idx_systemComponentId`(`system_component_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT '自定义组件表';

-- ------------------------------------------------
-- Record for sc_operation_printer_custom_component
-- ------------------------------------------------
-- 系统模板-结账单
INSERT INTO sc_operation_printer_custom_component(document_template_id, system_component_id, value_style, is_delete)
SELECT
  1 AS document_template_id,
  sc.id AS system_component_id,
  sc.value_style,
  0 AS is_delete
FROM sc_operation_printer_system_component sc
WHERE sc.module_id IN
(
  SELECT id
  FROM sc_operation_printer_template_module sm
  WHERE sm.document_type = 1
);
-- 系统模板-客看单
INSERT INTO sc_operation_printer_custom_component(document_template_id, system_component_id, value_style, is_delete)
SELECT
  2 AS document_template_id,
  sc.id AS system_component_id,
  sc.value_style,
  0 AS is_delete
FROM sc_operation_printer_system_component sc
WHERE sc.module_id IN
(
  SELECT id
  FROM sc_operation_printer_template_module sm
  WHERE sm.document_type = 2
);
-- 系统模板-预结单
INSERT INTO sc_operation_printer_custom_component(document_template_id, system_component_id, value_style, is_delete)
SELECT
  3 AS document_template_id,
  sc.id AS system_component_id,
  sc.value_style,
  0 AS is_delete
FROM sc_operation_printer_system_component sc
WHERE sc.module_id IN
(
  SELECT id
  FROM sc_operation_printer_template_module sm
  WHERE sm.document_type = 3
);
-- 系统模板-消费清单
INSERT INTO sc_operation_printer_custom_component(document_template_id, system_component_id, value_style, is_delete)
SELECT
  4 AS document_template_id,
  sc.id AS system_component_id,
  sc.value_style,
  0 AS is_delete
FROM sc_operation_printer_system_component sc
WHERE sc.module_id IN
(
  SELECT id
  FROM sc_operation_printer_template_module sm
  WHERE sm.document_type = 4
);
-- 系统模板-厨总单
INSERT INTO sc_operation_printer_custom_component(document_template_id, system_component_id, value_style, is_delete)
SELECT
  5 AS document_template_id,
  sc.id AS system_component_id,
  sc.value_style,
  0 AS is_delete
FROM sc_operation_printer_system_component sc
WHERE sc.module_id IN
(
  SELECT id
  FROM sc_operation_printer_template_module sm
  WHERE sm.document_type = 5
);
-- 系统模板-堂口单
INSERT INTO sc_operation_printer_custom_component(document_template_id, system_component_id, value_style, is_delete)
SELECT
  6 AS document_template_id,
  sc.id AS system_component_id,
  sc.value_style,
  0 AS is_delete
FROM sc_operation_printer_system_component sc
WHERE sc.module_id IN
(
  SELECT id
  FROM sc_operation_printer_template_module sm
  WHERE sm.document_type = 6
);
