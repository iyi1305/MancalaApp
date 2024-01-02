package mancala;

/**
 * Represents a pit that can hold a count of stones.
 */
public class Pit implements Countable {
    private int stoneCount;

    /**
     * Constructs a new Pit with no stones.
     */
    public Pit() {
        stoneCount = 0;
    }

    /**
     * Sets the count of stones in the pit.
     *
     * @param newStoneCount The new count of stones.
     */
    public void setStoneCount(final int newStoneCount) {
        if (newStoneCount < 0) {
            throw new IllegalArgumentException("Stone count cannot be negative.");
        }
        this.stoneCount = newStoneCount;
    }

    /**
     * Gets the count of stones in the pit.
     *
     * @return The count of stones in the pit.
     */
    @Override
    public int getStoneCount() {
        return stoneCount;
    }

    /**
     * Adds one stone to the pit.
     */
    @Override
    public void addStone() {
        stoneCount++;
    }

    /**
     * Adds a specified number of stones to the pit.
     *
     * @param numToAdd The number of stones to add.
     */
    @Override
    public void addStones(final int numToAdd) {
        if (numToAdd < 0) {
            throw new IllegalArgumentException("Number of stones to add cannot be negative.");
        }
        stoneCount += numToAdd;
    }

    /**
     * Removes and returns all stones from the pit.
     *
     * @return The number of stones removed from the pit.
     */
    @Override
    public int removeStones() {
        int removedStones = stoneCount;
        stoneCount = 0;
        return removedStones;
    }
}
