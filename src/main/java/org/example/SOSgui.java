package org.example;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SOSgui {
    private static GameLogic gameLogic;
    private static int newSize = 8;
    private static JLabel turnDisplay;
    private static JPanel gridPanel;
    private static JButton[][] gridButtons;

    public static void main(String[] args) {
        // Initialize game logic
        gameLogic = new GameLogic(newSize);
        
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
        simple.setSelected(true); // Default to Simple game mode

        simple.addActionListener(e -> {
            GameLogic.setGameMode("Simple");
            resetGame();
        });
        general.addActionListener(e -> {
            GameLogic.setGameMode("General");
            resetGame();
        });

        panel.add(simple);
        panel.add(general);
        panel.setBounds(0, 0, 300, 50);
        frame.add(panel);

        //GRID FOR GAMEPLAY
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        gridPanel = new JPanel();
        gridPanel.setPreferredSize(new Dimension(300, 300));
        initializeGrid();

        mainPanel.add(gridPanel, BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.CENTER);

        //BLUE PLAYER AND RADIO BUTTONS
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(130, 10, 10, 10));

        JPanel blue = new JPanel();
        blue.setLayout(new BoxLayout(blue, BoxLayout.Y_AXIS));
        JLabel bluePlayer = new JLabel("Blue Player");
        JRadioButton blueS = new JRadioButton("S");
        JRadioButton blueO = new JRadioButton("O");
        JCheckBox blueComputer = new JCheckBox("Computer");
        ButtonGroup groupBlue = new ButtonGroup();
        groupBlue.add(blueS);
        groupBlue.add(blueO);
        blueS.setSelected(true); // Default to S for blue player

        blueS.addActionListener(e -> {
            GameLogic.setBluePlayerLetterChoice("S");
            GameLogic.setBluePlayerComputer(false);
            blueComputer.setSelected(false);
            resetGame();
        });
        blueO.addActionListener(e -> {
            GameLogic.setBluePlayerLetterChoice("O");
            GameLogic.setBluePlayerComputer(false);
            blueComputer.setSelected(false);
            resetGame();
        });
        blueComputer.addActionListener(e -> {
            GameLogic.setBluePlayerComputer(blueComputer.isSelected());
        });

        blue.add(bluePlayer);
        blue.add(blueS);
        blue.add(blueO);
        blue.add(blueComputer);
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
        JCheckBox redComputer = new JCheckBox("Computer");
        ButtonGroup groupRed = new ButtonGroup();
        groupRed.add(redS);
        groupRed.add(redO);
        redS.setSelected(true); // Default to S for red player

        redS.addActionListener(e -> {
            GameLogic.setRedPlayerLetterChoice("S");
            GameLogic.setRedPlayerComputer(false);
            redComputer.setSelected(false);
            resetGame();
        });
        redO.addActionListener(e -> {
            GameLogic.setRedPlayerLetterChoice("O");
            GameLogic.setRedPlayerComputer(false);
            redComputer.setSelected(false);
            resetGame();
        });
        redComputer.addActionListener(e -> {
            GameLogic.setRedPlayerComputer(redComputer.isSelected());
        });

        red.add(redPlayer);
        red.add(redS);
        red.add(redO);
        red.add(redComputer);
        rightPanel.add(red, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);

        //BOARD SIZE LABEL AND BOX
        JLabel board = new JLabel("Board Size");
        JPanel size = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField boardSize = new JTextField(2);
        boardSize.setText(String.valueOf(newSize)); // Set default size
        size.add(board);
        size.add(boardSize);
        frame.add(size, BorderLayout.NORTH);

        //NEW GAME BUTTON
        JPanel newP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newButton = new JButton("New Game");

        newButton.addActionListener(e -> {
            try {
                newSize = Integer.parseInt(boardSize.getText());
                if (newSize < 3) {
                    JOptionPane.showMessageDialog(null, "Board size must be at least 3");
                    return;
                }
                resetGame();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
            }
        });

        newP.add(newButton);
        frame.add(newP, BorderLayout.SOUTH);

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

    private static void resetGame() {
        System.out.println("Resetting game");
        gameLogic = new GameLogic(newSize);
        gameLogic.resetGame();
        updateBoardSize(newSize);
        updateTurnLabel();
        updateBoard();
        
        // If both players are computers, start the game with a computer move
        if (GameLogic.isRedComputerPlayer() && GameLogic.isBlueComputerPlayer() && !gameLogic.isGameOver()) {
            System.out.println("Starting computer vs computer game");
            Timer timer = new Timer(500, ev -> {
                System.out.println("Making first computer move");
                // Get the current player's letter and color
                Player currentPlayer = gameLogic.getCurrentPlayer();
                String currentLetter = currentPlayer.getLetterChoice();
                Color currentColor = gameLogic.isPlayerTurn() ? Color.RED : Color.BLUE;
                
                // Make the move
                gameLogic.makeComputerMove();
                
                // Update the UI directly
                Board board = gameLogic.getBoard();
                for (int i = 0; i < newSize; i++) {
                    for (int j = 0; j < newSize; j++) {
                        String cell = board.get(i, j);
                        if (!cell.equals(" ")) {
                            gridButtons[i][j].setText(cell);
                            gridButtons[i][j].setForeground(board.getColor(i, j));
                        }
                    }
                }
                
                updateTurnLabel();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private static void initializeGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(newSize, newSize, 5, 5));
        gridButtons = new JButton[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                gridButtons[i][j] = new JButton(" ");
                gridButtons[i][j].setPreferredSize(new Dimension(50, 50));
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridButtons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                gridButtons[i][j].setOpaque(true);
                gridButtons[i][j].setBackground(Color.WHITE);
                gridButtons[i][j].setContentAreaFilled(true);

                final int row = i;
                final int col = j;
                gridButtons[i][j].addActionListener(e -> {
                    if (gameLogic.getBoard().isCellEmpty(row, col) && !gameLogic.isGameOver()) {
                        // Don't allow human move if it's computer's turn
                        if ((GameLogic.isRedComputerPlayer() && gameLogic.isPlayerTurn()) || 
                            (GameLogic.isBlueComputerPlayer() && !gameLogic.isPlayerTurn())) {
                            return;
                        }

                        // Get the current player's letter and color
                        Player currentPlayer = gameLogic.getCurrentPlayer();
                        String currentLetter = currentPlayer.getLetterChoice();
                        Color currentColor = gameLogic.isPlayerTurn() ? Color.RED : Color.BLUE;
                        
                        // Make the move and update the UI
                        gridButtons[row][col].setText(currentLetter);
                        gridButtons[row][col].setForeground(currentColor);
                        
                        // Process the move in game logic
                        if (gameLogic.playerMove(row, col)) {
                            // Update the turn display and board
                            updateTurnLabel();
                            updateBoard();
                            
                            // If it's computer's turn now and game isn't over, make the computer move
                            if (!gameLogic.isGameOver() && 
                                ((GameLogic.isRedComputerPlayer() && gameLogic.isPlayerTurn()) || 
                                 (GameLogic.isBlueComputerPlayer() && !gameLogic.isPlayerTurn()))) {
                                // Small delay to make computer moves visible
                                Timer timer = new Timer(500, ev -> {
                                    gameLogic.makeComputerMove();
                                    updateTurnLabel();
                                    updateBoard();
                                });
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }
                    }
                });
                gridPanel.add(gridButtons[i][j]);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public static void updateBoard() {
        System.out.println("Updating board display");
        Board board = gameLogic.getBoard();
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                String cell = board.get(i, j);
                gridButtons[i][j].setText(cell);
                if (!cell.equals(" ")) {
                    System.out.println("Updating cell (" + i + "," + j + ") with " + cell + " and color " + board.getColor(i, j));
                    gridButtons[i][j].setForeground(board.getColor(i, j));
                } else {
                    gridButtons[i][j].setForeground(Color.BLACK);
                }
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
        System.out.println("Board update complete");
    }

    private static void updateBoardSize(int size) {
        newSize = size;
        initializeGrid();
    }

    public static void updateTurnLabel() {
        String currentPlayer = gameLogic.isPlayerTurn() ? "Red" : "Blue";
        turnDisplay.setText("Current Turn: " + currentPlayer + " Player");
    }
}