package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import mancala.*;

public class MancalaApp extends JFrame {
    private MancalaGame currentGame;
    private AbstractGameRules currentRules; 
    private UserProfile userProfile1;
    private UserProfile userProfile2;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private int playerNum;
    private JPanel gamePanel;
    private JLabel statusLabel;
    private PositionAwareButton[][] boardButtons;
    private MancalaDataStructure data;
  
    public MancalaApp() {
        data = new MancalaDataStructure();
        initializeFrame();
        initializeMenu();
        initializeGamePanel();
        initializeStatusLabel();
    }

    private void initializeFrame() {
        setTitle("Mancala Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
    
        JMenuItem newKalahGame = new JMenuItem("New Kalah Game");
        newKalahGame.addActionListener(e -> startNewGame("Kalah"));
        JMenuItem newAyoGame = new JMenuItem("New Ayoayo Game");
        newAyoGame.addActionListener(e -> startNewGame("Ayoayo"));
    
        gameMenu.add(newKalahGame);
        gameMenu.add(newAyoGame);

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(this::saveGame);
        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(this::loadGame);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        gameMenu.add(saveGame);
        gameMenu.add(loadGame);
        gameMenu.add(exit);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
    }

    private void initializeGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        JButton newKalahGameButton = new JButton("Start New Kalah Game");
        newKalahGameButton.addActionListener(e -> startNewGame("Kalah"));
        JButton newAyoGameButton = new JButton("Start New Ayoayo Game");
        newAyoGameButton.addActionListener(e -> startNewGame("Ayoayo"));
    
        gamePanel.add(newKalahGameButton);
        gamePanel.add(newAyoGameButton);
        add(gamePanel, BorderLayout.CENTER);
    }
    

    private void initializeStatusLabel() {
        statusLabel = new JLabel("Welcome to Mancala!");
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void startNewGame(String gameType) {
        
        String playerOneName = JOptionPane.showInputDialog("Enter Player 1's name:");
        String playerTwoName = JOptionPane.showInputDialog("Enter Player 2's name:");

        userProfile1 = new UserProfile(playerOneName);
        userProfile2 = new UserProfile(playerTwoName);

        playerOne = new Player(userProfile1);
        playerTwo = new Player(userProfile2);
        currentPlayer = playerOne;
        playerNum = 1;
    
        // Create the game rules based on the selected game type
        if (gameType.equals("Kalah")) {
            currentRules = new KalahRules(data,playerNum,playerOne,currentGame); // Initialize KalahRules here
        } else { // Ayoayo game
            currentRules = new AyoayoRules(data,playerNum,playerOne,currentGame); // Initialize AyoayoRules here
        }
    
        // Create a new MancalaGame instance
        currentGame = new MancalaGame(userProfile1, userProfile2, currentRules, data);
    
        // Set the current player to player one and update UI components
        currentGame.setCurrentPlayer(playerOne);
        statusLabel.setText("New " + gameType + " game started.");
        data.setUpPits();
        clearGameBoard();
        createGameBoard();
        updateBoardUI(); // You need to implement this method to update the board
    }
    
    
    private void saveGame(ActionEvent e) {
        try {
            Saver.saveObject(currentGame, "currentGame.ser");
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving game.");
        }
    }

    private void createGameBoard() {
        // Create a grid layout for the game panel
        gamePanel.setLayout(new GridLayout(2, 6)); // For 2 rows and 6 columns
        boardButtons = new PositionAwareButton[2][6]; // Initialize the board buttons array
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                PositionAwareButton button = new PositionAwareButton();
                // Set button properties and add an ActionListener
                button.setAcross(x);
                button.setDown(y);
                button.addActionListener(this::pitClicked);
                gamePanel.add(button);
                boardButtons[y][x] = button;
            }
        }
    }

    private void clearGameBoard() {
        gamePanel.removeAll(); // Remove all components from the game panel
        gamePanel.revalidate(); // Revalidate the panel layout
        gamePanel.repaint(); // Repaint the panel
    }
    
    
    private void loadGame(ActionEvent e) {
        try {
            currentGame = (MancalaGame) Saver.loadObject("currentGame.ser");
            // Update UI to reflect loaded game state
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading game.");
        }
        updateBoardUI();
    }
    
    private void pitClicked(ActionEvent e) {
        if (currentGame == null || currentGame.isGameOver()) {
            return; // Do nothing if no game is active or the game is over
        }
    
        PositionAwareButton button = (PositionAwareButton) e.getSource();
        int pitIndex = button.getAcross() + button.getDown() * 6; // Calculate the pit index based on button position
    
        try {
            int stonesAddedToStore = currentRules.moveStones(pitIndex, playerNum);
            
            switchPlayer();
            // Refresh the UI to show the updated game state
            updateBoardUI();
    
            // Check if the move ended the game
            if (currentGame.isGameOver()) {
                handleGameOver();
            } else {
                // Update the status label for the next turn
                updateStatusLabelForNextTurn();
            }
        } catch (InvalidMoveException ex) {
            JOptionPane.showMessageDialog(this, "Invalid move. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void updateBoardUI() {
        // Update the text on each button to reflect the current number of stones
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 6; x++) {
                int pitIndex = x + y * 6;
                int stonesCount = currentGame.getBoard().getNumStones(pitIndex);
                boardButtons[y][x].setText(Integer.toString(stonesCount));
            }
        }
        
        // Refresh the game panel to reflect the changes
        gamePanel.revalidate();
        gamePanel.repaint();
    }
    
    
    private void handleGameOver() {
        try {
            Player winner = currentGame.getWinner();
            if (winner != null) {
                statusLabel.setText("Game Over! Winner: " + winner.getName());
            } else {
                statusLabel.setText("Game Over! It's a tie.");
            }
        } catch (GameNotOverException ex) {
            
        }
    }
    
    
    private void switchPlayer() {
        // Assuming the playerNum is 1 for playerOne and 2 for playerTwo
        // Also assuming there are methods like getCurrentPlayerNum() and setCurrentPlayerNum(int num) in MancalaGam
    
        if (playerNum == 1) {
            playerNum = 2;
            currentPlayer = playerTwo; // Set to playerTwo
            currentGame.setCurrentPlayer(playerTwo); // Corrected to playerTwo
        } else {
            playerNum = 1;
            currentPlayer = playerOne; // Set to playerOne
            currentGame.setCurrentPlayer(playerOne); // Corrected to playerOne
        }
    
        updateStatusLabelForNextTurn();
    }
    
    
    
    private void updateStatusLabelForNextTurn() {
        // Update statusLabel to show whose turn is next
        String playerName = currentGame.getCurrentPlayer().getName();
        statusLabel.setText("Player " + playerName + "'s turn");
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MancalaApp::new);
    }
}
