package mancala;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super("Invalid move. Please make a valid move.");
    }
}
