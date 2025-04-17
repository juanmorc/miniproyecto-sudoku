/**
 * Clase principal de la aplicación Sudoku que inicia la interfaz gráfica de usuario.
 * Esta clase extiende de Application y configura la ventana principal del juego.
 * @author Juan Moreno (2417575), Miguel Romero (2421802)
 * @version 1.0
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Método que inicia la aplicación JavaFX.
     * Carga la vista FXML y configura la escena principal.
     * @param primaryStage El escenario principal proporcionado por la plataforma JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/miniproyecto_sudoku_1/SudokuView.fxml"));
            primaryStage.setTitle("Sudoku Game");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Punto de entrada principal de la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        launch(args);
    }
}