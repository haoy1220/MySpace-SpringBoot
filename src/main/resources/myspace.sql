/*
Navicat MySQL Data Transfer

Source Server         : 49.233.166.243_33306
Source Server Version : 50728
Source Host           : 49.233.166.243:33306
Source Database       : myspace

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-03-09 19:32:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for myspace_card
-- ----------------------------
DROP TABLE IF EXISTS `myspace_card`;
CREATE TABLE `myspace_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `column_id` int(11) NOT NULL,
  `card_body` varchar(255) NOT NULL,
  `card_state` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for myspace_column
-- ----------------------------
DROP TABLE IF EXISTS `myspace_column`;
CREATE TABLE `myspace_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `column_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for myspace_diary
-- ----------------------------
DROP TABLE IF EXISTS `myspace_diary`;
CREATE TABLE `myspace_diary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `diary_title` varchar(50) NOT NULL,
  `diary_content` text,
  `diary_state` int(2) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for myspace_memo
-- ----------------------------
DROP TABLE IF EXISTS `myspace_memo`;
CREATE TABLE `myspace_memo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `memo_type` int(4) NOT NULL,
  `memo_email` varchar(50) NOT NULL,
  `memo_date` datetime NOT NULL,
  `memo_time` datetime NOT NULL,
  `memo_content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for myspace_project
-- ----------------------------
DROP TABLE IF EXISTS `myspace_project`;
CREATE TABLE `myspace_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `project_name` varchar(20) NOT NULL,
  `project_state` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for myspace_user
-- ----------------------------
DROP TABLE IF EXISTS `myspace_user`;
CREATE TABLE `myspace_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(40) NOT NULL,
  `sex` int(2) DEFAULT NULL,
  `photo` varchar(20) DEFAULT NULL,
  `role` int(2) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
