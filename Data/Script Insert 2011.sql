INSERT INTO `coopmanagerdb`.`bancos`
(`Empresa`,
`Ejercicio`,
`IdBanco`,
`IdSucursal`,
`NombreBanco`,
`NombreSucursal`,
`CuentaContable`)
SELECT
`Empresa`,
'2011',
`IdBanco`,
`IdSucursal`,
`NombreBanco`,
`NombreSucursal`,
`CuentaContable`
FROM `coopmanagerdb`.`bancos` WHERE Empresa = 1 and Ejercicio = 2010;

INSERT INTO `coopmanagerdb`.`barcos`
(`Empresa`,
`Ejercicio`,
`IdBarco`,
`NombreBarco`
)
SELECT
`Empresa`,
'2011',
`IdBarco`,
`NombreBarco`
FROM `coopmanagerdb`.`barcos` WHERE Empresa = 1 and Ejercicio = 2010;


INSERT INTO `coopmanagerdb`.`bimestres`
(`Empresa`,
`Ejercicio`,
`Bimestre`,
`Descripcion`,
`DesdeFecha`,
`HastaFecha`
)
SELECT
`Empresa`,
'2011',
`Bimestre`,
`Descripcion`,
`DesdeFecha`,
`HastaFecha`
FROM `coopmanagerdb`.`bimestres` WHERE Empresa = 1 and Ejercicio = 2010;

INSERT INTO `coopmanagerdb`.`calendario`
(`Empresa`,
`Ejercicio`,
`Semana`,
`DesdeFecha`,
`HastaFecha`
)
SELECT
`Empresa`,
'2011',
`Semana`,
`DesdeFecha`,
`HastaFecha`
FROM `coopmanagerdb`.`calendario` WHERE Empresa = 1 and Ejercicio = 2010;

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
`Empresa`,
'2011',
`IdCategoria`,
`NombreCategoria`,
`NumKilosCaja`,
`IdSubcategoria`,
`Privada`,
`Orden`
FROM `coopmanagerdb`.`categorias` WHERE Empresa = 1 and Ejercicio = 2010;

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
`Empresa`,
'2011',
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
FROM `coopmanagerdb`.`conductores` WHERE Empresa = 1 and Ejercicio = 2010;

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
`Empresa`,
'2011',
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
FROM `coopmanagerdb`.`cosecheros` WHERE Empresa = 1 and Ejercicio = 2010;

INSERT INTO `coopmanagerdb`.`paises`
(`Empresa`,
`Ejercicio`,
`IdPais`,
`NombrePais`
)
SELECT
`Empresa`,
'2011',
`IdPais`,
`NombrePais`
FROM `coopmanagerdb`.`paises` WHERE Empresa = 1 and Ejercicio = 2010;

INSERT INTO `coopmanagerdb`.`puertos`
(`Empresa`,
`Ejercicio`,
`IdPuerto`,
`NombrePuerto`,
`IdPais`,
`Plaza`
)
SELECT
`Empresa`,
'2011',
`IdPuerto`,
`NombrePuerto`,
`IdPais`,
`Plaza`
FROM `coopmanagerdb`.`puertos` WHERE Empresa = 1 and Ejercicio = 2010;

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
`Empresa`,
'2011',
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
FROM `coopmanagerdb`.`receptores` WHERE Empresa = 1 and Ejercicio = 2010;

INSERT INTO `coopmanagerdb`.`vehiculos`
(`Empresa`,
`Ejercicio`,
`IdVehiculo`,
`Marca`,
`Matricula`
)
SELECT
`Empresa`,
'2011',
`IdVehiculo`,
`Marca`,
`Matricula`
FROM `coopmanagerdb`.`vehiculos` WHERE Empresa = 1 and Ejercicio = 2010;

INSERT INTO `coopmanagerdb`.`zonas`
(`Empresa`,
`Ejercicio`,
`IdZona`,
`NombreZona`
)
SELECT
`Empresa`,
'2011',
`IdZona`,
`NombreZona`
FROM `coopmanagerdb`.`zonas` WHERE Empresa = 1 and Ejercicio = 2010;






