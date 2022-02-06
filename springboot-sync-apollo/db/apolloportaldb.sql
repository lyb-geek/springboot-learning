/*
SQLyog Ultimate v9.62 
MySQL - 8.0.19 : Database - apolloportaldb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`apolloportaldb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `apolloportaldb`;

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Name` varchar(500) NOT NULL DEFAULT 'default' COMMENT '应用名',
  `OrgId` varchar(32) NOT NULL DEFAULT 'default' COMMENT '部门Id',
  `OrgName` varchar(64) NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `OwnerName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  `OwnerEmail` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerEmail',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `AppId` (`AppId`(191)),
  KEY `DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_Name` (`Name`(191))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用表';

/*Data for the table `app` */

insert  into `app`(`Id`,`AppId`,`Name`,`OrgId`,`OrgName`,`OwnerName`,`OwnerEmail`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'SampleApp','Sample App','TEST1','样例部门1','apollo','apollo@acme.com','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(2,'springboot-gateway','springboot-gateway','TEST1','样例部门1','apollo','apollo@acme.com','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03');

/*Table structure for table `appnamespace` */

DROP TABLE IF EXISTS `appnamespace`;

CREATE TABLE `appnamespace` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Name` varchar(32) NOT NULL DEFAULT '' COMMENT 'namespace名字，注意，需要全局唯一',
  `AppId` varchar(64) NOT NULL DEFAULT '' COMMENT 'app id',
  `Format` varchar(32) NOT NULL DEFAULT 'properties' COMMENT 'namespace的format类型',
  `IsPublic` bit(1) NOT NULL DEFAULT b'0' COMMENT 'namespace是否为公共',
  `Comment` varchar(64) NOT NULL DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_AppId` (`AppId`),
  KEY `Name_AppId` (`Name`,`AppId`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用namespace定义';

/*Data for the table `appnamespace` */

insert  into `appnamespace`(`Id`,`Name`,`AppId`,`Format`,`IsPublic`,`Comment`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'application','SampleApp','properties','\0','default app namespace','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(2,'application','springboot-gateway','properties','\0','default app namespace','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03');

/*Table structure for table `authorities` */

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Username` varchar(64) NOT NULL,
  `Authority` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `authorities` */

insert  into `authorities`(`Id`,`Username`,`Authority`) values (1,'apollo','ROLE_user');

/*Table structure for table `consumer` */

DROP TABLE IF EXISTS `consumer`;

CREATE TABLE `consumer` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Name` varchar(500) NOT NULL DEFAULT 'default' COMMENT '应用名',
  `OrgId` varchar(32) NOT NULL DEFAULT 'default' COMMENT '部门Id',
  `OrgName` varchar(64) NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `OwnerName` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  `OwnerEmail` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'ownerEmail',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `AppId` (`AppId`(191)),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='开放API消费者';

/*Data for the table `consumer` */

insert  into `consumer`(`Id`,`AppId`,`Name`,`OrgId`,`OrgName`,`OwnerName`,`OwnerEmail`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'springboot-gateway','springboot-gateway','TEST1','样例部门1','apollo','apollo@acme.com','\0','apollo','2022-01-07 17:12:55','apollo','2022-01-07 17:12:55');

/*Table structure for table `consumeraudit` */

DROP TABLE IF EXISTS `consumeraudit`;

CREATE TABLE `consumeraudit` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `ConsumerId` int unsigned DEFAULT NULL COMMENT 'Consumer Id',
  `Uri` varchar(1024) NOT NULL DEFAULT '' COMMENT '访问的Uri',
  `Method` varchar(16) NOT NULL DEFAULT '' COMMENT '访问的Method',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_ConsumerId` (`ConsumerId`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='consumer审计表';

/*Data for the table `consumeraudit` */

insert  into `consumeraudit`(`Id`,`ConsumerId`,`Uri`,`Method`,`DataChange_CreatedTime`,`DataChange_LastTime`) values (1,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:42:40','2022-01-19 16:42:40'),(2,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:47:43','2022-01-19 16:47:43'),(3,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:48:27','2022-01-19 16:48:27'),(4,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:50:19','2022-01-19 16:50:19'),(5,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:52:48','2022-01-19 16:52:48'),(6,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:58:00','2022-01-19 16:58:00'),(7,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 16:58:29','2022-01-19 16:58:29'),(8,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 17:02:46','2022-01-19 17:02:46'),(9,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 17:02:47','2022-01-19 17:02:47'),(10,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 17:02:47','2022-01-19 17:02:47'),(11,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 17:02:47','2022-01-19 17:02:47'),(12,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:20:36','2022-01-19 18:20:36'),(13,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:20:36','2022-01-19 18:20:36'),(14,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:20:36','2022-01-19 18:20:36'),(15,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:20:36','2022-01-19 18:20:36'),(16,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-19 18:20:36','2022-01-19 18:20:36'),(17,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:28:05','2022-01-19 18:28:05'),(18,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:28:05','2022-01-19 18:28:05'),(19,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:28:05','2022-01-19 18:28:05'),(20,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:28:05','2022-01-19 18:28:05'),(21,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-19 18:28:05','2022-01-19 18:28:05'),(22,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:30:29','2022-01-19 18:30:29'),(23,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:30:29','2022-01-19 18:30:29'),(24,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:30:29','2022-01-19 18:30:29'),(25,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-19 18:30:30','2022-01-19 18:30:30'),(26,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-19 18:30:30','2022-01-19 18:30:30'),(27,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 13:38:18','2022-01-20 13:38:18'),(28,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 13:38:18','2022-01-20 13:38:18'),(29,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 13:38:18','2022-01-20 13:38:18'),(30,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 13:38:19','2022-01-20 13:38:19'),(31,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 13:38:19','2022-01-20 13:38:19'),(32,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 14:27:13','2022-01-20 14:27:13'),(33,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 14:27:13','2022-01-20 14:27:13'),(34,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 14:27:13','2022-01-20 14:27:13'),(35,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 14:27:14','2022-01-20 14:27:14'),(36,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri?operator=apollo','DELETE','2022-01-20 14:53:24','2022-01-20 14:53:24'),(37,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D?operator=apollo','DELETE','2022-01-20 14:53:24','2022-01-20 14:53:24'),(38,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D?operator=apollo','DELETE','2022-01-20 14:53:24','2022-01-20 14:53:24'),(39,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 14:58:03','2022-01-20 14:58:03'),(40,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.id','PUT','2022-01-20 14:58:03','2022-01-20 14:58:03'),(41,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 14:58:03','2022-01-20 14:58:03'),(42,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 14:58:03','2022-01-20 14:58:03'),(43,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 14:58:03','2022-01-20 14:58:03'),(44,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 14:58:03','2022-01-20 14:58:03'),(45,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 14:58:04','2022-01-20 14:58:04'),(46,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 14:58:04','2022-01-20 14:58:04'),(47,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 14:58:04','2022-01-20 14:58:04'),(48,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 14:59:10','2022-01-20 14:59:10'),(49,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 14:59:11','2022-01-20 14:59:11'),(50,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 14:59:11','2022-01-20 14:59:11'),(51,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 14:59:11','2022-01-20 14:59:11'),(52,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri?operator=apollo','DELETE','2022-01-20 14:59:46','2022-01-20 14:59:46'),(53,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D?operator=apollo','DELETE','2022-01-20 14:59:46','2022-01-20 14:59:46'),(54,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D?operator=apollo','DELETE','2022-01-20 14:59:46','2022-01-20 14:59:46'),(55,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 14:59:47','2022-01-20 14:59:47'),(56,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:10:58','2022-01-20 15:10:58'),(57,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.id','PUT','2022-01-20 15:10:58','2022-01-20 15:10:58'),(58,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:10:58','2022-01-20 15:10:58'),(59,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 15:10:58','2022-01-20 15:10:58'),(60,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:10:58','2022-01-20 15:10:58'),(61,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 15:10:59','2022-01-20 15:10:59'),(62,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:10:59','2022-01-20 15:10:59'),(63,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 15:10:59','2022-01-20 15:10:59'),(64,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 15:10:59','2022-01-20 15:10:59'),(65,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 15:11:38','2022-01-20 15:11:38'),(66,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 15:11:38','2022-01-20 15:11:38'),(67,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 15:11:38','2022-01-20 15:11:38'),(68,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 15:11:38','2022-01-20 15:11:38'),(69,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri?operator=apollo','DELETE','2022-01-20 15:12:11','2022-01-20 15:12:11'),(70,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D?operator=apollo','DELETE','2022-01-20 15:12:11','2022-01-20 15:12:11'),(71,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D?operator=apollo','DELETE','2022-01-20 15:12:11','2022-01-20 15:12:11'),(72,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 15:12:12','2022-01-20 15:12:12'),(73,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 15:12:45','2022-01-20 15:12:45'),(74,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 15:14:41','2022-01-20 15:14:41'),(75,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:15:10','2022-01-20 15:15:10'),(76,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.id','PUT','2022-01-20 15:15:11','2022-01-20 15:15:11'),(77,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:15:11','2022-01-20 15:15:11'),(78,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.uri','PUT','2022-01-20 15:15:11','2022-01-20 15:15:11'),(79,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:15:11','2022-01-20 15:15:11'),(80,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.predicates%5B0%5D','PUT','2022-01-20 15:15:11','2022-01-20 15:15:11'),(81,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:15:11','2022-01-20 15:15:11'),(82,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.filters%5B0%5D','PUT','2022-01-20 15:15:12','2022-01-20 15:15:12'),(83,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 15:15:12','2022-01-20 15:15:12'),(84,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:27:03','2022-01-20 15:27:03'),(85,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.id','PUT','2022-01-20 15:27:03','2022-01-20 15:27:03'),(86,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:27:03','2022-01-20 15:27:03'),(87,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 15:27:03','2022-01-20 15:27:03'),(88,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:27:04','2022-01-20 15:27:04'),(89,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 15:27:04','2022-01-20 15:27:04'),(90,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 15:27:04','2022-01-20 15:27:04'),(91,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 15:27:04','2022-01-20 15:27:04'),(92,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 15:27:04','2022-01-20 15:27:04'),(93,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 15:27:40','2022-01-20 15:27:40'),(94,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 15:27:40','2022-01-20 15:27:40'),(95,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 15:27:40','2022-01-20 15:27:40'),(96,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 15:27:40','2022-01-20 15:27:40'),(97,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:00:46','2022-01-20 16:00:46'),(98,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.id','PUT','2022-01-20 16:00:46','2022-01-20 16:00:46'),(99,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:00:46','2022-01-20 16:00:46'),(100,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 16:00:46','2022-01-20 16:00:46'),(101,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:00:46','2022-01-20 16:00:46'),(102,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 16:00:47','2022-01-20 16:00:47'),(103,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:00:47','2022-01-20 16:00:47'),(104,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 16:00:47','2022-01-20 16:00:47'),(105,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 16:00:47','2022-01-20 16:00:47'),(106,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:02:02','2022-01-20 16:02:02'),(107,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.id','PUT','2022-01-20 16:02:02','2022-01-20 16:02:02'),(108,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:02:03','2022-01-20 16:02:03'),(109,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.uri','PUT','2022-01-20 16:02:03','2022-01-20 16:02:03'),(110,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:02:03','2022-01-20 16:02:03'),(111,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.predicates%5B0%5D','PUT','2022-01-20 16:02:03','2022-01-20 16:02:03'),(112,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 16:02:03','2022-01-20 16:02:03'),(113,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B2%5D.filters%5B0%5D','PUT','2022-01-20 16:02:03','2022-01-20 16:02:03'),(114,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 16:02:03','2022-01-20 16:02:03'),(115,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 17:24:58','2022-01-20 17:24:58'),(116,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.id','PUT','2022-01-20 17:24:58','2022-01-20 17:24:58'),(117,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 17:24:58','2022-01-20 17:24:58'),(118,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 17:24:59','2022-01-20 17:24:59'),(119,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 17:24:59','2022-01-20 17:24:59'),(120,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 17:24:59','2022-01-20 17:24:59'),(121,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items','POST','2022-01-20 17:24:59','2022-01-20 17:24:59'),(122,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 17:24:59','2022-01-20 17:24:59'),(123,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 17:24:59','2022-01-20 17:24:59'),(124,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 17:27:01','2022-01-20 17:27:01'),(125,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 17:27:01','2022-01-20 17:27:01'),(126,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.filters%5B0%5D','PUT','2022-01-20 17:27:01','2022-01-20 17:27:01'),(127,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 17:27:01','2022-01-20 17:27:01'),(128,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.uri','PUT','2022-01-20 17:28:22','2022-01-20 17:28:22'),(129,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/items/spring.cloud.gateway.routes%5B1%5D.predicates%5B0%5D','PUT','2022-01-20 17:28:23','2022-01-20 17:28:23'),(130,1,'/openapi/v1/envs/dev/apps/springboot-gateway/clusters/default/namespaces/application/releases','POST','2022-01-20 17:28:23','2022-01-20 17:28:23');

/*Table structure for table `consumerrole` */

DROP TABLE IF EXISTS `consumerrole`;

CREATE TABLE `consumerrole` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `ConsumerId` int unsigned DEFAULT NULL COMMENT 'Consumer Id',
  `RoleId` int unsigned DEFAULT NULL COMMENT 'Role Id',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_RoleId` (`RoleId`),
  KEY `IX_ConsumerId_RoleId` (`ConsumerId`,`RoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='consumer和role的绑定表';

/*Data for the table `consumerrole` */

insert  into `consumerrole`(`Id`,`ConsumerId`,`RoleId`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,1,5,'\0','apollo','2022-01-07 17:14:48','apollo','2022-01-07 17:14:48');

/*Table structure for table `consumertoken` */

DROP TABLE IF EXISTS `consumertoken`;

CREATE TABLE `consumertoken` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `ConsumerId` int unsigned DEFAULT NULL COMMENT 'ConsumerId',
  `Token` varchar(128) NOT NULL DEFAULT '' COMMENT 'token',
  `Expires` datetime NOT NULL DEFAULT '2099-01-01 00:00:00' COMMENT 'token失效时间',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_Token` (`Token`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='consumer token表';

/*Data for the table `consumertoken` */

insert  into `consumertoken`(`Id`,`ConsumerId`,`Token`,`Expires`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,1,'44f94514c8b62d160b097acd59ba8f413f010c32','2099-01-01 00:00:00','\0','apollo','2022-01-07 17:12:55','apollo','2022-01-07 17:12:55');

/*Table structure for table `favorite` */

DROP TABLE IF EXISTS `favorite`;

CREATE TABLE `favorite` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `UserId` varchar(32) NOT NULL DEFAULT 'default' COMMENT '收藏的用户',
  `AppId` varchar(500) NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Position` int NOT NULL DEFAULT '10000' COMMENT '收藏顺序',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `AppId` (`AppId`(191)),
  KEY `IX_UserId` (`UserId`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应用收藏表';

/*Data for the table `favorite` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `PermissionType` varchar(32) NOT NULL DEFAULT '' COMMENT '权限类型',
  `TargetId` varchar(256) NOT NULL DEFAULT '' COMMENT '权限对象类型',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_TargetId_PermissionType` (`TargetId`(191),`PermissionType`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='permission表';

/*Data for the table `permission` */

insert  into `permission`(`Id`,`PermissionType`,`TargetId`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'CreateCluster','SampleApp','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(2,'CreateNamespace','SampleApp','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(3,'AssignRole','SampleApp','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(4,'ModifyNamespace','SampleApp+application','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(5,'ReleaseNamespace','SampleApp+application','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(6,'CreateApplication','SystemRole','\0','apollo','2022-01-07 15:50:46','apollo','2022-01-07 15:50:46'),(7,'CreateCluster','springboot-gateway','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(8,'CreateNamespace','springboot-gateway','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(9,'AssignRole','springboot-gateway','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(10,'ManageAppMaster','springboot-gateway','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(11,'ModifyNamespace','springboot-gateway+application','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(12,'ReleaseNamespace','springboot-gateway+application','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(13,'ModifyNamespace','springboot-gateway+application+DEV','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(14,'ReleaseNamespace','springboot-gateway+application+DEV','\0','apollo','2022-01-07 15:59:04','apollo','2022-01-07 15:59:04');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `RoleName` varchar(256) NOT NULL DEFAULT '' COMMENT 'Role name',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_RoleName` (`RoleName`(191)),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`Id`,`RoleName`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'Master+SampleApp','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(2,'ModifyNamespace+SampleApp+application','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(3,'ReleaseNamespace+SampleApp+application','\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(4,'CreateApplication+SystemRole','\0','apollo','2022-01-07 15:50:46','apollo','2022-01-07 15:50:46'),(5,'Master+springboot-gateway','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(6,'ManageAppMaster+springboot-gateway','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(7,'ModifyNamespace+springboot-gateway+application','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(8,'ReleaseNamespace+springboot-gateway+application','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(9,'ModifyNamespace+springboot-gateway+application+DEV','\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(10,'ReleaseNamespace+springboot-gateway+application+DEV','\0','apollo','2022-01-07 15:59:04','apollo','2022-01-07 15:59:04');

/*Table structure for table `rolepermission` */

DROP TABLE IF EXISTS `rolepermission`;

CREATE TABLE `rolepermission` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `RoleId` int unsigned DEFAULT NULL COMMENT 'Role Id',
  `PermissionId` int unsigned DEFAULT NULL COMMENT 'Permission Id',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_RoleId` (`RoleId`),
  KEY `IX_PermissionId` (`PermissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和权限的绑定表';

/*Data for the table `rolepermission` */

insert  into `rolepermission`(`Id`,`RoleId`,`PermissionId`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,1,1,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(2,1,2,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(3,1,3,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(4,2,4,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(5,3,5,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(6,4,6,'\0','apollo','2022-01-07 15:50:46','apollo','2022-01-07 15:50:46'),(7,5,7,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(8,5,8,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(9,5,9,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(10,6,10,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(11,7,11,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(12,8,12,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(13,9,13,'\0','apollo','2022-01-07 15:59:04','apollo','2022-01-07 15:59:04'),(14,10,14,'\0','apollo','2022-01-07 15:59:04','apollo','2022-01-07 15:59:04');

/*Table structure for table `serverconfig` */

DROP TABLE IF EXISTS `serverconfig`;

CREATE TABLE `serverconfig` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Key` varchar(64) NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `Value` varchar(2048) NOT NULL DEFAULT 'default' COMMENT '配置项值',
  `Comment` varchar(1024) DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_Key` (`Key`),
  KEY `DataChange_LastTime` (`DataChange_LastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配置服务自身配置';

/*Data for the table `serverconfig` */

insert  into `serverconfig`(`Id`,`Key`,`Value`,`Comment`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'apollo.portal.envs','dev','可支持的环境列表','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(2,'organizations','[{\"orgId\":\"TEST1\",\"orgName\":\"样例部门1\"},{\"orgId\":\"TEST2\",\"orgName\":\"样例部门2\"}]','部门列表','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(3,'superAdmin','apollo','Portal超级管理员','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(4,'api.readTimeout','10000','http接口read timeout','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(5,'consumer.token.salt','someSalt','consumer token salt','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(6,'admin.createPrivateNamespace.switch','true','是否允许项目管理员创建私有namespace','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(7,'configView.memberOnly.envs','pro','只对项目成员显示配置信息的环境列表，多个env以英文逗号分隔','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20'),(8,'apollo.portal.meta.servers','{}','各环境Meta Service列表','\0','default','2022-01-07 15:42:20','','2022-01-07 15:42:20');

/*Table structure for table `spring_session` */

DROP TABLE IF EXISTS `spring_session`;

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `spring_session` */

/*Table structure for table `spring_session_attributes` */

DROP TABLE IF EXISTS `spring_session_attributes`;

CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `spring_session_attributes` */

/*Table structure for table `userrole` */

DROP TABLE IF EXISTS `userrole`;

CREATE TABLE `userrole` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `UserId` varchar(128) DEFAULT '' COMMENT '用户身份标识',
  `RoleId` int unsigned DEFAULT NULL COMMENT 'Role Id',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(64) NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(64) DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`Id`),
  KEY `IX_DataChange_LastTime` (`DataChange_LastTime`),
  KEY `IX_RoleId` (`RoleId`),
  KEY `IX_UserId_RoleId` (`UserId`,`RoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和role的绑定表';

/*Data for the table `userrole` */

insert  into `userrole`(`Id`,`UserId`,`RoleId`,`IsDeleted`,`DataChange_CreatedBy`,`DataChange_CreatedTime`,`DataChange_LastModifiedBy`,`DataChange_LastTime`) values (1,'apollo',1,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(2,'apollo',2,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(3,'apollo',3,'\0','default','2022-01-07 15:42:22','','2022-01-07 15:42:22'),(4,'apollo',5,'\0','apollo','2022-01-07 15:59:03','apollo','2022-01-07 15:59:03'),(5,'apollo',7,'\0','apollo','2022-01-07 15:59:04','apollo','2022-01-07 15:59:04'),(6,'apollo',8,'\0','apollo','2022-01-07 15:59:04','apollo','2022-01-07 15:59:04');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `Id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Username` varchar(64) NOT NULL DEFAULT 'default' COMMENT '用户登录账户',
  `Password` varchar(512) NOT NULL DEFAULT 'default' COMMENT '密码',
  `UserDisplayName` varchar(512) NOT NULL DEFAULT 'default' COMMENT '用户名称',
  `Email` varchar(64) NOT NULL DEFAULT 'default' COMMENT '邮箱地址',
  `Enabled` tinyint DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `users` */

insert  into `users`(`Id`,`Username`,`Password`,`UserDisplayName`,`Email`,`Enabled`) values (1,'apollo','$2a$10$7r20uS.BQ9uBpf3Baj3uQOZvMVvB1RN3PYoKE94gtz2.WAOuiiwXS','apollo','apollo@acme.com',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
