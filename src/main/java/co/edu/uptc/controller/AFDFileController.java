package co.edu.uptc.controller;

import co.edu.uptc.model.*;

import java.io.*;
import java.util.*;

/**
 * Controlador para manejar la serialización y deserialización de AFDs en formato JSON simple
 */
public class AFDFileController {
    
    /**
     * Guarda un AFD en un archivo JSON
     * @param afd AFD a guardar
     * @param file archivo de destino
     * @throws IOException si hay error al escribir el archivo
     */
    public void saveAFD(AFD afd, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("{");
            
            // Alfabeto
            writer.print("  \"alphabet\": [");
            List<Character> alphabetList = new ArrayList<>(afd.getAlphabet().getSymbols());
            alphabetList.sort(Character::compareTo);
            for (int i = 0; i < alphabetList.size(); i++) {
                writer.print("\"" + alphabetList.get(i) + "\"");
                if (i < alphabetList.size() - 1) writer.print(", ");
            }
            writer.println("],");
            
            // Estados
            writer.print("  \"states\": [");
            List<String> statesList = new ArrayList<>();
            for (State state : afd.getStates()) {
                statesList.add(state.getName());
            }
            statesList.sort(String::compareTo);
            for (int i = 0; i < statesList.size(); i++) {
                writer.print("\"" + statesList.get(i) + "\"");
                if (i < statesList.size() - 1) writer.print(", ");
            }
            writer.println("],");
            
            // Estado inicial
            writer.println("  \"initialState\": \"" + 
                (afd.getInitialState() != null ? afd.getInitialState().getName() : "") + "\",");
            
            // Estados finales
            writer.print("  \"finalStates\": [");
            List<String> finalStatesList = new ArrayList<>();
            for (State state : afd.getFinalStates()) {
                finalStatesList.add(state.getName());
            }
            finalStatesList.sort(String::compareTo);
            for (int i = 0; i < finalStatesList.size(); i++) {
                writer.print("\"" + finalStatesList.get(i) + "\"");
                if (i < finalStatesList.size() - 1) writer.print(", ");
            }
            writer.println("],");
            
            // Transiciones
            writer.println("  \"transitions\": [");
            List<String> transitionsList = new ArrayList<>();
            for (State fromState : afd.getStates()) {
                for (Character symbol : afd.getAlphabet().getSymbols()) {
                    State toState = afd.getTransitions().getNextState(fromState, symbol);
                    if (toState != null) {
                        transitionsList.add(String.format(
                            "    {\"from\": \"%s\", \"symbol\": \"%s\", \"to\": \"%s\"}",
                            fromState.getName(), symbol, toState.getName()
                        ));
                    }
                }
            }
            for (int i = 0; i < transitionsList.size(); i++) {
                writer.print(transitionsList.get(i));
                if (i < transitionsList.size() - 1) writer.print(",");
                writer.println();
            }
            writer.println("  ]");
            writer.println("}");
        }
    }
    
    /**
     * Carga un AFD desde un archivo JSON
     * @param file archivo fuente
     * @return AFD cargado
     * @throws IOException si hay error al leer el archivo
     */
    public AFD loadAFD(File file) throws IOException {
        AFD afd = new AFD();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean inTransitions = false;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.startsWith("\"alphabet\":")) {
                    parseAlphabet(line, afd);
                } else if (line.startsWith("\"states\":")) {
                    parseStates(line, afd);
                } else if (line.startsWith("\"initialState\":")) {
                    parseInitialState(line, afd);
                } else if (line.startsWith("\"finalStates\":")) {
                    parseFinalStates(line, afd);
                } else if (line.startsWith("\"transitions\":")) {
                    inTransitions = true;
                } else if (inTransitions && line.contains("\"from\":")) {
                    parseTransition(line, afd);
                }
            }
        }
        
        return afd;
    }
    
    private void parseAlphabet(String line, AFD afd) {
        String alphabetPart = extractArrayContent(line);
        String[] symbols = alphabetPart.split(",");
        for (String symbol : symbols) {
            symbol = symbol.trim().replace("\"", "");
            if (!symbol.isEmpty()) {
                afd.getAlphabet().addSymbol(symbol.charAt(0));
            }
        }
    }
    
    private void parseStates(String line, AFD afd) {
        String statesPart = extractArrayContent(line);
        String[] states = statesPart.split(",");
        for (String stateName : states) {
            stateName = stateName.trim().replace("\"", "");
            if (!stateName.isEmpty()) {
                afd.addState(new State(stateName));
            }
        }
    }
    
    private void parseInitialState(String line, AFD afd) {
        String stateName = extractStringValue(line);
        if (!stateName.isEmpty()) {
            for (State state : afd.getStates()) {
                if (state.getName().equals(stateName)) {
                    afd.setInitialState(state);
                    break;
                }
            }
        }
    }
    
    private void parseFinalStates(String line, AFD afd) {
        String finalStatesPart = extractArrayContent(line);
        String[] finalStates = finalStatesPart.split(",");
        for (String stateName : finalStates) {
            stateName = stateName.trim().replace("\"", "");
            if (!stateName.isEmpty()) {
                for (State state : afd.getStates()) {
                    if (state.getName().equals(stateName)) {
                        afd.addFinalState(state);
                        break;
                    }
                }
            }
        }
    }
    
    private void parseTransition(String line, AFD afd) {
        // Formato: {"from": "q0", "symbol": "a", "to": "q1"}
        String fromState = extractJsonValue(line, "from");
        String symbolStr = extractJsonValue(line, "symbol");
        String toState = extractJsonValue(line, "to");
        
        if (!fromState.isEmpty() && !symbolStr.isEmpty() && !toState.isEmpty()) {
            State from = findState(afd, fromState);
            State to = findState(afd, toState);
            if (from != null && to != null) {
                afd.addTransition(from, symbolStr.charAt(0), to);
            }
        }
    }
    
    private String extractArrayContent(String line) {
        int start = line.indexOf('[');
        int end = line.lastIndexOf(']');
        if (start != -1 && end != -1 && start < end) {
            return line.substring(start + 1, end);
        }
        return "";
    }
    
    private String extractStringValue(String line) {
        int start = line.indexOf('"', line.indexOf(':'));
        int end = line.lastIndexOf('"');
        if (start != -1 && end != -1 && start < end) {
            return line.substring(start + 1, end);
        }
        return "";
    }
    
    private String extractJsonValue(String line, String key) {
        String pattern = "\"" + key + "\":";
        int keyIndex = line.indexOf(pattern);
        if (keyIndex != -1) {
            int start = line.indexOf('"', keyIndex + pattern.length());
            if (start != -1) {
                int end = line.indexOf('"', start + 1);
                if (end != -1) {
                    return line.substring(start + 1, end);
                }
            }
        }
        return "";
    }
    
    private State findState(AFD afd, String stateName) {
        for (State state : afd.getStates()) {
            if (state.getName().equals(stateName)) {
                return state;
            }
        }
        return null;
    }
}