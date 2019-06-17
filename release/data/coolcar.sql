/*
 Navicat Premium Data Transfer

 Source Server         : Perfwxc
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : 119.29.41.87:3306
 Source Schema         : coolcar

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 17/06/2019 13:16:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data`  (
  `ide` int(10) NOT NULL AUTO_INCREMENT,
  `userTemperature` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `heartRate` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `highBloodPressure` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '116',
  `lowBloodPressure` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '69',
  `Lux` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '20',
  `SaO2` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `environmentTemperature` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ambientHumidity` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `smokeDensity` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CODensity` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `alcoholConcentration` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `H2S` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timeStamp1` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timeStamp2` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ide`) USING BTREE,
  UNIQUE INDEX `ide`(`ide`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 143 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data
-- ----------------------------
INSERT INTO `data` VALUES (1, '36.7', '96', '118', '73', '20', '96', '21', '94.0', '992', '1394', '1363', '1101', '4[2019-03-18', '06:52:33.616]');
INSERT INTO `data` VALUES (4, '36.8', '87', '115', '69', '22', '93', '21', '94.0', '1048', '1372', '1352', '1153', '4[2019-03-17', '07:22:23.619]');
INSERT INTO `data` VALUES (2, '36.4', '87', '119', '68', '430', '97', '21', '94.0', '994', '1401', '1415', '1110', '4[2019-03-13', '03:52:13.618]');
INSERT INTO `data` VALUES (3, '37', '92', '117', '65', '21', '95', '21', '94.0', '1031', '1457', '1376', '1118', '4[2019-03-16', '05:59:03.619]');
INSERT INTO `data` VALUES (5, '38.2', '103', '121', '78', '18', '94', '20', '93.0', '1078', '1459', '1382', '1136', '4[2019-03-14', '03:54:53.618]');

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`  (
  `id` int(10) NOT NULL,
  `Name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Num` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Help` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of info
-- ----------------------------
INSERT INTO `info` VALUES (1, '彪哥', '13296507815', '奥迪A8', '湖北大学', '15838152641');
INSERT INTO `info` VALUES (2, '4', '13720278995', '4', '4', '4');
INSERT INTO `info` VALUES (3, '4', '13296507815', '甲壳虫', '4', '4');
INSERT INTO `info` VALUES (4, '2', '13296507815', '2', '湖北省武汉市友谊大道', '2');
INSERT INTO `info` VALUES (5, '2', '13296507815', '1', '2', '15071033750');

SET FOREIGN_KEY_CHECKS = 1;
