package com.kodilla.sudoku;

public class SudokuPuzzle {

    protected String [][] board;
    // Table to determine if a slot is mutable
    protected boolean [][] mutable;
    private final int rows;
    private final int columns;
    private final int boxWidth;
    private final int boxHeight;
    private final String [] validvalues;

    public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
        this.rows = rows;
        this.columns = columns;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.validvalues = validValues;
        this.board = new String[this.rows][this.columns];
        this.mutable = new boolean[this.rows][this.columns];
        initializeBoard();
        initializeMutableSlots();
    }

    public SudokuPuzzle(SudokuPuzzle puzzle) {
        this.rows = puzzle.rows;
        this.columns = puzzle.columns;
        this.boxWidth = puzzle.boxWidth;
        this.boxHeight = puzzle.boxHeight;
        this.validvalues = puzzle.validvalues;
        this.board = new String[rows][columns];
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                board[r][c] = puzzle.board[r][c];
            }
        }
        this.mutable = new boolean[rows][columns];
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                this.mutable[r][c] = puzzle.mutable[r][c];
            }
        }
    }

    public int getNumRows() {
        return this.rows;
    }

    public int getNumColumns() {
        return this.columns;
    }

    public int getBoxWidth() {
        return this.boxWidth;
    }

    public int getBoxHeight() {
        return this.boxHeight;
    }

    public String [] getValidValues() {
        return this.validvalues;
    }

    public void makeMove(int row,int col,String value,boolean isMutable) {
        if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
            this.board[row][col] = value;
            this.mutable[row][col] = isMutable;
        }
    }

    public boolean isValidMove(int row,int col,String value) {
        if(this.inRange(row,col)) {
            if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
                return true;
            }
        }
        return false;
    }

    public boolean numInCol(int col,String value) {
        if(col <= this.columns) {
            for(int row = 0; row < this.rows; row++) {
                if(this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInRow(int row,String value) {
        if(row <= this.rows) {
            for(int col = 0; col < this.columns; col++) {
                if(this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInBox(int row,int col,String value) {
        if(this.inRange(row, col)) {
            int boxRow = row / this.boxHeight;
            int boxCol = col / this.boxWidth;

            int startingRow = (boxRow*this.boxHeight);
            int startingCol = (boxCol*this.boxWidth);

            for(int r = startingRow; r <= (startingRow+this.boxHeight)-1; r++) {
                for(int c = startingCol; c <= (startingCol+this.boxWidth)-1; c++) {
                    if(this.board[r][c].equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSlotAvailable(int row,int col) {
        return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
    }

    public boolean isSlotMutable(int row,int col) {
        return this.mutable[row][col];
    }

    public String getValue(int row,int col) {
        if(this.inRange(row,col)) {
            return this.board[row][col];
        }
        return "";
    }

    public String [][] getBoard() {
        return this.board;
    }

    private boolean isValidValue(String value) {
        for(String str : this.validvalues) {
            if(str.equals(value)) return true;
        }
        return false;
    }

    public boolean inRange(int row,int col) {
        return row <= this.rows && col <= this.columns && row >= 0 && col >= 0;
    }

    public boolean boardFull() {
        for(int r = 0; r < this.rows; r++) {
            for(int c = 0; c < this.columns; c++) {
                if(this.board[r][c].equals("")) return false;
            }
        }
        return true;
    }

    public void makeSlotEmpty(int row,int col) {
        this.board[row][col] = "";
    }

    @Override
    public String toString() {
        String str = "Game Board:\n";
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.columns; col++) {
                str += this.board[row][col] + " ";
            }
            str += "\n";
        }
        return str+"\n";
    }

    private void initializeBoard() {
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.columns; col++) {
                this.board[row][col] = "";
            }
        }
    }

    private void initializeMutableSlots() {
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.columns; col++) {
                this.mutable[row][col] = true;
            }
        }
    }
}