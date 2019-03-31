/*
Navicat MySQL Data Transfer

Source Server         : MySQL_3306
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2019-03-31 19:42:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `available` varchar(4) DEFAULT NULL,
  `open` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('0', 'root', 'menu', '-1', '', '1', '1');
INSERT INTO `sys_resource` VALUES ('1', '用户权限', 'menu', '0', '', '1', '0');
INSERT INTO `sys_resource` VALUES ('2', '用户添加', 'menu', '1', 'user:create', '1', null);
INSERT INTO `sys_resource` VALUES ('3', '用户修改', 'menu', '1', 'user:update', '1', null);
INSERT INTO `sys_resource` VALUES ('4', '用户查询', 'menu', '1', 'user:view', '1', null);
INSERT INTO `sys_resource` VALUES ('5', '用户删除', 'menu', '1', 'user:delete', '1', null);
INSERT INTO `sys_resource` VALUES ('6', '导航', 'menu', '0', 'page:top', '1', '0');
INSERT INTO `sys_resource` VALUES ('7', '角色权限', 'menu', '0', null, '1', '0');
INSERT INTO `sys_resource` VALUES ('8', '角色添加', 'menu', '7', 'role:create', '1', null);
INSERT INTO `sys_resource` VALUES ('9', '角色修改', 'menu', '7', 'role:update', '1', null);
INSERT INTO `sys_resource` VALUES ('10', '角色查询', 'menu', '7', 'role:view', '1', null);
INSERT INTO `sys_resource` VALUES ('11', '角色删除', 'menu', '7', 'role:delete', '1', null);
INSERT INTO `sys_resource` VALUES ('12', '资源权限', 'menu', '0', null, '1', '0');
INSERT INTO `sys_resource` VALUES ('13', '资源添加', 'menu', '12', 'resource:create', '1', null);
INSERT INTO `sys_resource` VALUES ('14', '资源修改', 'menu', '12', 'resource:update', '1', null);
INSERT INTO `sys_resource` VALUES ('15', '资源查询', 'menu', '12', 'resource:view', '1', null);
INSERT INTO `sys_resource` VALUES ('16', '资源删除', 'menu', '12', 'resource:delete', '1', null);
INSERT INTO `sys_resource` VALUES ('17', '角色查询', 'menu', '7', null, '1', null);
INSERT INTO `sys_resource` VALUES ('18', '页面显示', 'menu', '0', 'page:*', '1', '0');
INSERT INTO `sys_resource` VALUES ('19', '分配角色', 'menu', '1', 'user:allow_role', '1', null);
INSERT INTO `sys_resource` VALUES ('20', '分配资源', 'menu', '7', 'role:*', '1', null);
INSERT INTO `sys_resource` VALUES ('22', '权限列表', 'menu', '0', 'resource:view', '1', '0');
INSERT INTO `sys_resource` VALUES ('23', '通用资源', 'menu', '0', 'common:*', '1', '0');
INSERT INTO `sys_resource` VALUES ('26', '通用资源2', 'menu', '23', 'common:**', '1', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员', '0,1,2,3,4,5,19,6,7,8,9,10,11,17,20,12,13,14,15,16,18,22,23,26', '1');
INSERT INTO `sys_role` VALUES ('5', 'deploy', '发布管理员', '0,1,4,5,19,6,7,8,9,10,11,17,20,12,13,14,15,16,18,22,23', '1');
INSERT INTO `sys_role` VALUES ('6', 'manager', '项目经理', '0,1,2,6,18,22,23', '1');
INSERT INTO `sys_role` VALUES ('7', 'system', '系统管理员', '0,1,2,3,4,5,19,6,7,8,9,12,13,14,15,16,18', '1');
INSERT INTO `sys_role` VALUES ('8', 'apply', '申请管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('9', 'Maintain', '维护管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('10', 'product', '产品管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('11', 'approval', '审批管理员', '18', '1');
INSERT INTO `sys_role` VALUES ('15', 'base', '基础角色', '0,1,2,4,6,7,8,9,10,11,12,14,15,18,23', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '张三', 'e00cf25ad42683b3df678c61f42c6bda', '1', 'yjsfgn@163.com', '13939393331', '2');
INSERT INTO `sys_user` VALUES ('7', 'lisi23', '李四', '1134b8e47e43e73e4e8c08bf1d725136', '1,5', 'sdgsfgn@163.com', '13638329323', '0');
INSERT INTO `sys_user` VALUES ('8', 'wangwu', '王五', '738d992e4099926873bd8520e377ddab', '6', 'huisfgn11@163.com', '13532919481', '2');
INSERT INTO `sys_user` VALUES ('13', 'sfgn', '慧艳', 'a1cce9106626be9d2f140d888bc463cf', '5,6', '1580766061@qq.com', '15010627375', '2');
INSERT INTO `sys_user` VALUES ('14', 'zhenzidan', '贞子', 'c1ff13e541ec84718a4414af787a87e0', '0', 'yjsfgn@163.com', '13939393331', '2');
INSERT INTO `sys_user` VALUES ('17', 'hmu88', '张三', 'e81a74c64665e45d1aac46af02698fb1', '0', 'yjiu11@163.com', '13939393331', '2');
INSERT INTO `sys_user` VALUES ('18', 'test', '测试人员', '1fb0e331c05a52d5eb847d6fc018320d', '15', 'yjiusfgn@163.com', '13919918383', '0');
INSERT INTO `sys_user` VALUES ('19', 'yjiu', '刘洋', 'a3846c4ec6ca4f175527df0498d8fc85', '15', 'yjiu11@163.com', '13641305513', '0');

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
