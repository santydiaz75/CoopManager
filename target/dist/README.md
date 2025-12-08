# GestCoop - Sistema de Gestión Cooperativa

## ?? Contenido del paquete

Este directorio contiene todos los archivos necesarios para ejecutar GestCoop:

```
?? GestCoop/
??? ?? GestCoop.exe              # Ejecutable principal (recomendado)
??? ? GestCoop.jar              # Archivo JAR ejecutable
??? ?? Ejecutar_GestCoop.bat     # Script batch alternativo
??? ?? GestCoop_Launcher.ps1     # Script PowerShell (código fuente)
??? ?? GestCoop_lib/             # Librerías requeridas (47 archivos)
??? ?? README.md                 # Este archivo
```

## ?? Formas de ejecutar GestCoop

### 1. ?? Ejecutable Windows (Recomendado)
```
Doble clic en: GestCoop.exe
```
- **Ventajas**: Busca Java automáticamente, interfaz amigable
- **Requisitos**: Windows con Java 11+ instalado

### 2. ? JAR Ejecutable
```
java -jar GestCoop.jar
```
- **Ventajas**: Multiplataforma
- **Requisitos**: Java 21 recomendado (mínimo Java 11)

### 3. ?? Script Batch
```
Doble clic en: Ejecutar_GestCoop.bat
```
- **Ventajas**: Diagnósticos detallados
- **Requisitos**: Windows con Java instalado

## ?? Requisitos del sistema

- **Sistema Operativo**: Windows 7+ (recomendado Windows 10/11)
- **Java**: Versión 11 o superior (recomendado Java 21)
- **Memoria RAM**: Mínimo 2GB (recomendado 4GB+)
- **Espacio en disco**: 200MB libres
- **Conexión**: Acceso a internet para base de datos

## ?? Instalación de Java (si es necesaria)

Si no tiene Java instalado:

1. Visite: https://adoptium.net/
2. Descargue Java 21 LTS
3. Instale siguiendo el asistente
4. Reinicie el sistema

## ?? Solución de problemas

### Error: "No se encontró Java"
- Instale Java desde el enlace arriba
- Verifique que Java esté en el PATH
- Configure la variable JAVA_HOME

### Error: "Versión de Java incompatible"
- GestCoop requiere Java 11 como mínimo
- Se recomienda Java 21 para mejor rendimiento

### Error: "No se encontró GestCoop.jar"
- Mantenga todos los archivos en el mismo directorio
- No mueva GestCoop.exe sin la carpeta GestCoop_lib

### La aplicación se cierra inmediatamente
- Ejecute desde línea de comandos para ver errores
- Verifique la conexión a internet (base de datos remota)

## ?? Soporte

Para soporte técnico, conserve:
- Este archivo README.md
- Los mensajes de error completos
- Información de versión de Java: `java -version`

---

**GestCoop v1.0.2**  
*Sistema de Gestión Cooperativa*  
Compilado el: 8 de Diciembre, 2025  
? **VERIFICADO Y FUNCIONAL**