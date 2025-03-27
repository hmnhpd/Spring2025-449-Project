package org.example;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SOSgui {

    public static GameLogic gameLogic;
    public static int newSize = 8;
    private static JLabel turnDisplay;//global variable
    public static String bluePlayerLetterChoice;
    public static String redPlayerLetterChoice;

    public static void main(String[] args) {

        //MAIN WINDOW
        JFrame frame = new JFrame("SOS Game");
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //LABEL FOR SOS GAME
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("SOS Game");
        panel.add(label);

        //RADIO BUTTONS FOR GAME CHOICE
        JRadioButton simple = new JRadioButton("Simple");
        JRadioButton general = new JRadioButton("General");
        ButtonGroup group = new ButtonGroup();
        group.add(simple);
        group.add(general);

        simple.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameLogic.setGameMode("Simple");
            }
        });

        general.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameLogic.setGameMode("General");
            }
        });

        panel.add(simple);
        panel.add(general);
        panel.setBounds(0, 0, 300, 50);

        frame.add(panel);

        //GRID FOR GAMEPLAY
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JPanel gridPanel = new JPanel(new GridLayout(8, 8, 5, 5));
        gridPanel.setPreferredSize(new Dimension(300, 300));

        gameLogic = new GameLogic(newSize);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                JButton space = new JButton();
                space.setPreferredSize(new Dimension(50, 50));
                space.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                space.setText("");

                //Listener for button pressed by user
                final int i1 = i;
                final int j1 = j;
                space.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameLogic.playerMove(i1,j1)){
                            space.setText(gameLogic.getCurrentPlayerLetter());
                            space.setForeground(gameLogic.playerTurn ? Color.RED : Color.BLUE);
                            updateTurnLabel();
                        }
                    }
                });
                gridPanel.add(space);
            }
        }

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        //BLUE PLAYER AND RADIO BUTTONS
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(130, 10, 10, 10));

        JPanel blue = new JPanel();
        blue.setLayout(new BoxLayout(blue, BoxLayout.Y_AXIS));
        JLabel bluePlayer = new JLabel("Blue Player");
        JRadioButton blueS = new JRadioButton("S");
        JRadioButton blueO = new JRadioButton("O");
        ButtonGroup groupBlue = new ButtonGroup();
        groupBlue.add(blueS);
        groupBlue.add(blueO);

        //LISTENER FOR BLUE PLAYER ACTION
        blueS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameLogic.setBluePlayerLetterChoice("S");
            }
        });
        blueO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameLogic.setBluePlayerLetterChoice("O");
            }
        });


        blue.add(bluePlayer);
        blue.add(blueS);
        blue.add(blueO);

        leftPanel.add(blue, BorderLayout.CENTER);
        frame.add(leftPanel, BorderLayout.WEST);

        //RED PLAYER AND RADIO BUTTONS
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(130, 10, 10, 10));

        JPanel red = new JPanel();
        red.setLayout(new BoxLayout(red, BoxLayout.Y_AXIS));
        JLabel redPlayer = new JLabel("Red Player");
        JRadioButton redS = new JRadioButton("S");
        JRadioButton redO = new JRadioButton("O");
        ButtonGroup groupRed = new ButtonGroup();
        groupRed.add(redS);
        groupRed.add(redO);

        //LISTENER FOR RED PLAYER CHOICE
        redS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameLogic.setRedPlayerLetterChoice("S");
            }
        });

        redO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameLogic.setRedPlayerLetterChoice("O");
            }
        });

        red.add(redPlayer);
        red.add(redS);
        red.add(redO);

        rightPanel.add(red, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);

        //BOARD SIZE LABEL AND BOX
        JLabel board = new JLabel("Board Size");
        JPanel size = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField boardSize = new JTextField(2);
        size.add(board);
        size.add(boardSize);
        frame.add(size, BorderLayout.NORTH);
        frame.setVisible(true);


        //NEW GAME BUTTON
        JPanel newP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newButton = new JButton("New Game");

        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    newSize = Integer.parseInt(boardSize.getText());
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                    return;
                }

                gameLogic = new GameLogic(newSize);
                updateBoardSize(gridPanel, newSize);
                updateTurnLabel();
            }
        });

        newP.add(newButton);
        frame.add(newP, BorderLayout.SOUTH);
        frame.setVisible(true);

        JPanel bottomScreen = new JPanel(new BorderLayout());
        bottomScreen.add(newP, BorderLayout.EAST);

        //TEXT TO DISPLAY CURRENT PLAYER TURN
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.add(Box.createHorizontalGlue());
        turnDisplay = new JLabel("Current Turn: Blue Player", JLabel.CENTER);
        textPanel.add(turnDisplay);
        textPanel.add(Box.createHorizontalGlue());
        textPanel.setPreferredSize(new Dimension(20, 20));
        bottomScreen.add(textPanel, BorderLayout.CENTER);

        frame.add(bottomScreen, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    private static void updateBoardSize(JPanel gridPanel, int newSize){
        gridPanel.removeAll();
        int boardSize = newSize;
        gridPanel.setLayout(new GridLayout(boardSize, boardSize, 5, 5));

        JButton[][] gridButtons = new JButton[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                gridButtons[i][j] = new JButton(" ");
                gridButtons[i][j].setPreferredSize(new Dimension(50, 50));
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                final int row = i;
                final int col = j;
                gridButtons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameLogic.playerMove(row, col)){
                            gridButtons[row][col].setText(gameLogic.getCurrentPlayerLetter());
                            gridButtons[row][col].setForeground(gameLogic.playerTurn ? Color.BLUE : Color.RED);
                            updateTurnLabel();
                        }
                    }
                });
                gridPanel.add(gridButtons[i][j]);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public static void updateTurnLabel(){
        if (gameLogic.playerTurn){
            String currentTurn = gameLogic.playerTurn ? "Blue Player" : "Red Player";
            turnDisplay.setText("Current Turn: " + currentTurn);
        }
    }
}