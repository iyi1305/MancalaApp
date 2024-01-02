package mancala;

import java.io.Serializable;

/**
 * Represents a user profile in a Mancala game application.
 * This class stores user information and game statistics for both Kalah and Ayo games.
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahGamesWon;
    private int ayoGamesWon;

    /**
     * Constructs a UserProfile with the specified name.
     * Initializes game statistics to zero.
     *
     * @param name the name of the user
     */
    public UserProfile(String name) {
        this.name = name;
        this.kalahGamesPlayed = 0;
        this.ayoGamesPlayed = 0;
        this.kalahGamesWon = 0;
        this.ayoGamesWon = 0;
    }

    /**
     * Gets the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the number of Kalah games played by the user.
     *
     * @return the number of Kalah games played
     */
    public int getKalahGames() {
        return kalahGamesPlayed;
    }

    /**
     * Sets the number of Kalah games played by the user.
     * Negative values are clamped to zero.
     *
     * @param kalahGamesPlayed the new number of Kalah games played
     */
    public void setKalahGames(int kalahGamesPlayed) {
        this.kalahGamesPlayed = Math.max(kalahGamesPlayed, 0);
    }

    /**
     * Gets the number of Ayo games played by the user.
     *
     * @return the number of Ayo games played
     */
    public int getAyoGames() {
        return ayoGamesPlayed;
    }

    /**
     * Sets the number of Ayo games played by the user.
     * Negative values are clamped to zero.
     *
     * @param ayoGamesPlayed the new number of Ayo games played
     */
    public void setAyoGames(int ayoGamesPlayed) {
        this.ayoGamesPlayed = Math.max(ayoGamesPlayed, 0);
    }

    /**
     * Gets the number of Kalah games won by the user.
     *
     * @return the number of Kalah games won
     */
    public int getKalahGamesWon() {
        return kalahGamesWon;
    }

    /**
     * Sets the number of Kalah games won by the user.
     * If the new value is greater than the total games played, it is not incremented.
     */
    public void setKalahGamesWon(int kalahGamesWon) {
        this.kalahGamesWon = Math.max(kalahGamesWon, 0);
    }

    /**
     * Gets the number of Ayo games won by the user.
     *
     * @return the number of Ayo games won
     */
    public int getAyoGamesWon() {
        return ayoGamesWon;
    }

    /**
     * Sets the number of Ayo games won by the user.
     * If the new value is greater than the total games played, it is not incremented.
     */
    public void setAyoGamesWon(int ayoGamesWon) {
        this.ayoGamesWon = Math.max(ayoGamesWon, 0);
    }

    /**
     * Increments the number of Kalah games played by the user.
     */
    public void kalahGamesPlayNum() {
        this.kalahGamesPlayed++;
    }

    /**
     * Increments the number of Ayo games played by the user.
     */
    public void ayoGamesPlayNum() {
        this.ayoGamesPlayed++;
    }

    /**
     * Increments the number of Kalah games won by the user, if it's less than the total games played.
     */
    public void kalahGamesWonNum() {
        if (kalahGamesWon < kalahGamesPlayed) {
            this.kalahGamesWon++;
        }
    }

    /**
     * Increments the number of Ayo games won by the user, if it's less than the total games played.
     */
    public void ayoGamesWonNum() {
        if (ayoGamesWon < ayoGamesPlayed) {
            this.ayoGamesWon++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        UserProfile that = (UserProfile) obj;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserProfile{" +
               "name='" + name + '\'' +
               ", kalahGamesPlayed=" + kalahGamesPlayed +
               ", ayoGamesPlayed=" + ayoGamesPlayed +
               ", kalahGamesWon=" + kalahGamesWon +
               ", ayoGamesWon=" + ayoGamesWon +
               '}';
    }
}
