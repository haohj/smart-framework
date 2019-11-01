CREATE TABLE `customer` (
	`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR (255) DEFAULT NULL,
	`contact` VARCHAR (255) DEFAULT NULL,
	`telphone` VARCHAR (255) DEFAULT NULL,
	`email` VARCHAR (255) DEFAULT NULL,
	`remark` text,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

INSERT INTO `customer` VALUES ( '1', 'customer1', 'Jack', '13512345678', 'jack@gmail.com', NULL );

INSERT INTO `customer` VALUES ( '2', 'customer2', 'Rose', '13623456789', 'rose@gmail.com', NULL );