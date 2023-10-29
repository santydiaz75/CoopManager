
-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionImporteTotalAcumulado`(lempresa INT, lejercicio INT, lmes INT, lgrupo INT) RETURNS float
BEGIN

DECLARE Importe float;
DECLARE ImporteTotal float;


    select  coalesce(Sum(ImporteBonificacion),0) into Importe  
    from `coopmanagerdb`.`Liquidaciones` l 
    where l.ejercicio = lejercicio and l.empresa = lempresa and l.mes <= lmes and l.IdCosechero = lgrupo;
    
    set ImporteTotal = Importe;
    
    select  Sum((coalesce(ll.KilosSemana1, 0) * coalesce(ll.PrecioSemana1, 0)) + 
        (coalesce(ll.KilosSemana2, 0) * coalesce(ll.PrecioSemana2, 0)) + 
        (coalesce(ll.KilosSemana3, 0) * coalesce(ll.PrecioSemana3, 0)) + 
        (coalesce(ll.KilosSemana4, 0) * coalesce(ll.PrecioSemana4, 0)) + 
        (coalesce(ll.KilosSemana5, 0) * coalesce(ll.PrecioSemana5, 0))) into Importe
    from `coopmanagerdb`.`LiquidacionesLineas` ll
    where ll.ejercicio = lejercicio and ll.empresa = lempresa
    and ll.NumeroFactura in (select  NumeroFactura  
    from `coopmanagerdb`.`Liquidaciones` l 
    where l.ejercicio = lejercicio and l.empresa = lempresa and l.mes <= lmes and l.IdCosechero = lgrupo);
    
    set ImporteTotal = ImporteTotal + Importe;
    
    RETURN ImporteTotal;


END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionPinasAcumuladas`(lempresa INT, lejercicio INT, lhastasemana INT, lgrupo INT) RETURNS float
BEGIN

DECLARE Pinas float;


    Select coalesce(Sum(NumPinas),0) into Pinas from `coopmanagerdb`.`entradascabecera` ec1 
    where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana <= lhastasemana and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
    RETURN Pinas;


END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionKilosAcumulados`(lempresa INT, lejercicio INT, lhastasemana INT, lgrupo INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

if lcategoria <> 0 then
    Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
    on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
    where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana <= lhastasemana and el1.IdCategoria = lCategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
    RETURN Kilos;
else
    Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
    on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
    where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana <= lhastasemana and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
    RETURN Kilos;
end if;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionKilosSemanaTotal`(lempresa INT, lejercicio INT, lmes INT, lsemana INT, lgrupo INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana = lsemana and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END