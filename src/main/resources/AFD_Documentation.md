# Documentación - Autómata Finito Determinista (AFD)

## ¿Qué es un AFD?

Un Autómata Finito Determinista (AFD) es un modelo matemático utilizado en ciencias de la computación para reconocer patrones en cadenas de caracteres. 

## Definición formal

Un AFD se define como una 5-tupla: **M = (Q, Σ, δ, q0, F)**

Donde:
- **Q**: Conjunto finito de estados
- **Σ**: Alfabeto de entrada (conjunto finito de símbolos)
- **δ**: Función de transición (Q × Σ → Q)
- **q0**: Estado inicial (q0 ∈ Q)
- **F**: Conjunto de estados finales o de aceptación (F ⊆ Q)

## Características

1. **Determinista**: Para cada estado y símbolo de entrada, existe exactamente una transición.
2. **Finito**: Tiene un número finito de estados.
3. **Acepta o rechaza**: Una palabra es aceptada si al procesarla se termina en un estado final.

## Ejemplos comunes

### Ejemplo 1: AFD que acepta cadenas que terminan en "01"
- Alfabeto: {0, 1}
- Estados: {q0, q1, q2}
- Estado inicial: q0
- Estados finales: {q2}

### Ejemplo 2: AFD que acepta números binarios divisibles por 3
- Alfabeto: {0, 1}
- Estados: {q0, q1, q2} (restos 0, 1, 2)
- Estado inicial: q0
- Estados finales: {q0}

## Uso de la aplicación

1. **Definir alfabeto**: Especifica los símbolos que puede procesar el AFD
2. **Crear estados**: Define cuántos estados tendrá tu AFD
3. **Estado inicial**: Selecciona desde dónde empezará el procesamiento
4. **Estados finales**: Marca cuáles estados indican aceptación
5. **Matriz de transiciones**: Define cómo se mueve entre estados
6. **Probar palabras**: Verifica si una cadena es aceptada o rechazada