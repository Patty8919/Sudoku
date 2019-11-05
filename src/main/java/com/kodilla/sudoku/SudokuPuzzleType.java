package com.kodilla.sudoku;

public enum SudokuPuzzleType {

    NINETONINE(9, 9, 3, 3, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, "9 To 9");

    private final int rows;
    private final int columns;
    private final int boxWitdh;
    private final int boxHeight;
    private final String[] validValues;
    private final String desc;

    private SudokuPuzzleType(int rows, int columns, int boxWitdh, int boxHeight, String[] validValues, String desc) {
        this.validValues = validValues;
        this.rows = rows;
        this.columns = columns;
        this.boxWitdh = boxWitdh;
        this.boxHeight = boxHeight;
        this.desc = desc;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBoxWitdh() {
        return boxWitdh;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public String[] getValidValues() {
        return validValues;
    }

    public String toString() {
        return desc;
    }
}
