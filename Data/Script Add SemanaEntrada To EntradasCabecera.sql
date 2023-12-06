ALTER TABLE `coopmanagerdb`.`entradascabecera` 
ADD COLUMN `SemanaEntrada` INT(11) NULL DEFAULT NULL AFTER `NumPinasBonificacion`;

Update EntradasCabecera set SemanaEntrada = Semana