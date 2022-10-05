-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nhaxinh_project
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('1c25762f-5a40-4173-a45b-e90e528a115d','2022-09-09 10:42:51.819000',NULL,0,'2022-10-04 17:02:45.686000',NULL,'Ghế siêu đẹp','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1664506360/Product_Image_Nhaxinh/0e8a3c58-fe07-43b8-ac7b-2ba3906c98fd.jpg','Chân bánh xe đen- Aluminium - Nệm da tổng hợp màu Cognac',14688000,12,'D490-R500-C920/820/530/430 ','ghe-xoay-basil-cognac','Còn Hàng','Ghế xoay Basil Cognac',1664877765629,29,18,'GL-31*12342123',NULL),('4ce3fa74-d7eb-4442-bbea-ce6c78f3b334','2022-09-09 10:09:47.287000',NULL,0,'2022-10-03 15:11:49.656000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1662693041/Product_Image_Nhaxinh/18bc42f0-5096-45b8-a732-b5279a882a9f.jpg','Gỗ dẻ gai, mặt melamine vân cẩm thạch',10769501,22,'D1800-R1000-C750','ban-an-go-pio-6-cho-1m8-mau-2','Còn Hàng','Bàn ăn gỗ Pio 6 chỗ 1m8 mẫu 2',1664784709637,21,9,'BA-31*32212321',NULL),('9a209b82-bff7-4a09-873b-7aa5ceb58a1f',NULL,NULL,0,'2022-09-09 11:02:08.991000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1662696143/Product_Image_Nhaxinh/412a8b3d-5884-4539-a436-cd0a698bc943.jpg','Chân gỗ mahogany  mặt mdf sơn màu trắng ',10769501,15,'D1200 - R500 - C750/1200 mm','ban-trang-diem-skagen','Còn Hàng','Bàn trang điểm Skagen',1662696128988,30,16,'BT-31*32141678',NULL),('b0cc2c60-681e-45ba-9e86-53b7a39b54d9','2022-09-17 00:14:46.880000',NULL,0,'2022-10-04 16:56:22.088000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1663665412/Product_Image_Nhaxinh/71958988-24d8-414d-b6ee-356a83cedd23.jpg','Chân Inox màu gold, tay gỗ, bọc vải, nệm ngồi tháo rời',24565000,16,'D2060 - R750 - C820/400 mm','sofa-3-cho-osaka-mau-1-vai-29','Còn Hàng','Sofa 3 chỗ Osaka mẫu 1 vải 29',1664877382065,23,11,'SF-31*12345638',NULL),('b4688eb9-c68a-4c87-b85b-1f8f5bed0e3e','2022-09-09 10:37:33.939000',NULL,0,'2022-10-03 11:37:45.011000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1662694709/Product_Image_Nhaxinh/bc8df184-e5e9-4830-8693-f95702dd6e5e.jpg','MDF sơn',3848000,23,'D1500 - R400 - C500','tu-tivi-mdf-son','Còn Hàng','Tủ tivi MDF sơn',1664771864793,28,19,'TT-31*12324786',NULL),('bfc0b510-3c33-496f-a40e-2677cb029056','2022-09-09 10:24:14.045000',NULL,0,'2022-10-04 15:14:51.498000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1663051580/Product_Image_Nhaxinh/4faea8c1-21ff-4463-8742-d3c0ea24ad67.jpg','Gỗ Sồi kết hợp  MDF veneer, chân thép sơn tĩnh điện',23488000,90,'D850 - R380 - C1980mm','ke-sach-artigo','Còn Hàng','Kệ Sách Artigo',1664871291288,25,13,'KS-31*23654432',NULL),('c5cb2a72-554e-45c6-99d8-f3cb93adb82c','2022-09-09 10:20:53.329000',NULL,0,'2022-09-09 10:20:53.351000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1662693711/Product_Image_Nhaxinh/a25a7f82-a0bd-4d95-8ce7-351dbe1a2a08.jpg','Thân gốm,chụp vải',15736000,12,'D260 - R580 - C660 mm','den-ban-savona','Còn Hàng','Đèn bàn Savona',1662693650055,24,11,'DT-31*35421321',NULL),('cd840137-2b73-44b2-8a71-dca8e883b209','2022-09-09 11:04:04.522000',NULL,0,'2022-09-12 21:01:21.980000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1662696325/Product_Image_Nhaxinh/bae7aa17-35aa-4259-ae5c-f383b02feddc.jpg','hân gỗ mahogany , mặt mdf sơn màu trắng +noughat , gương kính thủy',10769501,22,'D1200 - R500 - C750/1200 mm','ban-trang-diem-skagen','Còn Hàng','Bàn trang điểm Skagen',1662991281978,30,16,'BT-31*45214554',NULL),('e9cf3603-16c9-4159-8015-51d4af76859c','2022-09-09 10:33:23.493000',NULL,0,'2022-09-17 00:12:20.811000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1663348394/Product_Image_Nhaxinh/b45d986e-7331-4503-acf8-61fe4a27036f.jpg','Gỗ sồi+ tràm, MDF sơn trắng',6335000,1,'D980 - R630 - C1980','tu-ao-harmony','Đang chờ','Tủ áo Harmony',1663348330663,27,16,'TA-31*42152432',NULL),('f53690d7-0c3e-4d7e-9132-7d63a2771d7a','2022-09-09 11:00:46.847000',NULL,0,'2022-09-12 21:01:21.950000',NULL,'---','https://res.cloudinary.com/phucnguyenksqt11/image/upload/v1662696101/Product_Image_Nhaxinh/f58947da-bf94-4a1c-a65d-9da9101ccde2.jpg','gốm',1360000,20,'D140 - R140 - C420 mm','binh-gom-cao-nau-nho-42cm-22674','Còn Hàng','Bình gốm cao nâu nhỏ 42cm 22674',1662991281947,31,18,'BT-31*34543212',NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-05 17:27:46
