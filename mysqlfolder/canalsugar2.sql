-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 08, 2024 at 02:13 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
USE `canalSugar2`;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `canalsugar2`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `adminid` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`adminid`, `email`, `firstname`, `lastname`, `number`, `pass`) VALUES
(1, 'ibrahimahmed631@gmail.com', 'Ibrahim', 'Wagih', '01060094330', '$2a$12$TASfzmv9pG8O66dxidbTjunqLlVAF3.gFNlfTQBCpsnOElu7g4PUG'),
(3, 'esmaiel_shaymaa2007@yahoo.com', 'Dr, ', 'Shaymaa', '01000098305', '$2a$12$d8JdwXyZMSRk1e6aVg.iMubhdRzsTMMevBKMGUN..oZ32Nk9S.H9y'),
(4, 'mohamed.abdelmegeed@canalsugar.com', 'Mohamed', 'Abdelmegeed', '01111186609', '$2a$12$UAHu5ofAPkr3otMzuHEHRer7F/zwkGC3PcIckVc1RTyJJgGSRZtIu');

-- --------------------------------------------------------

--
-- Table structure for table `asset`
--

CREATE TABLE `asset` (
  `assetid` int(11) NOT NULL,
  `assetmodel` varchar(255) NOT NULL,
  `assetname` varchar(255) NOT NULL,
  `assetserial` varchar(255) NOT NULL,
  `assettypeid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `asset`
--

INSERT INTO `asset` (`assetid`, `assetmodel`, `assetname`, `assetserial`, `assettypeid`) VALUES
(28, '2024', 'SAMSUNG', 'OP098H1', 2),
(29, '2024', 'LENOVO', 'YA987H1	', 1),
(32, '2024', 'LENOVO', 'YA987H1', 1),
(33, '2024', 'LENOVO', 'HJ98873J', 1),
(34, '2024', 'SAMSUNG', 'HJ98873K', 1);

-- --------------------------------------------------------

--
-- Table structure for table `asset_type`
--

CREATE TABLE `asset_type` (
  `assettypeid` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `asset_type`
--

INSERT INTO `asset_type` (`assettypeid`, `name`) VALUES
(1, 'Laptop'),
(2, 'Screen'),
(6, 'Keyboard'),
(9, 'ROUTERS');

-- --------------------------------------------------------

--
-- Table structure for table `assignedasset`
--

CREATE TABLE `assignedasset` (
  `asid` int(11) NOT NULL,
  `assetid` int(11) NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `departmentid` int(11) NOT NULL,
  `departmentname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`departmentid`, `departmentname`) VALUES
(1, 'HR'),
(2, 'IT'),
(3, 'FINANCE'),
(5, 'SUPPLY CHAIN');

-- --------------------------------------------------------

--
-- Table structure for table `spring_session`
--

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `spring_session`
--

INSERT INTO `spring_session` (`PRIMARY_ID`, `SESSION_ID`, `CREATION_TIME`, `LAST_ACCESS_TIME`, `MAX_INACTIVE_INTERVAL`, `EXPIRY_TIME`, `PRINCIPAL_NAME`) VALUES
('a4d1558e-06de-4ad8-a64d-ed11de38ed92', 'd101c04c-8a92-4b63-96b0-30b17314794e', 1725796686732, 1725796713053, 1800, 1725798513053, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `spring_session_attributes`
--

CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `spring_session_attributes`
--

INSERT INTO `spring_session_attributes` (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`, `ATTRIBUTE_BYTES`) VALUES
('a4d1558e-06de-4ad8-a64d-ed11de38ed92', 'adminID', 0xaced0005737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b020000787000000001),
('a4d1558e-06de-4ad8-a64d-ed11de38ed92', 'email', 0xaced00057400196962726168696d61686d656436333140676d61696c2e636f6d),
('a4d1558e-06de-4ad8-a64d-ed11de38ed92', 'Firstname', 0xaced00057400074962726168696d),
('a4d1558e-06de-4ad8-a64d-ed11de38ed92', 'Lastname', 0xaced00057400055761676968),
('a4d1558e-06de-4ad8-a64d-ed11de38ed92', 'number', 0xaced000574000b3031303630303934333330);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userid` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL,
  `departmentid` int(11) NOT NULL,
  `employeeid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `email`, `firstname`, `lastname`, `number`, `departmentid`, `employeeid`) VALUES
(8, 'ezzat@gmail.com', 'Mazen', 'Ezzat', '01000098305', 3, '700141'),
(9, 'ammar@gmail.com', 'Ammar', 'Mohamed', '01060094330', 2, '700140');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`adminid`);

--
-- Indexes for table `asset`
--
ALTER TABLE `asset`
  ADD PRIMARY KEY (`assetid`),
  ADD KEY `FKady9kfncpblf03u01w9q7j39i` (`assettypeid`);

--
-- Indexes for table `asset_type`
--
ALTER TABLE `asset_type`
  ADD PRIMARY KEY (`assettypeid`);

--
-- Indexes for table `assignedasset`
--
ALTER TABLE `assignedasset`
  ADD PRIMARY KEY (`asid`),
  ADD KEY `FKjyy6nyfcmoiydtcmqsvhvfcaq` (`assetid`),
  ADD KEY `FKlr23t4g68yesui00nlandaupk` (`userid`);

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`departmentid`);

--
-- Indexes for table `spring_session`
--
ALTER TABLE `spring_session`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  ADD KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  ADD KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`);

--
-- Indexes for table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`),
  ADD KEY `FK9pmqrwksr2yyi9ntrf4ipglb6` (`departmentid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `adminid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `asset`
--
ALTER TABLE `asset`
  MODIFY `assetid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `asset_type`
--
ALTER TABLE `asset_type`
  MODIFY `assettypeid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `assignedasset`
--
ALTER TABLE `assignedasset`
  MODIFY `asid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `departmentid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `asset`
--
ALTER TABLE `asset`
  ADD CONSTRAINT `FKady9kfncpblf03u01w9q7j39i` FOREIGN KEY (`assettypeid`) REFERENCES `asset_type` (`assettypeid`);

--
-- Constraints for table `assignedasset`
--
ALTER TABLE `assignedasset`
  ADD CONSTRAINT `FKjyy6nyfcmoiydtcmqsvhvfcaq` FOREIGN KEY (`assetid`) REFERENCES `asset` (`assetid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKlr23t4g68yesui00nlandaupk` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK9pmqrwksr2yyi9ntrf4ipglb6` FOREIGN KEY (`departmentid`) REFERENCES `departments` (`departmentid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
