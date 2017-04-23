/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.38-0ubuntu0.12.04.1 : Database - iepg
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iepg` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `iepg`;

/*Table structure for table `nominee` */

DROP TABLE IF EXISTS `nominee`;

CREATE TABLE `nominee` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `price` int(10) DEFAULT NULL,
  `number` bigint(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `valid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `nominee` */

insert  into `nominee`(`id`,`price`,`number`,`name`,`valid`) values (1,1000,7,'روحانی',1);
insert  into `nominee`(`id`,`price`,`number`,`name`,`valid`) values (2,1000,5,'رئیسی',1);
insert  into `nominee`(`id`,`price`,`number`,`name`,`valid`) values (3,NULL,2,'قالیباف',1);
insert  into `nominee`(`id`,`price`,`number`,`name`,`valid`) values (4,NULL,0,'جهانگیری',1);
insert  into `nominee`(`id`,`price`,`number`,`name`,`valid`) values (5,NULL,0,'میرسلیم',1);
insert  into `nominee`(`id`,`price`,`number`,`name`,`valid`) values (6,NULL,0,'هاشمی‌طبا',1);

/*Table structure for table `nomineeHistory` */

DROP TABLE IF EXISTS `nomineeHistory`;

CREATE TABLE `nomineeHistory` (
  `nomineeId` int(10) DEFAULT NULL,
  `reportTime` datetime NOT NULL,
  `price` int(10) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `nomineeHistory` */

insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (2,'2017-04-23 00:00:00',87,1);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (3,'2017-04-23 00:00:00',84,2);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (3,'2017-04-23 00:00:00',83,3);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (2,'2017-04-23 00:00:00',177,4);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (2,'2017-04-23 00:00:00',175,5);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (2,'2017-04-23 00:00:00',174,6);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (2,'2017-04-23 00:00:00',172,7);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (1,'2017-04-23 00:00:00',167,8);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (1,'2017-04-23 00:00:00',168,9);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (1,'2017-04-23 00:00:00',167,10);
insert  into `nomineeHistory`(`nomineeId`,`reportTime`,`price`,`id`) values (1,'2017-04-23 00:00:00',174,11);

/*Table structure for table `schema_version` */

DROP TABLE IF EXISTS `schema_version`;

CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `schema_version` */

insert  into `schema_version`(`version_rank`,`installed_rank`,`version`,`description`,`type`,`script`,`checksum`,`installed_by`,`installed_on`,`execution_time`,`success`) values (1,1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'root','2017-04-17 15:35:57',0,1);

/*Table structure for table `userVote` */

DROP TABLE IF EXISTS `userVote`;

CREATE TABLE `userVote` (
  `voterId` bigint(20) NOT NULL,
  `nomineeId` int(10) NOT NULL,
  `number` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `userVote` */

insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (166129676,1,5,7);
insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (51380872,6,0,8);
insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (84494667,5,0,9);
insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (166129676,4,0,10);
insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (166129676,3,2,11);
insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (166129676,2,5,12);
insert  into `userVote`(`voterId`,`nomineeId`,`number`,`id`) values (51127923,1,2,13);

/*Table structure for table `voter` */

DROP TABLE IF EXISTS `voter`;

CREATE TABLE `voter` (
  `id` bigint(20) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `points` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `voter` */

insert  into `voter`(`id`,`username`,`points`) values (51127923,'Mohammadra90',1672);
insert  into `voter`(`id`,`username`,`points`) values (51380872,'RezaKesh',2000);
insert  into `voter`(`id`,`username`,`points`) values (78189981,NULL,2000);
insert  into `voter`(`id`,`username`,`points`) values (84494667,'MardeBozorg',1999);
insert  into `voter`(`id`,`username`,`points`) values (84640183,'JaliilR',2000);
insert  into `voter`(`id`,`username`,`points`) values (166129676,'Shajizade',13);
insert  into `voter`(`id`,`username`,`points`) values (169438251,'Mohammad_Haghgou',2000);

/*Table structure for table `nomineeSoldVote` */

DROP TABLE IF EXISTS `nomineeSoldVote`;

/*!50001 DROP VIEW IF EXISTS `nomineeSoldVote` */;
/*!50001 DROP TABLE IF EXISTS `nomineeSoldVote` */;

/*!50001 CREATE TABLE  `nomineeSoldVote`(
 `nomineeId` int(10) ,
 `soldNumber` decimal(41,0) 
)*/;

/*View structure for view nomineeSoldVote */

/*!50001 DROP TABLE IF EXISTS `nomineeSoldVote` */;
/*!50001 DROP VIEW IF EXISTS `nomineeSoldVote` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `nomineeSoldVote` AS (select `userVote`.`nomineeId` AS `nomineeId`,sum(`userVote`.`number`) AS `soldNumber` from `userVote` group by `userVote`.`nomineeId`) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
