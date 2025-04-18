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
    
        gameLogic = new GameLogic(newSize);
        
        // SET UP PLAYERS - BOTH PLAYERS START AS HUMAN WITH S
        GameLogic.setBluePlayerComputer(false);
        GameLogic.setRedPlayerComputer(false);
        GameLogic.setBluePlayerLetterChoice("S");
        GameLogic.setRedPlayerLetterChoice("S");
        
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
        simple.setSelected(true); // DEFAULT TO SIMPLE GAME MODE

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
            resetGame();
        });
        blueO.addActionListener(e -> {
            GameLogic.setBluePlayerLetterChoice("O");
            resetGame();
        });
        blueComputer.addActionListener(e -> {
            GameLogic.setBluePlayerComputer(blueComputer.isSelected());
            resetGame();
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
            resetGame();
        });
        redO.addActionListener(e -> {
            GameLogic.setRedPlayerLetterChoice("O");
            resetGame();
        });
        redComputer.addActionListener(e -> {
            GameLogic.setRedPlayerComputer(redComputer.isSelected());
            resetGame();
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
        boardSize.setText(String.valueOf(newSize)); // SET DEFAULT SIZE
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

    public static void resetGame() {
        // GET CURRENT PLAYER SETTINGS BEFORE CREATING NEW GAMELOGIC INSTANCE
        String blueLetter = GameLogic.getBluePlayerLetterChoice();
        String redLetter = GameLogic.getRedPlayerLetterChoice();
        boolean isBlueComputer = GameLogic.isBlueComputerPlayer();
        boolean isRedComputer = GameLogic.isRedComputerPlayer();
        
        // CREATE NEW GAMELOGIC INSTANCE
        gameLogic = new GameLogic(newSize);
        
        // RESTORE PLAYER SETTINGS
        GameLogic.setBluePlayerLetterChoice(blueLetter);
        GameLogic.setRedPlayerLetterChoice(redLetter);
        GameLogic.setBluePlayerComputer(isBlueComputer);
        GameLogic.setRedPlayerComputer(isRedComputer);
        
        // INITIALIZE THE GRID
        initializeGrid();
        
        // UPDATE UI
        updateBoardDisplay();
        updateTurnLabel();
        
        
        if (gameLogic.isComputerTurn()) {
            Timer timer = new Timer(500, e -> {
                if (gameLogic.isComputerTurn()) {
                    gameLogic.makeComputerMove();
                    updateBoardDisplay();
                    updateTurnLabel();
                }
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
                        // Only allow moves when it's the human player's turn
                        if (!gameLogic.isHumanTurn()) {
                            System.out.println("Move blocked - not human's turn");
                            return;
                        }

                        System.out.println("Processing human move at (" + row + "," + col + ")");
                        
                        // Process the move in game logic
                        if (gameLogic.playerMove(row, col)) {
                            // Update the UI after the move
                            updateBoardDisplay();
                            updateTurnLabel();
                            
                            // If it's computer's turn after the move, schedule their move with a delay
                            if (gameLogic.isComputerTurn()) {
                                Timer timer = new Timer(1000, event -> {
                                    if (gameLogic.isComputerTurn()) {
                                        gameLogic.makeComputerMove();
                                        updateBoardDisplay();
                                        updateTurnLabel();
                                    }
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

    public static void updateBoardDisplay() {
        System.out.println("Updating board display");
        Board board = gameLogic.getBoard();
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                String cell = board.get(i, j);
                Color cellColor = board.getColor(i, j);
                if (!cell.equals(" ")) {
                    gridButtons[i][j].setText(cell);
                    gridButtons[i][j].setForeground(cellColor);
                    System.out.println("Updating cell (" + i + "," + j + ") with " + cell + " and color " + cellColor);
                } else {
                    gridButtons[i][j].setText(" ");
                    gridButtons[i][j].setForeground(Color.BLACK);
                }
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
        System.out.println("Board update complete");
    }

    private static void updateBoardSize(int size) {
        if (size < 3) {
            JOptionPane.showMessageDialog(null, "Board size must be at least 3");
            return;
        }
        newSize = size;
        gameLogic = new GameLogic(newSize);
        initializeGrid();
        updateBoardDisplay();
        updateTurnLabel();
    }

    public static void updateTurnLabel() {
        String currentPlayer = gameLogic.isPlayerTurn() ? "Red" : "Blue";
        Player player = gameLogic.getCurrentPlayer();
        String letter = player.getLetterChoice();
        String playerType = gameLogic.isComputerTurn() ? " (Computer)" : " (Human)";
        turnDisplay.setText("Current Turn: " + currentPlayer + playerType + " - Letter: " + letter);
    }
}