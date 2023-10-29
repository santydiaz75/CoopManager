CREATE DEFINER=`coopuser`@`localhost` PROCEDURE `LiquidacionRetornoGenerate`(lempresa INT, lejercicio INT, lejercicioContable INT, lfecha DATE, lNumeroBonificacion INT, lImporteKilo FLOAT, lPorcOtrosKilos FLOAT, ltitulo varchar(50))
BEGIN

DECLARE l_last_row INT DEFAULT 0;
DECLARE lcosechero INT;
DECLARE lcategoria INT;
DECLARE CosecheroActual INT DEFAULT 0;
DECLARE lKilosCategoriaTotal FLOAT DEFAULT 0;
DECLARE lKilosInutilizados FLOAT DEFAULT 0;
DECLARE lPinasCategoriaTotal FLOAT DEFAULT 0;
DECLARE lKilosTotal FLOAT DEFAULT 0;
DECLARE lKilosTotalABonificar FLOAT DEFAULT 0;
DECLARE lOtrosKilosCosechero FLOAT DEFAULT 0;
DECLARE lBaseImponible DECIMAL(10,2) DEFAULT 0;
DECLARE lImporteIGIC DECIMAL(10,2) DEFAULT 0;
DECLARE lImporteIRPF DECIMAL(10,2) DEFAULT 0;
DECLARE lTipoIGIC FLOAT DEFAULT 0;
DECLARE lTipoIRPF FLOAT DEFAULT 0;
DECLARE lNumeroFactura FLOAT DEFAULT 0;
DECLARE lMes INT DEFAULT 0;
DECLARE ToInsert BOOLEAN DEFAULT TRUE;


DECLARE kitotal_cursor CURSOR FOR SELECT CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero) as IdCosechero, el1.IdCategoria, sum(NumPinas) as TotalPinas, sum(el1.NumKilos) as TotalKilos FROM `coopmanagerdb`.`entradascabecera` ec1 
        inner join `coopmanagerdb`.`entradaslineas` el1 on ec1.Empresa = el1.Empresa and ec1.Ejercicio = el1.Ejercicio and ec1.IdEntrada = el1.IdEntrada
        inner join `coopmanagerdb`.`categorias` ca on el1.IdCategoria = ca.IdCategoria and ca.Ejercicio = lejercicio and ca.empresa = lempresa
        inner join `coopmanagerdb`.`cosecheros` co on ec1.IdCosechero = co.IdCosechero and co.Ejercicio = lejercicio and co.empresa = lempresa
        where co.Retorno <> 0 and ca.Retorno <> 0 and ec1.Ejercicio = lejercicio and ec1.empresa = lempresa
        group by CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero), el1.IdCategoria order by CosecheroGetGrupo(lempresa, lejercicio, ec1.IdCosechero), el1.IdCategoria; 

DECLARE CONTINUE HANDLER FOR NOT FOUND SET l_last_row=1;


DELETE FROM liquidacionesretorno WHERE  Empresa = lempresa and Ejercicio = lejercicioContable And NumeroBonificacion = lNumeroBonificacion;
DELETE FROM Liquidaciones WHERE  Empresa = lempresa and Ejercicio = lejercicioContable AND Mes = 12 + lNumeroBonificacion;

OPEN kitotal_cursor;


kitotal_cursor_loop: LOOP
		 
		FETCH kitotal_cursor INTO lcosechero, lcategoria, lPinasCategoriaTotal, lKilosCategoriaTotal;
              
        IF (l_last_row=1) THEN
            LEAVE kitotal_cursor_loop;
        END IF;
       
        IF (lcosechero != CosecheroActual) THEN
            
            select max(COALESCE(NumeroFactura,0)) + 1 into lNumeroFactura from Liquidaciones where Empresa = lempresa and Ejercicio = lejercicioContable;
		
			IF lNumeroFactura IS NULL THEN
				SET lNumeroFactura = 1;
            END IF;
            
            IF (lPorcOtrosKilos = 0) THEN
				INSERT INTO `coopmanagerdb`.`liquidaciones` (Empresa, Ejercicio, NumeroFactura, Fecha, IdCosechero, Mes, TipoIGIC, TipoIRPF, BaseImponible, ImporteIGIC, ImporteIRPF, ImporteBonificacion) 
				VALUES (lempresa, lejercicioContable, lNumeroFactura, lfecha, lcosechero, 12 + lNumeroBonificacion, lTipoIGIC, lTipoIRPF,
				0,0,0,0);
			else
				INSERT INTO `coopmanagerdb`.`liquidaciones` (Empresa, Ejercicio, NumeroFactura, Fecha, IdCosechero, Mes, TipoIGIC, TipoIRPF, BaseImponible, ImporteIGIC, ImporteIRPF, ImporteBonificacion) 
				VALUES (lempresa, lejercicioContable, lNumeroFactura, lfecha, lcosechero, 12 + lNumeroBonificacion, lTipoIGIC, lTipoIRPF,
				0,0,0,0);
                
                INSERT INTO `coopmanagerdb`.`liquidacionesretorno` (Empresa, Ejercicio, NumeroFactura, IdCategoria, PinasTotal, PrecioEscandallo, KilosTotal, NumeroBonificacion, Titulo) 
				VALUES (lEmpresa, lejercicioContable, lNumeroFactura, 0, 0, 0, 0, lNumeroBonificacion, ltitulo);
            END IF;
            
            set lKilosTotal = 0;			
        END IF;
        
        IF (lPorcOtrosKilos = 0) THEN
			select LiquidacionKilosInutilizadosTotal(lempresa, lejercicio, lcosechero, lcategoria) into  lKilosInutilizados;
        
			INSERT INTO `coopmanagerdb`.`liquidacionesretorno` (Empresa, Ejercicio, NumeroFactura, IdCategoria, PinasTotal, PrecioEscandallo, KilosTotal, NumeroBonificacion, Titulo) 
				VALUES (lEmpresa, lejercicioContable, lNumeroFactura, lcategoria, lPinasCategoriaTotal, lImporteKilo, lKilosCategoriaTotal-lKilosInutilizados, lNumeroBonificacion, ltitulo);
          
		
			SET lKilosTotal = (lKilosTotal + lKilosCategoriaTotal - lKilosInutilizados);
		
        
			select TipoIGIC into lTipoIGIC from cosecheros where Retorno <> 0 and IdCosechero = lcosechero and Empresa = lempresa and Ejercicio = lejercicio;
		
			select TipoIRPF into lTipoIRPF from cosecheros where IdCosechero = lcosechero and Empresa = lempresa and Ejercicio = lejercicio;
		
			SET lBaseImponible = round(lImporteKilo * lKilosTotal, 2);
        
			SET lImporteIGIC = round(lBaseImponible * lTipoIGIC / 100, 2);
        
			SET lImporteIRPF = round((lBaseImponible + lImporteIGIC) * lTipoIRPF / 100, 2);
            
			UPDATE `coopmanagerdb`.`liquidaciones` set BaseImponible = lBaseImponible, ImporteIGIC = lImporteIGIC, ImporteIRPF = lImporteIRPF WHERE
			Empresa = lempresa AND Ejercicio = lejercicioContable and  NumeroFactura = lNumeroFactura;
            
        ELSE
			SET lKilosTotal = (lKilosTotal + lKilosCategoriaTotal);
            
            select TipoIGIC into lTipoIGIC from cosecheros where Retorno <> 0 and IdCosechero = lcosechero and Empresa = lempresa and Ejercicio = lejercicio;
		
			select TipoIRPF into lTipoIRPF from cosecheros where IdCosechero = lcosechero and Empresa = lempresa and Ejercicio = lejercicio;
            
            SET lKilosTotalABonificar = round((lKilosTotal) - (lKilosTotal * lPorcOtrosKilos / 100),0);
            
            SET lBaseImponible = round(lImporteKilo * lKilosTotalABonificar, 2);
        
			SET lImporteIGIC = round(lBaseImponible * lTipoIGIC / 100, 2);
        
			SET lImporteIRPF = round((lBaseImponible + lImporteIGIC) * lTipoIRPF / 100, 2);
            
			UPDATE `coopmanagerdb`.`liquidaciones` set BaseImponible = lBaseImponible, ImporteIGIC = lImporteIGIC, ImporteIRPF = lImporteIRPF WHERE
			Empresa = lempresa AND Ejercicio = lejercicioContable and  NumeroFactura = lNumeroFactura;
            
            UPDATE `coopmanagerdb`.`liquidacionesretorno` set PinasTotal = 0, PrecioEscandallo = lImporteKilo, KilosTotal = lKilosTotalABonificar WHERE
			Empresa = lempresa AND Ejercicio = lejercicioContable and  NumeroFactura = lNumeroFactura;
            
		END IF;
        
       SET CosecheroActual = lcosechero;
        
END LOOP kitotal_cursor_loop;

CLOSE kitotal_cursor;


 
END