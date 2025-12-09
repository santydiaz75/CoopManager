# Test script to run GestCoop with Java 21
$ErrorActionPreference = "Stop"

Write-Host "=== TESTING GESTCOOP WITH JAVA 21 ===" -ForegroundColor Cyan

# Set Java 21 environment
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "C:\Program Files\Java\jdk-21\bin;" + $env:PATH

Write-Host "Java version:" -ForegroundColor Yellow
java -version

Write-Host "`nJAVA_HOME: $env:JAVA_HOME" -ForegroundColor Yellow

# Navigate to target\dist
Set-Location target\dist
Write-Host "`nCurrent location: $(Get-Location)" -ForegroundColor Yellow

# Check if files exist
Write-Host "`nFiles in target\dist:" -ForegroundColor Yellow
Get-ChildItem -Name | ForEach-Object { Write-Host "  $_" }

# Test JAR directly
Write-Host "`nTesting JAR directly..." -ForegroundColor Green
try {
    Start-Process -FilePath "java" -ArgumentList "-jar GestCoop.jar" -WindowStyle Hidden
    Write-Host "JAR started successfully!" -ForegroundColor Green
    
    Start-Sleep -Seconds 3
    
    # Check for Java processes
    $javaProcesses = Get-Process java -ErrorAction SilentlyContinue
    if ($javaProcesses) {
        Write-Host "`nJava processes running:" -ForegroundColor Green
        $javaProcesses | Select-Object Id, ProcessName, StartTime | Format-Table
    } else {
        Write-Host "`nNo Java processes found" -ForegroundColor Red
    }
    
} catch {
    Write-Host "Error starting JAR: $($_.Exception.Message)" -ForegroundColor Red
}

# Check log file
if (Test-Path "GestCoop.log") {
    Write-Host "`nLatest log entries:" -ForegroundColor Yellow
    Get-Content "GestCoop.log" -Tail 5
} else {
    Write-Host "`nNo log file found" -ForegroundColor Yellow
}

Write-Host "`n=== TEST COMPLETED ===" -ForegroundColor Cyan