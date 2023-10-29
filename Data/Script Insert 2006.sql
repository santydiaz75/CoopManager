INSERT INTO `coopmanagerdb`.`bancos`
(`Empresa`,
`Ejercicio`,
`IdBanco`,
`IdSucursal`,
`NombreBanco`,
`NombreSucursal`,
`CuentaContable`)
SELECT
'1',
'2006',
RIGHT(CONCAT('0000', `bancos`.`CodigoBanco`),4),
RIGHT(CONCAT('0000', `bancos`.`CodigoSucursal`),4),
`bancos`.`NombreBanco`,
`bancos`.`NombreSucursal`,
`bancos`.`CuentaContable`
FROM `gesplata2006`.`bancos`;

INSERT INTO `coopmanagerdb`.`barcos`
(`Empresa`,
`Ejercicio`,
`IdBarco`,
`NombreBarco`
)
SELECT
'1',
'2006',
`barcos`.`Codigo`,
`barcos`.`Nombre`
FROM `gesplata2006`.`barcos`;

INSERT INTO `coopmanagerdb`.`bimestres`
(`Empresa`,
`Ejercicio`,
`Bimestre`,
`Descripcion`,
`DesdeFecha`,
`HastaFecha`
)
SELECT
'1',
'2006',
`bimestre`.`Bimestre`,
`bimestre`.`Mes`,
`bimestre`.`DesdeFecha`,
`bimestre`.`HastaFecha`
FROM `gesplata2006`.`bimestre`;

INSERT INTO `coopmanagerdb`.`calendario`
(`Empresa`,
`Ejercicio`,
`Semana`,
`DesdeFecha`,
`HastaFecha`
)
SELECT
'1',
'2006',
`calenda`.`Semana`,
`calenda`.`DesdeFecha`,
`calenda`.`HastaFecha`
FROM `gesplata2006`.`calenda`;

INSERT INTO `coopmanagerdb`.`categorias`
(`Empresa`,
`Ejercicio`,
`IdCategoria`,
`NombreCategoria`,
`NumKilosCaja`,
`IdSubcategoria`,
`Privada`,
`Orden`)
SELECT
'1',
'2006',
`catego`.`Codigo`,
`catego`.`Descripcion`,
`catego`.`KilosCaja`,
`catego`.`CodigoSubcategoria`,
`catego`.`Privada`,
`catego`.`Orden`
FROM `gesplata2006`.`catego`;

INSERT INTO `coopmanagerdb`.`conductores`
(`Empresa`,
`Ejercicio`,
`IdConductor`,
`Apellidos`,
`Nombre`,
`Direccion`,
`Poblacion`,
`CodigoPostal`,
`Telefono`,
`NIF`,
`DigitoControl`,
`IdBanco`,
`IdSucursal`,
`CuentaBancaria`,
`CuentaContable`
)
SELECT
'1',
'2006',
`conducto`.`Codigo`,
`conducto`.`Apellidos`,
`conducto`.`Nombre`,
`conducto`.`Direccion`,
`conducto`.`Poblacion`,
`conducto`.`CodigoPostal`,
`conducto`.`Telefono`,
`conducto`.`NIF`,
`conducto`.`DigitoControl`,
`conducto`.`CuentaBancaria`,
`conducto`.`CuentaContable`,
RIGHT(CONCAT('0000', `conducto`.`CodigoBanco`),4),
RIGHT(CONCAT('0000', `conducto`.`CodigoSucursal`),4)
FROM `gesplata2006`.`conducto`;

INSERT INTO `coopmanagerdb`.`cosecheros`
(`Empresa`,
`Ejercicio`,
`IdCosechero`,
`Nombre`,
`Apellidos`,
`Direccion`,
`Poblacion`,
`CodigoPostal`,
`FAX`,
`Telefono1`,
`Telefono2`,
`NIF`,
`CuentaContable`,
`TipoIGIC`,
`TipoIRPF`,
`IdZona`,
`IdBanco`,
`IdSucursal`,
`DigitoControl`,
`CuentaBancaria`,
`OCM`,
`GrupoPladimisa`,
`CodigoINE`,
`Cargo`,
`IdGrupo`,
`Poligono`,
`Parcela`,
`Recinto`,
`Superficie`,
`SuperficieCultivada`
)
SELECT
'1',
'2006',
`coseche`.`Codigo`,
`coseche`.`Nombre`,
`coseche`.`Apellidos`,
`coseche`.`Direccion`,
`coseche`.`Poblacion`,
`coseche`.`CodigoPostal`,
`coseche`.`FAX`,
`coseche`.`Telefono1`,
`coseche`.`Telefono2`,
`coseche`.`NIF`,
`coseche`.`CuentaContable`,
`coseche`.`TipoIGIC`,
`coseche`.`TipoIRPF`,
`coseche`.`CodigoZona`,
RIGHT(CONCAT('0000', `coseche`.`CodigoBanco`),4),
RIGHT(CONCAT('0000', `coseche`.`CodigoSucursal`),4),
`coseche`.`DigitoControl`,
`coseche`.`CuentaBancaria`,
`coseche`.`OCM`,
`coseche`.`GrupoPladimisa`,
`coseche`.`CodigoIne`,
`coseche`.`Cargo`,
`coseche`.`CodigoGrupo`,
`coseche`.`Poligono`,
`coseche`.`Parcela`,
`coseche`.`Recinto`,
`coseche`.`Superficie`,
`coseche`.`SuperficieCultivada`
FROM `gesplata2006`.`coseche`;

INSERT INTO `coopmanagerdb`.`entradascabecera`
(`Empresa`,
`Ejercicio`,
`IdEntrada`,
`Semana`,
`Fecha`,
`IdCosechero`,
`IdZona`,
`NumPinas`
)
SELECT
'1',
'2006',
`cabent`.`Vale`,
`cabent`.`Semana`,
`cabent`.`Fecha`,
`cabent`.`CodigoCosechero`,
`cabent`.`CodigoZona`,
`cabent`.`Pi¤as`
FROM `gesplata2006`.`cabent`;

INSERT INTO `coopmanagerdb`.`entradaslineas`
(`Empresa`,
`Ejercicio`,
`IdEntrada`,
`IdCategoria`,
`NumKilos`
)
SELECT
'1',
'2006',
`detent`.`Vale`,
`detent`.`CodigoCategoria`,
`detent`.`Kilos`
FROM `gesplata2006`.`detent`;

INSERT INTO `coopmanagerdb`.`paises`
(`Empresa`,
`Ejercicio`,
`IdPais`,
`NombrePais`
)
SELECT
'1',
'2006',
`paises`.`Codigo`,
`paises`.`Nombre`
FROM `gesplata2006`.`paises`;

INSERT INTO `coopmanagerdb`.`precios`
(`Empresa`,
`Ejercicio`,
`Semana`,
`IdCategoria`,
`Precio`
)
SELECT
'1',
'2006',
`precios`.`Semana`,
`precios`.`CodigoCategoria`,
`precios`.`Precio`
FROM `gesplata2006`.`precios`;

INSERT INTO `coopmanagerdb`.`puertos`
(`Empresa`,
`Ejercicio`,
`IdPuerto`,
`NombrePuerto`,
`IdPais`,
`Plaza`
)
SELECT
'1',
'2006',
`puertos`.`Codigo`,
`puertos`.`Nombre`,
`puertos`.`CodigoPais`,
`puertos`.`Plaza`
FROM `gesplata2006`.`puertos`;

INSERT INTO `coopmanagerdb`.`receptores`
(`Empresa`,
`Ejercicio`,
`IdReceptor`,
`Nombre`,
`Direccion`,
`Poblacion`,
`CodigoPostal`,
`Telefono`,
`FAX`,
`NIF`,
`CuentaContable`,
`IdZona`
)
SELECT
'1',
'2006',
`recepto`.`Codigo`,
`recepto`.`Nombre`,
`recepto`.`Direccion`,
`recepto`.`Poblacion`,
`recepto`.`CodigoPostal`,
`recepto`.`Telefono`,
`recepto`.`FAX`,
`recepto`.`NIF`,
`recepto`.`CuentaContable`,
`recepto`.`CodigoZona`
FROM `gesplata2006`.`recepto`;

INSERT INTO `coopmanagerdb`.`vehiculos`
(`Empresa`,
`Ejercicio`,
`IdVehiculo`,
`Marca`,
`Matricula`
)
SELECT
'1',
'2006',
`vehiculo`.`Codigo`,
`vehiculo`.`Marca`,
`vehiculo`.`Matricula`
FROM `gesplata2006`.`vehiculo`;

INSERT INTO `coopmanagerdb`.`ventascabecera`
(`Empresa`,
`Ejercicio`,
`IdVenta`,
`Fecha`,
`IdBarco`,
`IdPuerto`,
`Plataforma`,
`Semana`,
`IdVehiculo`,
`IdConductor`
)
SELECT
'1',
'2006',
`cabven`.`Salida`,
`cabven`.`Fecha`,
`cabven`.`CodigoBarco`,
`cabven`.`CodigoPuerto`,
`cabven`.`Plataforma`,
`cabven`.`Semana`,
`cabven`.`CodigoVehiculo`,
`cabven`.`CodigoConductor`
FROM `gesplata2006`.`cabven`;

INSERT INTO `coopmanagerdb`.`ventaslineas`
(`Empresa`,
`Ejercicio`,
`IdVenta`,
`IdReceptor`,
`IdCategoria`,
`NumCajas`,
`NumKilos`,
`Importe`,
`Precio`,
`IdZona`
)
SELECT
'1',
'2006',
`detven`.`Salida`,
`detven`.`CodigoReceptor`,
`detven`.`CodigoCategoria`,
`detven`.`Cajas`,
`detven`.`Kilos`,
`detven`.`Importe`,
`detven`.`Precio`,
`detven`.`CodigoZona`
FROM `gesplata2006`.`detven`;

INSERT INTO `coopmanagerdb`.`zonas`
(`Empresa`,
`Ejercicio`,
`IdZona`,
`NombreZona`
)
SELECT
'1',
'2006',
`zonas`.`Codigo`,
`zonas`.`Nombre`
FROM `gesplata2006`.`zonas`;


INSERT INTO `coopmanagerdb`.`ayudas`
(`Empresa`,
`Ejercicio`,
`IdCosechero`,
`NombreApellidos`,
`NIF`,
`Direccion`,
`Poblacion`,
`CodigoPostal`,
`Telefono`,
`IdBanco`,
`IdSucursal`,
`DigitoControl`,
`CuentaBancaria`,
`CuentaContable`,
`Kilos`,
`NumPinas`)
SELECT
'1',
'2006',
`ayudas`.`Codigo`,
`ayudas`.`NombreApellidos`,
`ayudas`.`NIF`,
`ayudas`.`Direccion`,
`ayudas`.`Poblacion`,
`ayudas`.`CodigoPostal`,
`ayudas`.`Telefono1`,
RIGHT(CONCAT('0000', `ayudas`.`CodigoBanco`),4),
RIGHT(CONCAT('0000', `ayudas`.`CodigoSucursal`),4),
RIGHT(CONCAT('0000', `ayudas`.`DigitoControl`),2),
RIGHT(CONCAT('0000', `ayudas`.`CuentaBancaria`),10),
`ayudas`.`Cuenta`,
`ayudas`.`Kilos`,
`ayudas`.`Pinas`
FROM `gesplata2006`.`ayudas`;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 1 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 1 and F.NumeroFactura <= 69
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 2 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 70 and F.NumeroFactura <= 143
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 3 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 144 and F.NumeroFactura <= 220
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 4 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 221 and F.NumeroFactura <= 296
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 5 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 297 and F.NumeroFactura <= 369
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 6 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 370 and F.NumeroFactura <= 443
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;
INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 7 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 444 and F.NumeroFactura <= 499
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 8 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 500 and F.NumeroFactura <= 562
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 9 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 563 and F.NumeroFactura <= 631
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 10 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 632 and F.NumeroFactura <= 699
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 11 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 700 and F.NumeroFactura <= 838
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

INSERT INTO `coopmanagerdb`.`liquidaciones`
(`Empresa`,
`Ejercicio`,
`NumeroFactura`,
`Fecha`,
`IdCosechero`,
`TipoIGIC`,
`TipoIRPF`,
`BaseImponible`,
`Mes`)
select 1, 2006, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 12 from 
`gesplata2006`.`higicirp` f where f.NumeroFactura >= 839 and F.NumeroFactura <= 892
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

Update `coopmanagerdb`.`calendario` c Set PorcKilosInutilizados = 
coalesce((SELECT Porcentaje FROM `gesplata2006`.`porkinu` p  where c.semana = p.semana), 0) WHERE Ejercicio = 2006 And Empresa = 1;