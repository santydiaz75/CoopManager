DELETE FROM coopmanagerdb.liquidacioneslineas where NumeroFactura >= 445 and NumeroFactura <= 492;
DELETE FROM coopmanagerdb.liquidaciones where NumeroFactura >= 445 and NumeroFactura <= 492;


insert into coopmanagerdb.entradaslineas SELECT Empresa, Ejercicio, idEntrada, 32, NumKilos, SID, LMD, Version, NumKilosOld FROM coopmanagerdb.entradaslineas where ejercicio = 2019 and identrada >= 1670 and identrada <= 1979 and idcategoria = 30;

UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 84.21 / 100 where ejercicio = 2019 and identrada >= 1670 and identrada <= 1706 and idcategoria = 30;
UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 15.79 / 100 where ejercicio = 2019 and identrada >= 1670 and identrada <= 1706 and idcategoria = 32;

UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 83.48 / 100 where ejercicio = 2019 and identrada >= 1707 and identrada <= 1747 and idcategoria = 30;
UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 16.52 / 100 where ejercicio = 2019 and identrada >= 1707 and identrada <= 1747 and idcategoria = 32;

UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 70.09 / 100 where ejercicio = 2019 and identrada >= 1748 and identrada <= 1778 and idcategoria = 30;
UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 29.91 / 100 where ejercicio = 2019 and identrada >= 1748 and identrada <= 1778 and idcategoria = 32;

UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 77.51 / 100 where ejercicio = 2019 and identrada >= 1779 and identrada <= 1810 and idcategoria = 30;
UPDATE coopmanagerdb.entradaslineas sET NumKilos = NumKilos * 22.49 / 100 where ejercicio = 2019 and identrada >= 1779 and identrada <= 1810 and idcategoria = 32;