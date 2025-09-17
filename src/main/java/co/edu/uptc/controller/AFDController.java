package co.edu.uptc.controller;

import co.edu.uptc.model.*;
import java.util.Set;
import java.io.File;
import java.io.IOException;

/**
 * Controlador para manejar la lógica de construcción y manipulación del AFD
 */
public class AFDController {
    private AFD currentAFD;
    private AFDFileController fileController;
    
    public AFDController() {
        this.currentAFD = new AFD();
        this.fileController = new AFDFileController();
    }
    
    /**
     * Define el alfabeto del AFD a partir de una cadena de símbolos separados por comas
     * @param symbolsString cadena con los símbolos del alfabeto separados por comas (ej: "a,b,c")
     * @return true si se definió correctamente, false si hay error
     */
    public boolean defineAlphabet(String symbolsString) {
        if (symbolsString == null || symbolsString.trim().isEmpty()) {
            return false;
        }
        
        try {
            currentAFD = new AFD();
            currentAFD.getAlphabet().clear();
            
            // Separar por comas y procesar cada símbolo
            String[] symbols = symbolsString.trim().split(",");
            for (String symbol : symbols) {
                String trimmedSymbol = symbol.trim();
                if (!trimmedSymbol.isEmpty() && trimmedSymbol.length() == 1) {
                    currentAFD.getAlphabet().addSymbol(trimmedSymbol.charAt(0));
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene el tamaño del alfabeto actual
     * @return número de símbolos en el alfabeto, 0 si no está definido
     */
    public int getAlphabetSize() {
        if (currentAFD.getAlphabet() == null) {
            return 0;
        }
        return currentAFD.getAlphabet().getSymbols().size();
    }
    
    /**
     * Define la cantidad de estados y los crea
     * @param numStates número de estados
     * @return true si se crearon correctamente
     */
    public boolean defineStates(int numStates) {
        if (numStates <= 0) {
            return false;
        }
        
        try {
            // Limpiar estados anteriores
            currentAFD.getStates().clear();
            currentAFD.getFinalStates().clear();
            
            // Crear estados q0, q1, q2, ...
            for (int i = 0; i < numStates; i++) {
                State state = new State("q" + i);
                currentAFD.addState(state);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Establece el estado inicial
     * @param stateName nombre del estado inicial
     * @return true si se estableció correctamente
     */
    public boolean setInitialState(String stateName) {
        try {
            for (State state : currentAFD.getStates()) {
                if (state.getName().equals(stateName)) {
                    currentAFD.setInitialState(state);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Establece los estados finales
     * @param finalStateNames conjunto de nombres de estados finales
     * @return true si se establecieron correctamente
     */
    public boolean setFinalStates(Set<String> finalStateNames) {
        try {
            // Limpiar estados finales anteriores
            for (State state : currentAFD.getStates()) {
                state.setFinal(false);
            }
            currentAFD.getFinalStates().clear();
            
            // Establecer nuevos estados finales
            for (String stateName : finalStateNames) {
                for (State state : currentAFD.getStates()) {
                    if (state.getName().equals(stateName)) {
                        currentAFD.addFinalState(state);
                        break;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Añade una transición al AFD
     * @param fromState estado origen
     * @param symbol símbolo
     * @param toState estado destino
     * @return true si se añadió correctamente
     */
    public boolean addTransition(String fromState, char symbol, String toState) {
        try {
            State from = null;
            State to = null;
            
            // Buscar estados
            for (State state : currentAFD.getStates()) {
                if (state.getName().equals(fromState)) {
                    from = state;
                }
                if (state.getName().equals(toState)) {
                    to = state;
                }
            }
            
            if (from != null && to != null && currentAFD.getAlphabet().contains(symbol)) {
                currentAFD.addTransition(from, symbol, to);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Procesa una palabra en el AFD
     * @param word palabra a procesar
     * @return resultado del procesamiento
     */
    public AFD.AFDResult processWord(String word) {
        if (word == null) {
            return new AFD.AFDResult(false, "Palabra nula", null);
        }
        return currentAFD.processString(word);
    }
    
    /**
     * Verifica si el AFD está completo y válido
     * @return true si el AFD está completo
     */
    public boolean isAFDComplete() {
        return currentAFD.isValid();
    }
    
    /**
     * Obtiene el AFD actual
     * @return AFD actual
     */
    public AFD getCurrentAFD() {
        return currentAFD;
    }
    
    /**
     * Obtiene información del alfabeto actual
     * @return cadena con los símbolos del alfabeto
     */
    public String getAlphabetInfo() {
        if (currentAFD.getAlphabet().isEmpty()) {
            return "No definido";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (char symbol : currentAFD.getAlphabet().getSymbols()) {
            if (!first) sb.append(", ");
            sb.append(symbol);
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Obtiene información de los estados
     * @return cadena con los estados
     */
    public String getStatesInfo() {
        if (currentAFD.getStates().isEmpty()) {
            return "No definidos";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (State state : currentAFD.getStates()) {
            if (!first) sb.append(", ");
            sb.append(state.getName());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Obtiene información del estado inicial
     * @return nombre del estado inicial o "No definido"
     */
    public String getInitialStateInfo() {
        return currentAFD.getInitialState() != null ? 
               currentAFD.getInitialState().getName() : "No definido";
    }
    
    /**
     * Obtiene información de los estados finales
     * @return cadena con los estados finales
     */
    public String getFinalStatesInfo() {
        if (currentAFD.getFinalStates().isEmpty()) {
            return "No definidos";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (State state : currentAFD.getFinalStates()) {
            if (!first) sb.append(", ");
            sb.append(state.getName());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Genera las primeras N cadenas más cortas aceptadas por el AFD
     * @param maxWords número máximo de palabras a generar
     * @return lista de cadenas ordenadas por longitud
     */
    public java.util.List<String> generateShortestValidWords(int maxWords) {
        java.util.List<String> validWords = new java.util.ArrayList<>();
        
        if (!isAFDComplete()) {
            return validWords;
        }
        
        // BFS para encontrar las cadenas más cortas
        java.util.Queue<WordCandidate> queue = new java.util.LinkedList<>();
        java.util.Set<String> visited = new java.util.HashSet<>();
        
        // Empezar con la cadena vacía
        queue.offer(new WordCandidate("", currentAFD.getInitialState()));
        
        Character[] symbols = currentAFD.getAlphabet().getSymbols().toArray(new Character[0]);
        char[] alphabetArray = new char[symbols.length];
        for (int i = 0; i < symbols.length; i++) {
            alphabetArray[i] = symbols[i];
        }
        
        while (!queue.isEmpty() && validWords.size() < maxWords) {
            WordCandidate current = queue.poll();
            String word = current.getWord();
            State state = current.getState();
            
            // Si ya visitamos esta combinación palabra-estado, continuamos
            String key = word + "|" + state.getName();
            if (visited.contains(key)) {
                continue;
            }
            visited.add(key);
            
            // Si estamos en un estado final, añadir la palabra
            if (currentAFD.getFinalStates().contains(state) && !validWords.contains(word)) {
                validWords.add(word);
            }
            
            // Evitar palabras muy largas para prevenir bucles infinitos
            if (word.length() < 15) {
                // Generar nuevas palabras añadiendo cada símbolo del alfabeto
                for (char symbol : alphabetArray) {
                    State nextState = currentAFD.getTransitions().getNextState(state, symbol);
                    if (nextState != null) {
                        String newWord = word + symbol;
                        queue.offer(new WordCandidate(newWord, nextState));
                    }
                }
            }
        }
        
        // Ordenar por longitud (las más cortas primero)
        validWords.sort((a, b) -> {
            if (a.length() != b.length()) {
                return Integer.compare(a.length(), b.length());
            }
            return a.compareTo(b); // Orden lexicográfico para palabras de igual longitud
        });
        
        // Retornar solo las primeras maxWords
        return validWords.subList(0, Math.min(validWords.size(), maxWords));
    }
    
    /**
     * Clase auxiliar para el algoritmo BFS de generación de palabras
     */
    private static class WordCandidate {
        private final String word;
        private final State state;
        
        public WordCandidate(String word, State state) {
            this.word = word;
            this.state = state;
        }
        
        public String getWord() {
            return word;
        }
        
        public State getState() {
            return state;
        }
    }
    
    /**
     * Guarda el AFD actual en un archivo
     * @param file archivo donde guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean saveAFD(java.io.File file) {
        try {
            fileController.saveAFD(currentAFD, file);
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }
    
    /**
     * Carga un AFD desde un archivo
     * @param file archivo desde donde cargar
     * @return true si se cargó correctamente, false en caso contrario
     */
    public boolean loadAFD(java.io.File file) {
        try {
            currentAFD = fileController.loadAFD(file);
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }
    
    /**
     * Obtiene el mensaje de error del último intento de cargar/guardar
     * @param file archivo que causó el error
     * @param isLoading true si el error fue al cargar, false si fue al guardar
     * @return mensaje de error
     */
    public String getLastFileError(java.io.File file, boolean isLoading) {
        try {
            if (isLoading) {
                fileController.loadAFD(file);
            } else {
                fileController.saveAFD(currentAFD, file);
            }
            return null;
        } catch (java.io.IOException e) {
            return e.getMessage();
        }
    }
}