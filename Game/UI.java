package Game;

import javax.swing.*;
import java.awt.*;
import java.lang.Object;

public class UI extends JFrame{


    private final int gridSize = 9; // Grid size for a 9x9 board
    private JPanel gridPanel;

    UI(Board board){
        super("Blockdoku");

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(gridSize, gridSize));

        initGrid();

        this.add(gridPanel);

        setState(board);

        this.setSize(900, 900);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

    }

    private void initGrid() {
        // Initialize the grid with default blocks
        for (int i = 0; i < gridSize * gridSize; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(panel);
        }
    }

    public void setState(Board board) {
        int[][] boardState = board.getBoard();
        Component[] components = gridPanel.getComponents();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JPanel panel = (JPanel) components[row * gridSize + col];
                if (boardState[row][col] == 1) {
                    panel.setBackground(new Color(17,125,184));
                } else {
                    panel.setBackground(new Color(210, 214, 217)); // Reset to default if not 1
                }
            }
        }

        // This will ensure the UI refreshes to display the new state
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}
