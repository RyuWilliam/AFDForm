package co.edu.uptc;

import co.edu.uptc.view.AFDBuilderView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AFDForm extends Application {
    
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Guardar referencia al stage
        
        // Configurar la ventana principal
        primaryStage.setTitle("AFD - Autómata Finito Determinista");
        
        // Crear componentes de la interfaz
        Label welcomeLabel = new Label("Bienvenido");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label subtitleLabel = new Label("Autómata Finito Determinista");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
        
        // Crear botones del menú principal
        Button createAFDButton = new Button("Crear AFD");
        createAFDButton.setPrefWidth(150);
        createAFDButton.setStyle("-fx-font-size: 14px; -fx-background-color: #3498db; -fx-text-fill: white;");
        createAFDButton.setOnAction(e -> openAFDBuilder());
        
        Button testAFDButton = new Button("Cargar AFD");
        testAFDButton.setPrefWidth(150);
        testAFDButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2ecc71; -fx-text-fill: white;");
        testAFDButton.setOnAction(e -> openLoadAFD());
        
        Button exitButton = new Button("Salir");
        exitButton.setPrefWidth(150);
        exitButton.setStyle("-fx-font-size: 14px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        exitButton.setOnAction(e -> primaryStage.close());
        
        // Crear layout principal
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Añadir componentes al layout
        root.getChildren().addAll(
            welcomeLabel, 
            subtitleLabel,
            createAFDButton, 
            testAFDButton, 
            exitButton
        );
        
        // Crear y mostrar la escena
        Scene scene = new Scene(root, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Abre la ventana del constructor de AFD
     */
    private void openAFDBuilder() {
        Stage builderStage = new Stage();
        builderStage.setTitle("Constructor de AFD");
        new AFDBuilderView(builderStage);
        
        // Cerrar la ventana principal
        primaryStage.close();
    }
    
    /**
     * Abre directamente el diálogo para cargar un AFD desde archivo
     */
    private void openLoadAFD() {
        try {
            // Crear FileChooser para seleccionar archivo
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Cargar AFD");
            fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Archivos AFD", "*.json")
            );
            
            java.io.File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                // Crear el constructor AFD y cargar el archivo
                Stage builderStage = new Stage();
                builderStage.setTitle("Constructor de AFD - Archivo Cargado");
                AFDBuilderView builderView = new AFDBuilderView(builderStage);
                
                // Cargar el archivo AFD
                builderView.loadAFDFromFile(file);
                
                primaryStage.close(); // Cerrar el menú principal
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error al cargar archivo", "No se pudo cargar el archivo AFD: " + e.getMessage());
        }
    }
    
    private void showErrorAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Método estático para iniciar la aplicación JavaFX
     */
    public static void startApplication(String[] args) {
        launch(args);
    }
}