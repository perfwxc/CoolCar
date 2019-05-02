-- phpMyAdmin SQL Dump
-- version phpStudy 2014
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2019 �?05 �?02 �?05:26
-- 服务器版本: 5.5.53
-- PHP 版本: 5.5.38

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `coolcar`
--

-- --------------------------------------------------------

--
-- 表的结构 `data`
--

CREATE TABLE IF NOT EXISTS `data` (
  `ide` int(10) NOT NULL AUTO_INCREMENT,
  `WD` varchar(30) NOT NULL,
  `XT` varchar(30) NOT NULL,
  `XYH` varchar(30) NOT NULL DEFAULT '116',
  `XYL` varchar(30) NOT NULL DEFAULT '69',
  `WZ` varchar(30) NOT NULL DEFAULT '20',
  `XY` varchar(10) NOT NULL,
  `WD1` varchar(30) NOT NULL,
  `XT1` varchar(30) NOT NULL,
  `XYH1` varchar(30) NOT NULL,
  `XYL1` varchar(30) NOT NULL,
  `WZ1` varchar(30) NOT NULL,
  `H2S` varchar(30) NOT NULL,
  `T1` varchar(30) NOT NULL,
  `T2` varchar(30) NOT NULL,
  PRIMARY KEY (`ide`),
  UNIQUE KEY `ide` (`ide`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=143 ;

--
-- 转存表中的数据 `data`
--

INSERT INTO `data` (`ide`, `WD`, `XT`, `XYH`, `XYL`, `WZ`, `XY`, `WD1`, `XT1`, `XYH1`, `XYL1`, `WZ1`, `H2S`, `T1`, `T2`) VALUES
(1, '36.7', '96', '118', '73', '20', '96', '21', '94.0', '992', '1394', '1363', '1101', '4[2019-03-18', '06:52:33.616]'),
(4, '36.8', '87', '115', '69', '22', '93', '21', '94.0', '1048', '1372', '1352', '1153', '4[2019-03-17', '07:22:23.619]'),
(2, '36.4', '87', '119', '68', '430', '97', '21', '94.0', '994', '1401', '1415', '1110', '4[2019-03-13', '03:52:13.618]'),
(3, '37', '92', '117', '65', '21', '95', '21', '94.0', '1031', '1457', '1376', '1118', '4[2019-03-16', '05:59:03.619]'),
(5, '38.2', '103', '121', '78', '18', '94', '20', '93.0', '1078', '1459', '1382', '1136', '4[2019-03-14', '03:54:53.618]');

-- --------------------------------------------------------

--
-- 表的结构 `info`
--

CREATE TABLE IF NOT EXISTS `info` (
  `id` int(10) NOT NULL,
  `Name` varchar(10) NOT NULL,
  `Num` varchar(30) NOT NULL,
  `Type` varchar(30) NOT NULL,
  `Address` varchar(30) NOT NULL,
  `Help` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `info`
--

INSERT INTO `info` (`id`, `Name`, `Num`, `Type`, `Address`, `Help`) VALUES
(1, '彪哥', '13296507815', '奥迪A8', '湖北大学', '15838152641'),
(2, '4', '13720278995', '4', '4', '4'),
(3, '4', '13296507815', '甲壳虫', '4', '4'),
(4, '2', '13296507815', '2', '湖北省武汉市友谊大道', '2'),
(5, '2', '13296507815', '1', '2', '15071033750');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
