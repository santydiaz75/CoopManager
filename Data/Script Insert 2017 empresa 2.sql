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
'2017',
`IdBanco`,
`IdSucursal`,
`NombreBanco`,
`NombreSucursal`,
`CuentaContable`
FROM `coopmanagerdb`.`bancos` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`barcos`
(`Empresa`,
`Ejercicio`,
`IdBarco`,
`NombreBarco`
)
SELECT
`Empresa`,
'2017',
`IdBarco`,
`NombreBarco`
FROM `coopmanagerdb`.`barcos` WHERE Empresa = 2 and Ejercicio = 2016;


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
'2017',
`Bimestre`,
`Descripcion`,
`DesdeFecha`,
`HastaFecha`
FROM `coopmanagerdb`.`bimestres` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`categorias`
(`Empresa`,
`Ejercicio`,
`IdCategoria`,
`NombreCategoria`,
`NumKilosCaja`,
`IdSubcategoria`,
`Privada`,
`Orden`,
`CodCategoriaAgriten`)
SELECT
`Empresa`,
'2017',
`IdCategoria`,
`NombreCategoria`,
`NumKilosCaja`,
`IdSubcategoria`,
`Privada`,
`Orden`,
`CodCategoriaAgriten`
FROM `coopmanagerdb`.`categorias` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`conceptos`
(`Empresa`,
`Ejercicio`,
`Concepto`,
`ConceptoDesc`,
`CuentaContable`,
`ConIgic`)
SELECT
`Empresa`,
'2017',
`Concepto`,
`ConceptoDesc`,
`CuentaContable`,
`ConIgic`
FROM `coopmanagerdb`.`conceptos` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`conceptospago`
(`Empresa`,
`Ejercicio`,
`ConceptoPago`,
`ConceptoPagoDesc`,
`CuentaContable`)
SELECT
`Empresa`,
'2017',
`ConceptoPago`,
`ConceptoPagoDesc`,
`CuentaContable`
FROM `coopmanagerdb`.`conceptospago` WHERE Empresa = 2 and Ejercicio = 2016;

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
'2017',
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
FROM `coopmanagerdb`.`conductores` WHERE Empresa = 2 and Ejercicio = 2016;

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
`IdGrupo`,
`CodigoAsesoria`,
`NumKilosReferencia`,
`Email`
)
SELECT
`Empresa`,
'2017',
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
`IdGrupo`,
`CodigoAsesoria`,
`NumKilosReferencia`,
`Email`
FROM `coopmanagerdb`.`cosecheros` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`cosecherosparcelas`
(`Empresa`,
`Ejercicio`,
`IdCosechero`,
`IdParcela`,
`Municipio`,
`Paraje`,
`Poligono`,
`Parcela`,
`Recinto`,
`Superficie`,
`SuperficieCultivada`
)
SELECT
`Empresa`,
'2017',
`IdCosechero`,
`IdParcela`,
`Municipio`,
`Paraje`,
`Poligono`,
`Parcela`,
`Recinto`,
`Superficie`,
`SuperficieCultivada`
FROM `coopmanagerdb`.`cosecherosparcelas` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`empleados`
(`Empresa`,
`Ejercicio`,
`IdEmpleado`,
`Apellidos`,
`FechaNacimiento`,
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
`Categoria`,
`FechaAntiguedad`,
`Observaciones`,
`Fijo`)
SELECT
`Empresa`,
'2017',
`IdEmpleado`,
`Apellidos`,
`FechaNacimiento`,
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
`Categoria`,
`FechaAntiguedad`,
`Observaciones`,
`Fijo`
FROM `coopmanagerdb`.`empleados` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`identidades`
(`Empresa`,
`Ejercicio`,
`Identidad`,
`NombreIdentidad`,
`Direccion`,
`Poblacion`,
`Provincia`,
`CodigoPostal`,
`Telefono`,
`NIF`,
`DigitoControl`,
`IdBanco`,
`IdSucursal`,
`CuentaBancaria`,
`CuentaContable`,
`Cliente`,
`Proveedor`,
`TipoImpuesto`,
`TipoIRPF`)
SELECT
`Empresa`,
'2017',
`Identidad`,
`NombreIdentidad`,
`Direccion`,
`Poblacion`,
`Provincia`,
`CodigoPostal`,
`Telefono`,
`NIF`,
`DigitoControl`,
`IdBanco`,
`IdSucursal`,
`CuentaBancaria`,
`CuentaContable`,
`Cliente`,
`Proveedor`,
`TipoImpuesto`,
`TipoIRPF`
FROM `coopmanagerdb`.`identidades` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`paises`
(`Empresa`,
`Ejercicio`,
`IdPais`,
`NombrePais`
)
SELECT
`Empresa`,
'2017',
`IdPais`,
`NombrePais`
FROM `coopmanagerdb`.`paises` WHERE Empresa = 2 and Ejercicio = 2016;

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
'2017',
`IdPuerto`,
`NombrePuerto`,
`IdPais`,
`Plaza`
FROM `coopmanagerdb`.`puertos` WHERE Empresa = 2 and Ejercicio = 2016;

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
'2017',
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
FROM `coopmanagerdb`.`receptores` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`tiposcoste`
(`Empresa`,
`Ejercicio`,
`IdTipoCoste`,
`DescTipoCoste`
)
SELECT
`Empresa`,
'2017',
`IdTipoCoste`,
`DescTipoCoste`
FROM `coopmanagerdb`.`tiposcoste` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`tiposgasto`
(`Empresa`,
`Ejercicio`,
`IdTipoGasto`,
`DescTipoGasto`,
`CuentaContable`
)
SELECT
`Empresa`,
'2017',
`IdTipoGasto`,
`DescTipoGasto`,
`CuentaContable`
FROM `coopmanagerdb`.`tiposgasto` WHERE Empresa = 2 and Ejercicio = 2016;


INSERT INTO `coopmanagerdb`.`vehiculos`
(`Empresa`,
`Ejercicio`,
`IdVehiculo`,
`Marca`,
`Matricula`
)
SELECT
`Empresa`,
'2017',
`IdVehiculo`,
`Marca`,
`Matricula`
FROM `coopmanagerdb`.`vehiculos` WHERE Empresa = 2 and Ejercicio = 2016;

INSERT INTO `coopmanagerdb`.`zonas`
(`Empresa`,
`Ejercicio`,
`IdZona`,
`NombreZona`
)
SELECT
`Empresa`,
'2017',
`IdZona`,
`NombreZona`
FROM `coopmanagerdb`.`zonas` WHERE Empresa = 2 and Ejercicio = 2016;






