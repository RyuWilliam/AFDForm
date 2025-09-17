# 📝 AFD - Autómata Finito Determinista
## Aplicación Ejecutable Java

### 🚀 Ejecución

#### Método 1 - Script (Recomendado)
```bash
# Windows
ejecutar-afd.bat

# Linux/macOS
chmod +x ejecutar-afd.sh
./ejecutar-afd.sh
```

#### Método 2 - Comando directo
```bash
java -jar afd-1.0-SNAPSHOT.jar
```

### 📋 Requisitos del Sistema
- **Java 17 o superior** (incluye JavaFX en el JAR)
- Aproximadamente 10MB de espacio libre
- Sistema operativo: Windows, Linux, o macOS

### 🎯 Funcionalidades

#### 1. **Definición del Alfabeto** ✅ CORREGIDO
- **Formato correcto:** Ingresa los símbolos separados por comas: `a,b,c`
- Máximo 10 símbolos únicos
- Solo letras y números permitidos
- Ejemplo válido: `a,b,c` o `0,1` o `x,y,z`
- ❌ **NO usar:** `abc` (sin comas)
- ✅ **SÍ usar:** `a,b,c` (con comas)

#### 2. **Configuración de Estados**
- Define entre 2 y 15 estados
- Los estados se nombran automáticamente: q0, q1, q2...
- Selecciona el estado inicial (generalmente q0)
- Marca los estados finales

#### 3. **Matriz de Transición**
- Define las transiciones para cada estado y símbolo
- Todas las celdas deben completarse
- Estados destino deben existir en el AFD

#### 4. **Validación de Palabras**
- Prueba palabras individuales
- Generación automática de palabras válidas
- Visualización paso a paso del proceso

#### 5. **Persistencia**
- Guarda AFDs en formato JSON
- Carga AFDs previamente guardados
- Mantiene toda la configuración

### 🐛 **PROBLEMA RESUELTO**
**Versión anterior:** Aparecían comas (,) como símbolos extra en el alfabeto
**Solución aplicada:** Corrección en el procesamiento del alfabeto para manejar correctamente la entrada separada por comas

### 💡 Ejemplos de Uso

#### Alfabeto binario:
```
Entrada: 0,1
Resultado: Alfabeto {0, 1}
```

#### Alfabeto con letras:
```
Entrada: a,b,c
Resultado: Alfabeto {a, b, c}
```

### 🔧 Resolución de Problemas

#### Error: "Símbolo adicional en matriz"
- **Causa:** Formato incorrecto del alfabeto
- **Solución:** Usar comas para separar símbolos: `a,b,c`

#### Error: Java no encontrado
```bash
# Verificar Java
java -version

# Descargar desde: https://www.oracle.com/java/technologies/downloads/
```

#### Error: JavaFX no encontrado
- No debería ocurrir (JavaFX incluido en JAR)
- Si persiste, verificar Java 17+

### 📊 Información Técnica
- **Tamaño del JAR:** ~9MB (incluye JavaFX)
- **Algoritmo:** BFS para generación de palabras
- **Formato de archivos:** JSON personalizado
- **Interfaz:** JavaFX 21.0.1

### 📄 Archivos Incluidos
```
afd-1.0-SNAPSHOT.jar                    (EJECUTABLE PRINCIPAL)
afd-1.0-SNAPSHOT-jar-with-dependencies.jar (ALTERNATIVO)
ejecutar-afd.bat                        (Script Windows)
ejecutar-afd.sh                         (Script Linux/macOS)
README-EJECUTABLE.md                    (Esta documentación)
```

---
**Desarrollado por:** GitHub Copilot & William  
**Universidad:** UPTC  
**Fecha:** Septiembre 2025