/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : micro_blog

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-05-10 12:55:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `activecode` varchar(50) NOT NULL,
  `state` int(11) NOT NULL COMMENT '激活状态：0代表未激活，1代表激活',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId1` int(11) unsigned NOT NULL COMMENT '关注者',
  `userId2` int(11) unsigned NOT NULL COMMENT '被关注者',
  PRIMARY KEY (`id`),
  KEY `Follow_userId1_User_id` (`userId1`),
  KEY `Follow_userId2_User_id` (`userId2`),
  CONSTRAINT `Follow_userId1_User_id` FOREIGN KEY (`userId1`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Follow_userId2_User_id` FOREIGN KEY (`userId2`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for microblog
-- ----------------------------
DROP TABLE IF EXISTS `microblog`;
CREATE TABLE `microblog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) unsigned NOT NULL COMMENT '这条微博现在属于谁',
  `content` text NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '博微发布时间',
  `isForward` bit(1) NOT NULL,
  `sourceUserId` int(11) unsigned NOT NULL COMMENT '原作者id',
  `forwardMicroblogId` int(11) NOT NULL,
  `sourceMicroblogId` int(11) NOT NULL,
  `forwardRemark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Microblog_userId_User_id` (`userId`) USING BTREE,
  KEY `Microblog_sourceUserId_User_id` (`sourceUserId`) USING BTREE,
  CONSTRAINT `Microblog_sourceUserId_User_id` FOREIGN KEY (`sourceUserId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Microblog_userId_User_id` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for remark
-- ----------------------------
DROP TABLE IF EXISTS `remark`;
CREATE TABLE `remark` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `microblogId` int(10) unsigned NOT NULL,
  `content` text NOT NULL,
  `isReply` bit(1) NOT NULL,
  `replyId` int(10) unsigned DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `isRead` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Remark_microblogId_Microblog_id` (`microblogId`),
  KEY `Remark_userId_User_id` (`userId`),
  CONSTRAINT `Remark_microblogId_Microblog_id` FOREIGN KEY (`microblogId`) REFERENCES `microblog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Remark_userId_User_id` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;
