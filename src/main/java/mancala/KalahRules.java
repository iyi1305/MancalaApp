package mancala;

public class KalahRules extends AbstractGameRules {

    private int stopPoint = 0;
    private final MancalaDataStructure data;
    private final int playerNum;
    private final Player currentPlayer;
    private MancalaGame game;

    /**
     * Constructor to initialize the KalahRules with a MancalaDataStructure.
     */
    public KalahRules(MancalaDataStructure data, int playerNum, Player currentPlayer, MancalaGame game) {
        super(data, playerNum, currentPlayer, game); // Pass the arguments to the superclass constructor
        this.data = data;
        this.playerNum = playerNum;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    @Override
    public int getNumStones(final int pitNum) {
        return data.getNumStones(pitNum);
    }


    /**
     * Check if a pit on a player's side is empty.
     *
     * @param pitNum The number of the pit.
     * @return True if the pit is empty, false otherwise.
     */
    @Override
    public boolean isSideEmpty(final int pitNum) {
        // Determine the player based on the pit number.
        int player = (pitNum <= 6) ? 1 : 2;
        boolean isEmpty =true;

        // Define the range of pit indices to check based on the player.
        // Player 1's pits are from index 0 to 5 (1-6 in the data structure) and
        // Player 2's pits are from index 6 to 11 (7-12 in the data structure).
        final int startPitIndex = (player == 1) ? 0 : 6;
        final int endPitIndex = (player == 1) ? 5 : 11;

        // Check if any pit on the player's side is not empty.
        for (int i = startPitIndex; i <= endPitIndex; i++) {
            if (data.getNumStones(i) > 0) {
                isEmpty = false; // Found a pit that is not empty.
            }
        }
        return isEmpty; // All pits on the player's side are empty.
    }


    /**
     * Registers two players for a new game, associating them with their respective stores.
     * This method prepares the game for play by initializing player-specific data.
     *
     * @param one The first player, typically associated with the first side of the board.
     * @param two The second player, typically associated with the second side of the board.
     */
    @Override
    public void registerPlayers(final Player one, final Player two) {
        // Create stores for each player
        final Store storeOne = new Store(); // Create a new store
        final Store storeTwo = new Store(); // Create another new store
    
        // Set the owners for each store
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
    
        // Set the stores in the MancalaDataStructure
        data.setStore(storeOne, 1); // Assuming 1 represents Player One
        data.setStore(storeTwo, 2); // Assuming 2 represents Player Two
    }
    
    
    
    /**
     * Resets the game board to its initial state. This involves setting all pits to have the starting number
     * of stones and ensuring both players' stores are empty.
     */
    @Override
    public void resetBoard() {
        // Set up the pits with the starting number of stones.
        data.setUpPits();

        // Empty both players' stores.
        data.emptyStores();
        stopPoint = 0;

    }


        /**
     * Move stones according to the rules of the Kalah game.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException{
        // Check if the starting pit is valid
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }
        
        // Declare stonesAdded outside of the try block
        int stonesAdded = 0;
        
        try {   
            stonesAdded = distributeStones(data.pitPos(startPit));
        } catch (InvalidMoveException e) {
            e.getMessage(); // Print the stack trace for debugging
        }
        
        return stonesAdded;
    }
    /**
     * Distributes stones from a starting pit according to Kalah rules.
     *
     * @param startPit  The starting pit for the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int distributeStones(final int startPit) throws InvalidMoveException {
        // Validate the starting pit
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }

        // Get the number of stones in the starting pit and remove them
        int stones = data.removeStones(startPit);
        int storeStones = 0;

        // Set up the iterator starting from the next pit
        data.setIterator(startPit + 1, playerNum, false);

        while (stones > 0) {
            // Move to the next pit using the iterator
            Countable currentElement = data.next();

            // Skip opponent's store
            if (currentElement instanceof Store && ((Store) currentElement).getOwner() != currentPlayer) {
                continue;
            }

            // Add a stone to the pit or the player's store
            if (currentElement instanceof Pit) {
                data.addStones(data.getIterator(), 1); // Add to a regular pit
            } else if (currentElement instanceof Store) {
                data.addToStore(playerNum, 1); // Add to the player's store
                storeStones++;
            }

            stones--;

            // Check if the last stone lands in the player's own empty pit for a possible capture
            if (stones == 0 && currentElement instanceof Pit && data.getNumStones(data.getIterator()) == 1
                    && inOwnPit(playerNum)) {
                final int capturedStones = captureStones(getStopPoint());
                storeStones += capturedStones;
            }
        }
        setStopPoint(data.getIterator());
        return storeStones;
    }


    /**
     * Checks if the last stone was placed in the player's own pit.
     *
     * @param playerNum The player number to check for.
     * @return True if the last stone was placed in the player's own pit, false otherwise.
     */
    private boolean inOwnPit(final int playerNum) {
        int iteratorPosition = data.getIterator();
        final int stonesInLastPit = data.getNumStones(iteratorPosition);

        return stonesInLastPit == 1 && iteratorPosition != data.storePos(playerNum);
    }


    /**
     * Capture stones from the opponent's pit if the last stone lands in an empty pit on the player's side.
     *
     * @param stoppingPoint The pit where the last stone was placed.
     * @param playerNum     The player making the move.
     * @return The number of stones captured and added to the player's store.
     */
    @Override
    public int captureStones(final int stoppingPoint) {
        final int oppositePitIndex = 12 - stoppingPoint; // Calculate the index of the opposite pit
        int capturedStones=0;

        // Check if the stopping point is on the player's side and it's an empty pit
        if (stoppingPoint >= 1 && stoppingPoint <= 6 && data.getNumStones(stoppingPoint) == 1) {
            capturedStones = data.removeStones(stoppingPoint); // Remove the last stone from the stopping point
            capturedStones += data.removeStones(oppositePitIndex); // Capture stones from the opposite pit
            data.addToStore(playerNum, capturedStones); // Add the captured stones to the player's store
        } 
        return capturedStones;
    }

    /**
     * Sets the new stop point for the game.
     *
     * @param newStopPoint The new stop point to set.
     */
    @Override
    public void setStopPoint(final int newStopPoint) {
        stopPoint = newStopPoint;
    }

    /**
     * Gets the stop point of the game.
     *
     * @return The stop point of the game.
     */
    @Override
    public int getStopPoint() {
        return stopPoint;
    }

    /**
     * Returns a string representation of the game state.
     *
     * @return A string containing information about the current player.
     */
    @Override
    public String toString() {
        // Implement this method to return a string representation of the game state
        return "Kalah Game Information:\n" +
            "Current Player: " + currentPlayer.getName() + "\n";
    }


}

