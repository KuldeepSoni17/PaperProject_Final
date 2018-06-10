-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2018 at 12:47 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `promise_paper_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `courselist`
--

CREATE TABLE IF NOT EXISTS `courselist` (
  `courseid` int(11) NOT NULL AUTO_INCREMENT,
  `coursename` varchar(500) NOT NULL,
  `semstart` int(11) NOT NULL,
  `semend` int(11) NOT NULL,
  PRIMARY KEY (`courseid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `courselist`
--

INSERT INTO `courselist` (`courseid`, `coursename`, `semstart`, `semend`) VALUES
(1, 'BE', 1, 8),
(2, 'ME', 1, 4),
(3, 'BTech', 1, 8),
(4, 'BCA', 1, 6);

-- --------------------------------------------------------

--
-- Table structure for table `paperlist`
--

CREATE TABLE IF NOT EXISTS `paperlist` (
  `paperid` int(11) NOT NULL AUTO_INCREMENT,
  `subjectid` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `pathoffile` varchar(500) NOT NULL,
  PRIMARY KEY (`paperid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `paperlist`
--

INSERT INTO `paperlist` (`paperid`, `subjectid`, `year`, `month`, `pathoffile`) VALUES
(1, 1, 2001, 2, 'papers/1_2001_02.pdf'),
(2, 1, 2002, 3, 'papers/1_2002_03.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `subjectlist`
--

CREATE TABLE IF NOT EXISTS `subjectlist` (
  `subjectid` int(11) NOT NULL AUTO_INCREMENT,
  `subjectname` varchar(500) NOT NULL,
  `courseid` int(11) NOT NULL,
  `semval` int(11) NOT NULL,
  PRIMARY KEY (`subjectid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `subjectlist`
--

INSERT INTO `subjectlist` (`subjectid`, `subjectname`, `courseid`, `semval`) VALUES
(1, 'EME', 1, 1),
(2, 'Physics', 1, 1),
(3, 'C Programming', 1, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
