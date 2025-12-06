# Fix ALL Java files with encoding problems
Write-Host "Finding all Java files in winUIPackage..."
$javaFiles = Get-ChildItem "src/winUIPackage" -Filter "*.java" -Recurse

foreach ($file in $javaFiles) {
    try {
        Write-Host "Processing $($file.FullName)"
        
        # Read as raw bytes
        $bytes = [System.IO.File]::ReadAllBytes($file.FullName)
        
        # Check for BOM or problematic characters at the start
        if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
            Write-Host "  Found UTF-8 BOM, removing..."
            $bytes = $bytes[3..($bytes.Length-1)]
        }
        
        # Check for other problematic byte sequences
        $problematicFound = $false
        if ($bytes.Length -ge 2) {
            if (($bytes[0] -eq 0xC2 -and $bytes[1] -eq 0xBB) -or ($bytes[0] -eq 0xC2 -and $bytes[1] -eq 0xBF)) {
                Write-Host "  Found problematic UTF-8 sequence at start, removing..."
                $bytes = $bytes[2..($bytes.Length-1)]
                $problematicFound = $true
            }
        }
        
        # Convert to string and clean
        $content = [System.Text.Encoding]::UTF8.GetString($bytes)
        
        # Remove any remaining problematic characters
        $originalLength = $content.Length
        $content = $content -replace [char]0x00BB, ""  # »
        $content = $content -replace [char]0x00BF, ""  # ¿
        $content = $content -replace [char]0xFEFF, ""  # BOM
        $content = $content -replace [char]0x00C2, ""  # Â
        
        if ($content.Length -ne $originalLength) {
            $problematicFound = $true
            Write-Host "  Removed problematic Unicode characters"
        }
        
        # Write back as UTF-8 without BOM
        if ($problematicFound -or $content.Length -ne $originalLength) {
            $utf8NoBom = New-Object System.Text.UTF8Encoding $false
            [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
            Write-Host "  Fixed: $($file.Name)"
        } else {
            Write-Host "  Clean: $($file.Name)"
        }
    }
    catch {
        Write-Host "  Error processing $($file.Name): $($_.Exception.Message)"
    }
}

Write-Host "Done processing all Java files in winUIPackage"