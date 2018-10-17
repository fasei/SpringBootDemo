/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : info

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-10-13 17:36:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user_infos`
-- ----------------------------
DROP TABLE IF EXISTS `user_infos`;
CREATE TABLE `user_infos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '昵称',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `headerimg` varchar(200) NOT NULL DEFAULT '' COMMENT '头像地址',
  `registertime` varchar(20) NOT NULL DEFAULT '' COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_infos
-- ----------------------------

-- ----------------------------
-- Table structure for `user_login`
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '帐号',
  `psw` varchar(20) NOT NULL DEFAULT '' COMMENT '密码',
  `registertime` varchar(20) NOT NULL DEFAULT '' COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_login
-- ----------------------------

-- ----------------------------
-- Event structure for `111`
-- ----------------------------
DROP EVENT IF EXISTS `111`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `111` ON SCHEDULE AT '2018-10-12 14:59:49' ON COMPLETION NOT PRESERVE ENABLE DO delete FROM userinfo WHERE id>11
;;
DELIMITER ;
