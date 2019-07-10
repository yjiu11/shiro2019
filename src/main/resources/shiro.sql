DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `available` varchar(4) DEFAULT NULL,
  `open` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '系统管理', 'menu', '0', '', '/', '1', '1');
INSERT INTO `sys_resource` VALUES ('2', '系统信息', 'menu', '1', '', '/', '1', '1');
INSERT INTO `sys_resource` VALUES ('3', '用户管理', 'menu', '2', 'user:*', '/page/sysuser/list', '1', null);
INSERT INTO `sys_resource` VALUES ('4', '角色管理', 'menu', '2', 'role:*', '/page/sysrole/list', '1', null);
INSERT INTO `sys_resource` VALUES ('5', '资源管理', 'menu', '2', 'resource:*', '/page/sysresource/list/', '1', null);
INSERT INTO `sys_resource` VALUES ('6', '接口平台', 'menu', '0', '', '/', '1', '1');
INSERT INTO `sys_resource` VALUES ('7', '中控平台', 'menu', '6', '', '/', '1', '1');
INSERT INTO `sys_resource` VALUES ('8', '马蜂窝', 'menu', '7', 'mfw:*', '/page/mfworder/list', '1', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `available` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员', '1,2,3,4,6,7,8', '1');
INSERT INTO `sys_role` VALUES ('5', 'deploy', '发布管理员', '1,4,5,19,6,7,8,9,10,11,17,20,12,13,14,15,16,18,22,23', '1');
INSERT INTO `sys_role` VALUES ('6', 'manager', '项目经理', '1,2,6,18,22,23', '1');
INSERT INTO `sys_role` VALUES ('7', 'system', '系统管理员', '1,2,3,4,5,19,6,7,8,9,12,13,14,15,16,18', '1');
INSERT INTO `sys_role` VALUES ('8', 'apply', '申请管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('9', 'Maintain', '维护管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('10', 'product', '产品管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('11', 'approval', '审批管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('15', 'base', '基础角色', '1,2,4,6,7,8,9,10,11,12,14,15,18,23', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `realname` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role_ids` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `locked` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', 'e00cf25ad42683b3df678c61f42c6bda', '1,5,6', 'yjsfgn@163.com', '13939393331', '2');
INSERT INTO `sys_user` VALUES ('28', 'yjiu', '刘洋', '30d6b3fe65a2b912352e241b4a5aebc9', '1,5', '1364130@163.com', '13641305513', '2');

-- ----------------------------
-- Procedure structure for insert_users
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_users`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_users`(in STAR int(10),in max_num INT(10))
BEGIN
	DECLARE i INT DEFAULT 0;
	DECLARE tmp VARCHAR(50) DEFAULT '';
	SET autocommit =0;
	REPEAT
		SET i = i+1;
		SET tmp = CONCAT(rand_string(4) ,'@163.com');
		INSERT INTO users(id,username,password,email,address,ENABLE,createDate,updateDate) 
			values((STAR+i),rand_string(6),rand_string(12),tmp,rand_address(),1,'20180104','20180104');
		UNTIL i = max_num
	end REPEAT;	
	COMMIT;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for rand_address
-- ----------------------------
DROP FUNCTION IF EXISTS `rand_address`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `rand_address`() RETURNS varchar(255) CHARSET utf8
BEGIN
		DECLARE str INT(10) DEFAULT 0;
		DECLARE return_str varchar(200) DEFAULT '';
		set str = rand_num();
		case str
			when 101 then set return_str = '北京';
			when 102 then set return_str = '天津';
			when 103 then set return_str = '广州';
			when 104 then set return_str = '广东';
			when 105 then set return_str = '河北';
			when 106 then set return_str = '河南';
			when 107 then set return_str = '四川';
			when 108 then set return_str = '厦门';
			when 109 then set return_str = '贵阳';
			when 110 then set return_str = '太原';
			else set return_str = '北上广';
		end case;
	RETURN return_str;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for rand_num
-- ----------------------------
DROP FUNCTION IF EXISTS `rand_num`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `rand_num`() RETURNS int(10)
BEGIN
	DECLARE i INT DEFAULT 0;
	SET i = FLOOR(100+RAND()*10);
	RETURN i;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for rand_string
-- ----------------------------
DROP FUNCTION IF EXISTS `rand_string`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `rand_string`(n INT) RETURNS varchar(255) CHARSET utf8
BEGIN
		declare chars_str varchar(100) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ' ;
		DECLARE return_str VARCHAR(255) DEFAULT '';
		DECLARE i INT DEFAULT 0 ;
		WHILE i < n DO
			set return_str = CONCAT(return_str, substring(chars_str, floor(1+ rand()*52),1));
			SET i = i + 1;
		END WHILE;
	RETURN return_str;
END
;;
DELIMITER ;