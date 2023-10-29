CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosGrupoCosecheroCategoria`(lempresa INT, lejercicio INT, lgrupo INT, lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where ec1.Semana = lsemana and `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and  `coopmanagerdb`.`CategoriaGetGrupo`(lempresa, lejercicio, el1.IdCategoria) = lcategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END