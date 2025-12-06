# PowerShell script to fix Spanish character encoding
param([string]$FilePath)

# Define the encoding to read (ISO-8859-1) and write (UTF-8)
$sourceEncoding = [System.Text.Encoding]::GetEncoding("iso-8859-1")
$targetEncoding = [System.Text.Encoding]::UTF8

# Character replacement mappings
$replacements = @{
    # á characters
    [char]225 = "á"  # á in ISO-8859-1
    # é characters  
    [char]233 = "é"  # é in ISO-8859-1
    # í characters
    [char]237 = "í"  # í in ISO-8859-1
    # ó characters
    [char]243 = "ó"  # ó in ISO-8859-1
    # ú characters
    [char]250 = "ú"  # ú in ISO-8859-1
    # ñ characters
    [char]241 = "ñ"  # ñ in ISO-8859-1
    # ¿ character
    [char]191 = "¿"  # ¿ in ISO-8859-1
}

function Fix-SpanishCharacters {
    param([string]$InputFile)
    
    Write-Host "Processing: $InputFile"
    
    # Read the file with ISO-8859-1 encoding
    $content = [System.IO.File]::ReadAllText($InputFile, $sourceEncoding)
    
    # Apply character replacements
    $newContent = $content
    foreach ($key in $replacements.Keys) {
        $newContent = $newContent.Replace($key, $replacements[$key])
    }
    
    # Write back with UTF-8 encoding if changes were made
    if ($content -ne $newContent) {
        [System.IO.File]::WriteAllText($InputFile, $newContent, $targetEncoding)
        Write-Host "Fixed characters in: $(Split-Path $InputFile -Leaf)"
        return $true
    }
    
    return $false
}

# If a file path is provided, fix that file
if ($FilePath) {
    Fix-SpanishCharacters -InputFile $FilePath
} else {
    # Fix all Java files in src\winUIPackage
    $count = 0
    Get-ChildItem -Path "src\winUIPackage\*.java" | ForEach-Object {
        if (Fix-SpanishCharacters -InputFile $_.FullName) {
            $count++
        }
    }
    Write-Host "Total files fixed: $count"
}