package co.edu.uptc.test;

import co.edu.uptc.controller.AFDController;

/**
 * Clase de prueba para identificar errores en el AFD
 */
public class AFDTest {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL AFD ===");
        
        AFDController controller = new AFDController();
        
        try {
            // Paso 1: Definir alfabeto
            System.out.println("1. Definiendo alfabeto 'ab'...");
            boolean result1 = controller.defineAlphabet("ab");
            System.out.println("   Resultado: " + result1);
            System.out.println("   Alfabeto: " + controller.getAlphabetInfo());
            
            // Paso 2: Definir estados
            System.out.println("\n2. Definiendo 3 estados...");
            boolean result2 = controller.defineStates(3);
            System.out.println("   Resultado: " + result2);
            System.out.println("   Estados: " + controller.getStatesInfo());
            
            // Paso 3: Estado inicial
            System.out.println("\n3. Definiendo estado inicial q0...");
            boolean result3 = controller.setInitialState("q0");
            System.out.println("   Resultado: " + result3);
            System.out.println("   Estado inicial: " + controller.getInitialStateInfo());
            
            // Paso 4: Estados finales
            System.out.println("\n4. Definiendo estado final q2...");
            java.util.Set<String> finalStates = new java.util.HashSet<>();
            finalStates.add("q2");
            boolean result4 = controller.setFinalStates(finalStates);
            System.out.println("   Resultado: " + result4);
            System.out.println("   Estados finales: " + controller.getFinalStatesInfo());
            
            // Paso 5: Transiciones
            System.out.println("\n5. Definiendo transiciones...");
            boolean t1 = controller.addTransition("q0", 'a', "q1");
            boolean t2 = controller.addTransition("q0", 'b', "q0");
            boolean t3 = controller.addTransition("q1", 'a', "q1");
            boolean t4 = controller.addTransition("q1", 'b', "q2");
            boolean t5 = controller.addTransition("q2", 'a', "q1");
            boolean t6 = controller.addTransition("q2", 'b', "q0");
            
            System.out.println("   q0 -a-> q1: " + t1);
            System.out.println("   q0 -b-> q0: " + t2);
            System.out.println("   q1 -a-> q1: " + t3);
            System.out.println("   q1 -b-> q2: " + t4);
            System.out.println("   q2 -a-> q1: " + t5);
            System.out.println("   q2 -b-> q0: " + t6);
            
            // Verificar si AFD está completo
            System.out.println("\n6. Verificando si AFD está completo...");
            boolean isComplete = controller.isAFDComplete();
            System.out.println("   AFD completo: " + isComplete);
            
            // Paso 6: Probar palabras
            System.out.println("\n7. Probando palabras...");
            
            String[] testWords = {"ab", "aab", "b", "abb", "abab"};
            
            for (String word : testWords) {
                System.out.println("\n   Probando palabra: '" + word + "'");
                try {
                    var result = controller.processWord(word);
                    System.out.println("   Resultado: " + (result.isAccepted() ? "ACEPTADA" : "RECHAZADA"));
                    System.out.println("   Mensaje: " + result.getMessage());
                    if (result.getPath() != null) {
                        System.out.println("   Ruta: " + result.getPath());
                    }
                } catch (Exception e) {
                    System.out.println("   ERROR: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
        } catch (Exception e) {
            System.out.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}