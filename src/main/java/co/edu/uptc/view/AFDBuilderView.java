package co.edu.uptc.view;

import co.edu.uptc.controller.AFDController;
import co.edu.uptc.model.AFD;
import co.edu.uptc.model.State;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Vista principal para construir y probar un AFD paso a paso
 */
public class AFDBuilderView {
    private Stage primaryStage;
    private AFDController controller;
    
    // Componentes de la interfaz
    private VBox mainContainer;
    private Label statusLabel;
    
    // Paso 1: Definir alfabeto
    private VBox alphabetSection;
    private TextField alphabetField;
    private Button defineAlphabetButton;
    private Label alphabetStatusLabel;
    
    // Paso 2: Definir estados
    private VBox statesSection;
    private Spinner<Integer> statesSpinner;
    private Button defineStatesButton;
    private Label statesStatusLabel;
    
    // Paso 3: Estado inicial
    private VBox initialStateSection;
    private ComboBox<String> initialStateCombo;
    private Button setInitialStateButton;
    private Label initialStateStatusLabel;
    
    // Paso 4: Estados finales
    private VBox finalStatesSection;
    private VBox finalStatesCheckBoxes;
    private Button setFinalStatesButton;
    private Label finalStatesStatusLabel;
    
    // Paso 5: Matriz de transiciones
    private VBox transitionsSection;
    private GridPane transitionMatrix;
    private Button buildMatrixButton;
    private Button saveTransitionsButton;
    private Label transitionsStatusLabel;
    
    // Paso 6: Probar palabras
    private VBox testSection;
    private TextField wordField;
    private Button testWordButton;
    private TextArea resultArea;
    
    // Paso 7: Generar cadenas válidas
    private VBox generateSection;
    private Button generateWordsButton;
    private TextArea validWordsArea;
    
    // Guardar/Cargar
    private HBox fileButtons;
    private Button saveButton;
    private Button loadButton;
    
    // Botones de navegación
    private HBox navigationButtons;
    private Button backButton;
    private Button resetButton;
    
    public AFDBuilderView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.controller = new AFDController();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #f5f5f5;");
        
        // Título principal
        Label titleLabel = new Label("Constructor de AFD");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        statusLabel = new Label("Paso 1: Define el alfabeto del AFD");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statusLabel.setTextFill(Color.DARKGREEN);
        
        // Inicializar secciones
        initializeAlphabetSection();
        initializeStatesSection();
        initializeInitialStateSection();
        initializeFinalStatesSection();
        initializeTransitionsSection();
        initializeTestSection();
        initializeGenerateSection();
        initializeFileButtons();
        initializeNavigationButtons();
        
        // Inicialmente solo mostrar la sección del alfabeto
        showOnlySection(alphabetSection);
    }
    
    private void initializeAlphabetSection() {
        alphabetSection = new VBox(10);
        alphabetSection.setPadding(new Insets(15));
        alphabetSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 1: Definir Alfabeto");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("Ingresa los símbolos del alfabeto separados por comas (ej: a,b,c o 0,1,2):");
        alphabetField = new TextField();
        alphabetField.setPromptText("Ejemplo: a,b,c");
        alphabetField.setPrefWidth(200);
        
        defineAlphabetButton = new Button("Definir Alfabeto");
        defineAlphabetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        
        alphabetStatusLabel = new Label("Estado: No definido");
        alphabetStatusLabel.setTextFill(Color.RED);
        
        alphabetSection.getChildren().addAll(
            sectionTitle, instructionLabel, alphabetField, 
            defineAlphabetButton, alphabetStatusLabel
        );
    }
    
    private void initializeStatesSection() {
        statesSection = new VBox(10);
        statesSection.setPadding(new Insets(15));
        statesSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 2: Definir Estados");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("¿Cuántos estados tendrá el AFD?");
        statesSpinner = new Spinner<>(2, 15, 3); // Límites más restrictivos
        statesSpinner.setPrefWidth(100);
        statesSpinner.setEditable(false); // Evitar entrada manual
        
        defineStatesButton = new Button("Crear Estados");
        defineStatesButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        
        statesStatusLabel = new Label("Estado: No definido");
        statesStatusLabel.setTextFill(Color.RED);
        
        statesSection.getChildren().addAll(
            sectionTitle, instructionLabel, statesSpinner, 
            defineStatesButton, statesStatusLabel
        );
    }
    
    private void initializeInitialStateSection() {
        initialStateSection = new VBox(10);
        initialStateSection.setPadding(new Insets(15));
        initialStateSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 3: Estado Inicial");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("Selecciona el estado inicial:");
        initialStateCombo = new ComboBox<>();
        initialStateCombo.setPrefWidth(150);
        
        setInitialStateButton = new Button("Establecer Estado Inicial");
        setInitialStateButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        
        initialStateStatusLabel = new Label("Estado: No definido");
        initialStateStatusLabel.setTextFill(Color.RED);
        
        initialStateSection.getChildren().addAll(
            sectionTitle, instructionLabel, initialStateCombo, 
            setInitialStateButton, initialStateStatusLabel
        );
    }
    
    private void initializeFinalStatesSection() {
        finalStatesSection = new VBox(10);
        finalStatesSection.setPadding(new Insets(15));
        finalStatesSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 4: Estados Finales");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("Selecciona los estados finales (de aceptación):");
        finalStatesCheckBoxes = new VBox(5);
        
        setFinalStatesButton = new Button("Establecer Estados Finales");
        setFinalStatesButton.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold;");
        
        finalStatesStatusLabel = new Label("Estado: No definido");
        finalStatesStatusLabel.setTextFill(Color.RED);
        
        finalStatesSection.getChildren().addAll(
            sectionTitle, instructionLabel, finalStatesCheckBoxes, 
            setFinalStatesButton, finalStatesStatusLabel
        );
    }
    
    private void initializeTransitionsSection() {
        transitionsSection = new VBox(10);
        transitionsSection.setPadding(new Insets(15));
        transitionsSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 5: Matriz de Transiciones");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("Define las transiciones del AFD:");
        
        buildMatrixButton = new Button("Construir Matriz");
        buildMatrixButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        
        transitionMatrix = new GridPane();
        transitionMatrix.setHgap(5);
        transitionMatrix.setVgap(5);
        transitionMatrix.setPadding(new Insets(10));
        transitionMatrix.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ccc;");
        
        saveTransitionsButton = new Button("Guardar Transiciones");
        saveTransitionsButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        saveTransitionsButton.setVisible(false);
        
        transitionsStatusLabel = new Label("Estado: No definido");
        transitionsStatusLabel.setTextFill(Color.RED);
        
        transitionsSection.getChildren().addAll(
            sectionTitle, instructionLabel, buildMatrixButton, 
            transitionMatrix, saveTransitionsButton, transitionsStatusLabel
        );
    }
    
    private void initializeTestSection() {
        testSection = new VBox(10);
        testSection.setPadding(new Insets(15));
        testSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 6: Probar Palabras");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("Ingresa una palabra para probar en el AFD:");
        wordField = new TextField();
        wordField.setPromptText("Ejemplo: aba");
        wordField.setPrefWidth(200);
        
        testWordButton = new Button("Probar Palabra");
        testWordButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold;");
        
        resultArea = new TextArea();
        resultArea.setPrefRowCount(8);
        resultArea.setEditable(false);
        resultArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        
        testSection.getChildren().addAll(
            sectionTitle, instructionLabel, wordField, 
            testWordButton, new Label("Resultado:"), resultArea
        );
    }
    
    private void initializeGenerateSection() {
        generateSection = new VBox(10);
        generateSection.setPadding(new Insets(15));
        generateSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label sectionTitle = new Label("Paso 7: Generar Cadenas Válidas");
        sectionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label instructionLabel = new Label("Genera las 10 cadenas más cortas del lenguaje:");
        
        generateWordsButton = new Button("Generar Cadenas");
        generateWordsButton.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold;");
        
        validWordsArea = new TextArea();
        validWordsArea.setPrefRowCount(6);
        validWordsArea.setEditable(false);
        validWordsArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        validWordsArea.setPromptText("Las cadenas válidas aparecerán aquí...");
        
        generateSection.getChildren().addAll(
            sectionTitle, instructionLabel, generateWordsButton, 
            new Label("Cadenas válidas:"), validWordsArea
        );
    }
    
    private void initializeFileButtons() {
        fileButtons = new HBox(10);
        fileButtons.setAlignment(Pos.CENTER);
        fileButtons.setPadding(new Insets(10));
        
        saveButton = new Button("Guardar AFD");
        saveButton.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setPrefWidth(120);
        
        loadButton = new Button("Cargar AFD");
        loadButton.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-font-weight: bold;");
        loadButton.setPrefWidth(120);
        
        fileButtons.getChildren().addAll(saveButton, loadButton);
    }
    
    private void initializeNavigationButtons() {
        navigationButtons = new HBox(10);
        navigationButtons.setAlignment(Pos.CENTER);
        
        backButton = new Button("Volver al Menú");
        backButton.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-weight: bold;");
        
        resetButton = new Button("Reiniciar AFD");
        resetButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        
        navigationButtons.getChildren().addAll(backButton, resetButton);
    }
    
    private void setupLayout() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f5f5f5;");
        
        Scene scene = new Scene(scrollPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Constructor de AFD - Paso a Paso");
        primaryStage.show();
    }
    
    private void setupEventHandlers() {
        defineAlphabetButton.setOnAction(e -> handleDefineAlphabet());
        defineStatesButton.setOnAction(e -> handleDefineStates());
        setInitialStateButton.setOnAction(e -> handleSetInitialState());
        setFinalStatesButton.setOnAction(e -> handleSetFinalStates());
        buildMatrixButton.setOnAction(e -> handleBuildMatrix());
        saveTransitionsButton.setOnAction(e -> handleSaveTransitions());
        testWordButton.setOnAction(e -> handleTestWord());
        resetButton.setOnAction(e -> handleReset());
        backButton.setOnAction(e -> handleBackToMenu());
        
        // Enter en campos de texto
        alphabetField.setOnAction(e -> handleDefineAlphabet());
        wordField.setOnAction(e -> handleTestWord());
        
        // Nuevos handlers
        generateWordsButton.setOnAction(e -> handleGenerateWords());
        saveButton.setOnAction(e -> handleSaveAFD());
        loadButton.setOnAction(e -> handleLoadAFD());
    }
    
    private void showOnlySection(VBox sectionToShow) {
        mainContainer.getChildren().clear();
        
        Label titleLabel = new Label("Constructor de AFD");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        mainContainer.getChildren().addAll(titleLabel, statusLabel, sectionToShow, navigationButtons);
    }
    
    private void showAllSections() {
        mainContainer.getChildren().clear();
        
        Label titleLabel = new Label("Constructor de AFD");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        mainContainer.getChildren().addAll(
            titleLabel, statusLabel, alphabetSection, statesSection, 
            initialStateSection, finalStatesSection, transitionsSection, 
            testSection, generateSection, fileButtons, navigationButtons
        );
    }
    
    private void handleDefineAlphabet() {
        String symbols = alphabetField.getText().trim();
        
        // Validación básica
        if (symbols.isEmpty()) {
            alphabetStatusLabel.setText("Error: Ingresa al menos un símbolo");
            alphabetStatusLabel.setTextFill(Color.RED);
            return;
        }
        
        // Validar entrada del alfabeto
        String validationResult = validateAlphabetInput(symbols);
        if (!validationResult.equals("VALID")) {
            alphabetStatusLabel.setText("Error: " + validationResult);
            alphabetStatusLabel.setTextFill(Color.RED);
            return;
        }
        
        if (controller.defineAlphabet(symbols)) {
            alphabetStatusLabel.setText("✓ Alfabeto definido: " + controller.getAlphabetInfo());
            alphabetStatusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Paso 2: Define la cantidad de estados");
            showOnlySection(statesSection);
        } else {
            alphabetStatusLabel.setText("Error: No se pudo definir el alfabeto");
            alphabetStatusLabel.setTextFill(Color.RED);
        }
    }
    
    private String validateAlphabetInput(String symbols) {
        // Límite máximo de símbolos (para evitar AFDs demasiado complejos)
        final int MAX_SYMBOLS = 10;
        
        // Separar símbolos por comas
        String[] symbolArray = symbols.split(",");
        
        if (symbolArray.length > MAX_SYMBOLS) {
            return "Máximo " + MAX_SYMBOLS + " símbolos permitidos";
        }
        
        Set<Character> uniqueSymbols = new HashSet<>();
        
        for (String symbol : symbolArray) {
            String trimmedSymbol = symbol.trim();
            
            // Validar que no esté vacío
            if (trimmedSymbol.isEmpty()) {
                return "No puede haber símbolos vacíos";
            }
            
            // Validar que sea un solo carácter
            if (trimmedSymbol.length() != 1) {
                return "Cada símbolo debe ser un solo carácter";
            }
            
            char symbolChar = trimmedSymbol.charAt(0);
            
            // Validar caracteres permitidos (letras y números)
            if (!Character.isLetterOrDigit(symbolChar)) {
                return "Solo se permiten letras y números como símbolos";
            }
            
            // Validar que no sea un carácter especial usado internamente
            if (symbolChar == 'λ' || symbolChar == 'ε') {
                return "Los símbolos λ y ε están reservados";
            }
            
            // Verificar duplicados
            if (uniqueSymbols.contains(symbolChar)) {
                return "El símbolo '" + symbolChar + "' está duplicado";
            }
            
            uniqueSymbols.add(symbolChar);
        }
        
        return "VALID";
    }
    
    private String validateWordInput(String word) {
        // Límite máximo de longitud de palabra
        final int MAX_WORD_LENGTH = 50;
        
        if (word.length() > MAX_WORD_LENGTH) {
            return "La palabra es demasiado larga (máximo " + MAX_WORD_LENGTH + " caracteres)";
        }
        
        // Obtener símbolos válidos del alfabeto
        Set<Character> validSymbols = controller.getCurrentAFD().getAlphabet().getSymbols();
        
        // Verificar cada carácter de la palabra
        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);
            if (!validSymbols.contains(symbol)) {
                return "El símbolo '" + symbol + "' no pertenece al alfabeto " + validSymbols;
            }
        }
        
        return "VALID";
    }
    
    private void handleDefineStates() {
        int numStates = statesSpinner.getValue();
        
        // Validación adicional (aunque el spinner ya limita)
        if (numStates < 2) {
            statesStatusLabel.setText("Error: Un AFD necesita al menos 2 estados");
            statesStatusLabel.setTextFill(Color.RED);
            return;
        }
        
        if (numStates > 15) {
            statesStatusLabel.setText("Error: Máximo 15 estados para mantener rendimiento");
            statesStatusLabel.setTextFill(Color.RED);
            return;
        }
        
        // Validar complejidad total (estados × símbolos)
        int alphabetSize = controller.getAlphabetSize();
        if (alphabetSize > 0 && numStates * alphabetSize > 150) {
            statesStatusLabel.setText("Error: AFD demasiado complejo (" + numStates + " estados × " + alphabetSize + " símbolos)");
            statesStatusLabel.setTextFill(Color.RED);
            return;
        }
        
        if (controller.defineStates(numStates)) {
            statesStatusLabel.setText("✓ Estados creados: " + controller.getStatesInfo());
            statesStatusLabel.setTextFill(Color.GREEN);
            
            // Actualizar combo de estado inicial
            initialStateCombo.getItems().clear();
            for (int i = 0; i < numStates; i++) {
                initialStateCombo.getItems().add("q" + i);
            }
            initialStateCombo.getSelectionModel().selectFirst();
            
            statusLabel.setText("Paso 3: Selecciona el estado inicial");
            showOnlySection(initialStateSection);
        } else {
            statesStatusLabel.setText("Error: No se pudieron crear los estados");
            statesStatusLabel.setTextFill(Color.RED);
        }
    }
    
    private void handleSetInitialState() {
        String selectedState = initialStateCombo.getValue();
        if (selectedState != null && controller.setInitialState(selectedState)) {
            initialStateStatusLabel.setText("✓ Estado inicial: " + controller.getInitialStateInfo());
            initialStateStatusLabel.setTextFill(Color.GREEN);
            
            // Actualizar checkboxes de estados finales
            updateFinalStatesCheckBoxes();
            
            statusLabel.setText("Paso 4: Selecciona los estados finales");
            showOnlySection(finalStatesSection);
        } else {
            initialStateStatusLabel.setText("Error: No se pudo establecer el estado inicial");
            initialStateStatusLabel.setTextFill(Color.RED);
        }
    }
    
    private void updateFinalStatesCheckBoxes() {
        finalStatesCheckBoxes.getChildren().clear();
        for (String stateName : initialStateCombo.getItems()) {
            CheckBox checkBox = new CheckBox(stateName);
            finalStatesCheckBoxes.getChildren().add(checkBox);
        }
    }
    
    private void handleSetFinalStates() {
        Set<String> finalStates = new HashSet<>();
        for (javafx.scene.Node node : finalStatesCheckBoxes.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    finalStates.add(checkBox.getText());
                }
            }
        }
        
        if (finalStates.isEmpty()) {
            finalStatesStatusLabel.setText("Error: Debe seleccionar al menos un estado final");
            finalStatesStatusLabel.setTextFill(Color.RED);
            return;
        }
        
        // Validación adicional: advertencia si todos los estados son finales
        int totalStates = initialStateCombo.getItems().size();
        if (finalStates.size() == totalStates) {
            finalStatesStatusLabel.setText("Advertencia: Todos los estados son finales (acepta cualquier palabra)");
            finalStatesStatusLabel.setTextFill(Color.ORANGE);
        }
        
        if (controller.setFinalStates(finalStates)) {
            finalStatesStatusLabel.setText("✓ Estados finales: " + controller.getFinalStatesInfo());
            finalStatesStatusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Paso 5: Define la matriz de transiciones");
            showOnlySection(transitionsSection);
        } else {
            finalStatesStatusLabel.setText("Error: No se pudieron establecer los estados finales");
            finalStatesStatusLabel.setTextFill(Color.RED);
        }
    }
    
    private void handleBuildMatrix() {
        buildTransitionMatrix();
        buildMatrixButton.setVisible(false);
        saveTransitionsButton.setVisible(true);
    }
    
    private void buildTransitionMatrix() {
        transitionMatrix.getChildren().clear();
        
        // Obtener símbolos del alfabeto y estados
        Character[] symbolsArray = controller.getCurrentAFD().getAlphabet().getSymbols().toArray(new Character[0]);
        char[] symbols = new char[symbolsArray.length];
        for (int i = 0; i < symbolsArray.length; i++) {
            symbols[i] = symbolsArray[i];
        }
        String[] states = controller.getCurrentAFD().getStates().stream()
                .map(state -> state.getName())
                .sorted()
                .toArray(String[]::new);
        
        // Crear encabezados
        Label emptyCell = new Label("");
        transitionMatrix.add(emptyCell, 0, 0);
        
        for (int i = 0; i < symbols.length; i++) {
            Label symbolLabel = new Label(String.valueOf(symbols[i]));
            symbolLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            transitionMatrix.add(symbolLabel, i + 1, 0);
        }
        
        // Crear filas para cada estado
        for (int i = 0; i < states.length; i++) {
            Label stateLabel = new Label(states[i]);
            stateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            transitionMatrix.add(stateLabel, 0, i + 1);
            
            for (int j = 0; j < symbols.length; j++) {
                ComboBox<String> combo = new ComboBox<>();
                combo.getItems().addAll(states);
                combo.setPrefWidth(80);
                combo.setPromptText("Estado");
                transitionMatrix.add(combo, j + 1, i + 1);
            }
        }
    }
    
    private void handleSaveTransitions() {
        try {
            // Obtener símbolos del alfabeto y estados
            Character[] symbolsArray = controller.getCurrentAFD().getAlphabet().getSymbols().toArray(new Character[0]);
            char[] symbols = new char[symbolsArray.length];
            for (int i = 0; i < symbolsArray.length; i++) {
                symbols[i] = symbolsArray[i];
            }
            String[] states = controller.getCurrentAFD().getStates().stream()
                    .map(state -> state.getName())
                    .sorted()
                    .toArray(String[]::new);
            
            boolean allTransitionsDefined = true;
            java.util.List<ComboBox<String>> combos = new java.util.ArrayList<>();
            
            // Buscar todos los ComboBox en la matriz
            for (javafx.scene.Node node : transitionMatrix.getChildren()) {
                if (node instanceof ComboBox) {
                    @SuppressWarnings("unchecked")
                    ComboBox<String> combo = (ComboBox<String>) node;
                    combos.add(combo);
                }
            }
            
            // Verificar que tenemos el número correcto de ComboBox
            int expectedCombos = states.length * symbols.length;
            if (combos.size() != expectedCombos) {
                transitionsStatusLabel.setText("Error: Matriz de transiciones incompleta (" + combos.size() + "/" + expectedCombos + ")");
                transitionsStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
                return;
            }
            
            // Validar que todas las transiciones estén definidas
            int emptyTransitions = 0;
            for (ComboBox<String> combo : combos) {
                if (combo.getValue() == null || combo.getValue().isEmpty()) {
                    emptyTransitions++;
                    allTransitionsDefined = false;
                }
            }
            
            if (!allTransitionsDefined) {
                transitionsStatusLabel.setText("Error: " + emptyTransitions + " transiciones sin definir");
                transitionsStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
                return;
            }
            
            // Procesar las transiciones en orden
            int comboIndex = 0;
            for (int i = 0; i < states.length; i++) {
                for (int j = 0; j < symbols.length; j++) {
                    ComboBox<String> combo = combos.get(comboIndex);
                    String toState = combo.getValue();
                    
                    if (toState == null || toState.isEmpty()) {
                        allTransitionsDefined = false;
                        combo.setStyle("-fx-border-color: red;");
                    } else {
                        combo.setStyle("");
                        controller.addTransition(states[i], symbols[j], toState);
                    }
                    comboIndex++;
                }
            }
            
            if (allTransitionsDefined) {
                transitionsStatusLabel.setText("✓ Transiciones definidas correctamente");
                transitionsStatusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                statusLabel.setText("¡AFD completo! Ahora puedes probar palabras");
                showAllSections();
            } else {
                transitionsStatusLabel.setText("Error: Completa todas las transiciones");
                transitionsStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        } catch (Exception e) {
            transitionsStatusLabel.setText("Error: " + e.getMessage());
            transitionsStatusLabel.setTextFill(javafx.scene.paint.Color.RED);
            e.printStackTrace(); // Para debugging
        }
    }
    
    private void handleTestWord() {
        try {
            String word = wordField.getText().trim();
            if (word.isEmpty()) {
                resultArea.setText("Error: Ingresa una palabra para probar");
                return;
            }
            
            // Validar que la palabra solo contenga símbolos del alfabeto
            String wordValidation = validateWordInput(word);
            if (!wordValidation.equals("VALID")) {
                resultArea.setText("Error: " + wordValidation);
                return;
            }
            
            if (!controller.isAFDComplete()) {
                resultArea.setText("Error: El AFD no está completo. Completa todos los pasos primero.");
                return;
            }
            
            AFD.AFDResult result = controller.processWord(word);
            
            StringBuilder output = new StringBuilder();
            output.append("PALABRA: ").append(word).append("\n");
            output.append("====================\n");
            output.append("RESULTADO: ").append(result.isAccepted() ? "ACEPTADA" : "RECHAZADA").append("\n");
            output.append("MENSAJE: ").append(result.getMessage()).append("\n");
            if (result.getPath() != null) {
                output.append("RUTA DE EJECUCIÓN:\n").append(result.getPath()).append("\n");
            }
            output.append("\n");
            output.append("INFORMACIÓN DEL AFD:\n");
            output.append("- Alfabeto: ").append(controller.getAlphabetInfo()).append("\n");
            output.append("- Estados: ").append(controller.getStatesInfo()).append("\n");
            output.append("- Estado inicial: ").append(controller.getInitialStateInfo()).append("\n");
            output.append("- Estados finales: ").append(controller.getFinalStatesInfo()).append("\n");
            
            resultArea.setText(output.toString());
            wordField.clear();
            
        } catch (Exception e) {
            StringBuilder errorOutput = new StringBuilder();
            errorOutput.append("ERROR AL PROBAR PALABRA:\n");
            errorOutput.append("========================\n");
            errorOutput.append("Mensaje: ").append(e.getMessage()).append("\n");
            errorOutput.append("Tipo: ").append(e.getClass().getSimpleName()).append("\n");
            errorOutput.append("\nVerifica que:\n");
            errorOutput.append("- El alfabeto esté definido\n");
            errorOutput.append("- Los estados estén creados\n");
            errorOutput.append("- El estado inicial esté configurado\n");
            errorOutput.append("- Los estados finales estén seleccionados\n");
            errorOutput.append("- Las transiciones estén completas\n");
            errorOutput.append("- La palabra solo contenga símbolos del alfabeto\n");
            
            resultArea.setText(errorOutput.toString());
            e.printStackTrace(); // Para debugging en consola
        }
    }
    
    private void handleReset() {
        controller = new AFDController();
        
        // Limpiar todos los campos
        alphabetField.clear();
        wordField.clear();
        resultArea.clear();
        
        // Reset de status labels
        alphabetStatusLabel.setText("Estado: No definido");
        alphabetStatusLabel.setTextFill(Color.RED);
        statesStatusLabel.setText("Estado: No definido");
        statesStatusLabel.setTextFill(Color.RED);
        initialStateStatusLabel.setText("Estado: No definido");
        initialStateStatusLabel.setTextFill(Color.RED);
        finalStatesStatusLabel.setText("Estado: No definido");
        finalStatesStatusLabel.setTextFill(Color.RED);
        transitionsStatusLabel.setText("Estado: No definido");
        transitionsStatusLabel.setTextFill(Color.RED);
        
        // Limpiar componentes
        initialStateCombo.getItems().clear();
        finalStatesCheckBoxes.getChildren().clear();
        transitionMatrix.getChildren().clear();
        
        buildMatrixButton.setVisible(true);
        saveTransitionsButton.setVisible(false);
        
        statusLabel.setText("Paso 1: Define el alfabeto del AFD");
        showOnlySection(alphabetSection);
    }
    
    private void handleGenerateWords() {
        if (controller.getCurrentAFD() == null) {
            showAlert("Error", "Primero debes construir completamente el AFD antes de generar palabras.");
            return;
        }
        
        List<String> validWords = controller.generateShortestValidWords(10);
        if (validWords.isEmpty()) {
            validWordsArea.setText("No se encontraron palabras válidas en el lenguaje del AFD.\n(El AFD podría no aceptar ninguna palabra)");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < validWords.size(); i++) {
                String word = validWords.get(i);
                if (word.isEmpty()) {
                    result.append((i + 1)).append(". λ (cadena vacía)\n");
                } else {
                    result.append((i + 1)).append(". ").append(word).append("\n");
                }
            }
            validWordsArea.setText(result.toString());
        }
    }
    
    private void handleSaveAFD() {
        if (controller.getCurrentAFD() == null) {
            showAlert("Error", "No hay un AFD para guardar. Construye el AFD primero.");
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar AFD");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos AFD", "*.json")
        );
        fileChooser.setInitialFileName("mi_automata.json");
        
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                controller.saveAFD(file);
                showAlert("Éxito", "AFD guardado correctamente en: " + file.getAbsolutePath());
            } catch (Exception e) {
                showAlert("Error", "Error al guardar el AFD: " + e.getMessage());
            }
        }
    }
    
    private void handleLoadAFD() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar AFD");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos AFD", "*.json")
        );
        
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                controller.loadAFD(file);
                refreshUIFromLoadedAFD();
                showAlert("Éxito", "AFD cargado correctamente desde: " + file.getAbsolutePath());
            } catch (Exception e) {
                showAlert("Error", "Error al cargar el AFD: " + e.getMessage());
            }
        }
    }
    
    private void refreshUIFromLoadedAFD() {
        AFD afd = controller.getCurrentAFD();
        if (afd == null) return;
        
        // Actualizar alfabeto
        String alphabetStr = afd.getAlphabet().getSymbols().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
        alphabetField.setText(alphabetStr);
        alphabetStatusLabel.setText("✓ Alfabeto definido: " + afd.getAlphabet().getSymbols());
        alphabetStatusLabel.setTextFill(Color.GREEN);
        
        // Actualizar estados
        statesSpinner.getValueFactory().setValue(afd.getStates().size());
        statesStatusLabel.setText("✓ Estados definidos: " + afd.getStates().size());
        statesStatusLabel.setTextFill(Color.GREEN);
        
        // Actualizar estado inicial
        initialStateStatusLabel.setText("✓ Estado inicial: " + afd.getInitialState().getName());
        initialStateStatusLabel.setTextFill(Color.GREEN);
        
        // Actualizar estados finales
        List<String> finalStates = afd.getFinalStates().stream()
            .map(State::getName)
            .toList();
        finalStatesStatusLabel.setText("✓ Estados finales: " + finalStates);
        finalStatesStatusLabel.setTextFill(Color.GREEN);
        
        // Actualizar transiciones
        transitionsStatusLabel.setText("✓ Matriz de transiciones configurada");
        transitionsStatusLabel.setTextFill(Color.GREEN);
        
        statusLabel.setText("AFD cargado - Puedes probar palabras o generar cadenas válidas");
        showAllSections();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void loadAFDFromFile(File file) {
        try {
            controller.loadAFD(file);
            refreshUIFromLoadedAFD();
            showAlert("Éxito", "AFD cargado correctamente desde: " + file.getAbsolutePath());
        } catch (Exception e) {
            showAlert("Error", "Error al cargar el AFD: " + e.getMessage());
        }
    }
    
    private void handleBackToMenu() {
        try {
            // Crear nueva instancia del menú principal
            Stage menuStage = new Stage();
            co.edu.uptc.AFDForm menuForm = new co.edu.uptc.AFDForm();
            menuForm.start(menuStage);
            
            // Cerrar la ventana actual
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Si hay error, al menos cerrar la ventana actual
            primaryStage.close();
        }
    }
}