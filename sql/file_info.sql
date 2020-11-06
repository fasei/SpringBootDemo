/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : springboot-upload

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-09 10:45:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `file_info`
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` bigint(15) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(15) DEFAULT NULL COMMENT '资源id',
  `file_name` varchar(100) NOT NULL COMMENT '上传后的文件名',
  `file_origin_name` varchar(100) NOT NULL COMMENT '原始文件名',
  `file_path` varchar(100) NOT NULL COMMENT '上传后的路径',
  `file_type` varchar(100) NOT NULL COMMENT '文件类型',
  `md5` varchar(255) DEFAULT NULL,
  `valid` tinyint(2) NOT NULL COMMENT '文件是否有效(true/1: 有效 ;false/0: 无效)',
  `is_delete` tinyint(2) NOT NULL COMMENT '是否删除(true/1 : 已删除 ; false/0: 未删除)',
  `size` bigint(50) NOT NULL COMMENT '文件大小',
  `upload_time` varchar(50) NOT NULL COMMENT '上传时间',
  `delete_time` varchar(50) DEFAULT NULL COMMENT '删除时间',
  `created_by` varchar(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

