CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionGetBaseImponible`(lempresa INT, lejercicio INT, lmes INT, lsemanadesde INT, lsemanahasta INT, lgrupo INT) RETURNS float
BEGIN

DECLARE l_last_row INT DEFAULT 0;
DECLARE lsemana INT DEFAULT 0;
DECLARE lcategoria INT DEFAULT 0;
DECLARE KilosCatsemana float DEFAULT 0;
DECLARE KilosCatInutsemana float DEFAULT 0;
DECLARE PrecioCatSemana float DEFAULT 0;
DECLARE ImporteBonificacion float DEFAULT 0;
DECLARE Importe float DEFAULT 0;



DECLARE categorias_cursor CURSOR FOR SELECT Semana, IdCategoria,  coalesce(Sum(NumKilos),0), coalesce(`coopmanagerdb`.LiquidacionKilosInutilizados(lempresa, lejercicio, Semana, lgrupo, IdCategoria),0) FROM `coopmanagerdb`.`entradascabecera` ec1 
inner join `coopmanagerdb`.`entradaslineas` el1 on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana >= lsemanadesde and ec1.Semana <= lsemanahasta and ec1.ejercicio = lejercicio and ec1.empresa = lempresa
group by Semana, IdCategoria ASC; 

DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_last_row=1;
 
 -- abrimos el cursor para empezar a recorrerlo
OPEN categorias_cursor;

categoria_loop: LOOP
    -- cada registro se le otorga a la variable 'v_ape_pat'
    FETCH categorias_cursor INTO lsemana, lcategoria, KilosCatsemana, KilosCatInutsemana;
       
        IF (l_last_row=1) THEN
            LEAVE categoria_loop;
        END IF;
 
        -- lógica propia de este*/ /*!50003 procedure
        Select  coalesce(`coopmanagerdb`.`LiquidacionPrecioSemana`(lempresa, lejercicio, lsemana, `coopmanagerdb`.`CategoriaGetGrupo`(lempresa,lejercicio,lcategoria)),0) INTO PrecioCatSemana;
        
        SET Importe = Importe + (PrecioCatSemana * (KilosCatsemana - KilosCatInutsemana));

END LOOP categoria_loop;
 
-- cerramos el cursor y retornamos el dato que nos interesa en este caso la variable 
CLOSE categorias_cursor;
Select  coalesce(`coopmanagerdb`.`LiquidacionImporteBonificacion`(lempresa, lejercicio, lmes, lsemanadesde, lsemanahasta, lgrupo),0) INTO ImporteBonificacion;
        
-- descontamos el importe de bonificación

SET Importe = importe + ImporteBonificacion;
RETURN Importe;
 
END