# PowerShell script to run CoopManager with Java 21 LTS and Maven
# Set environment variables
$env:JAVA_HOME = "C:\Users\santy\.jdk\jdk-21.0.8"
$env:PATH = "C:\Users\santy\.jdk\jdk-21.0.8\bin;C:\Users\santy\.maven\maven-3.9.11(2)\bin;" + $env:PATH

Write-Host "Using Java 21 LTS for CoopManager" -ForegroundColor Green
java -version
Write-Host ""

# Clean and compile the project
Write-Host "Cleaning and compiling with Java 21..." -ForegroundColor Yellow
mvn clean compile

if ($LASTEXITCODE -eq 0) {
    # Run the application
    Write-Host "Starting CoopManager with Java 21..." -ForegroundColor Green
    mvn exec:java
} else {
    Write-Host "Compilation failed. Please check the errors above." -ForegroundColor Red
}

Read-Host "Press Enter to continue..."