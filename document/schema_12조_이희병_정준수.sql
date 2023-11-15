-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema thememap
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema thememap
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `thememap` DEFAULT CHARACTER SET utf8 ;
USE `thememap` ;

-- -----------------------------------------------------
-- Table `thememap`.`editor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`editor` (
  `editor_id` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(20) NOT NULL,
  `pw` VARCHAR(20) NOT NULL,
  `salt` VARCHAR(100) NULL,
  `editor_name` VARCHAR(20) NOT NULL,
  `like_sum` INT UNSIGNED NOT NULL DEFAULT 0,
  `join_date` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  `token` VARCHAR(1000) NOT NULL,
  UNIQUE INDEX `email_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`editor_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`place` (
  `place_id` INT NOT NULL AUTO_INCREMENT,
  `place_name` VARCHAR(45) NOT NULL,
  `url` VARCHAR(100) NOT NULL,
  `latitude` DECIMAL(20,17) NOT NULL,
  `longitude` DECIMAL(20,17) NOT NULL,
  `score_sum` INT UNSIGNED NOT NULL DEFAULT 0,
  `score_count` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`place_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`theme` (
  `theme_id` INT NOT NULL AUTO_INCREMENT,
  `theme_name` VARCHAR(45) NOT NULL,
  `editor_id` INT NOT NULL,
  `public` TINYINT(1) NOT NULL DEFAULT 1,
  `visible` TINYINT(1) NOT NULL DEFAULT 1,
  `like_sum` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`theme_id`),
  INDEX `fk_theme_editor_idx` (`editor_id` ASC) VISIBLE,
  CONSTRAINT `fk_theme_editor`
    FOREIGN KEY (`editor_id`)
    REFERENCES `thememap`.`editor` (`editor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`place_in_theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`place_in_theme` (
  `theme_id` INT NOT NULL,
  `place_id` INT NOT NULL,
  `editor_id` INT NOT NULL,
  PRIMARY KEY (`theme_id`, `place_id`, `editor_id`),
  INDEX `fk_theme_has_Place_Place1_idx` (`place_id` ASC) VISIBLE,
  INDEX `fk_theme_has_Place_theme1_idx` (`theme_id` ASC) VISIBLE,
  INDEX `fk_place_in_theme_editor1_idx` (`editor_id` ASC) VISIBLE,
  CONSTRAINT `fk_theme_has_Place_theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `thememap`.`theme` (`theme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_theme_has_Place_Place1`
    FOREIGN KEY (`place_id`)
    REFERENCES `thememap`.`place` (`place_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_place_in_theme_editor1`
    FOREIGN KEY (`editor_id`)
    REFERENCES `thememap`.`editor` (`editor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`like_theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`like_theme` (
  `editor_id` INT NOT NULL,
  `theme_id` INT NOT NULL,
  INDEX `fk_like_editor1_idx` (`editor_id` ASC) VISIBLE,
  INDEX `fk_like_theme1_idx` (`theme_id` ASC) VISIBLE,
  PRIMARY KEY (`editor_id`, `theme_id`),
  CONSTRAINT `fk_like_editor1`
    FOREIGN KEY (`editor_id`)
    REFERENCES `thememap`.`editor` (`editor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_like_theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `thememap`.`theme` (`theme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `place_id` INT NOT NULL,
  `content` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comment_place1_idx` (`place_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_place1`
    FOREIGN KEY (`place_id`)
    REFERENCES `thememap`.`place` (`place_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`group_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`group_comment` (
  `theme_id` INT NOT NULL,
  `editor_id` INT NOT NULL,
  `content` VARCHAR(100) NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  INDEX `fk_group_theme1_idx` (`theme_id` ASC) VISIBLE,
  PRIMARY KEY (`theme_id`, `editor_id`),
  INDEX `fk_group_editor1_idx` (`editor_id` ASC) VISIBLE,
  CONSTRAINT `fk_group_theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `thememap`.`theme` (`theme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_editor1`
    FOREIGN KEY (`editor_id`)
    REFERENCES `thememap`.`editor` (`editor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`tag` (
  `tag_id` INT NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`tag_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `thememap`.`tag_in_theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thememap`.`tag_in_theme` (
  `theme_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`theme_id`, `tag_id`),
  INDEX `fk_tag_in_theme_tag1_idx` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `fk_tag_in_theme_theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `thememap`.`theme` (`theme_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tag_in_theme_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `thememap`.`tag` (`tag_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;