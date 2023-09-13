-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gtdaod
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gtdaod
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gtdaod` ;
USE `gtdaod` ;

-- -----------------------------------------------------
-- Table `gtdaod`.`aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gtdaod`.`aluno` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `matricula` VARCHAR(255) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `data_nasc` VARCHAR(12) NOT NULL,
  `curso` VARCHAR(255) NOT NULL,
  `coeficiente_rendimento` VARCHAR(45) NOT NULL,
  `coeficiente_progressao` VARCHAR(45) NOT NULL,
  `ch_obrigatoria_cumprida` INT NULL,
  `ch_estagio_cumprida` INT NULL,
  `ch_total_cumprida` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gtdaod`.`disciplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gtdaod`.`disciplina` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(45) NOT NULL,
  `nome_disciplina` VARCHAR(255) NOT NULL,
  `ch_prevista` VARCHAR(45) NOT NULL,
  `periodo` INT NOT NULL,
  `creditos` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gtdaod`.`aluno_cursa_disciplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gtdaod`.`aluno_cursa_disciplina` (
  `aluno_id` INT NOT NULL,
  `disciplina_id` INT NOT NULL,
  `ano` INT NOT NULL,
  `nf1e` FLOAT NULL,
  `nf2e` FLOAT NULL,
  `frequencia` FLOAT NULL,
  `situacao` VARCHAR(45) NULL,
  PRIMARY KEY (`aluno_id`, `disciplina_id`, `ano`),
  INDEX `fk_Alunos_has_disciplinas_disciplinas1_idx` (`disciplina_id` ASC) VISIBLE,
  INDEX `fk_Alunos_has_disciplinas_Alunos_idx` (`aluno_id` ASC) VISIBLE,
  CONSTRAINT `fk_Alunos_has_disciplinas_Alunos`
    FOREIGN KEY (`aluno_id`)
    REFERENCES `gtdaod`.`aluno` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Alunos_has_disciplinas_disciplinas1`
    FOREIGN KEY (`disciplina_id`)
    REFERENCES `gtdaod`.`disciplina` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
