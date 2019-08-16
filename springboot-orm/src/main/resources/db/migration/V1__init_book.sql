DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(200) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int(6) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book` */

/*Table structure for table `book_order` */

DROP TABLE IF EXISTS `book_order`;

CREATE TABLE `book_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_name` varchar(200) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `order_no` varchar(32) NOT NULL,
  `consumer` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book_order` */

/*Table structure for table `book_order_item` */

DROP TABLE IF EXISTS `book_order_item`;

CREATE TABLE `book_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(200) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `detail` varchar(1000) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book_order_item` */

