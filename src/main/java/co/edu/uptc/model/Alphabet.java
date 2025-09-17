package co.edu.uptc.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

/**
 * Representa el alfabeto de un Autómata Finito Determinista (AFD)
 * Contiene el conjunto de símbolos válidos que pueden ser procesados
 */
public class Alphabet {
    private Set<Character> symbols;
    
    /**
     * Constructor que crea un alfabeto vacío
     */
    public Alphabet() {
        this.symbols = new HashSet<>();
    }
    
    /**
     * Constructor que crea un alfabeto con símbolos específicos
     * @param symbols conjunto de símbolos del alfabeto
     */
    public Alphabet(Set<Character> symbols) {
        this.symbols = new HashSet<>(symbols);
    }
    
    /**
     * Constructor que crea un alfabeto a partir de caracteres
     * @param symbols array de caracteres del alfabeto
     */
    public Alphabet(Character... symbols) {
        this.symbols = new HashSet<>(Arrays.asList(symbols));
    }
    
    /**
     * Constructor que crea un alfabeto a partir de una cadena
     * @param symbolsString cadena que contiene todos los símbolos del alfabeto
     */
    public Alphabet(String symbolsString) {
        this.symbols = new HashSet<>();
        for (char c : symbolsString.toCharArray()) {
            this.symbols.add(c);
        }
    }
    
    /**
     * Añade un símbolo al alfabeto
     * @param symbol símbolo a añadir
     * @return true si el símbolo se añadió (no existía), false si ya existía
     */
    public boolean addSymbol(char symbol) {
        return symbols.add(symbol);
    }
    
    /**
     * Elimina un símbolo del alfabeto
     * @param symbol símbolo a eliminar
     * @return true si el símbolo se eliminó (existía), false si no existía
     */
    public boolean removeSymbol(char symbol) {
        return symbols.remove(symbol);
    }
    
    /**
     * Verifica si un símbolo pertenece al alfabeto
     * @param symbol símbolo a verificar
     * @return true si el símbolo está en el alfabeto, false en caso contrario
     */
    public boolean contains(char symbol) {
        return symbols.contains(symbol);
    }
    
    /**
     * Verifica si una cadena contiene solo símbolos válidos del alfabeto
     * @param word cadena a verificar
     * @return true si todos los símbolos de la cadena están en el alfabeto
     */
    public boolean isValidWord(String word) {
        if (word == null) return false;
        for (char c : word.toCharArray()) {
            if (!contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Obtiene el conjunto de símbolos del alfabeto
     * @return conjunto de símbolos
     */
    public Set<Character> getSymbols() {
        return new HashSet<>(symbols);
    }
    
    /**
     * Obtiene el tamaño del alfabeto
     * @return número de símbolos en el alfabeto
     */
    public int size() {
        return symbols.size();
    }
    
    /**
     * Verifica si el alfabeto está vacío
     * @return true si no hay símbolos, false en caso contrario
     */
    public boolean isEmpty() {
        return symbols.isEmpty();
    }
    
    /**
     * Limpia el alfabeto eliminando todos los símbolos
     */
    public void clear() {
        symbols.clear();
    }
    
    @Override
    public String toString() {
        return "Alphabet{" + symbols + "}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Alphabet alphabet = (Alphabet) obj;
        return symbols.equals(alphabet.symbols);
    }
    
    @Override
    public int hashCode() {
        return symbols.hashCode();
    }
}