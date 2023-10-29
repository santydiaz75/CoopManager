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
'2000',
RIGHT(CONCAT('0000', `bancos`.`CodigoBanco`),4),
RIGHT(CONCAT('0000', `bancos`.`CodigoSucursal`),4),
`bancos`.`NombreBanco`,
`bancos`.`NombreSucursal`,
`bancos`.`CuentaContable`
FROM `gesplata2000`.`bancos`;

INSERT INTO `coopmanagerdb`.`barcos`
(`Empresa`,
`Ejercicio`,
`IdBarco`,
`NombreBarco`
)
SELECT
'1',
'2000',
`barcos`.`Codigo`,
`barcos`.`Nombre`
FROM `gesplata2000`.`barcos`;

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
'2000',
`bimestre`.`Bimestre`,
`bimestre`.`Mes`,
`bimestre`.`DesdeFecha`,
`bimestre`.`HastaFecha`
FROM `gesplata2000`.`bimestre`;

INSERT INTO `coopmanagerdb`.`calendario`
(`Empresa`,
`Ejercicio`,
`Semana`,
`DesdeFecha`,
`HastaFecha`
)
SELECT
'1',
'2000',
`calenda`.`Semana`,
`calenda`.`DesdeFecha`,
`calenda`.`HastaFecha`
FROM `gesplata2000`.`calenda`;

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
'2000',
`catego`.`Codigo`,
`catego`.`Descripcion`,
`catego`.`KilosCaja`,
`catego`.`CodigoSubcategoria`,
`catego`.`Privada`,
`catego`.`Orden`
FROM `gesplata2000`.`catego`;

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
'2000',
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
FROM `gesplata2000`.`conducto`;

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
'2000',
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
FROM `gesplata2000`.`coseche`;

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
'2000',
`cabent`.`Vale`,
`cabent`.`Semana`,
`cabent`.`Fecha`,
`cabent`.`CodigoCosechero`,
`cabent`.`CodigoZona`,
`cabent`.`Pi¤as`
FROM `gesplata2000`.`cabent`;

INSERT INTO `coopmanagerdb`.`entradaslineas`
(`Empresa`,
`Ejercicio`,
`IdEntrada`,
`IdCategoria`,
`NumKilos`
)
SELECT
'1',
'2000',
`detent`.`Vale`,
`detent`.`CodigoCategoria`,
`detent`.`Kilos`
FROM `gesplata2000`.`detent`;

INSERT INTO `coopmanagerdb`.`paises`
(`Empresa`,
`Ejercicio`,
`IdPais`,
`NombrePais`
)
SELECT
'1',
'2000',
`paises`.`Codigo`,
`paises`.`Nombre`
FROM `gesplata2000`.`paises`;

INSERT INTO `coopmanagerdb`.`precios`
(`Empresa`,
`Ejercicio`,
`Semana`,
`IdCategoria`,
`Precio`
)
SELECT
'1',
'2000',
`precios`.`Semana`,
`precios`.`CodigoCategoria`,
`precios`.`Precio`
FROM `gesplata2000`.`precios`;

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
'2000',
`puertos`.`Codigo`,
`puertos`.`Nombre`,
`puertos`.`CodigoPais`,
`puertos`.`Plaza`
FROM `gesplata2000`.`puertos`;

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
'2000',
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
FROM `gesplata2000`.`recepto`;

INSERT INTO `coopmanagerdb`.`vehiculos`
(`Empresa`,
`Ejercicio`,
`IdVehiculo`,
`Marca`,
`Matricula`
)
SELECT
'1',
'2000',
`vehiculo`.`Codigo`,
`vehiculo`.`Marca`,
`vehiculo`.`Matricula`
FROM `gesplata2000`.`vehiculo`;

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
'2000',
`cabven`.`Salida`,
`cabven`.`Fecha`,
`cabven`.`CodigoBarco`,
`cabven`.`CodigoPuerto`,
`cabven`.`Plataforma`,
`cabven`.`Semana`,
`cabven`.`CodigoVehiculo`,
`cabven`.`CodigoConductor`
FROM `gesplata2000`.`cabven`;

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
'2000',
`detven`.`Salida`,
`detven`.`CodigoReceptor`,
`detven`.`CodigoCategoria`,
`detven`.`Cajas`,
`detven`.`Kilos`,
`detven`.`Importe`,
`detven`.`Precio`,
`detven`.`CodigoZona`
FROM `gesplata2000`.`detven`;

INSERT INTO `coopmanagerdb`.`zonas`
(`Empresa`,
`Ejercicio`,
`IdZona`,
`NombreZona`
)
SELECT
'1',
'2000',
`zonas`.`Codigo`,
`zonas`.`Nombre`
FROM `gesplata2000`.`zonas`;

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
'2000',
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
FROM `gesplata2000`.`ayudas`;

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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 1 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 1 and F.NumeroFactura <= 119
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 2 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 120 and F.NumeroFactura <= 249
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 3 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 250 and F.NumeroFactura <= 383
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 4 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 384 and F.NumeroFactura <= 520
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 5 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 521 and F.NumeroFactura <= 650
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 6 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 651 and F.NumeroFactura <= 775
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 7 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 779 and F.NumeroFactura <= 830
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 8 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 896 and F.NumeroFactura <= 1011
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 9 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 1012 and F.NumeroFactura <= 1131
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 10 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 1132 and F.NumeroFactura <= 1252
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 11 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 1253 and F.NumeroFactura <= 1372
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
select 1, 2000, f.NumeroFactura, f.fecha, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible, 12 from 
`gesplata2000`.`higicirp` f where f.NumeroFactura >= 1373 and F.NumeroFactura <= 1484
group by f.NumeroFactura, f.CodigoCosechero, f.TipoIGIC,  f.TipoIrpf, f.BaseImponible;

Update `coopmanagerdb`.`calendario` c Set PorcKilosInutilizados = 
coalesce((SELECT Porcentaje FROM `gesplata2000`.`porkinu` p  where c.semana = p.semana), 0) WHERE Ejercicio = 2000 And Empresa = 1;