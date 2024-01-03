import javax.swing.JOptionPane;

public class MancalaGame {
    private MancalaModel gameData;
    private GameBoard gameInterface;

    public static void main(String[] args) {
        MancalaGame bg = new MancalaGame();
    }

    public MancalaGame() {
        setupGame();
    }

    public void setupGame() {
        if (gameInterface != null) {
            gameInterface.close();
        }
        String stonesPerPit = JOptionPane.showInputDialog("Stones in each pit:", "4");
        gameData = new MancalaModel(Integer.parseInt(stonesPerPit));
        int styleChoice = JOptionPane.showOptionDialog(null, "Select board style:", "Style Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Classic", "Oval"}, "Classic");
        if (styleChoice == 1) {
            gameInterface = new GameInterface(this, gameData, new OvalStyle());
        } else {
            gameInterface = new GameInterface(this, gameData, new ClassicStyle());
        }
    }

    public void concludeGame(char victor) {
        int playAgain = JOptionPane.showConfirmDialog(gameInterface, "Player " + victor + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (playAgain == JOptionPane.YES_OPTION) {
            setupGame();
        } else {
            System.exit(0);
        }
    }
}
