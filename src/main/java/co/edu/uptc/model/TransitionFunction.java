package co.edu.uptc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Representa la función de transición de un Autómata Finito Determinista (AFD)
 * Mapea (estado_actual, símbolo) -> estado_siguiente
 */
public class TransitionFunction {
    private Map<TransitionKey, State> transitions;
    
    /**
     * Constructor que inicializa la función de transición vacía
     */
    public TransitionFunction() {
        this.transitions = new HashMap<>();
    }
    
    /**
     * Añade una transición a la función
     * @param fromState estado origen
     * @param symbol símbolo de entrada
     * @param toState estado destino
     */
    public void addTransition(State fromState, char symbol, State toState) {
        TransitionKey key = new TransitionKey(fromState, symbol);
        transitions.put(key, toState);
    }
    
    /**
     * Obtiene el estado destino para una transición dada
     * @param fromState estado origen
     * @param symbol símbolo de entrada
     * @return estado destino, o null si no existe la transición
     */
    public State getNextState(State fromState, char symbol) {
        TransitionKey key = new TransitionKey(fromState, symbol);
        return transitions.get(key);
    }
    
    /**
     * Verifica si existe una transición para el estado y símbolo dados
     * @param fromState estado origen
     * @param symbol símbolo de entrada
     * @return true si existe la transición, false en caso contrario
     */
    public boolean hasTransition(State fromState, char symbol) {
        TransitionKey key = new TransitionKey(fromState, symbol);
        return transitions.containsKey(key);
    }
    
    /**
     * Elimina una transición específica
     * @param fromState estado origen
     * @param symbol símbolo de entrada
     * @return true si se eliminó la transición (existía), false si no existía
     */
    public boolean removeTransition(State fromState, char symbol) {
        TransitionKey key = new TransitionKey(fromState, symbol);
        return transitions.remove(key) != null;
    }
    
    /**
     * Obtiene todas las transiciones
     * @return mapa de transiciones
     */
    public Map<TransitionKey, State> getTransitions() {
        return new HashMap<>(transitions);
    }
    
    /**
     * Obtiene el número de transiciones definidas
     * @return número de transiciones
     */
    public int size() {
        return transitions.size();
    }
    
    /**
     * Verifica si la función de transición está vacía
     * @return true si no hay transiciones, false en caso contrario
     */
    public boolean isEmpty() {
        return transitions.isEmpty();
    }
    
    /**
     * Limpia todas las transiciones
     */
    public void clear() {
        transitions.clear();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransitionFunction{\n");
        for (Map.Entry<TransitionKey, State> entry : transitions.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue().getName()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Clase interna que representa la clave de una transición (estado, símbolo)
     */
    public static class TransitionKey {
        private final State state;
        private final char symbol;
        
        public TransitionKey(State state, char symbol) {
            this.state = state;
            this.symbol = symbol;
        }
        
        public State getState() {
            return state;
        }
        
        public char getSymbol() {
            return symbol;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TransitionKey that = (TransitionKey) obj;
            return symbol == that.symbol && Objects.equals(state, that.state);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(state, symbol);
        }
        
        @Override
        public String toString() {
            return "(" + state.getName() + ", '" + symbol + "')";
        }
    }
}