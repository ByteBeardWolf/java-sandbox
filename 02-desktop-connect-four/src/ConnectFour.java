package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {
    private final JButton[][] buttons = new JButton[6][7];
    private char currentPlayerSign = 'X';
    private final int rows = 6;
    private final int cols = 7;
    private boolean won = false;

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 400);
        setVisible(true);
        setLayout(new FlowLayout());
        setResizable(false);
        setTitle("Connect Four");

        setLocationRelativeTo(null);

        JPanel board = new JPanel();
        board.setPreferredSize(new Dimension(300, 300));
        board.setSize(300, 300);
        board.setLayout(new GridLayout(rows, cols));
        add(board);

        JPanel footer = new JPanel();
        footer.setSize(300, 50);
        add(footer);

        initializeBoard(board);
        updateFooter(footer);

        addButtonActions();

        setVisible(true);
    }

    private void updateFooter(JPanel footer) {
        JButton button = new JButton("Reset");
        button.setSize(100, 30);
        button.setName("ButtonReset");
        button.addActionListener(_ -> {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    buttons[i][j].setText(" ");
                    buttons[i][j].setBackground(new Color(180, 240 /*- 30 * ((i + j) % 2)*/, 180));
                }
            }
            currentPlayerSign = 'X';
            won = false;
        });
        footer.add(button);
    }

    private void initializeBoard(JPanel board) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setName("Button" + (char)(65+j) + (rows-i));
                buttons[i][j].setText(" ");
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(new Color(180, 240-30*((i+j)%2), 180));
                buttons[i][j].setMargin(new Insets(10, 10, 10, 10));
                //buttons[i][j].setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                board.add(buttons[i][j]);
            }
        }
    }

    private void addButtonActions() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(_ -> {
                    if (buttons[finalI][finalJ].getText().equals(" ") && !won) {
                        //System.out.println("Field " + buttons[finalI][finalJ].getName() + "  is empty. Changing value to " + currentPlayerSign);
                        int emptyI = getFirstEmptyRow(finalJ);
                        if (emptyI != -1) {
                            buttons[emptyI][finalJ].setText(String.valueOf(currentPlayerSign));
                            won = checkWinCondition(emptyI, finalJ);
                            if (!won) {
                                currentPlayerSign = currentPlayerSign == 'X' ? 'O' : 'X';
                            }
                        }
                    }
                });
            }
        }
    }

    private boolean checkWinCondition(int emptyI, int finalJ) {
        // Check all four directions: vertical, horizontal, and two diagonals
        return checkDirection(emptyI, finalJ, 1, 0) ||      // vertical
               checkDirection(emptyI, finalJ, 0, 1) ||      // horizontal
               checkDirection(emptyI, finalJ, 1, 1) ||      // diagonal down-right
               checkDirection(emptyI, finalJ, 1, -1);       // diagonal down-left
    }

    private boolean checkDirection(int startI, int startJ, int deltaI, int deltaJ) {
        int countFour = 0;
        int lastWinI = -1, lastWinJ = -1;

        for (int k = -3; k <= 3; k++) {
            int i = startI + k * deltaI;
            int j = startJ + k * deltaJ;

            if (i >= 0 && i < rows && j >= 0 && j < cols) {
                if (buttons[i][j].getText().equals(String.valueOf(currentPlayerSign))) {
                    countFour++;
                    lastWinI = i;
                    lastWinJ = j;
                } else {
                    countFour = 0; // Reset count if we find a different piece
                }

                if (countFour == 4) {
                    // Update color of winning pieces
                    for (int m = 0; m < 4; m++) {
                        int winI = lastWinI - m * deltaI;
                        int winJ = lastWinJ - m * deltaJ;
                        buttons[winI][winJ].setBackground(new Color(210, 120, 210));
                    }
                    return true; // Win condition met
                }
            } else {
                countFour = 0; // Reset count if out of bounds
            }
        }

        return false;
    }

    private int getFirstEmptyRow(int finalJ) {
        for (int i = buttons.length - 1; i >= 0; i--) {
            if (buttons[i][finalJ].getText().equals(" ")) {
                return i;
            }
        }
        return -1;
    }
}
