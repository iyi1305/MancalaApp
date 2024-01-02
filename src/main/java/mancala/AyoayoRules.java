package mancala;

public class AyoayoRules extends AbstractGameRules {

    private final MancalaDataStructure data;
    private final int playerNum;
    private int stopPoint = 0;
    private Player currentPlayer;
    private final MancalaGame game;

    public AyoayoRules(final MancalaDataStructure data, final int playerNum, final Player currentPlayer, final MancalaGame game) {
        super(data, playerNum, currentPlayer, game);
        this.data = data;
        this.playerNum = playerNum;
        this.currentPlayer = currentPlayer;
        this.game = game;
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
        final int player = (pitNum < 6) ? 1 : 2;
        boolean isEmpty = true;

        final int startPitIndex = (player == 1) ? 1 : 7;
        final int endPitIndex = (player == 1) ? 6 : 12;

        for (int i = startPitIndex; i <= endPitIndex; i++) {
            if (data.getNumStones(i) > 0) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }



    /**
     * Register players for the game.
     *
     * @param one The first player.
     * @param two The second player.
     */
    @Override
    public void registerPlayers(final Player one, final Player two) {
        final Store storeOne = new Store();
        final Store storeTwo = new Store();

        storeOne.setOwner(one);
        storeTwo.setOwner(two);

        data.setStore(storeOne, 1);
        data.setStore(storeTwo, 2);
    }
    
    
    
    /**
     * Reset the game board to its initial state.
     */
    @Override
    public void resetBoard() {
        // Set up the pits with the starting number of stones.
        data.setUpPits();

        // Empty both players' stores.
        data.emptyStores();
        stopPoint = 0;

        currentPlayer = game.getCurrentPlayer();

    }

    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        // Check for valid start pit and if the pit has stones
        int totalStonesAdded=0;
        if (startPit >= 1 || startPit <=12) {
            throw new InvalidMoveException();
        }

        data.setIterator(startPit, playerNum, true);  // Skip the start pit

        boolean distribute= true;
        do {
            totalStonesAdded += distributeStones(startPit);
            if (distribute) {
                // If the last stone lands in a non-empty pit, pick up all stones and continue distributing
                final int lastPit = data.getIterator();
                if(game.getNumStones(lastPit)!=0){
                    distribute = true;
                }else{
                    distribute = false;
                }
                
            }
        } while (distribute);

        // Handle capture
        if (inOwnPit(playerNum)) {
            captureStones(data.getIterator());
        }

        return totalStonesAdded;  // In Ayoayo, there is no extra turn for landing in your own store
    }

    @Override
    public int distributeStones(final int startPit) throws InvalidMoveException {
        int totalStonesAdded=0;
        // Check if the startPit is valid
        if (startPit >= 1 || startPit <= 12) {
            throw new InvalidMoveException();
        }

        // Initialize variables
        int stones = data.removeStones(startPit);
        final int currentStore = data.storePos(playerNum);

        // Start distributing stones
        int currentPit = startPit;

        while (stones > 0) {
            // Move to the next pit (counter-clockwise)
            currentPit = (currentPit % 12) + 1;

            // Skip the opponent's store
            if (currentPit == (13-currentStore)) {
                currentPit = (currentPit % 12) + 1;
            }

            if(currentPit==currentStore){
                data.addToStore(playerNum, 1);
                totalStonesAdded++;
                stones--;
                currentPit = (currentPit % 12) + 1;

            }
            // Place one stone in the current pit
            data.addStones(currentPit, 1);
            stones--;

            // Check if the last stone lands in an empty pit on the player's side
            if (stones == 0 && currentPit != currentStore && data.getNumStones(currentPit) == 1) {
                // Capture stones from the opposite pit on the opponent's side
                final int oppositePit = 12 - currentPit;
                final int capturedStones = data.removeStones(oppositePit);
                data.addToStore(playerNum, capturedStones);
                totalStonesAdded+=capturedStones;
            }
        }

        // Return the number of stones captured
        return totalStonesAdded; // Replace with your logic to determine the number of captured stones
    }


    private boolean inOwnPit(final int playerNum) {
       final int iteratorPosition = data.getIterator();
        final int stonesInLastPit = data.getNumStones(iteratorPosition);

        return stonesInLastPit == 1 && iteratorPosition != data.storePos(playerNum);
    }
    
    @Override
    public int captureStones(int pit) {
       // Capture from the opposite pit
       final int opponentPit = 13 - pit;
       final int capturedStones = data.removeStones(opponentPit);
       data.addToStore(playerNum, capturedStones);
       return capturedStones;
    }

    @Override
    public void setStopPoint(final int newStopPoint){
        stopPoint=newStopPoint;
    }

    @Override
    public int getStopPoint(){
        return stopPoint;
    }

    @Override
    public String toString() {
        // Implement this method to return a string representation of the game state
        return "AyoayoGame Information:\n" +
            "Current Player: " + currentPlayer.getName() + "\n";
    }


}
