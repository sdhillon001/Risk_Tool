CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `risk_score`
--

DROP TABLE IF EXISTS `risk_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_score` (
  `idrisk_Score` int NOT NULL,
  `Asset_Names` varchar(45) DEFAULT NULL,
  `risk_vuln` varchar(45) DEFAULT NULL,
  `risk_likelihood` varchar(45) DEFAULT NULL,
  `risk_Impact` varchar(45) DEFAULT NULL,
  `risk_Score` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idrisk_Score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk_score`
--

LOCK TABLES `risk_score` WRITE;
/*!40000 ALTER TABLE `risk_score` DISABLE KEYS */;
INSERT INTO `risk_score` VALUES (1,'Computer','test','Low','Low','Low'),(2,'Water Meter, ','Natural Disasters','High','High','High'),(3,'Air Quality Sensor, Weather Station, ','Cybersecurity Breach','Low','Low','Low'),(4,'Surveillance Camera, ','Natural Disasters','Low','Medium','Medium'),(5,'Street Light, Air Quality Sensor, ','Malicious Insider Threats','Low','Low','Low'),(6,'Solar Panel, Weather Station, ','Natural Disasters','Low','Low','Low'),(7,'AS, ','Malicious Insider Threats','Low','Medium','Medium'),(8,'AS, ','Malicious Insider Threats','Low','Medium','Medium'),(9,'Solar Panel, ',' Data Privacy Breaches','Low','Low','Low'),(10,'Traffic_Light, Solar Panel, ','Malicious Insider Threats','Low','Medium','Medium'),(11,'Air Quality Sensor, Weather Station, ','Natural Disasters','Medium','Medium','Medium');
/*!40000 ALTER TABLE `risk_score` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-25 11:43:30
