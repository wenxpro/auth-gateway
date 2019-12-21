/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : inc

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-12-22 00:15:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(60) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '别名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT 'email',
  `access_token` blob COMMENT '认证token',
  `refresh_token` blob COMMENT '刷新token',
  `create_date` datetime DEFAULT NULL,
  `create_userid` varchar(36) DEFAULT NULL,
  `del_date` datetime DEFAULT NULL,
  `del_uerid` varchar(36) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0889e511-d329-430a-b406-ea2c17c09bc2', 'wenx', '$2a$10$Dl4DQWpfy6RDoewEBE1Ksus5IZXYLGVcd4Nxzi6scfXeMSwuyteFW', null, null, null, null, null, '2019-12-16 21:09:06', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('1', 'user_1', '$2a$10$13xePEiJau6gVPgPuU85suROy2I5R4mE5SDFK1QXTHYdog/v067b.', '', null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('132a9f3c-eb36-46db-a721-2e75decf8f40', 'admin', '$2a$10$ZPDhO41urvHXio1L197LjeRQf293TiDrNhVwwlEJX3VNvslpkP5Qy', null, null, null, null, null, '2019-12-17 21:02:22', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('2', 'suer2', '123', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('4b3ed4ff-8c65-4b74-9bbf-96c965562b86', 'admin', '$2a$10$B2Wyz/ET8/NGNfdepQ/jKOdddk9kdwWVY2REULqF0fvLm5xBtGKCa', null, null, null, null, null, '2019-12-17 21:02:26', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('d1cb2111-c125-4a89-ab97-25188d26404e', 'admin', '$2a$10$fl.eCh2dy7Hlru7NQpXwLuYgsFlaCjPVaSY7g9ge5ZtcWkar7zs3y', null, null, null, null, null, '2019-12-17 20:54:14', null, null, null, '1');
