package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {
    private final JButton[][] buttons = new JButton[6][7];
    private char currentPlayerSign = 'X';
    private int rows = 6;
    private int cols = 7;

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(null);
        setTitle("Connect Four");

        setLocationRelativeTo(null);

        setLayout(new GridLayout(rows, cols));

        initializeBoard(rows, cols);

        addButtonActions();

        setVisible(true);
    }

    private void initializeBoard(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setName("Button" + String.valueOf((char)(65+j)) + (rows-i));
                buttons[i][j].setText(" ");
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setMargin(new Insets(2, 10, 2, 10));
                add(buttons[i][j]);
            }
        }
    }

    private void addButtonActions() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(e -> {
                    System.out.println("Field " + buttons[finalI][finalJ].getName() + "  is empty. Changing value to " + currentPlayerSign);
                    int emptyI = getFirstEmptyRow(finalJ);
                    if(emptyI != -1) {
                        buttons[emptyI][finalJ].setText(String.valueOf(currentPlayerSign));
                        currentPlayerSign = currentPlayerSign == 'X' ? 'O' : 'X';
                    }
                });
            }
        }
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
