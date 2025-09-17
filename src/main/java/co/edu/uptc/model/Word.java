package co.edu.uptc.model;

import java.util.Objects;

/**
 * Representa una palabra (cadena de entrada) para ser procesada por un AFD
 */
public class Word {
    private String content;
    
    /**
     * Constructor que crea una palabra con contenido específico
     * @param content contenido de la palabra
     */
    public Word(String content) {
        this.content = content != null ? content : "";
    }
    
    /**
     * Constructor que crea una palabra vacía
     */
    public Word() {
        this("");
    }
    
    /**
     * Obtiene el contenido de la palabra
     * @return contenido de la palabra
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Establece el contenido de la palabra
     * @param content nuevo contenido
     */
    public void setContent(String content) {
        this.content = content != null ? content : "";
    }
    
    /**
     * Obtiene la longitud de la palabra
     * @return longitud de la palabra
     */
    public int length() {
        return content.length();
    }
    
    /**
     * Verifica si la palabra está vacía
     * @return true si la palabra está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return content.isEmpty();
    }
    
    /**
     * Obtiene el carácter en la posición especificada
     * @param index índice del carácter (0-based)
     * @return carácter en la posición especificada
     * @throws IndexOutOfBoundsException si el índice está fuera de rango
     */
    public char charAt(int index) {
        return content.charAt(index);
    }
    
    /**
     * Obtiene una subcadena de la palabra
     * @param beginIndex índice de inicio (incluido)
     * @param endIndex índice de fin (excluido)
     * @return nueva palabra con la subcadena
     */
    public Word substring(int beginIndex, int endIndex) {
        return new Word(content.substring(beginIndex, endIndex));
    }
    
    /**
     * Obtiene una subcadena desde el índice especificado hasta el final
     * @param beginIndex índice de inicio (incluido)
     * @return nueva palabra con la subcadena
     */
    public Word substring(int beginIndex) {
        return new Word(content.substring(beginIndex));
    }
    
    /**
     * Concatena esta palabra con otra
     * @param other otra palabra a concatenar
     * @return nueva palabra resultado de la concatenación
     */
    public Word concat(Word other) {
        if (other == null) return new Word(this.content);
        return new Word(this.content + other.content);
    }
    
    /**
     * Concatena esta palabra con una cadena
     * @param str cadena a concatenar
     * @return nueva palabra resultado de la concatenación
     */
    public Word concat(String str) {
        if (str == null) return new Word(this.content);
        return new Word(this.content + str);
    }
    
    /**
     * Verifica si la palabra contiene solo símbolos del alfabeto dado
     * @param alphabet alfabeto a verificar
     * @return true si todos los símbolos están en el alfabeto, false en caso contrario
     */
    public boolean isValidForAlphabet(Alphabet alphabet) {
        if (alphabet == null) return false;
        return alphabet.isValidWord(this.content);
    }
    
    /**
     * Convierte la palabra a un array de caracteres
     * @return array de caracteres de la palabra
     */
    public char[] toCharArray() {
        return content.toCharArray();
    }
    
    /**
     * Reversa la palabra
     * @return nueva palabra con el contenido invertido
     */
    public Word reverse() {
        return new Word(new StringBuilder(content).reverse().toString());
    }
    
    /**
     * Verifica si la palabra contiene un carácter específico
     * @param ch carácter a buscar
     * @return true si la palabra contiene el carácter, false en caso contrario
     */
    public boolean contains(char ch) {
        return content.indexOf(ch) >= 0;
    }
    
    @Override
    public String toString() {
        return content.isEmpty() ? "ε" : content; // ε representa la cadena vacía
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word word = (Word) obj;
        return Objects.equals(content, word.content);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}