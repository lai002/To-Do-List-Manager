/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.4.32-MariaDB : Database - taskmanager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`taskmanager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `taskmanager`;

/*Table structure for table `tasks` */

DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
  `TaskID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) DEFAULT NULL,
  `Des` varchar(10000) DEFAULT NULL,
  `Status` varchar(10) DEFAULT NULL,
  `Date` varchar(20) DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `Prio` time DEFAULT NULL,
  KEY `TaskID` (`TaskID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `tasks` */

insert  into `tasks`(`TaskID`,`Title`,`Des`,`Status`,`Date`,`Time`,`Prio`) values (1,'Monthly Report','Compile and finalize the monthly report for the team.','Incomplete','2024-12-05','09:00:00','01:47:42'),(2,'Buy Office Supplies','Purchase printer paper, pens, and sticky notes for the office.','Incomplete','2024-12-05','10:00:00','01:49:17'),(3,'Respond to Emails','Reply to all unread emails in the box','Incomplete','2024-12-06','10:00:00','01:49:45'),(4,'Presentation for Meeting','Create PowerPoint slides for the upcoming client presentation.','Incomplete','2024-12-11','10:00:00','01:50:02'),(5,'Buy Office Supplies','Purchase printer paper, pens, and sticky notes for the office.','Incomplete','2024-12-12','10:00:00','01:50:24'),(6,'Organize Files in Folder','Sort and organize files in the shared team folder.','Incomplete','2024-12-14','10:00:00','01:50:46'),(7,'Follow Up on Payment','Contact the client to follow up on the overdue invoice.','Incomplete','2024-12-20','11:00:00','01:51:15'),(8,'Grocery Shopping','Buy groceries including vegetables, fruits, and meat.','Incomplete','2024-12-20','12:00:00','01:51:40'),(9,'Clean the Kitchen','Clean the kitchen, including wiping down counters and washing dishes.','Incomplete','2024-12-25','12:00:00','01:51:55');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ua_id` int(11) NOT NULL AUTO_INCREMENT,
  `ua_uname` varchar(100) DEFAULT NULL,
  `ua_pword` varchar(100) DEFAULT NULL,
  KEY `ua_id` (`ua_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `user` */

insert  into `user`(`ua_id`,`ua_uname`,`ua_pword`) values (1,'ella','123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
