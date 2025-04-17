/**
 * Clase que representa la vista del tablero de Sudoku.
 * Extiende de BorderPane para organizar los componentes de la interfaz.
 * @author Juan Moreno (2417575), Miguel Romero (2421802)
 * @version 1.0
 */
package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class SudokuView extends BorderPane {
    /**
     * GridPane que contiene las celdas del tablero de Sudoku.
     * Esta cuadrícula se configura en el archivo FXML asociado.
     */
    @FXML
    private GridPane gridPane;
}