# ?? Guía de Resolución de Problemas - GestCoop

## ?? **Problema Resuelto**
El error "El estado del motor ha cambiado de Available a Stopped" ha sido identificado y solucionado.

### ?? **Diagnóstico del Problema**
- **Causa**: El launcher original generado por ps2exe tenía incompatibilidades con ciertas configuraciones de PowerShell
- **Síntoma**: El ejecutable se iniciaba pero se cerraba inmediatamente sin mensaje de error visible
- **Solución**: Se ha creado un launcher mejorado y versiones alternativas

## ? **Soluciones Disponibles**

### **1. Ejecutable Principal (Recomendado)**
```
target\dist\GestCoop.exe
```
- **Estado**: ? Funciona correctamente
- **Características**: Instalación automática de Java 21, logging mejorado
- **Uso**: Hacer doble clic en el archivo

### **2. Launcher Standalone (Alternativo)**
```
target\dist\GestCoop_Standalone.ps1
```
- **Estado**: ? Funciona perfectamente 
- **Características**: Versión completa sin conversión ps2exe, debugging detallado
- **Uso**: 
  ```powershell
  Set-ExecutionPolicy Bypass -Scope Process -Force
  .\GestCoop_Standalone.ps1
  ```

### **3. Launcher PowerShell (Manual)**
```
target\dist\GestCoop_Launcher.ps1
```
- **Estado**: ? Disponible para troubleshooting
- **Uso**: 
  ```powershell
  Set-ExecutionPolicy Bypass -Scope Process -Force
  .\GestCoop_Launcher.ps1
  ```

## ?? **Script de Compilación Mejorado**

### **Nuevas Características Implementadas:**

#### ?? **Instalación Automática de Java 21**
- Detecta automáticamente si Java 21 está instalado
- Si no está disponible, descarga e instala OpenJDK 21 desde Microsoft
- Instalación silenciosa en `C:\Users\%USERNAME%\.jdk\jdk-21.0.8`
- Verificación de versión automática

#### ?? **Sistema de Logging Completo**
- **Log de compilación**: `GestCoop.log` (en directorio del script)
- **Log del ejecutable**: `GestCoop.log` (en directorio de distribución)
- Rotación automática de logs (mantiene últimas 1000 líneas)
- Logging de errores con stack trace completo

#### ??? **Manejo Robusto de Errores**
- Detección inteligente de problemas de instalación
- Mensajes de error detallados con información de debugging
- Recuperación automática de errores comunes
- Validación de permisos y dependencias

## ?? **Cómo Usar en Máquinas Nuevas**

### **Opción 1: Usar el Ejecutable (Más Fácil)**
1. Copiar toda la carpeta `target\dist` a la máquina de destino
2. Hacer doble clic en `GestCoop.exe`
3. Si Java 21 no está instalado, se instalará automáticamente
4. La aplicación se iniciará automáticamente

### **Opción 2: Usar el Launcher Standalone**
1. Copiar toda la carpeta `target\dist` a la máquina de destino
2. Abrir PowerShell como administrador
3. Navegar al directorio: `cd "ruta\a\target\dist"`
4. Ejecutar: 
   ```powershell
   Set-ExecutionPolicy Bypass -Scope Process -Force
   .\GestCoop_Standalone.ps1
   ```

## ?? **Herramientas de Diagnóstico**

### **Script de Diagnóstico Automatizado**
```powershell
.\debug_launcher.ps1 -GestCoopPath "target\dist"
```

**Este script verifica:**
- ? Instalación de Java y versión
- ? Presencia de archivos requeridos (JAR, ejecutable, dependencias)
- ? Permisos de directorio
- ? Funcionamiento del JAR directamente
- ? Funcionamiento del ejecutable PowerShell

### **Revisar Logs de Errores**
```powershell
# Log principal del script
Get-Content GestCoop.log -Tail 20

# Log del launcher standalone
Get-Content GestCoop_Standalone.log -Tail 20

# Verificar procesos Java
Get-Process java -ErrorAction SilentlyContinue
```

## ?? **Requisitos del Sistema**

### **Automáticamente Instalados:**
- ? Java 21 (se instala automáticamente si no existe)

### **Requeridos:**
- ? Windows 10/11 o Windows Server 2016+
- ? PowerShell 5.1+ (incluido en Windows)
- ? Conexión a Internet (solo para instalación inicial de Java)
- ? Permisos de escritura en directorio de la aplicación

### **Opcionales:**
- ps2exe (solo para recompilar el proyecto)
- Maven (solo para recompilar el proyecto)

## ?? **Resultado**
El problema original está **completamente resuelto**. El ejecutable ahora:
- ? Se inicia correctamente en máquinas nuevas
- ? Instala Java automáticamente si no está presente
- ? Proporciona logging detallado para troubleshooting
- ? Tiene múltiples versiones de backup para diferentes escenarios
- ? Incluye herramientas de diagnóstico automático

## ?? **Soporte**
Si encuentra problemas, ejecute primero el script de diagnóstico:
```powershell
.\debug_launcher.ps1 -GestCoopPath "target\dist"
```

Los logs estarán disponibles en:
- `GestCoop.log` (ejecutable principal)
- `GestCoop_Standalone.log` (versión standalone)