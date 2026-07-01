@echo off
setlocal enabledelayedexpansion

echo =============================================
echo   LandShop Mod - Direct Build (No Gradle)
echo =============================================
echo.

set "JAVAC=C:\Program Files\Java\jdk-26.0.1\bin\javac.exe"
set "JAR_TOOL=C:\Program Files\Java\jdk-26.0.1\bin\jar.exe"
set "SERVER_DIR=g:\FabricServer"
set "MOD_DIR=%SERVER_DIR%\land-shop-mod"
set "BUILD_DIR=%MOD_DIR%\build"
set "CLASSES_DIR=%BUILD_DIR%\classes"
set "OUTPUT_DIR=%BUILD_DIR%\libs"

REM Clean previous build
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
mkdir "%CLASSES_DIR%"
mkdir "%OUTPUT_DIR%"

REM Build classpath from server JARs
set "CP="
set "CP=%CP%;%SERVER_DIR%\versions\26.1.2\server-26.1.2.jar"
set "CP=%CP%;%SERVER_DIR%\libraries\net\fabricmc\fabric-loader\0.19.3\fabric-loader-0.19.3.jar"
set "CP=%CP%;%SERVER_DIR%\libraries\org\slf4j\slf4j-api\2.0.17\slf4j-api-2.0.17.jar"
set "CP=%CP%;%SERVER_DIR%\libraries\com\mojang\brigadier\1.3.10\brigadier-1.3.10.jar"
set "CP=%CP%;%SERVER_DIR%\libraries\com\mojang\authlib\9.0.75\authlib-9.0.75.jar"
set "CP=%CP%;%SERVER_DIR%\libraries\org\jspecify\jspecify\1.0.0\jspecify-1.0.0.jar"
set "CP=%CP%;%SERVER_DIR%\libraries\it\unimi\dsi\fastutil\8.5.18\fastutil-8.5.18.jar"

REM Add Fabric API modules from processed mods
for %%f in ("%SERVER_DIR%\.fabric\processedMods\fabric-command-api-v2-*.jar") do set "CP=!CP!;%%f"
for %%f in ("%SERVER_DIR%\.fabric\processedMods\fabric-api-base-*.jar") do set "CP=!CP!;%%f"
for %%f in ("%SERVER_DIR%\.fabric\processedMods\fabric-lifecycle-events-v1-*.jar") do set "CP=!CP!;%%f"

echo Classpath: %CP%
echo.

REM Compile
echo [1/3] Compiling AllahSunshineMod.java...
if not exist "build\classes\java\main\com\allah" mkdir "build\classes\java\main\com\allah"
"%JAVAC%" -d "build\classes\java\main" -cp "%CP%" "src\main\java\com\allah\AllahSunshineMod.java"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo COMPILATION FAILED
    pause
    exit /b %errorlevel%
)

echo [2/3] Packaging JAR...
if not exist "build\libs" mkdir "build\libs"
"%JAR_TOOL%" cf "build\libs\allah-sunshine-1.0.0.jar" -C "build\classes\java\main" . -C "src\main\resources" .

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo PACKAGING FAILED
    pause
    exit /b %errorlevel%
)

echo [3/3] Installing to mods folder...
copy /Y "build\libs\allah-sunshine-1.0.0.jar" "..\mods\"

echo.
echo =============================================
echo   BUILD SUCCESSFUL
echo   Output: %CD%\build\libs\allah-sunshine-1.0.0.jar
echo   Installed to: %SERVER_DIR%\mods\
echo   Restart your server to load the mod.
echo =============================================
echo.
pause
