# Instalador de Java 21 SDK para GestCoop

Este directorio contiene scripts para descargar e instalar automáticamente Java 21 SDK en su equipo.

## ?? Archivos Incluidos

- `Install-Java21-SDK.ps1` - Script principal de PowerShell
- `Instalar-Java21.bat` - Archivo por lotes para ejecución fácil
- `README-Java21-Installer.md` - Este archivo de documentación

## ?? Uso Rápido

### Opción 1: Ejecutar el archivo .bat (Más fácil)
1. Haga doble clic en `Instalar-Java21.bat`
2. Siga las instrucciones en pantalla

### Opción 2: Ejecutar el script de PowerShell
```powershell
.\Install-Java21-SDK.ps1
```

### Opción 3: Con parámetros personalizados
```powershell
# Instalar en ubicación personalizada
.\Install-Java21-SDK.ps1 -InstallPath "C:\MiJava\jdk21"

# Solo descargar, sin configurar variables de entorno
.\Install-Java21-SDK.ps1 -SetJavaHome:$false -UpdatePath:$false

# Mostrar información detallada
.\Install-Java21-SDK.ps1 -Verbose
```

## ?? Parámetros Disponibles

| Parámetro | Tipo | Default | Descripción |
|-----------|------|---------|-------------|
| `-InstallPath` | String | `C:\Development\Java\jdk-21` | Directorio donde instalar Java |
| `-SetJavaHome` | Switch | `$true` | Configurar variable JAVA_HOME |
| `-UpdatePath` | Switch | `$true` | Actualizar variable PATH del sistema |
| `-Verbose` | Switch | `$false` | Mostrar información detallada |

## ?? Lo que hace el script

1. **Verifica** si Java 21 ya está instalado
2. **Descarga** Microsoft OpenJDK 21 desde el sitio oficial
3. **Extrae** los archivos en el directorio especificado
4. **Configura** las variables de entorno JAVA_HOME y PATH
5. **Verifica** que la instalación fue exitosa

## ?? Requisitos

- ? Windows 10/11
- ? PowerShell 5.0 o superior
- ? Conexión a Internet
- ? Privilegios de administrador (recomendado)

## ??? Privilegios de Administrador

- **Con privilegios**: Configura variables de entorno para todo el sistema
- **Sin privilegios**: Configura variables solo para el usuario actual

## ?? Casos de Uso

### Para desarrolladores
```powershell
# Instalación completa con configuración automática
.\Install-Java21-SDK.ps1
```

### Para múltiples versiones de Java
```powershell
# Instalar sin modificar PATH (mantener Java existente)
.\Install-Java21-SDK.ps1 -UpdatePath:$false -InstallPath "C:\Java\jdk-21-secondary"
```

### Para servidores/despliegue
```powershell
# Instalación silenciosa con verificación
.\Install-Java21-SDK.ps1 -InstallPath "C:\Java\jdk21" -Verbose
```

## ?? Verificación de la Instalación

Después de la instalación, puede verificar que Java 21 está correctamente instalado:

```cmd
java -version
javac -version
echo %JAVA_HOME%
```

## ?? Solución de Problemas

### Error de Política de Ejecución
```powershell
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope CurrentUser
```

### Java no reconocido después de la instalación
1. Reinicie su terminal/IDE
2. O ejecute: `$env:PATH = "C:\Development\Java\jdk-21\jdk-21.0.8\bin;" + $env:PATH`

### Error de permisos
- Ejecute como administrador
- O use `-SetJavaHome:$false -UpdatePath:$false` para instalación local

## ?? Notas Importantes

- El script descarga Microsoft OpenJDK 21, que es gratuito y compatible con Oracle JDK
- La instalación no interfiere con otras versiones de Java existentes (a menos que use `-UpdatePath`)
- Las variables de entorno se configuran automáticamente para facilitar el uso
- Se recomienda reiniciar el IDE/terminal después de la instalación

## ?? Soporte

Para problemas específicos de GestCoop, contacte al equipo de desarrollo.
Para problemas de Java, consulte la documentación oficial de Microsoft OpenJDK.