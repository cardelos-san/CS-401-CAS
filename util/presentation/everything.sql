-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.22-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table lost_and_found.categories: ~7 rows (approximately)
DELETE FROM `categories`;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`category_id`, `name`) VALUES
	(1, 'Cell Phones'),
	(2, 'Wallets'),
	(3, 'Books'),
	(4, 'Glasses'),
	(5, 'Headphones'),
	(6, 'Keys'),
	(7, 'Miscellaneous');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;

-- Dumping data for table lost_and_found.images: ~2 rows (approximately)
DELETE FROM `images`;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` (`image_id`, `item_id`, `format`, `is_primary`) VALUES
	(1, 1, 'png', 1),
	(2, 1, 'jpg', 0);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;

-- Dumping data for table lost_and_found.inventory: ~14 rows (approximately)
DELETE FROM `inventory`;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` (`item_id`, `description_public`, `description_private`, `location_found`, `category`, `status`, `image`, `date_created`, `date_found`, `date_retrieved`, `added_by_user`) VALUES
	(1, 'Brown leather wallet', 'Contains ID of Robert Lattner', 'Found on the quad', 'wallet', 'lost', '1037770236771744.jpg', '2017-04-24 21:22:28', '2017-04-24', NULL, 1),
	(2, 'iPhone with light blue case', 'Lock screen says it belongs to Abbie Reily', 'Adams Library near the entrance', 'cellphone', 'retrieved', NULL, '2017-04-24 21:22:28', '2017-03-24', '2017-04-17', 1),
	(3, 'Plaid winter scarf', '', 'Whipple 106B', 'miscellaneous', 'lost', '1037934374108410.jpg', '2017-04-24 21:31:04', '2017-04-03', NULL, 1),
	(4, 'Modern Atomic and Nuclear Physics by AB Gupta', 'Initials JMR inside cover', 'Left on table in Donovan', 'books', 'lost', '1038138170705916.jpg', '2017-04-24 21:34:12', '2017-03-23', NULL, 1),
	(5, 'Damasko watch, model DC66', '', 'Memorial union, second floor bathroom', 'miscellaneous', 'retrieved', '1038387476281647.jpg', '2017-04-24 21:38:37', '2017-03-14', '2017-04-24', 1),
	(6, 'Toyota car key, 2 silver keys', '', 'Outside Horace Mann', 'keys', 'lost', '1038713591506577.jpg', '2017-04-24 21:43:52', '2017-04-21', NULL, 1),
	(7, 'Samsung Galaxy Note 7', '', 'Dumpster', 'cellphone', 'lost', NULL, '2017-04-24 21:46:15', '2017-02-09', NULL, 1),
	(8, 'Sunglasses with yellow lenses', '', 'Parking lot B', 'glasses', 'lost', '1039037519165176.jpg', '2017-04-24 21:49:16', '2017-04-11', NULL, 1),
	(9, 'AKG K182 headphones', '', 'Craig-Lee 202', 'headphones', 'retrieved', '1039333472272628.jpg', '2017-04-24 21:54:23', '2017-04-10', '2017-04-24', 1),
	(10, 'Michael Kors gray wallet', 'Contains license of Amanda Smith', 'Alger 109', 'wallet', 'lost', '1039549740490931.jpg', '2017-04-24 21:58:00', '2017-04-19', NULL, 1),
	(11, 'The Death of Expertise book', '', 'Craig-Lee 105', 'books', 'lost', NULL, '2017-04-24 22:00:21', '2017-03-22', NULL, 1),
	(12, 'Oakley sunglasses', '', 'Student Union lobby', 'glasses', 'lost', '1039873534124511.jpg', '2017-04-24 22:03:23', '2017-04-19', NULL, 1),
	(13, 'Scott Pilgrim volume 1', '', 'Donovan', 'books', 'lost', '1040165417986861.jpg', '2017-04-24 22:08:15', '2017-04-08', NULL, 1),
	(14, 'Black key fob & black leather keychain', 'Back of keychain has initials RK', 'Lot D', 'keys', 'retrieved', '1040321143996622.jpg', '2017-04-24 22:10:38', '2017-04-22', '2017-04-24', 1),
	(15, 'Rainbow color umbrella', '', 'Fogarty 207', 'miscellaneous', 'lost', '1040668071309044.jpg', '2017-04-24 22:16:38', '2017-03-28', NULL, 1),
	(16, 'Pixel phone', '', 'Lot B', 'cellphone', 'lost', '1040836385281407.jpg', '2017-04-24 22:19:26', '2017-04-18', NULL, 1),
	(17, 'Reading glasses, retro look', '', 'Adams Library', 'glasses', 'lost', '1041024371787054.jpg', '2017-04-24 22:22:34', '2017-02-23', NULL, 1);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;

-- Dumping data for table lost_and_found.inventory_category_map: ~2 rows (approximately)
DELETE FROM `inventory_category_map`;
/*!40000 ALTER TABLE `inventory_category_map` DISABLE KEYS */;
INSERT INTO `inventory_category_map` (`item_id`, `category_id`) VALUES
	(1, 2),
	(2, 1);
/*!40000 ALTER TABLE `inventory_category_map` ENABLE KEYS */;

-- Dumping data for table lost_and_found.retrieval_records: ~3 rows (approximately)
DELETE FROM `retrieval_records`;
/*!40000 ALTER TABLE `retrieval_records` DISABLE KEYS */;
INSERT INTO `retrieval_records` (`retrieval_id`, `item_id`, `first_name`, `last_name`, `email`, `phone`, `identification`) VALUES
	(1, 2, 'Kaitlyn', 'Somename', 'kaitlyn@example.net', '4016559815', 'RI1865497'),
	(2, 5, 'Michael', 'Arbel', 'marbel@example.com', '4015555562', 'RI1234567'),
	(3, 9, 'Lee', 'Henry', 'lhenry@example.com', '4015555587', 'OK9870987098435'),
	(4, 14, 'Robert', 'Kleiner', 'robtheman@exmaple.com', '4015556781', 'MA511235484');
/*!40000 ALTER TABLE `retrieval_records` ENABLE KEYS */;

-- Dumping data for table lost_and_found.users: ~1 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `email`, `passwd`, `role`, `first_name`, `last_name`, `time_registered`) VALUES
	(1, 'tester@example.net', '$2a$10$NHHKb4wYP6d60sHrpVUIxudvpqyOnPAKw6J..B9PE2YYycWChHE6u', 'admin', 'Admin', 'User', '2017-04-24 21:22:28');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping data for table lost_and_found.user_hashes: ~2 rows (approximately)
DELETE FROM `user_hashes`;
/*!40000 ALTER TABLE `user_hashes` DISABLE KEYS */;
INSERT INTO `user_hashes` (`hash`, `user_id`, `expiration_date`) VALUES
	('27c30ca513d2651d076f74738b255a2038961bde9a1aa552e9fbacf4c71b8113', 1, '2017-05-24'),
	('FE1E6A4E528A8DCF75F306DA2FFF5EF9B4C7C276BBBE4E8E8D69AAE95F590787', 1, '2017-05-24');
/*!40000 ALTER TABLE `user_hashes` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
