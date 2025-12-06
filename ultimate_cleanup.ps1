# Ultimate cleanup script for all Java files with encoding issues
Write-Host "Starting comprehensive cleanup of all Java files..."

# Get all Java files in winUIPackage
$javaFiles = Get-ChildItem "src/winUIPackage" -Filter "*.java"

foreach ($file in $javaFiles) {
    try {
        Write-Host "Processing: $($file.Name)"
        
        # Read as raw bytes
        $bytes = [System.IO.File]::ReadAllBytes($file.FullName)
        
        # Skip empty files
        if ($bytes.Length -eq 0) {
            Write-Host "  Skipping empty file"
            continue
        }
        
        # Remove BOM sequences at start
        while ($bytes.Length -ge 3 -and (
            ($bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) -or  # UTF-8 BOM
            ($bytes[0] -eq 0xC2 -and $bytes[1] -eq 0xBB) -or                          # »
            ($bytes[0] -eq 0xC2 -and $bytes[1] -eq 0xBF)                              # ¿
        )) {
            if ($bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
                $bytes = $bytes[3..($bytes.Length-1)]
                Write-Host "  Removed UTF-8 BOM"
            } elseif ($bytes[0] -eq 0xC2) {
                $bytes = $bytes[2..($bytes.Length-1)]
                Write-Host "  Removed problematic C2 sequence"
            }
        }
        
        # Convert to string
        $content = [System.Text.Encoding]::UTF8.GetString($bytes)
        
        # Clean problematic characters
        $originalLength = $content.Length
        $content = $content -replace [char]0x00BB, ""  # »
        $content = $content -replace [char]0x00BF, ""  # ¿
        $content = $content -replace [char]0xFEFF, ""  # BOM
        $content = $content -replace [char]0x00C2, ""  # Â
        $content = $content -replace "^\s*»\s*", ""    # Remove » at start of lines
        $content = $content -replace "^\s*¿\s*", ""    # Remove ¿ at start of lines
        
        # Additional cleanup for any non-printable characters at start of file
        $content = $content -replace "^[^\x20-\x7E\r\n\t]*", ""
        
        if ($content.Length -ne $originalLength) {
            Write-Host "  Removed $($originalLength - $content.Length) problematic characters"
        }
        
        # Ensure file starts correctly
        if (-not $content.StartsWith("/*") -and -not $content.StartsWith("package")) {
            # Find first valid Java content
            $lines = $content -split "`r?`n"
            $firstValidLine = -1
            for ($i = 0; $i -lt $lines.Length; $i++) {
                if ($lines[$i].Trim() -match "^(package|import|/\*|public|class)" -or $lines[$i].Trim().StartsWith("//")) {
                    $firstValidLine = $i
                    break
                }
            }
            
            if ($firstValidLine -gt 0) {
                $content = ($lines[$firstValidLine..($lines.Length-1)] -join "`r`n")
                Write-Host "  Removed $firstValidLine invalid lines from start"
            }
        }
        
        # Write back as UTF-8 without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
        Write-Host "  Cleaned successfully"
        
    } catch {
        Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "Cleanup complete!" -ForegroundColor Green