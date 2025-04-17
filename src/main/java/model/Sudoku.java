/**
 * Clase que representa el modelo de un juego de Sudoku.
 * Contiene la lógica del juego y el estado del tablero.
 * Este Sudoku es de tamaño 6x6 con subgrillas de 2x3.
 * @author Juan Moreno (2417575), Miguel Romero (2421802)
 * @version 1.0
 */
package model;

public class Sudoku {
    /** Matriz que representa el tablero de Sudoku */
    private int[][] board;
    
    /** Tamaño del tablero de Sudoku (6x6) */
    private static final int SIZE = 6;

    /**
     * Constructor que inicializa un nuevo tablero de Sudoku vacío.
     * Todas las celdas se inicializan a cero.
     */
    public Sudoku() {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * Obtiene el tamaño del tablero.
     * @return El tamaño del tablero (6)
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Verifica si un número es válido en una posición específica.
     * Comprueba si el número no se repite en la fila, columna o subgrilla.
     * @param row Fila donde se desea colocar el número
     * @param col Columna donde se desea colocar el número
     * @param num Número a verificar
     * @return true si el número es válido en esa posición, false en caso contrario
     */
    public boolean isValid(int row, int col, int num) {
        // Temporalmente removemos el valor actual para verificar
        int current = board[row][col];
        board[row][col] = 0;

        boolean result = isValid(row, col, num, -1, -1);

        // Restauramos el valor
        board[row][col] = current;

        return result;
    }

    /**
     * Método interno para verificar si un número es válido en una posición específica.
     * Permite excluir una celda específica de la verificación.
     * @param row Fila donde se desea colocar el número
     * @param col Columna donde se desea colocar el número
     * @param num Número a verificar
     * @param checkRow Fila a excluir de la verificación (-1 si no se excluye ninguna)
     * @param checkCol Columna a excluir de la verificación (-1 si no se excluye ninguna)
     * @return true si el número es válido en esa posición, false en caso contrario
     */
    public boolean isValid(int row, int col, int num, int checkRow, int checkCol) {
        // Verifica si el número ya existe en la fila
        for (int i = 0; i < SIZE; i++) {
            if (i != checkCol && board[row][i] == num) {
                return false;
            }
        }

        // Verifica si el número ya existe en la columna
        for (int i = 0; i < SIZE; i++) {
            if (i != checkRow && board[i][col] == num) {
                return false;
            }
        }

        // Verifica si el número ya existe en la subgrilla 2x3
        int boxRowStart = row - (row % 2);
        int boxColStart = col - (col % 3);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int r = boxRowStart + i;
                int c = boxColStart + j;
                if ((r != checkRow || c != checkCol) && board[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Establece un valor en una celda específica del tablero.
     * @param row Fila de la celda
     * @param col Columna de la celda
     * @param value Valor a establecer
     */
    public void setValue(int row, int col, int value) {
        board[row][col] = value;
    }

    /**
     * Obtiene el valor de una celda específica del tablero.
     * @param row Fila de la celda
     * @param col Columna de la celda
     * @return El valor de la celda
     */
    public int getValue(int row, int col) {
        return board[row][col];
    }

    /**
     * Verifica si el tablero está completamente lleno.
     * @return true si todas las celdas tienen un valor distinto de cero, false en caso contrario
     */
    public boolean isComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Rellena el tablero con algunos valores iniciales aleatorios válidos.
     * Coloca 2 números aleatorios en cada subgrilla.
     */
    public void fillGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }

        for (int boxRow = 0; boxRow < SIZE; boxRow += 2) {
            for (int boxCol = 0; boxCol < SIZE; boxCol += 3) {
                int placed = 0;
                while (placed < 2) {
                    int row = boxRow + (int)(Math.random() * 2);
                    int col = boxCol + (int)(Math.random() * 3);

                    int num = 1 + (int)(Math.random() * SIZE);

                    if (board[row][col] == 0 && isValid(row, col, num)) {
                        board[row][col] = num;
                        placed++;
                    }
                }
            }
        }
    }

    /**
     * Verifica si la solución del tablero es válida.
     * Comprueba que no haya números repetidos en filas, columnas o subgrillas.
     * @param board El tablero a verificar
     * @return true si la solución es válida, false en caso contrario
     */
    public boolean ValidSolution(int[][] board) {
        for (int i = 0; i < 6; i++) {
            boolean[] view = new boolean[7];
            for (int j = 0; j < 6; j++) {
                int valor = board[i][j];
                if (valor < 1 || valor > 6 || view[valor]) {
                    return false;
                }
                view[valor] = true;
            }
        }
        for (int j = 0; j < 6; j++) {
            boolean[] view = new boolean[7];
            for (int i = 0; i < 6; i++) {
                int valor = board[i][j];
                if (valor < 1 || valor > 6 || view[valor]) {
                    return false;
                }
                view[valor] = true;
            }
        }

        for (int bloqueFila = 0; bloqueFila < 6; bloqueFila += 2) {
            for (int bloqueCol = 0; bloqueCol < 6; bloqueCol += 3) {
                boolean[] view = new boolean[7];
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        int valor = board[bloqueFila + i][bloqueCol + j];
                        if (valor < 1 || valor > 6 || view[valor]) {
                            return false;
                        }
                        view[valor] = true;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Obtiene una copia del tablero actual.
     * @return Una copia del tablero de Sudoku
     */
    public int[][] getBoard(){
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
}