

DROP TABLE IF EXISTS `book_0`;

CREATE TABLE `book_0` (
  `id` bigint(20) NOT NULL,
  `book_name` varchar(200) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int(6) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `book_1` */

DROP TABLE IF EXISTS `book_1`;

CREATE TABLE `book_1` (
  `id` bigint(20) NOT NULL,
  `book_name` varchar(200) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int(6) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

