/**
 * Controlador de la interfaz de usuario para el juego Sudoku.
 * Maneja las interacciones del usuario y actualiza la vista según el modelo.
 * @author Juan Moreno (2417575), Miguel Romero (2421802)
 * @version 1.0
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Sudoku;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;
import java.util.List;

public class SudokuController {
    /** GridPane que contiene las celdas del tablero Sudoku */
    @FXML
    private GridPane grid;
    
    /** Etiqueta para mostrar el título y mensajes del juego */
    @FXML
    private Label titleLabel;
    
    /** Modelo que contiene la lógica del juego Sudoku */
    private Sudoku sudokuModel;
    
    /**
     * Método de inicialización que se llama automáticamente después de cargar el FXML.
     * Configura el tablero de Sudoku y actualiza la interfaz de usuario.
     */
    @FXML
    public void initialize() {
        System.out.println("Controlador inicializado");
        if (titleLabel != null) {
            titleLabel.setText("Bienvenido al Sudoku!");
        } else {
            System.out.println("Error: titleLabel no está vinculado correctamente");
        }
        sudokuModel = new Sudoku();
        setupSudokuBoard();
        sudokuModel.fillGrid();
        updateUIFromModel();
    }

    /**
     * Configura el tablero de Sudoku con los estilos y listeners para cada celda.
     * Maneja la validación de entrada y actualiza el modelo.
     */
    private void setupSudokuBoard() {
        if (grid == null) {
            System.out.println("Error: No se ha podido vincular el GridPane 'grid'");
            return;
        }

        for (int i = 0; i < grid.getChildren().size(); i++) {
            if (grid.getChildren().get(i) instanceof TextField) {
                TextField cell = (TextField) grid.getChildren().get(i);
                final int rowIndex = GridPane.getRowIndex(cell) != null ? GridPane.getRowIndex(cell) : 0;
                final int colIndex = GridPane.getColumnIndex(cell) != null ? GridPane.getColumnIndex(cell) : 0;

                int blockRow = rowIndex / 2;  // 0, 1, 2 (para las filas 0-1, 2-3, 4-5)
                int blockCol = colIndex / 3;  // 0, 1 (para las columnas 0-2, 3-5)

                // Colorear las subgrillas alternadamente
                String baseColor;
                if ((blockRow + blockCol) % 2 == 0) {
                    // Subgrillas en posiciones pares (0,0), (0,2), (2,0), (2,2)
                    baseColor = "-fx-background-color: #F0F0F0;"; // Gris claro
                } else {
                    // Subgrillas en posiciones impares (0,1), (1,0), (1,2), (2,1)
                    baseColor = "-fx-background-color: #FFFFFF;"; // Blanco
                }

                cell.setStyle(baseColor + "-fx-border-color: #CCCCCC; -fx-border-width: 1;");

                cell.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        cell.setText(newValue.replaceAll("\\D", ""));
                    } else if (newValue.length() > 1) {
                        cell.setText(newValue.substring(0, 1));
                    } else if (!newValue.isEmpty() && Integer.parseInt(newValue) > 6) {
                        cell.setText("6");
                    }

                    if (!newValue.isEmpty()) {
                        int value = Integer.parseInt(newValue);
                        int currentValue = sudokuModel.getValue(rowIndex, colIndex);

                        if (sudokuModel.isValid(rowIndex, colIndex, value)) {
                            cell.setStyle("-fx-border-color: black; -fx-border-width: 1;");
                            sudokuModel.setValue(rowIndex, colIndex, value);
                        } else {
                            cell.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                            sudokuModel.setValue(rowIndex, colIndex, value);
                        }
                    } else {
                        cell.setStyle("-fx-border-color: black; -fx-border-width: 1;");
                        sudokuModel.setValue(rowIndex, colIndex, 0);
                    }
                });
            }
        }
    }

    /**
     * Maneja el evento de finalización del juego Sudoku.
     * Verifica si el tablero está completo y si la solución es correcta.
     * @param event El evento de acción generado al hacer clic en el botón finalizar
     */
    @FXML
    public void handleFinalizeSudoku(ActionEvent event) {
        System.out.println("Verificando solución del Sudoku...");

        if (sudokuModel.isComplete()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sudoku Finalizado");
            titleLabel.setText("¡Sudoku completado!");
            if (sudokuModel.ValidSolution(sudokuModel.getBoard())){
                alert.setHeaderText("La solución es correcta!");
                alert.setContentText("Finalizaste el juego.");
            }else {
                alert.setHeaderText("Solución incorrecta!");
                alert.setContentText("Por favor verifica las casillas.");
            }
            alert.showAndWait();
        } else {
            titleLabel.setText("El Sudoku no está completo");
        }
    }

    /**
     * Actualiza la interfaz de usuario a partir del estado actual del modelo.
     * Establece los valores iniciales y configura los estilos de las celdas.
     */
    private void updateUIFromModel() {
        int rowCount = 0;
        int colCount = 0;

        for (int i = 0; i < grid.getChildren().size(); i++) {
            if (grid.getChildren().get(i) instanceof TextField) {
                TextField cell = (TextField) grid.getChildren().get(i);
                int value = sudokuModel.getValue(rowCount, colCount);

                // Determinar a qué subgrilla pertenece esta celda
                int blockRow = rowCount / 2;
                int blockCol = colCount / 3;

                // Color base según la subgrilla
                String baseColor;
                if ((blockRow + blockCol) % 2 == 0) {
                    baseColor = "#F0F0F0"; // Gris claro
                } else {
                    baseColor = "#FFFFFF"; // Blanco
                }

                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    // Las celdas iniciales tienen un tono más oscuro
                    cell.setStyle("-fx-background-color: " + darkenColor(baseColor) +
                            "; -fx-border-color: #CCCCCC; -fx-border-width: 1;");
                    cell.setEditable(false);
                } else {
                    cell.setText("");
                    cell.setStyle("-fx-background-color: " + baseColor +
                            "; -fx-border-color: #CCCCCC; -fx-border-width: 1;");
                    cell.setEditable(true);
                }

                colCount++;
                if (colCount >= 6) {
                    colCount = 0;
                    rowCount++;
                }
            }
        }
    }

    /**
     * Oscurece un color hexadecimal para diferenciar las celdas iniciales.
     * @param color El color base en formato hexadecimal
     * @return El color oscurecido en formato hexadecimal
     */
    private String darkenColor(String color) {
        if (color.equals("#F0F0F0")) {
            return "#D0D0D0"; // Versión más oscura del gris claro
        } else {
            return "#E0E0E0"; // Versión más oscura del blanco
        }
    }

    /**
     * Maneja el evento de ayuda para el jugador.
     * Busca una celda vacía y muestra los valores posibles para esa celda.
     * @param event El evento de acción generado al hacer clic en el botón de ayuda
     */
    @FXML
    public void handleHelp(ActionEvent event) {
        System.out.println("Mostrando ayuda para Sudoku...");
        boolean foundEmptyCell = false;
        int helpRow = -1;
        int helpCol = -1;
        List<Integer> validNumbers = new ArrayList<>();

        outerLoop:
        for (int row = 0; row < sudokuModel.getSize(); row++) {
            for (int col = 0; col < sudokuModel.getSize(); col++) {
                if (sudokuModel.getValue(row, col) == 0) {
                    helpRow = row;
                    helpCol = col;
                    foundEmptyCell = true;

                    for (int num = 1; num <= sudokuModel.getSize(); num++) {
                        if (sudokuModel.isValid(row, col, num)) {
                            validNumbers.add(num);
                        }
                    }

                    break outerLoop;
                }
            }
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ayuda de Sudoku");

        if (foundEmptyCell) {
            alert.setHeaderText("Sugerencias para celda [" + (helpRow+1) + "," + (helpCol+1) + "]");

            if (validNumbers.isEmpty()) {
                alert.setContentText("No hay valores posibles para esta celda. Revisa y corrige errores.");
            } else {
                StringBuilder content = new StringBuilder("Valores posibles: ");
                for (int i = 0; i < validNumbers.size(); i++) {
                    content.append(validNumbers.get(i));
                    if (i < validNumbers.size() - 1) {
                        content.append(", ");
                    }
                }
                alert.setContentText(content.toString());
            }
        } else {
            alert.setHeaderText("No hay celdas vacías");
            alert.setContentText("El tablero está lleno o no se encontraron celdas vacías.");
        }

        alert.showAndWait();
    }
}