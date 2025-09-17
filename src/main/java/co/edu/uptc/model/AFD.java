package co.edu.uptc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un Autómata Finito Determinista (AFD)
 * Un AFD está definido por la 5-tupla: (Q, Σ, δ, q0, F)
 * - Q: conjunto finito de estados
 * - Σ: alfabeto de entrada
 * - δ: función de transición
 * - q0: estado inicial
 * - F: conjunto de estados finales
 */
public class AFD {
    private Set<State> states;              // Q: conjunto de estados
    private Alphabet alphabet;             // Σ: alfabeto
    private TransitionFunction transitions; // δ: función de transición
    private State initialState;            // q0: estado inicial
    private Set<State> finalStates;        // F: estados finales
    
    /**
     * Constructor que inicializa un AFD vacío
     */
    public AFD() {
        this.states = new HashSet<>();
        this.alphabet = new Alphabet();
        this.transitions = new TransitionFunction();
        this.initialState = null;
        this.finalStates = new HashSet<>();
    }
    
    /**
     * Constructor completo para crear un AFD
     * @param states conjunto de estados
     * @param alphabet alfabeto
     * @param transitions función de transición
     * @param initialState estado inicial
     * @param finalStates conjunto de estados finales
     */
    public AFD(Set<State> states, Alphabet alphabet, TransitionFunction transitions, 
               State initialState, Set<State> finalStates) {
        this.states = new HashSet<>(states);
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = new HashSet<>(finalStates);
    }
    
    /**
     * Añade un estado al AFD
     * @param state estado a añadir
     * @return true si se añadió (no existía), false si ya existía
     */
    public boolean addState(State state) {
        boolean added = states.add(state);
        if (added) {
            if (state.isInitial()) {
                setInitialState(state);
            }
            if (state.isFinal()) {
                finalStates.add(state);
            }
        }
        return added;
    }
    
    /**
     * Elimina un estado del AFD
     * @param state estado a eliminar
     * @return true si se eliminó (existía), false si no existía
     */
    public boolean removeState(State state) {
        boolean removed = states.remove(state);
        if (removed) {
            if (state.equals(initialState)) {
                initialState = null;
            }
            finalStates.remove(state);
            // TODO: También deberíamos eliminar las transiciones que involucran este estado
        }
        return removed;
    }
    
    /**
     * Establece el estado inicial del AFD
     * @param state estado inicial
     */
    public void setInitialState(State state) {
        if (states.contains(state)) {
            // Marcar el estado anterior como no inicial
            if (initialState != null) {
                initialState.setInitial(false);
            }
            // Establecer el nuevo estado inicial
            this.initialState = state;
            state.setInitial(true);
        }
    }
    
    /**
     * Añade un estado final al AFD
     * @param state estado final a añadir
     */
    public void addFinalState(State state) {
        if (states.contains(state)) {
            finalStates.add(state);
            state.setFinal(true);
        }
    }
    
    /**
     * Elimina un estado final del AFD
     * @param state estado final a eliminar
     */
    public void removeFinalState(State state) {
        if (finalStates.remove(state)) {
            state.setFinal(false);
        }
    }
    
    /**
     * Añade una transición al AFD
     * @param fromState estado origen
     * @param symbol símbolo de entrada
     * @param toState estado destino
     */
    public void addTransition(State fromState, char symbol, State toState) {
        if (states.contains(fromState) && states.contains(toState) && alphabet.contains(symbol)) {
            transitions.addTransition(fromState, symbol, toState);
        }
    }
    
    /**
     * Procesa una palabra y determina si es aceptada por el AFD
     * @param word palabra a procesar
     * @return resultado del procesamiento
     */
    public AFDResult processWord(Word word) {
        if (initialState == null) {
            return new AFDResult(false, "No hay estado inicial definido", null);
        }
        
        if (!word.isValidForAlphabet(alphabet)) {
            return new AFDResult(false, "La palabra contiene símbolos no válidos", null);
        }
        
        State currentState = initialState;
        StringBuilder path = new StringBuilder();
        path.append(currentState.getName());
        
        // Procesar cada símbolo de la palabra
        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);
            State nextState = transitions.getNextState(currentState, symbol);
            
            if (nextState == null) {
                return new AFDResult(false, 
                    "No existe transición desde " + currentState.getName() + 
                    " con símbolo '" + symbol + "' en posición " + i, 
                    path.toString());
            }
            
            currentState = nextState;
            path.append(" -").append(symbol).append("-> ").append(currentState.getName());
        }
        
        // Verificar si el estado final es de aceptación
        boolean accepted = finalStates.contains(currentState);
        String message = accepted ? 
            "Palabra aceptada. Estado final: " + currentState.getName() :
            "Palabra rechazada. Estado final: " + currentState.getName() + " (no es de aceptación)";
            
        return new AFDResult(accepted, message, path.toString());
    }
    
    /**
     * Procesa una cadena de texto y determina si es aceptada
     * @param input cadena de entrada
     * @return resultado del procesamiento
     */
    public AFDResult processString(String input) {
        return processWord(new Word(input));
    }
    
    /**
     * Verifica si el AFD está completamente definido
     * @return true si el AFD es válido, false en caso contrario
     */
    public boolean isValid() {
        return initialState != null && 
               !states.isEmpty() && 
               !alphabet.isEmpty() && 
               !finalStates.isEmpty();
    }
    
    // Getters
    public Set<State> getStates() {
        return new HashSet<>(states);
    }
    
    public Alphabet getAlphabet() {
        return alphabet;
    }
    
    public TransitionFunction getTransitions() {
        return transitions;
    }
    
    public State getInitialState() {
        return initialState;
    }
    
    public Set<State> getFinalStates() {
        return new HashSet<>(finalStates);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AFD{\n");
        sb.append("  Estados: ").append(states).append("\n");
        sb.append("  Alfabeto: ").append(alphabet).append("\n");
        sb.append("  Estado inicial: ").append(initialState != null ? initialState.getName() : "null").append("\n");
        sb.append("  Estados finales: ").append(finalStates).append("\n");
        sb.append("  Transiciones: ").append(transitions).append("\n");
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Clase interna para representar el resultado del procesamiento de una palabra
     */
    public static class AFDResult {
        private final boolean accepted;
        private final String message;
        private final String path;
        
        public AFDResult(boolean accepted, String message, String path) {
            this.accepted = accepted;
            this.message = message;
            this.path = path;
        }
        
        public boolean isAccepted() {
            return accepted;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getPath() {
            return path;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Resultado: ").append(accepted ? "ACEPTADA" : "RECHAZADA").append("\n");
            sb.append("Mensaje: ").append(message).append("\n");
            if (path != null) {
                sb.append("Ruta: ").append(path).append("\n");
            }
            return sb.toString();
        }
    }
}