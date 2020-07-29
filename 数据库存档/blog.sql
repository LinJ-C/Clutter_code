/*
Navicat MySQL Data Transfer

Source Server         : lzh
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : vueblog

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2020-07-29 09:04:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `content` longtext,
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_updata` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `show` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('11', '1', 'lin', '笔记', '学习', '学习计划', '学习巴拉巴拉吧', '2020-07-25 20:45:54', '2020-07-25 17:44:04', '1', '1');
INSERT INTO `blog` VALUES ('12', '1', 'lin', 'Django', 'Django', 'Django学习', '关于Django巴拉巴拉', '2020-07-25 20:45:57', '2020-07-25 17:44:08', '1', '1');
INSERT INTO `blog` VALUES ('13', '1', 'lin', 'Machine learning', '机器学习', 'learning', '关于图像识别的机器学习', '2020-07-25 20:45:58', '2020-07-25 17:44:12', '1', '1');
INSERT INTO `blog` VALUES ('14', '1', 'lin', 'Java', 'java', 'java后端', 'java那个难啊，真的难', '2020-07-27 16:09:55', '2020-07-25 17:44:15', '0', '1');
INSERT INTO `blog` VALUES ('15', '1', 'lin', 'Mind', '所思所想', 'something he say in sometime', 'just this', '2020-07-27 17:58:11', '2020-07-25 17:44:17', '1', '1');
INSERT INTO `blog` VALUES ('30', '1', 'lin', '部署相关', 'MyBlog后端开发遇见的问题', 'SpringBoot+shiro+mybtis Plus+redis+jwt', '\n::: hljs-center\n\n##  问题总结\n\n:::\n\n#### 数据库错误\n1. 数据库不匹配问题  \n当数据库版本不与本地MySQL版本不同时，Mybatis puls 代码生成器将无法使用。有时候本地mysql版本太低就算在pom内配置相应的MySQL版本的依赖也不行，需要将本地MySQL进行升级。\n2. lombok插件使用出现的问题  \n此插件大大的简化了实体类的开发。无需写set与get方法。但是在pom配置了相应文件也不能使用，需要idea下载相应插件才能使用，对于没用使用过的开发来说有点小问题。\n\n3. redis数据库问题  \n因为本项目使用了shiro，认证需要redis来做缓存。但是redis创建的时候可以选择设置密码和不设置密码。如果选择设置密码，shiro在认证的时候会报错，即使在shiro配置文件中配置正确的密码也无法解决此问题。解决其中一个方法是在创建链接前就把密码给redis，具体怎么实现有点难度，更简单的方法是直接设置redis密码为\'\'.\n在安装路径中找到redis.conf，点击输入CONFIG set requirepass \"\"即可（前提是你已经登录redis了）\n4.   待续。。。。', '2020-07-27 17:58:05', '2020-07-25 17:44:19', '1', '1');
INSERT INTO `blog` VALUES ('31', '1', 'lin', '部署', 'MyBlog前端开发遇见的问题', 'vue+element-ui+axios', '::: hljs-center\n\n## 前端遇见的问题\n\n:::\n\n&nbsp;&nbsp;总的来说前端的错误很少，因为这个页面并不复杂就是多个element组件的组合，大多css都是居中位置定位类的。  \n    \n&nbsp;&nbsp;其中多数还是对vue前端框架不熟悉。需要加强学习并运用，数据绑定，方法绑定，生命周期等需要熟悉，但是在我看来，这套很想wx小程序的操作。不过这里使用的模板开发类型，所有都写在一个文件中，小程序分别写在不同的文档中。  \n\n&nbsp;&nbsp;随便说一个问题把，跳转并刷新问题。\n\"```language\n@click.native=\"del\"\n\ndel(){\n	this.$router.go(0)\n}\n```\n这样便实现跳转并在跳转到的新页面进行刷新操作，但是网页会闪一下，对用户体验\n不是很好，或许有其他更好的办法。\n\n  \n\n  ', '2020-07-25 20:46:02', '2020-07-25 17:44:21', '1', '1');
INSERT INTO `blog` VALUES ('35', '1', null, 'Mind', '啊实打实打算', '阿三大苏打', '阿三大苏打', '2020-07-27 10:21:28', null, '1', '1');
INSERT INTO `blog` VALUES ('36', '1', 'lin', '测试', '测试标签', '成不成', '	啊实打实大苏打哇额', '2020-07-28 09:04:25', null, '1', '1');
