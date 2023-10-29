-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `CosecheroGetGrupo`(lempresa INT, lejercicio INT, lcosechero INT) RETURNS int(11)
BEGIN

DECLARE lgrupo INT;

Select IdGrupo into lgrupo from `coopmanagerdb`.`cosecheros` co 
where co.ejercicio = lejercicio and co.empresa = lempresa and co.IdCosechero = lcosechero;
RETURN lgrupo;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `CosecheroGetNif`(lempresa INT, lejercicio INT, lcosechero INT) RETURNS varchar(12) CHARSET latin1
BEGIN

DECLARE lnif varchar(12);

Select Nif into lnif from `coopmanagerdb`.`cosecheros` co 
where co.ejercicio = lejercicio and co.empresa = lempresa and co.IdCosechero = lcosechero;
RETURN lnif;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `CosecheroGetNombreByNif`(lempresa INT, lejercicio INT, lnif varchar(12)) RETURNS varchar(100) CHARSET latin1
BEGIN

DECLARE lnombre varchar(100);

Select concat(concat(co.Apellidos, ', '), co.Nombre) as NombreApellidos into lnombre from `coopmanagerdb`.`cosecheros` co 
where co.ejercicio = lejercicio and co.empresa = lempresa and co.nif = lnif LIMIT 1;
RETURN lnombre;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasGetFecha`(lempresa INT, lejercicio INT, lsemana INT) RETURNS date
BEGIN

DECLARE lfecha date;

Select Max(Fecha) into lfecha from `coopmanagerdb`.`entradascabecera` ec 
where ec.Semana = lsemana and ec.ejercicio = lejercicio and ec.empresa = lempresa;
RETURN lfecha;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosInutilizadosNifCosechero`(lempresa INT, lejercicio INT, lnif varchar(12), lsemanadesde INT, lsemanahasta INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`KilosInutilizados` k
where `coopmanagerdb`.CosecheroGetNif(lempresa, lejercicio, k.IdCosechero) = lnif and k.Semana >= lsemanadesde and k.Semana <= lsemanahasta and k.ejercicio = lejercicio and k.empresa = lempresa;

RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosInutilizadosSemana`(lempresa INT, lejercicio INT, lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`KilosInutilizados` k
where  k.Semana = lsemana and k.IdCategoria = lCategoria and k.ejercicio = lejercicio and k.empresa = lempresa;

RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosMesZona`(lempresa INT, lejercicio INT, lmes INT, lzona INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec inner join `coopmanagerdb`.`entradaslineas` el
on ec.Empresa = el.Empresa and ec.Ejercicio = el.Ejercicio and ec.IdEntrada = el.IdEntrada
where  ec.ejercicio = lejercicio and ec.empresa = lempresa and ec.IdZona = lzona
and month(ec.Fecha) = lmes;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosNifCosechero`(lempresa INT, lejercicio INT, lnif varchar(12), lsemanadesde INT, lsemanahasta INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where ec1.Semana >= lsemanadesde and ec1.Semana <= lsemanahasta and `coopmanagerdb`.CosecheroGetNif(lempresa, lejercicio, ec1.IdCosechero) = lnif and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosSemana`(lempresa INT, lejercicio INT, lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where ec1.Semana = lsemana and el1.IdCategoria = lCategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasNumPinasMesZona`(lempresa INT, lejercicio INT, lmes INT, lzona INT) RETURNS float
BEGIN

DECLARE Pinas float;

Select coalesce(Sum(NumPinas),0) into Pinas from `coopmanagerdb`.`entradascabecera` ec 
where  ec.ejercicio = lejercicio and ec.empresa = lempresa and ec.IdZona = lzona
and month(ec.Fecha) = lmes;
RETURN Pinas;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

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
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde, el.IdCategoria) as PrecioSemana1,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 1, el.IdCategoria) as PrecioSemana2,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 2, el.IdCategoria) as PrecioSemana3,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 3, el.IdCategoria) as PrecioSemana4,
`coopmanagerdb`.LiquidacionPrecioSemana(lempresa,lejercicio,lsemanadesde + 4, el.IdCategoria) as PrecioSemana5,
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
left outer join `coopmanagerdb`.`entradaslineas` el on ec.IdEntrada = el.IdEntrada and el.ejercicio = lejercicio and el.empresa = lempresa
left outer join `coopmanagerdb`.`meses` m on l.mes = m.mes
left outer join (Select * From `coopmanagerdb`.`cosecheros` where idgrupo = IdCosechero) co on l.IdCosechero = co.IdCosechero and co.ejercicio = lejercicio and co.empresa = lempresa
left outer join `coopmanagerdb`.`categorias` ca on el.IdCategoria = ca.IdCategoria and ca.ejercicio = lejercicio and ca.empresa = lempresa
where l.mes = lmes and l.ejercicio = lejercicio and l.empresa = lempresa
group by l.empresa, l.ejercicio, l.mes, l.NumeroFactura, l.fecha, l.IdCosechero, co.Nombre, co.Apellidos, co.Nif,
co.Direccion, co.CodigoPostal, co.Poblacion, co.TipoIgic, co.TipoIrpf, el.IdCategoria, ca.NombreCategoria;
 
END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionGetBaseImponible`(lempresa INT, lejercicio INT, lmes INT, lsemanadesde INT, lsemanahasta INT, lgrupo INT) RETURNS float
BEGIN

DECLARE l_last_row INT DEFAULT 0;
DECLARE lsemana INT DEFAULT 0;
DECLARE lcategoria INT DEFAULT 0;
DECLARE KilosCatsemana float DEFAULT 0;
DECLARE KilosCatInutsemana float DEFAULT 0;
DECLARE PrecioCatSemana float DEFAULT 0;
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
 
        -- lógica propia de este procedure
        Select  coalesce(`coopmanagerdb`.`LiquidacionPrecioSemana`(lempresa, lejercicio, lsemana, lcategoria),0) INTO PrecioCatSemana;
        
        SET Importe = Importe + (PrecioCatSemana * (KilosCatsemana - KilosCatInutsemana));

END LOOP categoria_loop;
 
-- cerramos el cursor y retornamos el dato que nos interesa en este caso la variable 
CLOSE categorias_cursor;
RETURN Importe;
 
END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `LiquidacionInsertKilosInutilizados`(lempresa INT, lejercicio INT, lsemanadesde INT, lsemanahasta INT)
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

 
END


-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionKilosInutilizados`(lempresa INT, lejercicio INT, lsemana INT, lgrupo INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`KilosInutilizados` k
where `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, k.IdCosechero) = lgrupo and k.Semana = lsemana and k.IdCategoria = lCategoria and k.ejercicio = lejercicio and k.empresa = lempresa;

RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionKilosSemana`(lempresa INT, lejercicio INT, lmes INT, lsemana INT, lgrupo INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where  `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana = lsemana and el1.IdCategoria = lCategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionNumPinas`(lempresa INT, lejercicio INT, lmes INT, lsemana INT, lgrupo INT) RETURNS float
BEGIN

DECLARE NumPinas FLOAT;

Select coalesce(Sum(ec1.NumPinas),0) INTO NumPinas from `coopmanagerdb`.`entradascabecera` ec1
where `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and ec1.Semana = lsemana 
and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;

RETURN NumPinas;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `LiquidacionPrecioSemana`(lempresa INT, lejercicio INT, lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Precio float;

Select coalesce(p.Precio, 0) into Precio From `coopmanagerdb`.`precios` p where p.IdCategoria = lcategoria and p.Semana = lsemana and p.ejercicio = lejercicio and p.empresa = lempresa;
RETURN Precio;

END


-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `VentasKilosSemana`(lempresa INT, lejercicio INT, lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`ventascabecera` vc1 inner join `coopmanagerdb`.`ventaslineas` vl1
on vc1.Empresa = vl1.Empresa and vc1.Ejercicio = vl1.Ejercicio and vc1.IdVenta = vl1.IdVenta
where vc1.Semana = lsemana and vl1.IdCategoria = lCategoria and vc1.ejercicio = lejercicio and vc1.empresa = lempresa;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradaGetNumKilos`(lempresa INT, lejercicio INT, lentrada INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradaslineas` el
where el.ejercicio = lejercicio and el.empresa = lempresa and el.IdEntrada = lentrada;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosNifCosecheroCategoria`(lempresa INT, lejercicio INT, lnif varchar(12), lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where ec1.Semana = lsemana and `coopmanagerdb`.CosecheroGetNif(lempresa, lejercicio, ec1.IdCosechero) = lnif and el1.IdCategoria = lcategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END


-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `EntradasKilosGrupoCosecheroCategoria`(lempresa INT, lejercicio INT, lgrupo INT, lsemana INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec1 inner join `coopmanagerdb`.`entradaslineas` el1
on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where ec1.Semana = lsemana and `coopmanagerdb`.CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) = lgrupo and el1.IdCategoria = lcategoria and ec1.ejercicio = lejercicio and ec1.empresa = lempresa;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `PreLiquidacionGetBaseImponible`(lempresa INT, lejercicio INT, lsemanadesde INT, lsemanahasta INT, lcosechero INT) RETURNS float
BEGIN

DECLARE l_last_row INT DEFAULT 0;
DECLARE lsemana INT DEFAULT 0;
DECLARE lcategoria INT DEFAULT 0;
DECLARE KilosCatsemana float DEFAULT 0;
DECLARE KilosCatInutsemana float DEFAULT 0;
DECLARE PrecioCatSemana float DEFAULT 0;
DECLARE Importe float DEFAULT 0;



DECLARE categorias_cursor CURSOR FOR SELECT Semana, IdCategoria,  coalesce(Sum(NumKilos),0), coalesce(`coopmanagerdb`.PreLiquidacionKilosInutilizados(lempresa, lejercicio, Semana, lcosechero, IdCategoria),0) FROM `coopmanagerdb`.`entradascabecera` ec1 
inner join `coopmanagerdb`.`entradaslineas` el1 on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
where  ec1.IdCosechero = lcosechero and ec1.Semana >= lsemanadesde and ec1.Semana <= lsemanahasta and ec1.ejercicio = lejercicio and ec1.empresa = lempresa
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
 
        -- lógica propia de este procedure
        Select  coalesce(`coopmanagerdb`.`LiquidacionPrecioSemana`(lempresa, lejercicio, lsemana, lcategoria),0) INTO PrecioCatSemana;
        
        SET Importe = Importe + (PrecioCatSemana * (KilosCatsemana - KilosCatInutsemana));

END LOOP categoria_loop;
 
-- cerramos el cursor y retornamos el dato que nos interesa en este caso la variable 
CLOSE categorias_cursor;
RETURN Importe;
 
END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `PreLiquidacionGetNumKilos`(lempresa INT, lejercicio INT, lsemanadesde INT, lsemanahasta INT, lcosechero INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec inner join `coopmanagerdb`.`entradaslineas` el
on ec.ejercicio = el.ejercicio and ec.empresa = el.empresa and ec.IdEntrada = el.IdEntrada
where el.ejercicio = lejercicio and el.empresa = lempresa and ec.Semana >= lsemanadesde and ec.Semana <= lsemanahasta and ec.IdCosechero = lcosechero;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `PreLiquidacionGetNumPinas`(lempresa INT, lejercicio INT, lsemanadesde INT, lsemanahasta INT, lcosechero INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumPinas),0) into Kilos from `coopmanagerdb`.`entradascabecera` ec 
where ec.ejercicio = lejercicio and ec.empresa = lempresa and ec.Semana >= lsemanadesde and ec.Semana <= lsemanahasta and ec.IdCosechero = lcosechero;
RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `PreLiquidacionKilosInutilizados`(lempresa INT, lejercicio INT, lsemana INT, lcosechero INT, lcategoria INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`KilosInutilizados` k
where k.IdCosechero = lcosechero and k.Semana = lsemana and k.IdCategoria = lcategoria and k.ejercicio = lejercicio and k.empresa = lempresa;

RETURN Kilos;

END

-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` FUNCTION `PreLiquidacionKilosInutilizadosCosechero`(lempresa INT, lejercicio INT, lsemanadesde INT, lsemanahasta INT, lcosechero INT) RETURNS float
BEGIN

DECLARE Kilos float;

Select coalesce(Sum(NumKilos),0) into Kilos from `coopmanagerdb`.`KilosInutilizados` k
where k.IdCosechero = lcosechero and k.Semana >= lsemanadesde and k.Semana <= lsemanahasta and k.ejercicio = lejercicio and k.empresa = lempresa;

RETURN Kilos;

END

