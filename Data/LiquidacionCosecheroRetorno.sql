CREATE DEFINER=`coopuser`@`localhost` PROCEDURE `LiquidacionCosecheroRetornoGenerate`(lempresa INT, lejercicio INT, lejercicioContable INT, lfecha DATE, lNumeroBonificacion INT, lcosechero INT, lImporteKilo FLOAT, lNumeroKilos FLOAT, ltitulo varchar(50), lconcepto varchar(200))
BEGIN

DECLARE lBaseImponible DECIMAL(10,2) DEFAULT 0;
DECLARE lImporteIGIC DECIMAL(10,2) DEFAULT 0;
DECLARE lImporteIRPF DECIMAL(10,2) DEFAULT 0;
DECLARE lTipoIGIC FLOAT DEFAULT 0;
DECLARE lTipoIRPF FLOAT DEFAULT 0;
DECLARE lNumeroFactura FLOAT DEFAULT 0;
DECLARE lMes INT DEFAULT 0;

SELECT NumeroFactura INTO lNumeroFactura FROM  Liquidaciones WHERE Empresa = lempresa and Ejercicio = lejercicioContable AND Mes = 12 + lNumeroBonificacion AND IdCosechero = lCosechero;
DELETE FROM liquidacionesretorno WHERE  Empresa = lempresa and Ejercicio = lejercicioContable And NumeroBonificacion = lNumeroBonificacion And NumeroFactura = lNumeroFactura;
DELETE FROM Liquidaciones WHERE  Empresa = lempresa and Ejercicio = lejercicioContable AND Mes = 12 + lNumeroBonificacion AND NumeroFactura = lNumeroFactura;

		 
select max(COALESCE(NumeroFactura,0)) + 1 into lNumeroFactura from Liquidaciones where Empresa = lempresa and Ejercicio = lejercicioContable;

IF lNumeroFactura IS NULL THEN
	SET lNumeroFactura = 1;
END IF;


INSERT INTO `coopmanagerdb`.`liquidaciones` (Empresa, Ejercicio, NumeroFactura, Fecha, IdCosechero, Mes, TipoIGIC, TipoIRPF, BaseImponible, ImporteIGIC, ImporteIRPF, ImporteBonificacion) 
VALUES (lempresa, lejercicioContable, lNumeroFactura, lfecha, lcosechero, 12 + lNumeroBonificacion, lTipoIGIC, lTipoIRPF,
0,0,0,0);

INSERT INTO `coopmanagerdb`.`liquidacionesretorno` (Empresa, Ejercicio, NumeroFactura, IdCategoria, PinasTotal, PrecioEscandallo, KilosTotal, NumeroBonificacion, Titulo, Concepto) 
VALUES (lEmpresa, lejercicioContable, lNumeroFactura, 0, 0, 0, 0, lNumeroBonificacion, ltitulo, lconcepto);

select TipoIGIC into lTipoIGIC from cosecheros where Retorno <> 0 and IdCosechero = lcosechero and Empresa = lempresa and Ejercicio = lejercicio;

select TipoIRPF into lTipoIRPF from cosecheros where IdCosechero = lcosechero and Empresa = lempresa and Ejercicio = lejercicio;

SET lBaseImponible = round(lImporteKilo * lNumeroKilos, 2);

SET lImporteIGIC = round(lBaseImponible * lTipoIGIC / 100, 2);

SET lImporteIRPF = round((lBaseImponible + lImporteIGIC) * lTipoIRPF / 100, 2);

UPDATE `coopmanagerdb`.`liquidaciones` set BaseImponible = lBaseImponible, ImporteIGIC = lImporteIGIC, ImporteIRPF = lImporteIRPF WHERE
Empresa = lempresa AND Ejercicio = lejercicioContable and  NumeroFactura = lNumeroFactura;

UPDATE `coopmanagerdb`.`liquidacionesretorno` set PinasTotal = 0, PrecioEscandallo = lImporteKilo, KilosTotal = lNumeroKilos WHERE
Empresa = lempresa AND Ejercicio = lejercicioContable and  NumeroFactura = lNumeroFactura;
            
 
END