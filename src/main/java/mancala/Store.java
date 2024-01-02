package mancala;

/**
 * Represents a store that can hold a count of stones and is associated with a player.
 */
public class Store implements Countable {
    private Player owner;
    private int totalStones;

    /**
     * Constructs a new store with no stones.
     */
    public Store() {
        totalStones = 0;
    }

    /**
     * Sets the owner of the store.
     *
     * @param player The player who owns the store.
     */
    public void setOwner(final Player player) {
        owner = player;
    }

    /**
     * Gets the owner of the store.
     *
     * @return The player who owns the store.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Gets the count of stones in the store.
     *
     * @return The count of stones in the store.
     */
    @Override
    public int getStoneCount() {
        return totalStones;
    }

    /**
     * Adds one stone to the store.
     */
    @Override
    public void addStone() {
        totalStones += 1;
    }

    /**
     * Adds a specified number of stones to the store.
     *
     * @param numToAdd The number of stones to add.
     */
    @Override
    public void addStones(final int numToAdd) {
        if (numToAdd < 0) {
            throw new IllegalArgumentException("Number of stones to add cannot be negative.");
        }
        totalStones += numToAdd;
    }


    /**
     * Removes and returns all stones from the store.
     *
     * @return The number of stones removed from the store.
     */
    @Override
    public int removeStones() {
        final int stonesInStore = totalStones;
        totalStones = 0;
        return stonesInStore;
    }
}
