package co.edu.uptc.model;

import java.util.Objects;

/**
 * Representa un estado en un Autómata Finito Determinista (AFD)
 */
public class State {
    private String name;
    private boolean isInitial;
    private boolean isFinal;
    
    /**
     * Constructor para crear un estado
     * @param name nombre del estado
     * @param isInitial indica si es el estado inicial
     * @param isFinal indica si es un estado final (de aceptación)
     */
    public State(String name, boolean isInitial, boolean isFinal) {
        this.name = name;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
    }
    
    /**
     * Constructor simplificado para estados que no son inicial ni final
     * @param name nombre del estado
     */
    public State(String name) {
        this(name, false, false);
    }
    
    // Getters y Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isInitial() {
        return isInitial;
    }
    
    public void setInitial(boolean initial) {
        isInitial = initial;
    }
    
    public boolean isFinal() {
        return isFinal;
    }
    
    public void setFinal(boolean finalState) {
        isFinal = finalState;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return Objects.equals(name, state.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State{name='").append(name).append("'");
        if (isInitial) sb.append(" [INICIAL]");
        if (isFinal) sb.append(" [FINAL]");
        sb.append("}");
        return sb.toString();
    }
}