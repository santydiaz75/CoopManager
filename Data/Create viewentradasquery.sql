CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `viewentradasquery` AS
    SELECT 
        `e`.`IdEntrada` AS `IdEntrada`,
        `e`.`Empresa` AS `Empresa`,
        `e`.`Ejercicio` AS `Ejercicio`,
        `e`.`Semana` AS `Semana`,
        `e`.`Fecha` AS `Fecha`,
        `e`.`IdCosechero` AS `IdCosechero`,
        COALESCE(`c`.`NIF`, '') AS `Nif`,
        COALESCE(`c`.`Nombre`, '') AS `Nombre`,
        COALESCE(`c`.`Apellidos`, '') AS `Apellidos`,
        `c`.`NumKilosReferencia` AS `NumKilosReferencia`,
        `e`.`NumPinas` AS `NumPinas`,
        `coopmanagerdb`.`EntradaGetNumKilos`(`e`.`Empresa`,
                `e`.`Ejercicio`,
                `e`.`IdEntrada`) AS `NumKIlos`
    FROM
        (`entradascabecera` `e`
        JOIN `cosecheros` `c` ON (((`e`.`IdCosechero` = `c`.`IdCosechero`)
            AND (`e`.`Empresa` = `c`.`Empresa`)
            AND (`e`.`Ejercicio` = `c`.`Ejercicio`))))
    ORDER BY `e`.`IdEntrada`