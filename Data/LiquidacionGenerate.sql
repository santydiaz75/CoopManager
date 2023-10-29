CREATE DEFINER=`root`@`localhost` PROCEDURE `LiquidacionGenerate`(lempresa INT, lejercicio INT, lmes INT, lsemanadesde INT, lsemanahasta INT)
BEGIN

DECLARE l_last_row INT DEFAULT 0;
DECLARE lcosechero INT;
DECLARE lsemana INT;
DECLARE CosecheroActual INT DEFAULT 0;
DECLARE SemanaActual INT DEFAULT 0;
DECLARE lcategoria INT ;
DECLARE KilosSemana FLOAT DEFAULT 0;
DECLARE KilosCatsemana FLOAT DEFAULT 0;
DECLARE KilosInutMinimo FLOAT DEFAULT 0;
DECLARE KilosInutCategoria FLOAT DEFAULT 0;
DECLARE KilosInutilizados FLOAT DEFAULT 0;
DECLARE ToInsert BOOLEAN DEFAULT TRUE;
DECLARE lporcentaje FLOAT DEFAULT 0;

DECLARE kinu_cursor CURSOR FOR SELECT IdCosechero, ec1.Semana, IdCategoria FROM `coopmanagerdb`.`entradascabecera` ec1 
        inner join `coopmanagerdb`.`entradaslineas` el1 on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
        inner join `coopmanagerdb`.`calendario` c on ec1.semana = c.semana and ec1.Empresa = c.Empresa and ec1.Ejercicio = c.Ejercicio
        where  ec1.Semana >= lsemanadesde and ec1.Semana <= lsemanahasta and ec1.ejercicio = lejercicio and ec1.empresa = lempresa and c.PorcKilosInutilizados != 0
        group by IdCosechero, ec1.Semana, IdCategoria order by IdCosechero, ec1.Semana Asc, IdCategoria Desc; 

DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_last_row=1;



DELETE from `coopmanagerdb`.`kilosinutilizados` WHERE Empresa = lempresa AND Ejercicio = lejercicio and Semana >= lsemanadesde and Semana <= lsemanahasta;

 -- abrimos el cursor para empezar a recorrerlo
OPEN kinu_cursor;


kinu_cursor_loop: LOOP
   
            -- cada registro se le otorga a la variable
    FETCH kinu_cursor INTO lcosechero, lsemana, lcategoria;
       
        IF (l_last_row=1) THEN
            LEAVE kinu_cursor_loop;
        END IF;
       
        IF ((lcosechero != CosecheroActual) || (lsemana != SemanaActual)) THEN
            SET CosecheroActual = lcosechero;
            SET SemanaActual = lsemana;
            SET KilosInutilizados = 0;
            SET ToInsert = TRUE;
            
                 -- Total kilos de la semana del cosechero 
            Select coalesce(Sum(NumKilos),0) into KilosSemana from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
            on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
            where  ec1.IdCosechero = lcosechero and ec1.Semana = lsemana and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
            
            Select PorcKilosInutilizados Into lporcentaje From `coopmanagerdb`.`calendario` c where  c.Semana = lsemana and c.ejercicio = lejercicio and c.empresa = lempresa;
            
            SET KilosInutMinimo = KilosSemana * lporcentaje / 100;
        END IF;
        
        Select coalesce(Sum(NumKilos),0) into KilosCatsemana from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
        on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
        where  ec1.IdCosechero = lcosechero and ec1.Semana = lsemana and el1.IdCategoria = lcategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
        
        
        IF (KilosInutMinimo > (KilosInutilizados + KilosCatsemana)) THEN
            SET KilosInutCategoria = KilosCatsemana;
        ELSE
            SET KilosInutCategoria = KilosInutMinimo - KilosInutilizados;
        END IF;
        
        SET KilosInutilizados = KilosInutilizados + KilosInutCategoria;
     
        IF (ToInsert = TRUE) THEN
            INSERT INTO `coopmanagerdb`.`kilosinutilizados` (Empresa, Ejercicio, IdCosechero, Semana, IdCategoria, NumKilos) 
            VALUES (lEmpresa, lejercicio, lcosechero, lsemana, lcategoria, KilosInutCategoria);
        END IF;
        
        IF (KilosInutilizados > KilosInutMinimo) THEN
            SET ToInsert = FALSE;
        END IF;
        

        
END LOOP kinu_cursor_loop;

-- cerramos el cursor y retornamos el dato que nos interesa en este caso la variable 
CLOSE kinu_cursor;

INSERT INTO `coopmanagerdb`.`liquidacioneslineas` 
SELECT l.empresa, l.ejercicio, l.NumeroFactura, el.IdCategoria,
`coopmanagerdb`.LiquidacionNumPinas(lempresa,lejercicio,lmes,lsemanadesde, l.IdCosechero) AS PinasSemana1,
`coopmanagerdb`.LiquidacionNumPinas(lempresa,lejercicio,lmes,lsemanadesde + 1, l.IdCosechero) AS PinasSemana2,
`coopmanagerdb`.LiquidacionNumPinas(lempresa,lejercicio,lmes,lsemanadesde + 2, l.IdCosechero) AS PinasSemana3,
`coopmanagerdb`.LiquidacionNumPinas(lempresa,lejercicio,lmes,lsemanadesde + 3, l.IdCosechero) AS PinasSemana4,
(case when lsemanahasta < lsemanadesde + 4 then null else `coopmanagerdb`.LiquidacionNumPinas(lempresa,lejercicio,lmes,lsemanadesde + 4, l.IdCosechero) end) AS PinasSemana5,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde, `coopmanagerdb`.`CategoriaGetGrupo`(lempresa,lejercicio,el.IdCategoria)) as PrecioSemana1,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 1, `coopmanagerdb`.`CategoriaGetGrupo`(lempresa,lejercicio,el.IdCategoria)) as PrecioSemana2,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 2, `coopmanagerdb`.`CategoriaGetGrupo`(lempresa,lejercicio,el.IdCategoria)) as PrecioSemana3,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 3, `coopmanagerdb`.`CategoriaGetGrupo`(lempresa,lejercicio,el.IdCategoria)) as PrecioSemana4,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 4, `coopmanagerdb`.`CategoriaGetGrupo`(lempresa,lejercicio,el.IdCategoria)) as PrecioSemana5,
(`coopmanagerdb`.LiquidacionKilosSemana(lempresa,lejercicio,lmes,lsemanadesde , l.IdCosechero, el.IdCategoria) - `coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde, l.IdCosechero, el.IdCategoria)) as KilosSemana1,
(`coopmanagerdb`.LiquidacionKilosSemana(lempresa,lejercicio,lmes,lsemanadesde + 1, l.IdCosechero, el.IdCategoria) - `coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 1, l.IdCosechero, el.IdCategoria)) as KilosSemana2,
(`coopmanagerdb`.LiquidacionKilosSemana(lempresa,lejercicio,lmes,lsemanadesde + 2, l.IdCosechero, el.IdCategoria) - `coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 2, l.IdCosechero, el.IdCategoria)) as KilosSemana3,
(`coopmanagerdb`.LiquidacionKilosSemana(lempresa,lejercicio,lmes,lsemanadesde + 3, l.IdCosechero, el.IdCategoria) - `coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 3, l.IdCosechero, el.IdCategoria)) as KilosSemana4,
(case when lsemanahasta < lsemanadesde + 4 then null else (`coopmanagerdb`.LiquidacionKilosSemana(lempresa,lejercicio,lmes,lsemanadesde + 4, l.IdCosechero, el.IdCategoria) - `coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 4, l.IdCosechero, el.IdCategoria)) end) as KilosSemana5,
`coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde , l.IdCosechero, el.IdCategoria) as KilosInutSemana1,
`coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 1, l.IdCosechero, el.IdCategoria) as KilosInutSemana2,
`coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 2, l.IdCosechero, el.IdCategoria) as KilosInutSemana3,
`coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 3, l.IdCosechero, el.IdCategoria) as KilosInutSemana4,
(case when lsemanahasta < lsemanadesde + 4 then null else `coopmanagerdb`.LiquidacionKilosInutilizados(lempresa,lejercicio,lsemanadesde + 4, l.IdCosechero, el.IdCategoria) end) as KilosInutSemana5, null, null, null
FROM `coopmanagerdb`.`liquidaciones` l
left outer join `coopmanagerdb`.`entradascabecera` ec on `coopmanagerdb`.CosecheroGetGrupo(lempresa,lejercicio, ec.IdCosechero) = l.IdCosechero and
ec.semana >= lsemanadesde and ec.semana <= lsemanahasta and ec.ejercicio = lejercicio  and ec.empresa = lempresa
inner join `coopmanagerdb`.`entradaslineas` el on ec.IdEntrada = el.IdEntrada and el.ejercicio = lejercicio and el.empresa = lempresa
inner join `coopmanagerdb`.`meses` m on l.mes = m.mes
inner join (Select * From `coopmanagerdb`.`cosecheros` where idgrupo = IdCosechero) co on l.IdCosechero = co.IdCosechero and co.ejercicio = lejercicio and co.empresa = lempresa
inner join `coopmanagerdb`.`categorias` ca on el.IdCategoria = ca.IdCategoria and ca.ejercicio = lejercicio and ca.empresa = lempresa
where l.mes = lmes and l.ejercicio = lejercicio and l.empresa = lempresa
group by l.empresa, l.ejercicio, l.mes, l.NumeroFactura, l.fecha, l.IdCosechero, co.Nombre, co.Apellidos, co.Nif,
co.Direccion, co.CodigoPostal, co.Poblacion, co.TipoIgic, co.TipoIrpf, el.IdCategoria, ca.NombreCategoria;
 
END