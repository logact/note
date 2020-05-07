CREATE TABLE `sp_attribute` (
  `attr_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `attr_name` varchar(32) NOT NULL COMMENT '属性名称',
  `cat_id` smallint(5) unsigned NOT NULL COMMENT '外键，类型id',
  `attr_sel` enum('only','many') NOT NULL DEFAULT 'only' COMMENT 'only:输入框(唯一)  many:后台下拉列表/前台单选框',
  `attr_write` enum('manual','list') NOT NULL DEFAULT 'manual' COMMENT 'manual:手工录入  list:从列表选择',
  `attr_vals` text NOT NULL COMMENT '可选值列表信息,例如颜色：白色,红色,绿色,多个可选值通过逗号分隔',
  `delete_time` int(11) DEFAULT NULL COMMENT '删除时间标志',
  PRIMARY KEY (`attr_id`),
  KEY `type_id` (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3803 DEFAULT CHARSET=utf8 COMMENT='属性表';

CREATE TABLE `sp_category` (
  `cat_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '分类唯一ID',
  `cat_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `cat_pid` int(32) DEFAULT NULL COMMENT '分类父ID',
  `cat_level` int(4) DEFAULT NULL COMMENT '分类层级 0: 顶级 1:二级 2:三级',
  `cat_deleted` int(2) DEFAULT '0' COMMENT '是否删除 1为删除',
  `cat_icon` varchar(255) DEFAULT NULL,
  `cat_src` text,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1484 DEFAULT CHARSET=utf8;


CREATE TABLE `sp_consignee` (
  `cgn_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '会员id',
  `cgn_name` varchar(32) NOT NULL COMMENT '收货人名称',
  `cgn_address` varchar(200) NOT NULL DEFAULT '' COMMENT '收货人地址',
  `cgn_tel` varchar(20) NOT NULL DEFAULT '' COMMENT '收货人电话',
  `cgn_code` char(6) NOT NULL DEFAULT '' COMMENT '邮编',
  `delete_time` int(11) DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`cgn_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='收货人表';


CREATE TABLE `sp_express` (
  `express_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id',
  `express_com` varchar(32) DEFAULT NULL COMMENT '订单快递公司名称',
  `express_nu` varchar(32) DEFAULT NULL COMMENT '快递单编号',
  `create_time` int(10) unsigned NOT NULL COMMENT '记录生成时间',
  `update_time` int(10) unsigned NOT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`express_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='快递表';


CREATE TABLE `sp_goods` (
  `goods_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `goods_number` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '商品数量',
  `goods_weight` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '商品重量',
  `cat_id` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '类型id',
  `goods_introduce` text COMMENT '商品详情介绍',
  `goods_big_logo` char(128) NOT NULL DEFAULT '' COMMENT '图片logo大图',
  `goods_small_logo` char(128) NOT NULL DEFAULT '' COMMENT '图片logo小图',
  `is_del` enum('0','1') NOT NULL DEFAULT '0' COMMENT '0:正常  1:删除',
  `add_time` int(11) NOT NULL COMMENT '添加商品时间',
  `upd_time` int(11) NOT NULL COMMENT '修改商品时间',
  `delete_time` int(11) DEFAULT NULL COMMENT '软删除标志字段',
  `cat_one_id` smallint(5) DEFAULT '0' COMMENT '一级分类id',
  `cat_two_id` smallint(5) DEFAULT '0' COMMENT '二级分类id',
  `cat_three_id` smallint(5) DEFAULT '0' COMMENT '三级分类id',
  `hot_mumber` int(11) unsigned DEFAULT '0' COMMENT '热卖数量',
  `is_promote` smallint(5) DEFAULT '0' COMMENT '是否促销',
  `goods_state` int(11) DEFAULT '0' COMMENT '商品状态 0: 未通过 1: 审核中 2: 已审核',
  PRIMARY KEY (`goods_id`),
  UNIQUE KEY `goods_name` (`goods_name`),
  KEY `goods_price` (`goods_price`),
  KEY `add_time` (`add_time`),
  KEY `goods_name_2` (`goods_name`)
) ENGINE=InnoDB AUTO_INCREMENT=928 DEFAULT CHARSET=utf8 COMMENT='商品表';


CREATE TABLE `sp_goods_attr` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` mediumint(8) unsigned NOT NULL COMMENT '商品id',
  `attr_id` smallint(5) unsigned NOT NULL COMMENT '属性id',
  `attr_value` text NOT NULL COMMENT '商品对应属性的值',
  `add_price` decimal(8,2) DEFAULT NULL COMMENT '该属性需要额外增加的价钱',
  PRIMARY KEY (`id`),
  KEY `attr_id` (`attr_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3174 DEFAULT CHARSET=utf8 COMMENT='商品-属性关联表';


CREATE TABLE `sp_goods_cats` (
  `cat_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `parent_id` int(11) NOT NULL COMMENT '父级id',
  `cat_name` varchar(50) NOT NULL COMMENT '分类名称',
  `is_show` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `cat_sort` int(11) NOT NULL DEFAULT '0' COMMENT '分类排序',
  `data_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据标记',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;


CREATE TABLE `sp_goods_pics` (
  `pics_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` mediumint(8) unsigned NOT NULL COMMENT '商品id',
  `pics_big` char(128) NOT NULL DEFAULT '' COMMENT '相册大图800*800',
  `pics_mid` char(128) NOT NULL DEFAULT '' COMMENT '相册中图350*350',
  `pics_sma` char(128) NOT NULL DEFAULT '' COMMENT '相册小图50*50',
  PRIMARY KEY (`pics_id`),
  KEY `goods_id` (`goods_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4580 DEFAULT CHARSET=utf8 COMMENT='商品-相册关联表';


CREATE TABLE `sp_manager` (
  `mg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mg_name` varchar(32) NOT NULL COMMENT '名称',
  `mg_pwd` char(64) NOT NULL COMMENT '密码',
  `mg_time` int(10) unsigned NOT NULL COMMENT '注册时间',
  `role_id` tinyint(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `mg_mobile` varchar(32) DEFAULT NULL,
  `mg_email` varchar(64) DEFAULT NULL,
  `mg_state` tinyint(2) DEFAULT '1' COMMENT '1：表示启用 0:表示禁用',
  PRIMARY KEY (`mg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8 COMMENT='管理员表';


CREATE TABLE `sp_order` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` mediumint(8) unsigned NOT NULL COMMENT '下订单会员id',
  `order_number` varchar(32) NOT NULL COMMENT '订单编号',
  `order_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `order_pay` enum('0','1','2','3') NOT NULL DEFAULT '1' COMMENT '支付方式  0未支付 1支付宝  2微信  3银行卡',
  `is_send` enum('是','否') NOT NULL DEFAULT '否' COMMENT '订单是否已经发货',
  `trade_no` varchar(32) NOT NULL DEFAULT '' COMMENT '支付宝交易流水号码',
  `order_fapiao_title` enum('个人','公司') NOT NULL DEFAULT '个人' COMMENT '发票抬头 个人 公司',
  `order_fapiao_company` varchar(32) NOT NULL DEFAULT '' COMMENT '公司名称',
  `order_fapiao_content` varchar(32) NOT NULL DEFAULT '' COMMENT '发票内容',
  `consignee_addr` text NOT NULL COMMENT 'consignee收货人地址',
  `pay_status` enum('0','1') NOT NULL DEFAULT '0' COMMENT '订单状态： 0未付款、1已付款',
  `create_time` int(10) unsigned NOT NULL COMMENT '记录生成时间',
  `update_time` int(10) unsigned NOT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_number` (`order_number`),
  KEY `add_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='订单表';


CREATE TABLE `sp_order_goods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(10) unsigned NOT NULL COMMENT '订单id',
  `goods_id` mediumint(8) unsigned NOT NULL COMMENT '商品id',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `goods_number` tinyint(4) NOT NULL DEFAULT '1' COMMENT '购买单个商品数量',
  `goods_total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品小计价格',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='商品订单关联表';


CREATE TABLE `sp_permission` (
  `ps_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `ps_name` varchar(20) NOT NULL COMMENT '权限名称',
  `ps_pid` smallint(6) unsigned NOT NULL COMMENT '父id',
  `ps_c` varchar(32) NOT NULL DEFAULT '' COMMENT '控制器',
  `ps_a` varchar(32) NOT NULL DEFAULT '' COMMENT '操作方法',
  `ps_level` enum('0','2','1') NOT NULL DEFAULT '0' COMMENT '权限等级',
  PRIMARY KEY (`ps_id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8 COMMENT='权限表';


CREATE TABLE `sp_permission_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ps_id` int(11) NOT NULL,
  `ps_api_service` varchar(255) DEFAULT NULL,
  `ps_api_action` varchar(255) DEFAULT NULL,
  `ps_api_path` varchar(255) DEFAULT NULL,
  `ps_api_order` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ps_id` (`ps_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;


CREATE TABLE `sp_report_1` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `rp1_user_count` int(8) DEFAULT NULL COMMENT '用户数',
  `rp1_area` varchar(128) DEFAULT NULL COMMENT '地区',
  `rp1_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

CREATE TABLE `sp_report_2` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `rp2_page` varchar(128) DEFAULT NULL,
  `rp2_count` int(8) DEFAULT NULL,
  `rp2_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;

CREATE TABLE `sp_report_3` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `rp3_src` varchar(127) DEFAULT NULL COMMENT '用户来源',
  `rp3_count` int(8) DEFAULT NULL COMMENT '数量',
  `rp3_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sp_role` (
  `role_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `ps_ids` varchar(512) NOT NULL DEFAULT '' COMMENT '权限ids,1,2,5',
  `ps_ca` text COMMENT '控制器-操作,控制器-操作,控制器-操作',
  `role_desc` text,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

CREATE TABLE `sp_type` (
  `type_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type_name` varchar(32) NOT NULL COMMENT '类型名称',
  `delete_time` int(11) DEFAULT NULL COMMENT '删除时间标志',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='类型表';

CREATE TABLE `sp_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(128) NOT NULL DEFAULT '' COMMENT '登录名',
  `qq_open_id` char(32) DEFAULT NULL COMMENT 'qq官方唯一编号信息',
  `password` char(64) NOT NULL DEFAULT '' COMMENT '登录密码',
  `user_email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `user_email_code` char(13) DEFAULT NULL COMMENT '新用户注册邮件激活唯一校验码',
  `is_active` enum('是','否') DEFAULT '否' COMMENT '新用户是否已经通过邮箱激活帐号',
  `user_sex` enum('保密','女','男') NOT NULL DEFAULT '男' COMMENT '性别',
  `user_qq` varchar(32) NOT NULL DEFAULT '' COMMENT 'qq',
  `user_tel` varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
  `user_xueli` enum('博士','硕士','本科','专科','高中','初中','小学') NOT NULL DEFAULT '本科' COMMENT '学历',
  `user_hobby` varchar(32) NOT NULL DEFAULT '' COMMENT '爱好',
  `user_introduce` text COMMENT '简介',
  `create_time` int(11) NOT NULL COMMENT '创建时间',
  `update_time` int(11) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='会员表';

CREATE TABLE `sp_user_cart` (
  `cart_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) unsigned NOT NULL COMMENT '学员id',
  `cart_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '购物车详情信息，二维数组序列化信息',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `delete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

