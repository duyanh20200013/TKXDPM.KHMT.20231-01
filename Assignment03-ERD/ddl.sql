-- MariaDB dump 10.19-11.2.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: aims
-- ------------------------------------------------------
-- Server version	11.2.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart` (
  `lastSaved` datetime NOT NULL,
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_item` (
  `item_id` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `count` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`item_id`,`cart_id`),
  KEY `cart_item_FK_1` (`cart_id`),
  CONSTRAINT `cart_item_FK` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `cart_item_FK_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `state` smallint(6) NOT NULL,
  `is_rush` tinyint(1) NOT NULL,
  `create_date` datetime NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `province_id` int(11) NOT NULL,
  `note` text DEFAULT NULL,
  `rush_address` varchar(100) DEFAULT NULL,
  `rush_province_id` int(11) DEFAULT NULL,
  `rush_note` text DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `invoice_FK` (`province_id`),
  KEY `invoice_FK_1` (`rush_province_id`),
  CONSTRAINT `invoice_FK` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `invoice_FK_1` FOREIGN KEY (`rush_province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_item`
--

DROP TABLE IF EXISTS `invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_item` (
  `invoice_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `price` bigint(20) NOT NULL,
  `remain` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `image` bigint(20) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `rushable` tinyint(1) NOT NULL,
  `invoice_id` int(11) DEFAULT NULL,
  `is_rush` tinyint(1) NOT NULL,
  PRIMARY KEY (`invoice_item_id`),
  KEY `invoice_item_FK` (`invoice_id`),
  CONSTRAINT `invoice_item_FK` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_province_rushable`
--

DROP TABLE IF EXISTS `item_province_rushable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_province_rushable` (
  `item_id` int(11) NOT NULL,
  `province_id` int(11) NOT NULL,
  PRIMARY KEY (`item_id`,`province_id`),
  KEY `item_province_rushable_FK_1` (`province_id`),
  CONSTRAINT `item_province_rushable_FK` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
  CONSTRAINT `item_province_rushable_FK_1` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `price` bigint(20) NOT NULL,
  `remain` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `image` bigint(20) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `lastPriceChange` datetime NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL,
  `ammount` bigint(20) NOT NULL,
  `note` text NOT NULL,
  `success` tinyint(1) NOT NULL,
  `desc` varchar(100) NOT NULL,
  `native_data` longtext NOT NULL,
  `create_date` datetime NOT NULL,
  `gate_name` varchar(30) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  KEY `payment_FK` (`invoice_id`),
  CONSTRAINT `payment_FK` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `province` (
  `province_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `policy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`province_id`),
  KEY `province_FK` (`policy_id`),
  CONSTRAINT `province_FK` FOREIGN KEY (`policy_id`) REFERENCES `ship_policy` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ship_policy`
--

DROP TABLE IF EXISTS `ship_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ship_policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `free_threadhold` bigint(20) NOT NULL,
  `lowest` bigint(20) NOT NULL,
  `start_price` bigint(20) NOT NULL,
  `extend_price` bigint(20) NOT NULL,
  `rush_charge` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-23 23:13:35
