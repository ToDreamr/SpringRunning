# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: localhost (MySQL 8.0.32)
# 数据库: road_map
# 生成时间: 2023-08-12 07:19:03 +0000
# ************************************************************
CREATE TABLE articles (
                          id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
                          title VARCHAR(200),
                          body TEXT,
                          FULLTEXT (body));


INSERT INTO articles VALUES
                         (NULL,'MySQL Tutorial', 'DBMS stands for DataBase ...'),
                         (NULL,'How To Use MySQL Efficiently', 'After you went through a ...'),
                         (NULL,'Optimising MySQL','In this tutorial we will show ...'),
                         (NULL,'1001 MySQL Tricks','1. Never run mysqld as root. 2. ...'),
                         (NULL,'MySQL vs. YourSQL', 'In the following database comparison ...'),
                         (NULL,'MySQL Security', 'When configured properly, MySQL ...');


explain SELECT * FROM articles WHERE MATCH (body) AGAINST ('mysql');
explain SELECT * FROM articles WHERE body like '%database%';

show engines;

SHOW VARIABLES LIKE '%storage_engine%';




/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 user_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_order`;

CREATE TABLE `user_order` (
                              `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID；【必须保留自增ID，不要将一些有随机特性的字段值设计为主键，例如order_id，会导致innodb内部page分裂和大量随机I/O，性能下降】int 大约21亿左右，超过会报错。bigint 大约9千亿左右。',
                              `user_name` varchar(64) NOT NULL COMMENT '用户姓名；',
                              `user_id` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户编号；',
                              `user_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户电话；使用varchar(20)存储手机号，不要使用整型。手机号不会做数学计算、涉及到区号或者国家代号，可能出现+-()、支持模糊查询，例如：like“135%”',
                              `sku` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品编号',
                              `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
                              `order_id` varchar(64) NOT NULL COMMENT '订单ID',
                              `quantity` int NOT NULL DEFAULT '1' COMMENT '商品数量；整形定义中不显示规定显示长度，比如使用 INT，而不使用 INT(4)',
                              `unit_price` decimal(10,2) NOT NULL COMMENT '商品价格；小数类型为 decimal，禁止使用 float、double',
                              `discount_amount` decimal(10,2) NOT NULL COMMENT '折扣金额；',
                              `tax` decimal(4,2) NOT NULL COMMENT '费率金额；',
                              `total_amount` decimal(10,2) NOT NULL COMMENT '支付金额；(商品的总金额 - 折扣) * (1 - 费率)',
                              `order_date` datetime NOT NULL COMMENT '订单日期；timestamp的时间范围在1970-01-01 00:00:01到2038-01-01 00:00:00之间',
                              `order_status` tinyint(1) NOT NULL COMMENT '订单状态；0 创建、1完成、2掉单、3关单 【不要使用 enum 要使用 tinyint 替代。0-80 范围，都可以使用 tinyint】',
                              `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删单；0未删除，1已删除 【表达是否概念的字段必须使用is_】',
                              `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一索引；分布式下全局唯一，用于binlog 同步 ES 方便使用',
                              `ipv4` int unsigned NOT NULL DEFAULT '2130706433' COMMENT '设备地址；存储IPV4地址，通过MySQL 函数转换，inet_ntoa、inet_aton 示例；SELECT INET_ATON(‘209.207.224.40′); 3520061480 SELECT INET_NTOA(3520061480); 209.207.224.40所有字段定义为NOT NULL，并设置默认值，因为null值的字段会导致每一行都占用额外存储空间\\n数据迁移容易出错，在聚合函数计算结果偏差（如count结果不准）并且null的列使索引/索引统计/值比较都更加复杂，MySQL内部需要进行特殊处理，表中有较多空字段的时候，数据库性能下降严重。开发中null只能采用is null或is not null检索，而不能采用=、in、<、<>、!=、not in这些操作符号。如：where name!=’abc’，如果存在name为null值的记录，查询结果就不会包含name为null值的记录',
                              `ipv6` varbinary(16) NOT NULL COMMENT '设备地址；存储IPV6地址，VARBINARY(16)  插入：INET6_ATON(''2001:0db8:85a3:0000:0000:8a2e:0370:7334'') 查询：SELECT INET6_NTOA(ip_address) ',
                              `ext_data` json NOT NULL COMMENT '扩展数据；记录下单时用户的设备环境等信息(核心业务字段，要单独拆表)。【select user_name, ext_data, ext_data->>''$.device'', ext_data->>''$.device.machine'' from `user_order`;】',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uq_orderid` (`order_id`),
                              UNIQUE KEY `uq_uuid` (`uuid`),
                              KEY `idx_order_date` (`order_date`),
                              KEY `idx_sku_unit_price_total_amount` (`sku`,`unit_price`,`total_amount`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `user_order` WRITE;
/*!40000 ALTER TABLE `user_order` DISABLE KEYS */;

INSERT INTO `user_order` (`id`, `user_name`, `user_id`, `user_mobile`, `sku`, `sku_name`, `order_id`, `quantity`, `unit_price`, `discount_amount`, `tax`, `total_amount`, `order_date`, `order_status`, `is_delete`, `uuid`, `ipv4`, `ipv6`, `ext_data`, `update_time`, `create_time`)
VALUES
    (1,'小傅哥','U001','13512345678','SKU001','Mac Pro M2 贴膜','ORD001',2,10.99,2.00,0.50,19.48,'2023-08-12 10:00:00',0,0,'uuid001',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"IPhone 14 Pro\", \"location\": \"shanghai\"}}','2023-08-12 10:00:00','2023-08-12 10:00:00'),
    (2,'福禄娃','U002','13698765432','SKU002','IPad mini4 外套','ORD002',1,25.99,0.00,1.50,24.49,'2023-08-12 11:30:00',1,0,'uuid002',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 11:30:00','2023-08-12 11:30:00'),
    (3,'拎瓢冲','U003','13755555555','SKU003','数据线','ORD003',3,9.99,1.50,0.00,26.97,'2023-08-12 13:45:00',0,0,'uuid003',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 13:45:00','2023-08-12 13:45:00'),
    (4,'熏5null','U004','13812345678','SKU004','U盘','ORD004',1,15.99,0.00,0.75,15.24,'2023-08-12 14:20:00',1,0,'uuid004',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 14:20:00','2023-08-12 14:20:00'),
    (5,'温柔一刀','U005','13999999999','SKU005','坐垫','ORD005',2,12.50,1.25,0.25,23.75,'2023-08-12 15:55:00',0,0,'uuid005',2130706433,X'20010DB885A3000000008A2E03707334','{\"device\": {\"machine\": \"PC Windows\", \"location\": \"BeiJing\"}}','2023-08-12 15:55:00','2023-08-12 15:55:00');

/*!40000 ALTER TABLE `user_order` ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

INSERT INTO `user_order` (`id`, `user_name`, `user_id`, `user_mobile`, `sku`, `sku_name`, `order_id`, `quantity`, `unit_price`, `discount_amount`, `tax`, `total_amount`, `order_date`, `order_status`, `is_delete`, `uuid`, `ipv4`, `ipv6`, `ext_data`, `update_time`, `create_time`)
VALUES
    (9,'小傅哥','U001','13512345678','SKU001','Mac Pro M2 贴膜','ORD0101',2,10.99,2.00,0.50,19.48,'2023-08-12 10:00:00',0,0,'uuid010',INET_ATON('127.0.0.1'),INET6_ATON('2001:0db8:85a3:0000:0000:8a2e:0370:7334'),'{\"device\": {\"machine\": \"IPhone 14 Pro\", \"location\": \"shanghai\"}}','2023-08-12 10:00:00','2023-08-12 10:00:00');

select user_name, sku, INET_NTOA(ipv4), INET6_NTOA(ipv6) from `user_order`;

# MySQL8.0提供了json的数据类型支持
select user_name, ext_data, ext_data->>'$.device', ext_data->>'$.device.machine' from `user_order`;

# 使用 order_id 唯一索引
EXPLAIN select user_name, sku, INET_NTOA(ipv4), INET6_NTOA(ipv6) from `user_order` where order_id = 'ORD002';

# 使用组合索引
EXPLAIN select sku,total_amount,order_date from `user_order` where total_amount > 10 and order_date between '2023-08-09 00:00:00' and '2023-08-09 23:59:59';

# 不要使用列名，或者常量来替代count(*)
select count(*) from `user_order`;

START TRANSACTION;

SELECT user_name, sku, total_amount, order_date, order_status FROM `user_order` WHERE order_id = 'ORD002' FOR UPDATE;

-- 在这里执行其他操作，其他会话无法修改 order_id 为 ORD002 的订单信息

COMMIT;

# 行级锁
UPDATE `user_order` SET order_status = 0 WHERE order_id = 'ORD002' AND order_status = 3 FOR UPDATE ;

SHOW PROCESSLIST; #该命令用于显示当前正在运行的所有MySQL连接和查询。它将显示每个连接的ID、用户、主机、数据库、执行时间和当前执行的查询。

SHOW STATUS; # 该命令用于显示MySQL服务器的各种状态信息，例如连接数、线程状态、查询缓存命中率等。

SHOW ENGINE INNODB STATUS; #该命令用于显示InnoDB存储引擎的详细状态信息，包括死锁信息、事务信息和缓冲池状态等。


SHOW VARIABLES LIKE 'max_connections'; # 显示MySQL服务器当前配置的最大连接数。