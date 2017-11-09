/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : yangxing

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-09 14:14:08
*/



-- ----------------------------
-- Table structure for `base_data`
-- ----------------------------
DROP TABLE IF EXISTS `base_data`;
CREATE TABLE `base_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `start` double(6,2) DEFAULT NULL,
  `height` double(6,2) DEFAULT NULL,
  `end` double(6,2) DEFAULT NULL,
  `low` double(6,2) DEFAULT NULL,
  `volume` int(10) DEFAULT NULL,
  `money` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_data
-- ----------------------------
