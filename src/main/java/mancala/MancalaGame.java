package mancala;

import java.io.Serializable;
import java.util.ArrayList;

public class MancalaGame implements Serializable{

    private AbstractGameRules board;
    private Player currentPlayer;
    private int currentPlayerNum=0;
    private Player playerOne;
    private Player playerTwo;
    private MancalaDataStructure data;

    /**
     * Constructor for MancalaGame that allows choosing between Kalah and Ayoayo games.
     *
     * @param userProfile1 The UserProfile for Player 1.
     * @param userProfile2 The UserProfile for Player 2.
     * @param gameRules    The GameRules object specifying the type of game (Kalah or Ayoayo).
     */
    public MancalaGame(UserProfile userProfile1, UserProfile userProfile2, AbstractGameRules gameRules, MancalaDataStructure newData) {
        currentPlayer = new Player(userProfile1);
        playerOne = new Player(userProfile1);
        playerTwo = new Player(userProfile2);
        board = gameRules;
        this.data = newData;
    }

    /**
     * Get the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return board.getNumStones(pitNum);
    }

    /**
     * Get the stone count in a player's store.
     *
     * @param player The player for whom to get the store count.
     * @return The stone count in the player's store.
     */
    public int getStoreCount(final Player player) {
        return player.getStoreCount();
    }

    /**
     * Get the winner of the game.
     *
     * @return The player who won the game, or null if it's a tie or the game is ongoing.
     */
    public Player getWinner() throws GameNotOverException{
        if (!isGameOver()) {
            throw new GameNotOverException();
        }

        int storeCountPlayerOne = data.getStoreCount(1);
        int storeCountPlayerTwo = data.getStoreCount(2);

        if (storeCountPlayerOne > storeCountPlayerTwo) {
            return playerOne;
        } else if (storeCountPlayerTwo > storeCountPlayerOne) {
            return playerTwo;
        } else {
            return null;
        }
    }

    /**
     * Check if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {

        return board.isSideEmpty(0) || board.isSideEmpty(6);
       
    
    }

    /** 
    * Check if the game is over.
    *
    * @return board of game
    */
    public AbstractGameRules getBoard(){
        return board;
    }

   /**
 * Perform a move in the game.
 *
 * @param startPit The starting pit for the move.
 * @return The number of stones added to the player's store.
 */
public int move(final int startPit) throws InvalidMoveException, PitNotFoundException {
    if (isGameOver()) {
        throw new InvalidMoveException();
    }

    if (board.getNumStones(startPit) == 0) {
        throw new InvalidMoveException();
    }

    int totalStonesAdded = 0; // Initialize totalStonesAdded

    try {
        totalStonesAdded = board.moveStones(startPit, currentPlayerNum);

        // Check for PitNotFoundException and rethrow it if necessary
        if (totalStonesAdded == -1) {
            throw new PitNotFoundException();
        }
    } catch (PitNotFoundException e) {
        e.getMessage();
        // Handle PitNotFoundException or log it as needed
    }

    int stoppingPoint = board.getStopPoint();
    int capturedStones;
    capturedStones = board.captureStones(stoppingPoint);
    data.addToStore(currentPlayerNum, capturedStones);
    totalStonesAdded += capturedStones;

    return totalStonesAdded;
}

    /**
     * Set the game board.
     *
     * @param theBoard The game board to set.
     */
    public void setBoard(final AbstractGameRules theBoard) {
        board = theBoard;
    }

    /**
     * Set the current player.
     *
     * @param player The current player to set.
     */
    public void setCurrentPlayer(final Player player) {
        currentPlayer = player;
    }

    /**
     * Set the players for the game.
     *
     * @param onePlayer The first player.
     * @param twoPlayer The second player.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        playerOne = onePlayer;
        playerTwo = twoPlayer;
    }

    /**
     * Start a new game.
     */
    public void startNewGame( final UserProfile newUser1, final UserProfile newUser2) {
        board.resetBoard();

        Player newPlayer1 = new Player(newUser1);
        Player newPlayer2 = new Player(newUser2);
        board.registerPlayers(newPlayer1, newPlayer2);

    }

    /**
     * Get a string representation of the game state.
     *
     * @return A string representing the current state of the game.
     */
    @Override
    public String toString() {
        // Implement this method to return a string representation of the game state
        return "Mancala Game Information:\n" +
               "Current Player: " + currentPlayer.getName() + "\n";
    }
}
