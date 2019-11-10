package com.kodilla.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {

    private SudokuPanel sPanel;
    private JPanel buttonSelectionPanel;

    public SudokuFrame() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(800, 600));

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Game");

        JMenuItem nineToNine = new JMenuItem("Nine to nine");
        nineToNine.addActionListener(new NewGameListener(SudokuPuzzleType.NINETONINE, 26));

        file.add(nineToNine);

        menuBar.add(file);
        this.setJMenuBar(menuBar);

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(800, 600));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

        sPanel = new SudokuPanel();
        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(SudokuPuzzleType.NINETONINE, 26);

    }


    public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize) {
        SudokuPuzzle generatedPuzzle = new SudokuGame().generateRandomSudoku(puzzleType);
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        buttonSelectionPanel.removeAll();

        for (String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            b.setPreferredSize(new Dimension(45, 45));
            b.addActionListener(sPanel.new NumActionListener());
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();

    }

    private class NewGameListener implements ActionListener {

        private SudokuPuzzleType puzzleType;
        private int fontSize;

        public NewGameListener(SudokuPuzzleType puzzleType, int fontSize) {
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(puzzleType, fontSize);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SudokuFrame frame = new SudokuFrame();
                frame.setVisible(true);

            }
        });

    }
}
