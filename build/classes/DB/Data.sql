-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.35 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for pcmart
CREATE DATABASE IF NOT EXISTS `pcmart` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pcmart`;

-- Dumping structure for table pcmart.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` text NOT NULL,
  `employee_nic` varchar(12) NOT NULL,
  `city_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_employee1_idx` (`employee_nic`),
  KEY `fk_address_city1_idx` (`city_id`),
  CONSTRAINT `fk_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_address_employee1` FOREIGN KEY (`employee_nic`) REFERENCES `employee` (`nic`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.address: ~1 rows (approximately)
REPLACE INTO `address` (`id`, `address`, `employee_nic`, `city_id`) VALUES
	(1, '12/5, Dondra, Matara, Sri Lanka', '200312713420', 1);

-- Dumping structure for table pcmart.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.brand: ~5 rows (approximately)
REPLACE INTO `brand` (`id`, `name`) VALUES
	(1, 'Asus'),
	(2, 'HP'),
	(3, 'Dell'),
	(4, 'Lenovo'),
	(5, 'Sony');

-- Dumping structure for table pcmart.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.city: ~5 rows (approximately)
REPLACE INTO `city` (`id`, `name`) VALUES
	(1, 'Matara'),
	(2, 'Galle'),
	(3, 'Colombo'),
	(4, 'Thangalle'),
	(5, 'Negombo');

-- Dumping structure for table pcmart.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.company: ~6 rows (approximately)
REPLACE INTO `company` (`id`, `company_name`) VALUES
	(1, 'HP'),
	(2, 'Asus'),
	(3, 'Dell'),
	(4, 'Lenovo'),
	(5, 'Sony'),
	(6, 'Huawei');

-- Dumping structure for table pcmart.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `email` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `mobile` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.customer: ~2 rows (approximately)
REPLACE INTO `customer` (`email`, `firstName`, `lastName`, `mobile`) VALUES
	('Sadun@gmail.com', 'Sadun', 'Akalanka', '0769898146'),
	('Unknown', 'None', 'None', 'None');

-- Dumping structure for table pcmart.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `nic` varchar(12) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `employee_type_id` int NOT NULL,
  `gender_id` int NOT NULL,
  `dob` date DEFAULT NULL,
  `join_date` date DEFAULT NULL,
  PRIMARY KEY (`nic`),
  KEY `fk_employee_employee_type_idx` (`employee_type_id`),
  KEY `fk_employee_gender1_idx` (`gender_id`),
  CONSTRAINT `fk_employee_employee_type` FOREIGN KEY (`employee_type_id`) REFERENCES `employee_type` (`id`),
  CONSTRAINT `fk_employee_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.employee: ~5 rows (approximately)
REPLACE INTO `employee` (`nic`, `firstName`, `lastName`, `password`, `mobile`, `employee_type_id`, `gender_id`, `dob`, `join_date`) VALUES
	('200312713420', 'Kaveesh', 'Senanayaka', '123456', '0769898170', 1, 1, '2024-05-14', '2024-05-14'),
	('200312713421', 'Supun', 'Perera', '123456', '0769898171', 2, 1, '2024-05-14', '2024-05-14'),
	('200312713422', 'Pasindu', 'Subashitha', '123456', '0769898172', 1, 1, '2024-05-14', '2024-05-14'),
	('200312713423', 'Uvindu', 'Sandeep', '123456', '0769898173', 2, 1, '2024-05-14', '2024-05-14'),
	('200312713424', 'Sansala', 'Prabashvari', '123456', '0769898174', 1, 2, '2024-05-14', '2024-05-14');

-- Dumping structure for table pcmart.employee_type
CREATE TABLE IF NOT EXISTS `employee_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.employee_type: ~2 rows (approximately)
REPLACE INTO `employee_type` (`id`, `type`) VALUES
	(1, 'Admin'),
	(2, 'Cashier');

-- Dumping structure for table pcmart.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.gender: ~2 rows (approximately)
REPLACE INTO `gender` (`id`, `type`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table pcmart.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` varchar(10) NOT NULL,
  `employee_nic` varchar(12) NOT NULL,
  `price` varchar(45) NOT NULL,
  `purchesedDate` date NOT NULL,
  `supplier_email` varchar(45) NOT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_employee1_idx` (`employee_nic`),
  KEY `fk_grn_supplier1_idx` (`supplier_email`),
  KEY `fk_grn_company1_idx` (`company_id`),
  CONSTRAINT `fk_grn_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_grn_employee1` FOREIGN KEY (`employee_nic`) REFERENCES `employee` (`nic`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_email`) REFERENCES `supplier` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.grn: ~1 rows (approximately)
REPLACE INTO `grn` (`id`, `employee_nic`, `price`, `purchesedDate`, `supplier_email`, `company_id`) VALUES
	('#329b6e5f', '200312713420', '9000000', '2024-05-19', 'kasun@gmail.com', 1);

-- Dumping structure for table pcmart.grn_item
CREATE TABLE IF NOT EXISTS `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `buying_price` varchar(45) NOT NULL,
  `qty` int NOT NULL,
  `stock_id` varchar(10) NOT NULL,
  `grn_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_stock1_idx` (`stock_id`),
  KEY `fk_grn_item_grn1_idx` (`grn_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_item_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.grn_item: ~1 rows (approximately)
REPLACE INTO `grn_item` (`id`, `buying_price`, `qty`, `stock_id`, `grn_id`) VALUES
	(5, '450000', 20, '#7c503caa', '#329b6e5f');

-- Dumping structure for table pcmart.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` varchar(10) NOT NULL,
  `employee_nic` varchar(12) NOT NULL,
  `products` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `Date` date NOT NULL,
  `discount` double NOT NULL,
  `customer_email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_employee1_idx` (`employee_nic`),
  KEY `fk_invoice_customer1_idx` (`customer_email`),
  CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_email`) REFERENCES `customer` (`email`),
  CONSTRAINT `fk_invoice_employee1` FOREIGN KEY (`employee_nic`) REFERENCES `employee` (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.invoice: ~2 rows (approximately)
REPLACE INTO `invoice` (`id`, `employee_nic`, `products`, `price`, `Date`, `discount`, `customer_email`) VALUES
	('#9e035baf', '200312713420', '1', 500000, '2024-05-16', 0, 'Unknown'),
	('#c606bf1b', '200312713420', '1', 5000000, '2024-05-18', 0, 'Unknown');

-- Dumping structure for table pcmart.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_id` varchar(10) NOT NULL,
  `qty` int NOT NULL,
  `invoice_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_table1_stock1_idx` (`stock_id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_table1_stock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.invoice_item: ~2 rows (approximately)
REPLACE INTO `invoice_item` (`id`, `stock_id`, `qty`, `invoice_id`) VALUES
	(6, '#7c503caa', 1, '#9e035baf'),
	(8, '#7c503caa', 10, '#c606bf1b');

-- Dumping structure for table pcmart.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_brand1_idx` (`brand_id`),
  CONSTRAINT `fk_product_brand1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.product: ~3 rows (approximately)
REPLACE INTO `product` (`id`, `brand_id`, `name`) VALUES
	(1, 1, 'i5 Laptop'),
	(2, 5, 'i3 CPU'),
	(3, 2, 'i7 Laptop');

-- Dumping structure for table pcmart.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `id` varchar(10) NOT NULL,
  `product_id` int NOT NULL,
  `price` int NOT NULL,
  `qty` int NOT NULL,
  `warrenty_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  KEY `fk_stock_warrenty1_idx` (`warrenty_id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_stock_warrenty1` FOREIGN KEY (`warrenty_id`) REFERENCES `warrenty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.stock: ~2 rows (approximately)
REPLACE INTO `stock` (`id`, `product_id`, `price`, `qty`, `warrenty_id`) VALUES
	('#7c503caa', 3, 500000, 60, 7),
	('#99f2537e', 1, 250000, 110, 7);

-- Dumping structure for table pcmart.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `email` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `mobile` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.supplier: ~1 rows (approximately)
REPLACE INTO `supplier` (`email`, `firstName`, `lastName`, `mobile`) VALUES
	('kasun@gmail.com', 'Kasun', 'Siriwardhana', '0767878170');

-- Dumping structure for table pcmart.warrenty
CREATE TABLE IF NOT EXISTS `warrenty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `duration` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table pcmart.warrenty: ~8 rows (approximately)
REPLACE INTO `warrenty` (`id`, `duration`) VALUES
	(1, '1 Week'),
	(2, '3 Week'),
	(3, '1 Month'),
	(4, '3 Month'),
	(5, '6 Month'),
	(6, '1 Year'),
	(7, '2 Year'),
	(8, '5 Year');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
