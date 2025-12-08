$env:JAVA_HOME = "C:\Users\santy\.jdk\jdk-21.0.8"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

Write-Host "Starting GestCoop Application..." -ForegroundColor Green
try {
    $javaArgs = @(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", 
        "--add-opens", "java.base/java.io=ALL-UNNAMED",
        "--add-opens", "java.sql/java.sql=ALL-UNNAMED",
        "-cp", "GestCoop.jar;GestCoop_lib\*",
        "-Dfile.encoding=ISO-8859-1",
        "winUIPackage.Launcher"
    )
    & java $javaArgs
} catch {
    Write-Host "Error running the application" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
}
Read-Host "Press Enter to continue"
