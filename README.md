# list-todos
This is a server app that listens to the slash commands from slack and on the basis of commands it does 1.Stores the tasks for
different channels 2. marks as done 3. lists all todos

for storing todos at the backend mysql has been used as DB and for serving the requests tomcat server is up and serves 
accordingly


Used DB Schema

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema docable
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema docable
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `docable` ;
USE `docable` ;

-- -----------------------------------------------------
-- Table `docable`.`channel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `docable`.`channel` (
  `channel_id` INT NOT NULL AUTO_INCREMENT,
  `channel_name` VARCHAR(300) NULL,
  PRIMARY KEY (`channel_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `docable`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `docable`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(250) NULL,
  `channel_id` INT NULL,
  PRIMARY KEY (`user_id`),
    FOREIGN KEY (`channel_id`)
    REFERENCES `docable`.`channel` (`channel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `docable`.`todos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `docable`.`todos` (
  `todo_id` INT NOT NULL AUTO_INCREMENT,
  `todo` VARCHAR(1000) NULL,
  `added_by_id` INT NOT NULL,
  `done` TINYINT(1) NULL,
  `channel_id` INT NULL,
  PRIMARY KEY (`todo_id`),
    FOREIGN KEY (`channel_id`)
    REFERENCES `docable`.`channel` (`channel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
