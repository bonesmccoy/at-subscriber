# ************************************************************
# Sequel Pro SQL dump
# Version 4529
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.16)
# Database: collector
# Generation Time: 2016-12-24 08:46:47 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table stream_trade
# ------------------------------------------------------------

DROP TABLE IF EXISTS `stream_trade`;

CREATE TABLE `stream_trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `symbol` varchar(20) NOT NULL,
  `hour` int(11) NOT NULL,
  `minute` int(11) NOT NULL,
  `seconds` int(11) NOT NULL,
  `milliseconds` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `price` float NOT NULL,
  `last_size` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `price_precision` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `stream_trade` WRITE;
/*!40000 ALTER TABLE `stream_trade` DISABLE KEYS */;

INSERT INTO `stream_trade` (`id`, `symbol`, `hour`, `minute`, `seconds`, `milliseconds`, `timestamp`, `price`, `last_size`, `day`, `month`, `price_precision`, `year`)
VALUES
	(1,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(2,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(3,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(4,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(5,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(6,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(7,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(8,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(9,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(10,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(11,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(12,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(13,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(14,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(15,'AAPL',12,0,0,0,'2016-12-22 12:00:00',123.457,2000,12,12,2,2016),
	(16,'AAPL',14,19,1,546,'2016-12-23 14:19:01',116.3,100,14,14,2,2016),
	(17,'AAPL',14,19,4,513,'2016-12-23 14:19:04',116.3,100,14,14,2,2016),
	(18,'AAPL',14,19,4,513,'2016-12-23 14:19:04',116.3,100,14,14,2,2016),
	(19,'AAPL',14,19,4,513,'2016-12-23 14:19:04',116.3,100,14,14,2,2016),
	(20,'AAPL',14,19,4,513,'2016-12-23 14:19:04',116.3,100,14,14,2,2016),
	(21,'AAPL',14,19,4,513,'2016-12-23 14:19:04',116.3,200,14,14,2,2016),
	(22,'AAPL',14,19,4,513,'2016-12-23 14:19:04',116.3,95,14,14,2,2016),
	(23,'AAPL',14,19,4,514,'2016-12-23 14:19:04',116.3,200,14,14,2,2016),
	(24,'AAPL',14,19,4,514,'2016-12-23 14:19:04',116.3,200,14,14,2,2016),
	(25,'AAPL',14,19,4,514,'2016-12-23 14:19:04',116.3,100,14,14,2,2016),
	(26,'AAPL',14,19,4,514,'2016-12-23 14:19:04',116.3,200,14,14,2,2016),
	(27,'AAPL',14,19,4,514,'2016-12-23 14:19:04',116.3,200,14,14,2,2016),
	(28,'AAPL',14,19,4,516,'2016-12-23 14:19:04',116.3,66,14,14,2,2016),
	(29,'AAPL',14,19,4,980,'2016-12-23 14:19:04',116.3,51,14,14,4,2016),
	(30,'AAPL',14,19,6,5,'2016-12-23 14:19:06',116.3,1,14,14,2,2016),
	(31,'AAPL',14,19,6,6,'2016-12-23 14:19:06',116.3,100,14,14,2,2016),
	(32,'AAPL',14,19,6,6,'2016-12-23 14:19:06',116.3,100,14,14,2,2016),
	(33,'AAPL',14,19,6,6,'2016-12-23 14:19:06',116.3,100,14,14,2,2016),
	(34,'AAPL',14,19,10,149,'2016-12-23 14:19:10',116.295,100,14,14,3,2016),
	(35,'AAPL',14,19,10,153,'2016-12-23 14:19:10',116.29,274,14,14,2,2016),
	(36,'AAPL',14,19,10,155,'2016-12-23 14:19:10',116.293,274,14,14,4,2016),
	(37,'AAPL',14,19,10,155,'2016-12-23 14:19:10',116.29,26,14,14,2,2016),
	(38,'AAPL',14,19,10,155,'2016-12-23 14:19:10',116.29,300,14,14,2,2016),
	(39,'AAPL',14,19,10,156,'2016-12-23 14:19:10',116.29,84,14,14,2,2016),
	(40,'AAPL',14,19,10,158,'2016-12-23 14:19:10',116.293,26,14,14,4,2016),
	(41,'AAPL',14,19,10,161,'2016-12-23 14:19:10',116.29,174,14,14,2,2016),
	(42,'AAPL',14,19,10,163,'2016-12-23 14:19:10',116.294,174,14,14,4,2016),
	(43,'AAPL',14,19,10,174,'2016-12-23 14:19:10',116.295,100,14,14,3,2016),
	(44,'AAPL',14,19,10,179,'2016-12-23 14:19:10',116.29,26,14,14,2,2016),
	(45,'AAPL',14,19,10,179,'2016-12-23 14:19:10',116.29,29,14,14,2,2016),
	(46,'AAPL',14,19,10,181,'2016-12-23 14:19:10',116.29,26,14,14,4,2016),
	(47,'AAPL',14,19,10,182,'2016-12-23 14:19:10',116.292,29,14,14,4,2016),
	(48,'AAPL',14,19,10,767,'2016-12-23 14:19:10',116.3,100,14,14,2,2016),
	(49,'AAPL',14,19,10,767,'2016-12-23 14:19:10',116.3,100,14,14,2,2016),
	(50,'AAPL',14,19,10,769,'2016-12-23 14:19:10',116.3,1,14,14,2,2016),
	(51,'AAPL',14,19,11,422,'2016-12-23 14:19:11',116.3,100,14,14,2,2016),
	(52,'AAPL',14,19,11,626,'2016-12-23 14:19:11',116.3,100,14,14,2,2016),
	(53,'AAPL',14,19,11,921,'2016-12-23 14:19:11',116.3,100,14,14,2,2016),
	(54,'AAPL',14,19,12,121,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(55,'AAPL',14,19,12,139,'2016-12-23 14:19:12',116.305,100,14,14,3,2016),
	(56,'AAPL',14,19,12,153,'2016-12-23 14:19:12',116.3,114,14,14,2,2016),
	(57,'AAPL',14,19,12,153,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(58,'AAPL',14,19,12,153,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(59,'AAPL',14,19,12,153,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(60,'AAPL',14,19,12,155,'2016-12-23 14:19:12',116.305,100,14,14,3,2016),
	(61,'AAPL',14,19,12,155,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(62,'AAPL',14,19,12,164,'2016-12-23 14:19:12',116.305,100,14,14,3,2016),
	(63,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,200,14,14,2,2016),
	(64,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(65,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(66,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(67,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(68,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(69,'AAPL',14,19,12,179,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(70,'AAPL',14,19,12,180,'2016-12-23 14:19:12',116.305,100,14,14,3,2016),
	(71,'AAPL',14,19,12,181,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(72,'AAPL',14,19,12,274,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(73,'AAPL',14,19,12,274,'2016-12-23 14:19:12',116.3,74,14,14,2,2016),
	(74,'AAPL',14,19,12,274,'2016-12-23 14:19:12',116.3,26,14,14,2,2016),
	(75,'AAPL',14,19,12,274,'2016-12-23 14:19:12',116.3,100,14,14,2,2016),
	(76,'AAPL',14,19,12,275,'2016-12-23 14:19:12',116.305,100,14,14,3,2016),
	(77,'AAPL',14,19,12,291,'2016-12-23 14:19:12',116.29,1800,14,14,4,2016),
	(78,'AAPL',14,19,12,674,'2016-12-23 14:19:12',116.3,71,14,14,2,2016),
	(79,'AAPL',14,19,12,977,'2016-12-23 14:19:12',116.3,200,14,14,2,2016),
	(80,'AAPL',14,19,13,914,'2016-12-23 14:19:13',116.305,25,14,14,4,2016),
	(81,'AAPL',14,19,14,15,'2016-12-23 14:19:14',116.3,4,14,14,4,2016),
	(82,'AAPL',14,19,14,540,'2016-12-23 14:19:14',116.31,100,14,14,2,2016),
	(83,'AAPL',14,19,14,540,'2016-12-23 14:19:14',116.31,100,14,14,2,2016),
	(84,'AAPL',14,19,14,540,'2016-12-23 14:19:14',116.31,100,14,14,2,2016),
	(85,'AAPL',14,19,14,540,'2016-12-23 14:19:14',116.31,100,14,14,2,2016),
	(86,'AAPL',14,19,14,819,'2016-12-23 14:19:14',116.31,130,14,14,4,2016),
	(87,'AAPL',14,19,18,202,'2016-12-23 14:19:18',116.3,7,14,14,4,2016),
	(88,'AAPL',14,19,20,356,'2016-12-23 14:19:20',116.31,1,14,14,2,2016),
	(89,'AAPL',14,19,21,121,'2016-12-23 14:19:21',116.3,1,14,14,2,2016),
	(90,'AAPL',14,19,21,812,'2016-12-23 14:19:21',116.31,47,14,14,2,2016),
	(91,'AAPL',14,19,21,812,'2016-12-23 14:19:21',116.31,53,14,14,2,2016),
	(92,'AAPL',14,19,22,243,'2016-12-23 14:19:22',116.304,30,14,14,4,2016),
	(93,'AAPL',14,19,24,309,'2016-12-23 14:19:24',116.303,400,14,14,4,2016),
	(94,'AAPL',14,19,24,310,'2016-12-23 14:19:24',116.31,100,14,14,2,2016),
	(95,'AAPL',14,19,24,310,'2016-12-23 14:19:24',116.3,125,14,14,2,2016),
	(96,'AAPL',14,19,24,590,'2016-12-23 14:19:24',116.31,100,14,14,2,2016),
	(97,'AAPL',14,19,27,723,'2016-12-23 14:19:27',116.31,100,14,14,2,2016),
	(98,'AAPL',14,19,28,244,'2016-12-23 14:19:28',116.3,2,14,14,4,2016),
	(99,'AAPL',14,19,28,474,'2016-12-23 14:19:28',116.305,1,14,14,3,2016),
	(100,'AAPL',14,19,28,810,'2016-12-23 14:19:28',116.3,10,14,14,4,2016),
	(101,'AAPL',14,19,29,997,'2016-12-23 14:19:29',116.303,39,14,14,4,2016),
	(102,'AAPL',14,19,30,728,'2016-12-23 14:19:30',116.3,85,14,14,2,2016),
	(103,'AAPL',14,19,35,332,'2016-12-23 14:19:35',116.308,160,14,14,3,2016),
	(104,'AAPL',14,19,39,279,'2016-12-23 14:19:39',116.3,3,14,14,2,2016),
	(105,'AAPL',14,19,39,280,'2016-12-23 14:19:39',116.3,12,14,14,2,2016),
	(106,'AAPL',14,19,39,281,'2016-12-23 14:19:39',116.3,3,14,14,4,2016),
	(107,'AAPL',14,19,39,282,'2016-12-23 14:19:39',116.3,2,14,14,2,2016),
	(108,'AAPL',14,19,39,282,'2016-12-23 14:19:39',116.3,12,14,14,4,2016),
	(109,'AAPL',14,19,39,284,'2016-12-23 14:19:39',116.3,2,14,14,4,2016),
	(110,'AAPL',14,19,39,653,'2016-12-23 14:19:39',116.3,10,14,14,2,2016),
	(111,'AAPL',14,19,42,113,'2016-12-23 14:19:42',116.3,63,14,14,2,2016),
	(112,'AAPL',14,19,45,652,'2016-12-23 14:19:45',116.305,100,14,14,3,2016),
	(113,'AAPL',14,19,45,652,'2016-12-23 14:19:45',116.305,100,14,14,3,2016),
	(114,'AAPL',14,19,45,663,'2016-12-23 14:19:45',116.3,198,14,14,2,2016),
	(115,'AAPL',14,19,45,663,'2016-12-23 14:19:45',116.3,100,14,14,2,2016),
	(116,'AAPL',14,19,45,663,'2016-12-23 14:19:45',116.3,92,14,14,2,2016),
	(117,'AAPL',14,19,45,668,'2016-12-23 14:19:45',116.302,198,14,14,3,2016),
	(118,'AAPL',14,19,45,669,'2016-12-23 14:19:45',116.303,100,14,14,4,2016),
	(119,'AAPL',14,19,45,671,'2016-12-23 14:19:45',116.303,92,14,14,4,2016),
	(120,'AAPL',14,19,45,814,'2016-12-23 14:19:45',116.302,63,14,14,4,2016),
	(121,'AAPL',14,19,46,64,'2016-12-23 14:19:46',116.305,100,14,14,3,2016),
	(122,'AAPL',14,19,46,64,'2016-12-23 14:19:46',116.3,100,14,14,2,2016),
	(123,'AAPL',14,19,46,67,'2016-12-23 14:19:46',116.3,100,14,14,2,2016),
	(124,'AAPL',14,19,46,67,'2016-12-23 14:19:46',116.3,100,14,14,2,2016),
	(125,'AAPL',14,19,46,67,'2016-12-23 14:19:46',116.3,100,14,14,2,2016);

/*!40000 ALTER TABLE `stream_trade` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;