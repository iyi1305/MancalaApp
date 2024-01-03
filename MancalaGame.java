
import javax.swing.JOptionPane;

public class MancalaGame 
{
	private MancalaModel model;
	private GameBoard board;
	
	public static void main(String [] args)
	{
		MancalaGame m = new MancalaGame();
	}
	
	/**
	 * Constructor for MancalaGame.
	 */
	public MancalaGame()
	{
		initGame();
	}
	
	/**
	 * Initializes the game by creating the model and views and attaching
	 * them.
	 */
	public void initGame()
	{
		if (board != null)
		{
			board.dispose();
		}
		String initPits = JOptionPane.showInputDialog("Enter the number of stones initially in the pits: ", "3");
		model = new MancalaModel(Integer.parseInt(initPits));
		int opt = JOptionPane.showOptionDialog(null, "Choose a board style:", "Board Style", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Rectangle", "Elliptical" }, 0);
		switch (opt)
		{
		case 0:
			board = new GameBoard(this, model, new RectangleFormat());
			break;
		case 1:
			board = new GameBoard(this, model, new EllipseFormat());
			break;
		default:
			board = new GameBoard(this, model, new RectangleFormat());
		}
	}
	
	/**
	 * Handles the end of the game and asks the player if they want to play
	 * again.
	 * @param winner the winner of the game, 'a' or 'b'
	 */
	public void endGame(char winner)
	{
		int option = JOptionPane.showConfirmDialog(board, "Player " + Character.toUpperCase(winner) + " wins!\nDo you wish to play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (option == JOptionPane.NO_OPTION)
		{
			board.dispose();
			System.exit(0);
		}
		else
		{
			initGame();
		}		
	}
}