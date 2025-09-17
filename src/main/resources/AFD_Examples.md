# Ejemplos de AFD Paso a Paso

## Ejemplo 1: AFD que acepta cadenas que terminan en "01"

### Paso 1: Definir el alfabeto
- Alfabeto: `01` (símbolos: 0, 1)

### Paso 2: Crear estados
- Cantidad de estados: 3
- Estados creados: q0, q1, q2

### Paso 3: Estado inicial
- Estado inicial: q0

### Paso 4: Estados finales
- Estados finales: q2

### Paso 5: Matriz de transiciones
```
     | 0  | 1  |
-----|----|----|
 q0  | q1 | q0 |
 q1  | q1 | q2 |
 q2  | q1 | q0 |
```

### Pruebas
- "01" → ACEPTADA (q0 -0→ q1 -1→ q2)
- "101" → ACEPTADA (q0 -1→ q0 -0→ q1 -1→ q2)
- "11" → RECHAZADA (q0 -1→ q0 -1→ q0)
- "010" → RECHAZADA (q0 -0→ q1 -1→ q2 -0→ q1)

---

## Ejemplo 2: AFD que acepta números binarios pares

### Paso 1: Definir el alfabeto
- Alfabeto: `01` (símbolos: 0, 1)

### Paso 2: Crear estados
- Cantidad de estados: 2
- Estados creados: q0, q1

### Paso 3: Estado inicial
- Estado inicial: q0

### Paso 4: Estados finales
- Estados finales: q0 (representa números pares)

### Paso 5: Matriz de transiciones
```
     | 0  | 1  |
-----|----|----|
 q0  | q0 | q1 |
 q1  | q0 | q1 |
```

### Pruebas
- "0" → ACEPTADA (par)
- "10" → ACEPTADA (2 en decimal)
- "100" → ACEPTADA (4 en decimal)
- "1" → RECHAZADA (impar)
- "11" → RECHAZADA (3 en decimal)

---

## Ejemplo 3: AFD que acepta cadenas con número par de a's

### Paso 1: Definir el alfabeto
- Alfabeto: `ab` (símbolos: a, b)

### Paso 2: Crear estados
- Cantidad de estados: 2
- Estados creados: q0, q1

### Paso 3: Estado inicial
- Estado inicial: q0

### Paso 4: Estados finales
- Estados finales: q0 (número par de a's, incluyendo 0)

### Paso 5: Matriz de transiciones
```
     | a  | b  |
-----|----|----|
 q0  | q1 | q0 |
 q1  | q0 | q1 |
```

### Pruebas
- "" → ACEPTADA (0 a's = par)
- "b" → ACEPTADA (0 a's = par)
- "aa" → ACEPTADA (2 a's = par)
- "abab" → ACEPTADA (2 a's = par)
- "a" → RECHAZADA (1 a = impar)
- "aba" → RECHAZADA (2 a's pero termina en impar)
- "aaa" → RECHAZADA (3 a's = impar)

## Consejos para crear AFD

1. **Planifica primero**: Antes de usar la aplicación, dibuja el AFD en papel
2. **Estados mínimos**: Usa solo los estados necesarios
3. **Transiciones completas**: Asegúrate de definir transiciones para todos los símbolos desde todos los estados
4. **Prueba casos extremos**: Cadena vacía, un solo carácter, cadenas largas
5. **Verifica la lógica**: El AFD debe reflejar exactamente el patrón que quieres reconocer