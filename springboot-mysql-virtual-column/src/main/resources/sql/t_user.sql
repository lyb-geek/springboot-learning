/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.19 : Database - demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `demo`;

/*Table structure for table `t_user_json` */

DROP TABLE IF EXISTS `t_user_json`;

CREATE TABLE `t_user_json` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_info` json DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!默认是Virtual Column，虚拟列不持久化 ,insert时不要给虚拟列设定值*，JSON_UNQUOTE用来去除字符上的双引号/;
ALTER TABLE t_user_json ADD COLUMN v_user_name VARCHAR(50) GENERATED ALWAYS AS (JSON_UNQUOTE(json_extract(user_info,'$.username')));
ALTER TABLE t_user_json ADD COLUMN v_date_month VARCHAR(8) GENERATED ALWAYS AS (DATE_FORMAT(create_time,'%Y-%m'));

/*!虚拟列是支持索引*/;
ALTER TABLE t_user_json ADD INDEX idx_v_user_name(v_user_name);
