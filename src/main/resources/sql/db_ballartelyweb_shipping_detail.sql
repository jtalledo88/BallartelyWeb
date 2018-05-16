CREATE DATABASE  IF NOT EXISTS `db_ballartelyweb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_ballartelyweb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: db_ballartelyweb
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `shipping_detail`
--

DROP TABLE IF EXISTS `shipping_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shipping_detail` (
  `shipping_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `shipping_head_id` int(11) NOT NULL,
  `product_label_id` int(11) NOT NULL,
  `shipping_quantity` int(11) NOT NULL,
  `shipping_unit_price` decimal(10,2) NOT NULL,
  `shipping_amout` decimal(10,2) NOT NULL,
  `shipping_creation_date` datetime NOT NULL,
  `shipping_modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`shipping_detail_id`,`shipping_head_id`),
  KEY `fk_shipping_head_id_idx` (`shipping_head_id`),
  KEY `fk_sd_product_label_id_idx` (`product_label_id`),
  CONSTRAINT `fk_sd_product_label_id` FOREIGN KEY (`product_label_id`) REFERENCES `product_label` (`product_label_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_shipping_head_id` FOREIGN KEY (`shipping_head_id`) REFERENCES `shipping_head` (`shipping_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_detail`
--

LOCK TABLES `shipping_detail` WRITE;
/*!40000 ALTER TABLE `shipping_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-15 22:43:38
