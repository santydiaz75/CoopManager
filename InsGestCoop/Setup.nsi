;InstallOptions Test Script
;Written by Joost Verburg
;--------------------------

!define TEMP1 $R0 ;Temp variable

;The name of the installer
Name "Setup"


;The file to write
OutFile "Setup.exe"

; El directorio default para la instalación
InstallDir $PROGRAMFILES\GestCoop

; Clave en el registro de Windows chequeado para el directorio (Si existe, Este 
; se sobre escribirá en el viejo)
InstallDirRegKey HKLM "Software\GestCoop" "Install_Dir"
     
; Requerir permisos para Windows Vista
RequestExecutionLevel admin

; Show install details
ShowInstDetails show

;Things that need to be extracted on startup (keep these lines before any File command!)
;Only useful for BZIP2 compression
;Use ReserveFile for your own InstallOptions INI files too!

ReserveFile "InstallOptions.dll"
ReserveFile "Connection.ini"

;Order of pages
Page components
Page directory
Page custom SetCustom uninstallCustom ": MySQL Connection" ;Custom page. InstallOptions gets called in SetCustom.

Function .onInit

  ;Extract InstallOptions files
  ;$PLUGINSDIR will automatically be removed when the installer closes
  
  InitPluginsDir
  File /oname=$PLUGINSDIR\Connection.ini "Connection.ini"
  
FunctionEnd

Function SetCustom

  ;Display the InstallOptions dialog

  Push ${TEMP1}

    InstallOptions::dialog "$PLUGINSDIR\Connection.ini"
    Pop ${TEMP1}
  
  Pop ${TEMP1}

  ;Get Install Options dialog user input

  ReadINIStr ${TEMP1} "$PLUGINSDIR\Connection.ini" "Field 1" "State"
  WriteRegStr HKLM SOFTWARE\GestCoop "Server" "${TEMP1}"
  ReadINIStr ${TEMP1} "$PLUGINSDIR\Connection.ini" "Field 2" "State"
  WriteRegStr HKLM SOFTWARE\GestCoop "Database" "${TEMP1}"
  ReadINIStr ${TEMP1} "$PLUGINSDIR\Connection.ini" "Field 3" "State"
  WriteRegStr HKLM SOFTWARE\GestCoop "User" "${TEMP1}"
  ReadINIStr ${TEMP1} "$PLUGINSDIR\Connection.ini" "Field 4" "State"
  WriteRegStr HKLM SOFTWARE\GestCoop "Password" "${TEMP1}"

FunctionEnd

Function uninstallCustom

  DeleteRegKey HKLM SOFTWARE\GestCoop\Server
  DeleteRegKey HKLM SOFTWARE\GestCoop\Database
  DeleteRegKey HKLM SOFTWARE\GestCoop\User
  DeleteRegKey HKLM SOFTWARE\GestCoop\Password

FunctionEnd

Page instfiles

UninstPage uninstConfirm
UninstPage instfiles

 ; El material para la instalación
     Section "GestCoop (requerido)"
     
       SectionIn RO
       
       ; Toma el directorio de que se selecciono para la instalación.
     
       SetOutPath $INSTDIR
       
       ; Pone los Archivos a instalar
       File "GestCoop.jar"
       File "GestCoop.exe"

       SetOutPath $INSTDIR\reportsPackage

       File "reportsPackage\AyudasOCM.jasper"
       File "reportsPackage\FacturaLiquidacion.jasper"
       File "reportsPackage\FacturaLiquidacionConCopia.jasper"
       File "reportsPackage\FacturaLiquidacionPrecios.jasper"
       File "reportsPackage\FacturaServicios.jasper"
       File "reportsPackage\ControlESAlmacen.jasper"
       File "reportsPackage\ControlProduccionZonas.jasper"
       File "reportsPackage\IGIC.jasper"
       File "reportsPackage\InformeVale.jasper"
       File "reportsPackage\InformeValeGrupo.jasper"
       File "reportsPackage\InformeValeVentas.jasper"
       File "reportsPackage\IRPF.jasper"
       File "reportsPackage\KilosInutilizados.jasper"
       File "reportsPackage\ResumenLiquidacion.jasper"
       File "reportsPackage\PreLiquidacion.jasper"
       File "reportsPackage\Anagrama1.jpg"
       File "reportsPackage\Anagrama2.jpg"
       File "reportsPackage\AnagramaAsprocan.jpg"
       File "reportsPackage\AnagramaAgriten.jpg"
       File "reportsPackage\AyudasOCM.jrxml"
       File "reportsPackage\FacturaLiquidacion.jrxml"
       File "reportsPackage\FacturaLiquidacionConCopia.jrxml"
       File "reportsPackage\FacturaLiquidacionPrecios.jrxml"
       File "reportsPackage\FacturaServicios.jrxml"
       File "reportsPackage\ControlESAlmacen.jrxml"
       File "reportsPackage\ControlProduccionZonas.jrxml"	
       File "reportsPackage\IGIC.jrxml"
       File "reportsPackage\InformeVale.jrxml"
       File "reportsPackage\InformeValeGrupo.jrxml"
       File "reportsPackage\InformeValeVentas.jrxml"
       File "reportsPackage\IRPF.jrxml"
       File "reportsPackage\KilosInutilizados.jrxml"
       File "reportsPackage\ResumenLiquidacion.jrxml"
       File "reportsPackage\PreLiquidacion.jrxml"
       File "reportsPackage\ListadoPersonal.jrxml"
       File "reportsPackage\ListadoPersonalBanco.jrxml"
       File "reportsPackage\ListadoNominas.jrxml"
       File "reportsPackage\ControlCalidad.jrxml"
       File "reportsPackage\ListadoCosecherosKilos.jrxml"
     
       
       ; Escribimos los registros de instalación dentro del directorio del registro
       WriteRegStr HKLM SOFTWARE\GestCoop "Install_Dir" "$INSTDIR"
       
       ; Escribimos las claves de desinstalación de Windows
     
       WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\GestCoop" "DisplayName" "GestCoop"
       WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\GestCoop" "UninstallString" '"$INSTDIR\uninstall.exe"'
       WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\GestCoop" "NoModify" 1
       WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\GestCoop" "NoRepair" 1
       WriteUninstaller "uninstall.exe"
       
     SectionEnd
     
     ; Sección opcional (Pudiera ser deshabilitada pero en este caso necesitamos crear todo.)
     Section "Start Menu Shortcuts"
     
       CreateDirectory "$SMPROGRAMS\GestCoop"
       CreateShortCut "$SMPROGRAMS\GestCoop\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
       CreateShortCut "$SMPROGRAMS\GestCoop\GestCoop.lnk" "$INSTDIR\GestCoop.exe" "" "$INSTDIR\GestCoop.exe" 0
       
     SectionEnd

     Section "Uninstall"
    
     ; Remover las claves del Registro
     DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\GestCoop"
     DeleteRegKey HKLM SOFTWARE\GestCoop
     
       ; Eliminados los archivos y los desinstalamos
       Delete $INSTDIR\GestCoop.jar
       Delete $INSTDIR\GestCoop.exe
       Delete $INSTDIR\uninstall.exe
       Delete "$INSTDIR\GestCoop\reportsPackage\*.*"
     
       ; Eliminamos todos los accesos directos del menú de inicio
       Delete "$SMPROGRAMS\GestCoop\*.*"
     
       ; Eliminamos las carpetas creadas
       RMDir "$SMPROGRAMS\GestCoop"
       RMDir "$INSTDIR\reportsPackage"
       RMDir "$INSTDIR"
     
     SectionEnd
