CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionKilosAcumulados`(lempresa INT, lejercicio INT, lhastasemana INT, lgrupo INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

if lcategoria <> 0 then
    Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
    on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
    where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana <= lhastasemana and `coopmanagerdb`.`CategoriaGetGrupo`(el1.empresa, el1.ejercicio, el1.IdCategoria) = lCategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
    RETURN Kilos;
else
    Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
    on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
    where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana <= lhastasemana and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
    RETURN Kilos;
end if;

END