Update  `coopmanagerdb`.`cosecheros` SEt OCM='1' WHERE OCM='SI' And Empresa=1;
Update  `coopmanagerdb`.`cosecheros` SEt OCM='0' WHERE OCM='NO' And Empresa=1;
Update  `coopmanagerdb`.`cosecheros` SEt OCM='0' WHERE OCM<>'1' And OCM<>'0' And Empresa=1;

Cambiar el campo OCM En cosecheros de varchar(2) a Smallint(6).

Cambiar el campo Unidades de FacturasLineas y facturaPagoLineas de Int(11) a Float.

Eliminar campo Cargo de cosecheros.

Ampliar Nombre, Apellidos, Direccion y Poblacion a 100 en cosecheros.

Ampliar Nombre, Apellidos, Direccion y Poblacion a 100 en conductores.

Ampliar Nombre, Apellidos, Direccion y Poblacion a 100 en empleados.

Ampliar Nombre, Provincia, Direccion y Poblacion a 100 en empresas.

Ampliar Nombre, Provincia, Direccion y Poblacion a 100 en facturascabecera.

Ampliar Nombre, Provincia, Direccion y Poblacion a 100 en facturaspagocabecera.

Ampliar Nombre, Provincia, Direccion y Poblacion a 100 en identidades.

Ampliar Nombre, Direccion y Poblacion a 100 en receptores.

Añadir CorreoElectronico varchar(100) a empresas

Añadir Fijo Smallint(6) a empleados