/*
SQLyog Ultimate v9.62 
MySQL - 5.7.29-6-log : Database - jomoo-pipeline-demo
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `msg_log` */

DROP TABLE IF EXISTS `msg_log`;

CREATE TABLE `msg_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '本地消息表主键',
  `biz_id` bigint(20) DEFAULT NULL COMMENT '业务主键',
  `biz_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `msg_content` varchar(5000) DEFAULT NULL COMMENT '消息内容',
  `msg_status` varchar(200) DEFAULT NULL COMMENT '消息状态：待发送、成功、失败',
  `msg_remark` varchar(500) DEFAULT NULL COMMENT '消息备注',
  `try_count` int(2) DEFAULT NULL COMMENT '重试次数',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户id',
  `app_id` bigint(20) DEFAULT NULL COMMENT '应用id',
  `created_by` varchar(500) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `last_updated_by` varchar(500) DEFAULT NULL COMMENT '修改人',
  `last_update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `object_version_number` smallint(6) DEFAULT '0' COMMENT '版本号',
  `delete_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记：1：删除， 0 ：未删除',
  `created_by_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `last_updated_by_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
