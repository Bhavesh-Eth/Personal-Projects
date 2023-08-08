import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private char[][] gameboard = {
            {' ', '|', ' ', '|', ' '},
            {'-', '*', '-', '*', '-'},
            {' ', '|', ' ', '|', ' '},
            {'-', '*', '-', '*', '-'},
            {' ', '|', ' ', '|', ' '}
    };

    private JButton[] buttons = new JButton[9];
    private ArrayList<Integer> playerposition = new ArrayList<Integer>();
    private ArrayList<Integer> pcposition = new ArrayList<Integer>();

    private char currentPlayer = 'X';

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int buttonIndex = -1;

        for (int i = 0; i < 9; i++) {
            if (clickedButton == buttons[i]) {
                buttonIndex = i + 1;
                break;
            }
        }

        if (buttonIndex != -1 && !playerposition.contains(buttonIndex) && !pcposition.contains(buttonIndex)) {
            placepiece(buttonIndex, "player");
            clickedButton.setText("X");

            String result = winner();
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, result);
                resetGame();
            } else {
                performPCMove();
                result = winner();
                if (!result.isEmpty()) {
                    JOptionPane.showMessageDialog(this, result);
                    resetGame();
                }
            }
        }
    }

    private void placepiece(int pos, String user) {
        char symbol = (user.equals("player")) ? 'X' : 'O';

        switch (pos) {
            case 1, 2, 3 -> gameboard[0][(pos - 1) * 2] = symbol;
            case 4, 5, 6 -> gameboard[2][(pos - 4) * 2] = symbol;
            case 7, 8, 9 -> gameboard[4][(pos - 7) * 2] = symbol;
        }

        if (user.equals("player")) {
            playerposition.add(pos);
        } else {
            pcposition.add(pos);
        }
    }

    private void performPCMove() {
        Random r = new Random();
        int pcpos = r.nextInt(9) + 1;
        while (playerposition.contains(pcpos) || pcposition.contains(pcpos)) {
            pcpos = r.nextInt(9) + 1;
        }
        placepiece(pcpos, "pc");
        buttons[pcpos - 1].setText("O");
    }

    public String winner() {
        List toprow = Arrays.asList(1, 2, 3);
        List midrow = Arrays.asList(4, 5, 6);
        List botrow = Arrays.asList(7, 8, 9);
        List leftcol = Arrays.asList(1, 4, 7);
        List midcol = Arrays.asList(2, 5, 8);
        List rightcol = Arrays.asList(3, 6, 9);
        List diag1 = Arrays.asList(1, 5, 9);
        List diag2 = Arrays.asList(3, 5, 7);

        List<List> winning = new ArrayList<List>();
        winning.add(toprow);
        winning.add(midrow);
        winning.add(botrow);
        winning.add(leftcol);
        winning.add(midcol);
        winning.add(rightcol);
        winning.add(diag1);
        winning.add(diag2);

        for (List l : winning) {
            if (playerposition.containsAll(l)) {
                return "PLAYER WON";
            } else if (pcposition.containsAll(l)) {
                return "PC WON";
            }
        }
        if (playerposition.size() + pcposition.size() == 9) {
                return "DRAW";
        }
        return "";
    }

    private void resetGame() {
        playerposition.clear();
        pcposition.clear();

        for (int i = 0; i < 5; i++) {
            gameboard[i] = new char[]{' ', '|', ' ', '|', ' '};
        }

        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}