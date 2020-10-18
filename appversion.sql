/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.2.101_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 192.168.2.101:3306
 Source Schema         : appversion

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 18/10/2020 23:28:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `platform` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `app_desc` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `icon` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_app_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_app_channel`;
CREATE TABLE `t_app_channel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NULL DEFAULT NULL,
  `version_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version_code` int(11) NULL DEFAULT NULL,
  `channel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `next_id` int(11) NULL DEFAULT NULL,
  `app_open` int(1) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_app_version
-- ----------------------------
DROP TABLE IF EXISTS `t_app_version`;
CREATE TABLE `t_app_version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version_code` int(11) NULL DEFAULT NULL,
  `app_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
