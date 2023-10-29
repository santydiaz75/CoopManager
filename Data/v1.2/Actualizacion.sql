delimiter $$

CREATE TABLE `conceptos` (
  `Empresa` int(11) NOT NULL DEFAULT '1',
  `Ejercicio` int(11) NOT NULL DEFAULT '2010',
  `Concepto` int(11) NOT NULL,
  `ConceptoDesc` varchar(30) DEFAULT NULL,
  `CuentaContable` varchar(10) DEFAULT NULL,
  `SID` varchar(38) DEFAULT NULL,
  `LMD` datetime DEFAULT NULL,
  `Version` int(11) DEFAULT NULL,
  PRIMARY KEY (`Concepto`,`Empresa`,`Ejercicio`),
  KEY `FK_EmpresaConcepto` (`Empresa`),
  KEY `FK_EjercicioConcepto` (`Ejercicio`),
  CONSTRAINT `FK_EjercicioConcepto` FOREIGN KEY (`Ejercicio`) REFERENCES `ejercicios` (`Ejercicio`),
  CONSTRAINT `FK_EmpresaConcepto` FOREIGN KEY (`Empresa`) REFERENCES `empresas` (`IdEmpresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `conceptospago` (
  `Empresa` int(11) NOT NULL DEFAULT '1',
  `Ejercicio` int(11) NOT NULL DEFAULT '2010',
  `ConceptoPago` int(11) NOT NULL,
  `ConceptoPagoDesc` varchar(30) DEFAULT NULL,
  `CuentaContable` varchar(10) DEFAULT NULL,
  `SID` varchar(38) DEFAULT NULL,
  `LMD` datetime DEFAULT NULL,
  `Version` int(11) DEFAULT NULL,
  PRIMARY KEY (`ConceptoPago`,`Empresa`,`Ejercicio`),
  KEY `FK_EmpresaConceptoPago` (`Empresa`),
  KEY `FK_EjercicioConceptoPago` (`Ejercicio`),
  CONSTRAINT `FK_EjercicioConceptoPago` FOREIGN KEY (`Ejercicio`) REFERENCES `ejercicios` (`Ejercicio`),
  CONSTRAINT `FK_EmpresaConceptoPago` FOREIGN KEY (`Empresa`) REFERENCES `empresas` (`IdEmpresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

ALTER TABLE `coopmanagerdb`.`facturaspago` ADD COLUMN `IdConcepto` INT(11) NULL  AFTER `Unidades` 

, DROP PRIMARY KEY 

, ADD PRIMARY KEY (`Empresa`, `Ejercicio`, `IdFactura`, `Linea`) ;

delimiter $$

ALTER TABLE `coopmanagerdb`.`facturaspagolineas` ADD COLUMN `IdConcepto` INT(11) NULL  AFTER `Unidades` 

, DROP PRIMARY KEY 

, ADD PRIMARY KEY (`Empresa`, `Ejercicio`, `IdFactura`, `Linea`) ;

delimiter $$

CREATE TABLE `cosecherosparcelas` (
  `Empresa` int(11) NOT NULL DEFAULT '1',
  `Ejercicio` int(11) NOT NULL DEFAULT '2010',
  `IdCosechero` int(11) NOT NULL,
  `IdParcela` int(11) NOT NULL,
  `Municipio` varchar(100) DEFAULT NULL,
  `Paraje` varchar(100) DEFAULT NULL,
  `Poligono` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `Parcela` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `Recinto` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `Superficie` float DEFAULT NULL,
  `SuperficieCultivada` float DEFAULT NULL,
  `SID` varchar(38) DEFAULT NULL,
  `LMD` datetime DEFAULT NULL,
  `Version` int(11) DEFAULT NULL,
  PRIMARY KEY (`Empresa`,`Ejercicio`,`IdCosechero`,`IdParcela`),
  KEY `FK_Empresa1CosecheroParcela` (`Empresa`),
  KEY `FK_EjercicioCosecheroParcela` (`Ejercicio`),
  CONSTRAINT `FK_EjercicioCosecheroParcela` FOREIGN KEY (`Empresa`) REFERENCES `empresas` (`IdEmpresa`),
  CONSTRAINT `FK_Empresa1CosecheroParcela` FOREIGN KEY (`Ejercicio`) REFERENCES `ejercicios` (`Ejercicio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$



delimiter $$

ALTER TABLE `coopmanagerdb`.`cosecheros` DROP COLUMN `SuperficieCultivada` , DROP COLUMN `Superficie` , DROP COLUMN `Recinto` , DROP COLUMN `Parcela` , DROP COLUMN `Poligono` ;

delimiter $$

Actualizar LiquidacionInsertKilosInutilizados

delimiter $$

Quitar campo DiasLiquidacion de empleadoscontratos

AÒadir Sabado y Precio a  empleadoshorasextras

a—ADIR ConIGIC a Conceptos