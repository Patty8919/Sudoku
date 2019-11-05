package com.kodilla.sudoku;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;


public class SudokuPanel extends JPanel {

    private SudokuPuzzle puzzle;
    private int currentlySelectedCol;
    private int currentlySelectedRow;
    private int usedWidth;
    private int usedHeight;
    private int fontSize;

    public SudokuPanel() {
        this.setPreferredSize(new Dimension(540, 450));
        this.addMouseListener(new SudokuPanelMouseAdapter());
        this.puzzle = new SudokuGame().generateRandomSudoku(SudokuPuzzleType.NINETONINE);
        currentlySelectedCol = -1;
        currentlySelectedRow = -1;
        usedWidth = 0;
        usedHeight = 0;
        fontSize = 26;
    }

    public SudokuPanel(SudokuPuzzle puzzle) {
        this.setPreferredSize(new Dimension(540, 450));
        this.addMouseListener(new SudokuPanelMouseAdapter());
        this.puzzle = puzzle;
        currentlySelectedCol = -1;
        currentlySelectedRow = -1;
        usedWidth = 0;
        usedHeight = 0;
        fontSize = 26;
    }

    public void newSudokuPuzzle(SudokuPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(1.0f, 1.0f, 1.0f));

        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
        int slotWidth = this.getWidth() / 9;
        int slotHeight = this.getHeight() / 9;

        for (int x = 0; x <= this.getWidth(); x += slotWidth) {
            if ((x / slotWidth) % 3 == 0) {
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x, 0, x, this.getHeight());
            } else {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(x, 0, x, this.getHeight());
            }

        }
        for (int y = 0; y <= this.getHeight(); y += slotHeight) {
            if ((y / slotHeight) % 3 == 0) {
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, y, this.getWidth(), y);
            } else {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(0, y, this.getWidth(), y);
            }

        }
        Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
        g2d.setFont(f);
        FontRenderContext fContext = g2d.getFontRenderContext();
        for (int row = 0; row < puzzle.getNumRows(); row++) {
            for (int col = 0; col < puzzle.getNumColumns(); col++) {
                if (!puzzle.isSlotAvailable(row, col)) {
                    int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
                    int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
                    g2d.drawString(puzzle.getValue(row, col), (col * slotWidth) + ((slotWidth / 2) - (textWidth / 2)), (row * slotHeight) + ((slotHeight / 2) + (textHeight / 2)));
                }
            }
        }
        if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
            g2d.setColor(new Color(0.0f, 0.0f, 1.0f, 0.3f));
            g2d.fillRect(currentlySelectedCol * slotWidth, currentlySelectedRow * slotHeight, slotWidth, slotHeight);
        }
    }

    public void messageFromNumActionListener(String buttonValue) {
        if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
            puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true);
            repaint();
        }
    }

    public class NumActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            messageFromNumActionListener(((JButton) e.getSource()).getText());
        }
    }

    private class SudokuPanelMouseAdapter extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                int slotWidth = usedWidth / puzzle.getNumColumns();
                int slotHeight = usedHeight / puzzle.getNumRows();
                currentlySelectedRow = e.getY() / slotHeight;
                currentlySelectedCol = e.getX() / slotWidth;
                e.getComponent().repaint();
            }
        }
    }
}
