package mancala;

public abstract class AbstractGameRules{

    private final MancalaDataStructure data;
    private final int playerNum;
    private int stopPoint = 0;
    private Player currentPlayer; // Declare currentPlayer
    private final MancalaGame game;
    private Store storeOne;
    private Store storeTwo;

    public AbstractGameRules(MancalaDataStructure data, int playerNum, Player currentPlayer, MancalaGame game) {
        this.data = data;
        this.playerNum = playerNum; // Initialize playerNum
        this.currentPlayer = currentPlayer; // Initialize currentPlayer
        this.game = game;
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public abstract int getNumStones(int pitNum);

    /**
     * Check if a pit on a player's side is empty.
     *
     * @param pitNum The number of the pit.
     * @return True if the pit is empty, false otherwise.
     */
    public abstract boolean isSideEmpty(int pitNum);

    /**
     * Move stones according to the game's rules.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     */
    public abstract int moveStones(int startPit, int playerNum)throws InvalidMoveException;

    /**
     * Distribute stones from a selected pit according to the game's rules.
     *
     * @param startPit The starting pit for distributing stones.
     * @return The number of stones distributed.
     */
    public abstract int distributeStones(int startPit)throws InvalidMoveException;

    /**
     * Capture stones according to the game's rules.
     *
     * @param stoppingPoint The pit where the last stone was placed.
     * @return The number of stones captured.
     */
    public abstract int captureStones(int stoppingPoint);

    /**
     * Register players for the game.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public abstract void registerPlayers(Player one, Player two);

    /**
     * Reset the game board to its initial state.
     */
    public abstract void resetBoard();

    /**
     * Sets the stopping point in the game, typically the index of the last pit where a stone was placed.
     * This is used to determine game actions like capturing stones or granting an extra turn.
     *
     * @param newStopPoint The index of the pit that is the new stopping point.
     */
    public abstract void setStopPoint(int newStopPoint);

    /**
     * Retrieves the current stopping point in the game.
     * The stopping point is the index of the last pit where a stone was placed during a move.
     * It is used to determine subsequent actions in the game rules.
     *
     * @return The index of the pit that is the current stopping point.
     */
    public abstract int getStopPoint();

    /**
     * Get a string representation of the game board.
     *
     * @return A string representing the current state of the game board.
     */
    @Override
    public abstract String toString();
}
