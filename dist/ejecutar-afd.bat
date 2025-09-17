@echo off
title AFD - Autómata Finito Determinista

echo ========================================
echo    AFD - Autómata Finito Determinista
echo           Ejecutable Java
echo ========================================
echo.

REM Verificar si Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo.
    echo Descarga Java desde: https://www.oracle.com/java/technologies/downloads/
    echo Se recomienda Java 17 o superior
    echo.
    pause
    exit /b 1
)

echo Iniciando aplicación AFD...
echo.

REM Ejecutar el JAR
java -jar afd-1.0-SNAPSHOT.jar

echo.
echo La aplicación se ha cerrado.
pause