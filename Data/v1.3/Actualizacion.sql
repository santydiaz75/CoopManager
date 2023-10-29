ALTER TABLE `coopmanagerdb`.`liquidaciones` ADD COLUMN `ImporteIGIC` FLOAT NOT NULL DEFAULT 0  AFTER `BaseImponible` , ADD COLUMN `ImporteIRPF` FLOAT NOT NULL DEFAULT 0  AFTER `ImporteIGIC` ;

Update `coopmanagerdb`.`liquidaciones` Set ImporteIGIC = round(BaseImponible * coalesce(TipoIGIC, 0) / 100, 2) where Empresa = 1

Update `coopmanagerdb`.`liquidaciones` Set ImporteIRPF = round((BaseImponible + ImporteIGIC) * coalesce(TipoIRPF, 0) / 100, 2) where Empresa = 1

USE `coopmanagerdb`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewliquidaciones` AS select `l`.`NumeroFactura` AS `Numerofactura`,`l`.`Empresa` AS `Empresa`,`l`.`Ejercicio` AS `Ejercicio`,`l`.`Mes` AS `Mes`,`l`.`Fecha` AS `Fecha`,coalesce(`c`.`Nombre`,'') AS `Nombre`,coalesce(`c`.`Apellidos`,'') AS `Apellidos`,coalesce(`l`.`TipoIRPF`,0) AS `TipoIRPF`,coalesce(`l`.`TipoIGIC`,0) AS `TipoIGIC`, `l`.`BaseImponible` AS `BaseImponible`, `l`.`ImporteIGIC` AS `ImporteIGIC`, `l`.`ImporteIRPF` AS `ImporteIRPF` from (`liquidaciones` `l` left join `cosecheros` `c` on(((`l`.`IdCosechero` = `c`.`IdCosechero`) and (`l`.`Empresa` = `c`.`Empresa`) and (`l`.`Ejercicio` = `c`.`Ejercicio`))));

USE `coopmanagerdb`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewcontaliquidaciones` AS select `l`.`Empresa` AS `empresa`,`l`.`Ejercicio` AS `ejercicio`,`l`.`Mes` AS `mes`,`c`.`CuentaContable` AS `cuentaContable`,`l`.`Fecha` AS `fecha`,concat('PLATANOS DE ',`m`.`NombreMes`) AS `concepto`,`l`.`BaseImponible` AS `baseImponible`, `l`.`ImporteIGIC` AS `ImporteIGIC`, `l`.`ImporteIRPF` AS `ImporteIRPF`, `l`.`TipoIGIC` AS `tipoIgic`,`l`.`TipoIRPF` AS `tipoIrpf` from ((`liquidaciones` `l` join `cosecheros` `c` on(((`l`.`IdCosechero` = `c`.`IdCosechero`) and (`l`.`Empresa` = `c`.`Empresa`) and (`l`.`Ejercicio` = `c`.`Ejercicio`)))) join `meses` `m` on((`l`.`Mes` = `m`.`Mes`)));

Añadir CodigoAsesoria a Cosecheros

ALTER TABLE `coopmanagerdb`.`empleados` ADD COLUMN `FechaNacimiento` DATETIME NULL DEFAULT NULL  AFTER `Apellidos` ;

ALTER TABLE `coopmanagerdb`.`empleados` ADD COLUMN `FechaAntiguedad` DATETIME NULL  AFTER `Categoria` ;

delimiter $$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewentradasquery` AS select `e`.`IdEntrada` AS `IdEntrada`,`e`.`Empresa` AS `Empresa`,`e`.`Ejercicio` AS `Ejercicio`,`e`.`Semana` AS `Semana`,`e`.`Fecha` AS `Fecha`,`e`.`IdCosechero` AS `IdCosechero`,coalesce(`c`.`Nombre`,'') AS `Nombre`,coalesce(`c`.`Apellidos`,'') AS `Apellidos`,`e`.`NumPinas` AS `NumPinas`,sum(`el`.`NumKilos`) AS `NumKIlos` from ((`entradascabecera` `e` left join `cosecheros` `c` on(((`e`.`IdCosechero` = `c`.`IdCosechero`) and (`e`.`Empresa` = `c`.`Empresa`) and (`e`.`Ejercicio` = `c`.`Ejercicio`)))) left join `entradaslineas` `el` on(((`e`.`IdEntrada` = `el`.`IdEntrada`) and (`e`.`Empresa` = `el`.`Empresa`) and (`e`.`Ejercicio` = `el`.`Ejercicio`)))) group by `e`.`IdEntrada`,`e`.`Empresa`,`e`.`Ejercicio`,`e`.`Semana`,`e`.`Fecha`,`e`.`IdCosechero`,coalesce(`c`.`Nombre`,''),coalesce(`c`.`Apellidos`,''),`e`.`NumPinas` order by `e`.`IdEntrada`$$

delimiter $$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `coopmanagerdb`.`viewentradascategoriasquery` AS select `e`.`Empresa` AS `Empresa`,`e`.`Ejercicio` AS `Ejercicio`,`e`.`Semana` AS `Semana`,`el`.`IdCategoria` AS `IdCategoria`,`c`.`NombreCategoria` AS `NombreCategoria`,sum(`el`.`NumKilos`) AS `NumKIlos` from ((`coopmanagerdb`.`entradascabecera` `e` join `coopmanagerdb`.`entradaslineas` `el` on(((`e`.`IdEntrada` = `el`.`IdEntrada`) and (`e`.`Empresa` = `el`.`Empresa`) and (`e`.`Ejercicio` = `el`.`Ejercicio`)))) join `coopmanagerdb`.`categorias` `c` on(((`el`.`IdCategoria` = `c`.`IdCategoria`) and (`el`.`Empresa` = `c`.`Empresa`) and (`el`.`Ejercicio` = `c`.`Ejercicio`)))) group by `e`.`Empresa`,`e`.`Ejercicio`,`e`.`Semana`,`el`.`IdCategoria`,`c`.`NombreCategoria` order by `el`.`IdCategoria`$$

delimiter $$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `coopmanagerdb`.`viewentradascategoriascosecheroquery` AS select `e`.`Empresa` AS `Empresa`,`e`.`Ejercicio` AS `Ejercicio`, `e`.`IdCosechero` AS `IdCosechero`, `e`.`Semana` AS `Semana`,`el`.`IdCategoria` AS `IdCategoria`,`c`.`NombreCategoria` AS `NombreCategoria`,sum(`el`.`NumKilos`) AS `NumKIlos` from ((`coopmanagerdb`.`entradascabecera` `e` join `coopmanagerdb`.`entradaslineas` `el` on(((`e`.`IdEntrada` = `el`.`IdEntrada`) and (`e`.`Empresa` = `el`.`Empresa`) and (`e`.`Ejercicio` = `el`.`Ejercicio`)))) join `coopmanagerdb`.`categorias` `c` on(((`el`.`IdCategoria` = `c`.`IdCategoria`) and (`el`.`Empresa` = `c`.`Empresa`) and (`el`.`Ejercicio` = `c`.`Ejercicio`)))) group by `e`.`Empresa`,`e`.`Ejercicio`, `e`.`IdCosechero`, `e`.`Semana`,`el`.`IdCategoria`,`c`.`NombreCategoria` order by `el`.`IdCategoria`$$

delimiter $$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewventasquery` AS select `v`.`IdVenta` AS `IdVenta`,`v`.`Empresa` AS `Empresa`,`v`.`Ejercicio` AS `Ejercicio`,`v`.`Semana` AS `Semana`,`v`.`Fecha` AS `Fecha`,`vl`.`IdReceptor` AS `IdReceptor`,`r`.`Nombre` AS `Nombre`,sum(`vl`.`NumCajas`) AS `NumCajas`,sum(`vl`.`NumKilos`) AS `NumKIlos`,sum(`vl`.`Importe`) AS `Importe` from ((`ventascabecera` `v` left join `ventaslineas` `vl` on(((`v`.`IdVenta` = `vl`.`IdVenta`) and (`v`.`Empresa` = `vl`.`Empresa`) and (`v`.`Ejercicio` = `vl`.`Ejercicio`)))) left join `receptores` `r` on(((`vl`.`IdReceptor` = `r`.`IdReceptor`) and (`vl`.`Empresa` = `r`.`Empresa`) and (`vl`.`Ejercicio` = `r`.`Ejercicio`)))) group by `v`.`IdVenta`,`v`.`Empresa`,`v`.`Ejercicio`,`v`.`Semana`,`v`.`Fecha`,`vl`.`IdReceptor`,`r`.`Nombre` order by `v`.`IdVenta`$$

delimiter $$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewventascategoriasquery` AS select `v`.`Empresa` AS `Empresa`,`v`.`Ejercicio` AS `Ejercicio`,`v`.`Semana` AS `Semana`,`vl`.`IdCategoria` AS `IdCategoria`,`c`.`NombreCategoria` AS `NombreCategoria`,sum(`vl`.`NumKilos`) AS `NumKIlos`,sum(`vl`.`NumCajas`) AS `NumCajas`,sum(`vl`.`Importe`) AS `Impore` from ((`ventascabecera` `v` join `ventaslineas` `vl` on(((`v`.`IdVenta` = `vl`.`IdVenta`) and (`v`.`Empresa` = `vl`.`Empresa`) and (`v`.`Ejercicio` = `vl`.`Ejercicio`)))) join `categorias` `c` on(((`vl`.`IdCategoria` = `c`.`IdCategoria`) and (`vl`.`Empresa` = `c`.`Empresa`) and (`vl`.`Ejercicio` = `c`.`Ejercicio`)))) group by `v`.`Empresa`,`v`.`Ejercicio`,`v`.`Semana`,`vl`.`IdCategoria`,`c`.`NombreCategoria` order by `vl`.`IdCategoria`$$

delimiter $$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `viewventascategoriasreceptorquery` AS select `v`.`Empresa` AS `Empresa`,`v`.`Ejercicio` AS `Ejercicio`,`vl`.`IdReceptor` AS `IdReceptor`,`v`.`Semana` AS `Semana`,`vl`.`IdCategoria` AS `IdCategoria`,`c`.`NombreCategoria` AS `NombreCategoria`,sum(`vl`.`NumKilos`) AS `NumKIlos`,sum(`vl`.`NumCajas`) AS `NumCajas`,sum(`vl`.`Importe`) AS `Impore` from ((`ventascabecera` `v` join `ventaslineas` `vl` on(((`v`.`IdVenta` = `vl`.`IdVenta`) and (`v`.`Empresa` = `vl`.`Empresa`) and (`v`.`Ejercicio` = `vl`.`Ejercicio`)))) join `categorias` `c` on(((`vl`.`IdCategoria` = `c`.`IdCategoria`) and (`vl`.`Empresa` = `c`.`Empresa`) and (`vl`.`Ejercicio` = `c`.`Ejercicio`)))) group by `v`.`Empresa`,`v`.`Ejercicio`,`vl`.`IdReceptor`,`v`.`Semana`,`vl`.`IdCategoria`,`c`.`NombreCategoria` order by `vl`.`IdCategoria`$$

delimiter $$

ALTER TABLE `coopmanagerdb`.`empleadosvacaciones` CHANGE COLUMN `Semana` `Linea` INT(11) NOT NULL  
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`Empresa`, `Ejercicio`, `IdEmpleado`, `Linea`) ;$$


