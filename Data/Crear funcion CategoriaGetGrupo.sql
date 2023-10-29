CREATE DEFINER=`coopuser`@`%` FUNCTION `CategoriaGetGrupo`(lempresa INT, lejercicio INT, lcategoria INT) RETURNS int(11)
BEGIN
DECLARE lgrupo INTEGER;

Select IDGrupo into lgrupo from `coopmanagerdb`.`categorias` ca 
where ca.ejercicio = lejercicio and ca.empresa = lempresa and ca.IdCategoria = lcategoria;
RETURN lgrupo;

END