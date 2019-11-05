package com.kodilla.sudoku;

import java.util.*;


public class SudokuGame {

    public static SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
        SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(),
                puzzleType.getBoxWitdh(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
        SudokuPuzzle copy = new SudokuPuzzle(puzzle);

        Random randomGenerator = new Random();

        List<String> notUsedValidValues = new ArrayList<>(Arrays.asList(puzzle.getValidValues()));
        for (int r = 0; r < puzzle.getNumRows(); r++) {
            int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
            copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
            notUsedValidValues.remove(randomValue);
        }
        backtrackSudokuSolver(0, 0, copy);

        int numberOfValuesToKeep = (int) (0.22222 * (copy.getNumRows() * copy.getNumRows()));

        for (int i = 0; i < numberOfValuesToKeep; ) {
            int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
            int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());

            if (puzzle.isSlotAvailable(randomRow, randomColumn)) {
                puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
                i++;
            }
        }
        return puzzle;
    }

    private static boolean backtrackSudokuSolver(int r, int c, SudokuPuzzle puzzle) {

        if (!puzzle.inRange(r, c)) {
            return false;
        }

        if (puzzle.isSlotAvailable(r, c)) {

            //loop to find the correct value for the space
            for (int i = 0; i < puzzle.getValidValues().length; i++) {

                //if the current number works in the space
                if (!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c, puzzle.getValidValues()[i]) && !puzzle.numInBox(r, c, puzzle.getValidValues()[i])) {

                    //make the move
                    puzzle.makeMove(r, c, puzzle.getValidValues()[i], true);

                    //if puzzle solved return true
                    if (puzzle.boardFull()) {
                        return true;
                    }

                    //go to next move
                    if (r == puzzle.getNumRows() - 1) {
                        if (backtrackSudokuSolver(0, c + 1, puzzle)) return true;
                    } else {
                        if (backtrackSudokuSolver(r + 1, c, puzzle)) return true;
                    }
                }
            }
        }

        //if the current space is not empty
        else {
            //got to the next move
            if (r == puzzle.getNumRows() - 1) {
                return backtrackSudokuSolver(0, c + 1, puzzle);
            } else {
                return backtrackSudokuSolver(r + 1, c, puzzle);
            }
        }

        //undo move
        puzzle.makeSlotEmpty(r, c);

        //backtrack
        return false;
    }
}
