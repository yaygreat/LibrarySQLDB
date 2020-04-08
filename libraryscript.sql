-- MySQL dump 10.13  Distrib 5.5.8, for Win32 (x86)
--
-- Host: localhost    Database: abpbooks_db
-- ------------------------------------------------------
-- Server version	5.5.8
-- DROP DATABASE IF EXISTS library;
-- CREATE DATABASE library;
USE library;

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
-- Table structure for table `book`
--

 DROP TABLE IF EXISTS `book`;
 /*!40101 SET @saved_cs_client     = @@character_set_client */;
 /*!40101 SET character_set_client = utf8 */;
 CREATE TABLE `book` (
  `ISBN10` varchar(10) NOT NULL,
  `ISBN13` varchar(13) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author_names` varchar(500) NOT NULL,
  `cover` varchar(100) NOT NULL,
  `publisher` varchar(100) NOT NULL,
  `pages` int NOT NULL,
  `isAvailable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ISBN10`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 /*!40101 SET character_set_client = @saved_cs_client */;

LOAD DATA INFILE 'books.csv' INTO TABLE book
FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\r\n'
(@ISBN10, @ISBN13, @Title, @Author, @Cover, @Publisher, @Pages) set ISBN10=@ISBN10, ISBN13=@ISBN13, title=@Title, author_names=@author, cover=@Cover, publisher=@Publisher, pages=@Pages;

--
-- Table structure for table `author`
--

 DROP TABLE IF EXISTS `author`;
 /*!40101 SET @saved_cs_client     = @@character_set_client */;
 /*!40101 SET character_set_client = utf8 */;
 CREATE TABLE `author` (
  `author_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`author_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 /*!40101 SET character_set_client = @saved_cs_client */;

  INSERT INTO author (name)(
  select distinct
  SUBSTRING_INDEX(SUBSTRING_INDEX(book.author_names, ',', numbers.n), ',', -1) author_names
  from
 (select 1 n union
   select 2) numbers INNER JOIN book
  on CHAR_LENGTH(book.author_names)
     -CHAR_LENGTH(REPLACE(book.author_names, ',', ''))>=numbers.n-1);


--
-- Table structure for table `book_author`
--

 DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_author` (
  `ISBN10` varchar(10) NOT NULL,
  `author_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`ISBN10`, `author_id`),
  FOREIGN KEY (`author_id`) REFERENCES author(`author_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`ISBN10`) REFERENCES book(`ISBN10`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO book_author (ISBN10, author_id)(
SELECT isbn10, author_id FROM book, author WHERE author_names=name or author_names LIKE concat('%,', name , ',%') or author_names LIKE concat('%', name , ',%') or author_names LIKE concat('%,', name , '%'));

DROP VIEW IF EXISTS ConcatAuthorsBooks;
CREATE VIEW ConcatAuthorsBooks AS SELECT ISBN10, ISBN13, title, cover, publisher, pages, isAvailable, group_concat(distinct name order by name) from (SELECT book.ISBN10, book.ISBN13, book.title, v.name, book.cover, book.publisher, book.pages, book.isAvailable FROM (SELECT book_author.*, author.name FROM book_author JOIN author ON author.author_id=book_author.author_id) AS v JOIN book ON v.ISBN10=book.ISBN10) as w group by ISBN10;

ALTER TABLE book DROP COLUMN author_names;

--
-- Table structure for table `borrower`
--

 DROP TABLE IF EXISTS `borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrower` (
  `borrower_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ssn` varchar(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`borrower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOAD DATA INFILE 'borrowers.csv' INTO TABLE borrower
FIELDS TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 LINES
(@borrower_id, @ssn, @first_name, @last_name, @email, @address, @city, @state, @phone) set borrower_id=@borrower_id, ssn=@ssn, first_name=@first_name, last_name=@last_name, email=@email, address=@address, city=@city, state=@state, phone=@phone;

--
-- Table structure for table `book_loan`
--

 DROP TABLE IF EXISTS `book_loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_loan` (
  `loan_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ISBN10` varchar(10) NOT NULL,
  `borrower_id` bigint(20) unsigned NOT NULL,
  `date_out` date NOT NULL,
  `due_date`date NOT NULL,
  `date_in` date DEFAULT null,
  PRIMARY KEY (`loan_id`),
  FOREIGN KEY (`ISBN10`) REFERENCES book(`ISBN10`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`borrower_id`) REFERENCES borrower(`borrower_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fine`
--

 DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fine` (
  `loan_id` bigint(20) unsigned NOT NULL DEFAULT '1',
  `fine_amt` decimal(19, 2) NOT NULL DEFAULT '1',
  `paid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`loan_id`),
  FOREIGN KEY (`loan_id`) REFERENCES book_loan(`loan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP VIEW IF EXISTS ConcatFinesBorrowers;
CREATE VIEW ConcatFinesBorrowers AS SELECT fine.*, book_loan.ISBN10, book_loan.due_date, book_loan.date_in, borrower.borrower_id, borrower.flag FROM fine JOIN book_loan ON fine.loan_id=book_loan.loan_id JOIN borrower ON book_loan.borrower_id=borrower.borrower_id;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-07-21  9:31:38
