#!/bin/bash

echo "========================================"
echo "   AFD - Autómata Finito Determinista"
echo "           Ejecutable Java"
echo "========================================"
echo

# Verificar si Java está instalado
if ! command -v java &> /dev/null; then
    echo "ERROR: Java no está instalado o no está en el PATH"
    echo
    echo "Instalación:"
    echo "- Ubuntu/Debian: sudo apt install openjdk-17-jre"
    echo "- macOS: brew install openjdk@17"
    echo "- O descarga desde: https://www.oracle.com/java/technologies/downloads/"
    echo
    read -p "Presiona Enter para continuar..."
    exit 1
fi

# Verificar versión de Java
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$java_version" -lt 17 ]; then
    echo "ADVERTENCIA: Se recomienda Java 17 o superior"
    echo "Versión actual: $(java -version 2>&1 | head -n 1)"
    echo
fi

echo "Iniciando aplicación AFD..."
echo

# Ejecutar el JAR
java -jar afd-1.0-SNAPSHOT.jar

echo
echo "La aplicación se ha cerrado."
read -p "Presiona Enter para continuar..."