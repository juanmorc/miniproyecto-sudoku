package model;

public class Sudoku {
    private int[][] board;
    private static final int SIZE = 6;

    public Sudoku() {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int getSize() {
        return SIZE;
    }

    public boolean isValid(int row, int col, int num) {
        // Temporalmente removemos el valor actual para verificar
        int current = board[row][col];
        board[row][col] = 0;

        boolean result = isValid(row, col, num, -1, -1);

        // Restauramos el valor
        board[row][col] = current;

        return result;
    }

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

    public void setValue(int row, int col, int value) {
        board[row][col] = value;
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

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