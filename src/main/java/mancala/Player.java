package mancala;

public class Player {
    private UserProfile userProfile;
    private Store store; 

    /**
     * Constructs a Player with a UserProfile.
     * 
     * @param userProfile the user profile associated with this player
     */
    public Player(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Get the name of the player from the associated user profile.
     * 
     * @return the name of the player
     */
    public String getName() {
        return userProfile.getName();
    }

    /**
     * Set the player's store.
     * 
     * @param store the store to set for the player
     */
    public void setStore(final Store store) {
        this.store = store;
    }

    /**
     * Get the count of stones in the player's store.
     * 
     * @return the count of stones in the player's store
     */
    public int getStoreCount() {
        if (store != null) {
            return store.getStoneCount(); // Replace with the actual method to get the store count
        } else {
            return 0; // Return 0 or handle the case where the store is not set
        }
    }

   /**
     * Returns a string representation of the player.
     *
     * @return A string containing player information.
     */
    @Override
    public String toString() {
        return "Player{" +
                "name='" + getName() + '\'' +
                ", storeCount=" + getStoreCount() +
                // Add other player-related information as needed
                '}';
    }

    

}
