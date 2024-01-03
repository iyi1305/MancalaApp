import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MancalaModel
{
   private char currentPlayer;   
   private int hand;   
   private Pit[] a;   
   private Pit[] b;   
   private Mancala mancalaA;   
   private Mancala mancalaB;   
   private int[] undoValues;   
   private char undoPlayer;   
   private boolean undoActive;   
   private int undoCount;   
   private int undoReset;   
   private ArrayList<ChangeListener> listeners;
   
   /**
    * Constructor. Initializes the model with a specified starting stone 
    * count.
    * @param stoneAmount amount of stones to put in each pit
    */
   public MancalaModel(int stoneAmount)
   {
	      hand = 0;
	      currentPlayer = 'a';
	      a = new Pit[6];
	      for (int i = 0; i < 6; i++)
	         a[i] = new Pit(stoneAmount);
	      b = new Pit[6];
	      for (int i = 0; i < 6; i++)
	         b[i] = new Pit(stoneAmount);
	      
	      mancalaA = new Mancala();
	      mancalaB = new Mancala(); 
	      
	      undoActive = false;
	      undoValues = new int[14];
	      undoCount = 3;
	      undoReset = 0;

	      listeners = new ArrayList<ChangeListener>();
   }
   
   /**
    * Get the number of stones in a pit.      
    * @param side 'a' or 'b', side of board
    * @param pitNumber pit number, 1-6
    * @return number of stones in pit
    */
   public int getPitValue(char side, int pitNumber)
   {
      if (side == 'a')
         return a[pitNumber].getAmount();
      else if (side == 'b')
         return b[pitNumber].getAmount();

      return -1;
   }
   
   /**
    * Get the number of stones in a mancala.
    * @param player 'a' or 'b'
    * @return number of stones in mancala
    */
   public int getMancalaValue(char player)
   {
      if (player == 'a')
         return mancalaA.getAmount();
      else if (player == 'b')
         return mancalaB.getAmount();
      
      System.out.println("Error: getMancalaValue()");
      return -1;
   }
   
   /**
    * Attach a listener to the model.
    * @param c the listener
    */
   public void attach(ChangeListener c)
   {
      listeners.add(c);
   }
   
   /**
    * Get method for undo status.
    * @return if undo is available true else false
    */
   public boolean canUndo()
   {
	   return undoActive;
   }
   
   /**
    * Get method for remaining undos.
    * @return remaining undo count
    */
   public int getUndoCount()
   {
	   return undoCount;
   }
   
   /**
    * Get method for current player.
    * @return current player 'a' or 'b'
    */
   public char getCurrPlayer()
   {
	   return currentPlayer;
   }
   
   /**
    * Causes an undo to occur.
    */
   public void undo()
   {
	   // A0-A5 = 0-5, AM = 6, B0-B5 = 7-12, BM = 13
	   if (undoActive == false || undoCount == 0)
		   return;
	   
	   currentPlayer = undoPlayer;
	   for (int i = 0; i < 6; i++)
		   getPits('a')[i].setStones(undoValues[i]);
	   getMancala('a').setStones(undoValues[6]);
	   for (int i = 7; i < 13; i++)
		   getPits('b')[i-7].setStones(undoValues[i]);
	   getMancala('b').setStones(undoValues[13]);
	   undoActive = false;
	   undoCount--;
	   undoReset--;
	   System.out.println(undoReset);
	   updateView();
   }
   
   /**
    * Perform a turn of the game.
    * @param player player performing action
    * @param pitNumber pit they chose
    */
   public void doTurn(char player, int pitNumber)
   {
		  // Mancala Layout
		  // 					[B5] [B4] [B3] [B2] [B1] [B0]
		  // [Player B Mancala]							      [Player A Mancala]
		  //					[A0] [A1] [A2] [A3] [A4] [A5] 
		  // Play goes counterclockwise.
	   
	   int pitValue = getPitValue(player, pitNumber);
	   if (pitValue == 0 || player != currentPlayer)
		   return; // No turn occurs
	   
	   undoReset++;
	   if (undoReset == 1)
	   {
		   undoReset = 0;
		   undoCount = 3;
	   }
	   
	   saveState();	   

	   char currSide = player;
	   

	   
	   int tempPitNumber = pitNumber+1;
	   boolean freeTurn = false;
	   
	   while (pitValue != 0)
	   {
		   // Handle pits on player's side
		   while (tempPitNumber < 6 && pitValue != 0)
		   {
			   getPits(currSide)[tempPitNumber].addStone();
			   pitValue--;	
			   tempPitNumber++;
		   }
		   // Handle player's mancala
		   if (pitValue > 0)
		   {
			   getMancala(currSide).addStones(1);
			   pitValue--;
			   freeTurn = true;
		   }
		   // Handle opposite side pits
		   if (pitValue > 0)
		   {
			   currSide = getOppositePlayer(currSide);
			   tempPitNumber = 0;
			   freeTurn = false;
			   while (tempPitNumber < 6 && pitValue != 0)
			   {
				   getPits(currSide)[tempPitNumber].addStone();
				   pitValue--;
				   tempPitNumber++;
			   }
		   }
		   // Loop around if we still have more stones..
		   if (pitValue > 0)
		   {
			   tempPitNumber = 0;
			   currSide = getOppositePlayer(currSide);
		   }
	   }
	   // Play did not end on player's mancala, therefore 
	   // 1) turn changes
	   // 2) check for ending on an empty pit
	   if (!freeTurn) 
	   {
		   if (currentPlayer == 'a')
			   currentPlayer = 'b';
		   else
			   currentPlayer = 'a';
		   
		   // ended on empty pit
		   if (currSide == player && getPitValue(player, tempPitNumber-1) == 1)
		   {
			   char oppPlayer = getOppositePlayer(player);
			   int oppPit = getOppositePit(tempPitNumber - 1);
			   int giveValue = getPitValue(oppPlayer, oppPit) + 1;
			   getMancala(player).addStones(giveValue);
			   getPits(player)[tempPitNumber-1].takeAll();
			   getPits(oppPlayer)[oppPit].takeAll();
		   }
	   }
	   getPits(player)[pitNumber].takeAll();
	   updateView();
   }
   
   /**
    * Check for a winner.
    * @return 'a' or 'b' if winner, 'c' if no winner
    */
   public char checkWinner()
   {
	   // Check if A side has no stones
	   boolean gameOver = true;
	   for (int i = 0; i < 6; i++)
	   {
		   if (a[i].getAmount() != 0)
			   gameOver = false;
	   }
	   if (gameOver)
	   {
		   for (int i = 0; i < 6; i++)
		   {
			   mancalaB.addStones(b[i].takeAll());
		   }
	   }
	   // Check if B side has no stones
	   if (gameOver == false)
	   {
		   gameOver = true;
		   for (int i = 0; i < 6; i++)
		   {
			   if (b[i].getAmount() != 0)
				   gameOver = false;
		   }	
		   if (gameOver)
		   {
			   for (int i = 0; i < 6; i++)
			   {
			   mancalaA.addStones(a[i].takeAll());
			   }
		   }
	   }
	   // Game is over
	   if (gameOver)
	   {
		   updateView();
		   if (mancalaA.getAmount() > mancalaB.getAmount())
			   return 'a';
		   else
			   return 'b';
	   }
	   
	   return 'c';
   }
   
   /**
    * Force all views to update.
    */
   public void updateView()
   {      
      for (ChangeListener l : listeners)
      {
         l.stateChanged(new ChangeEvent(this));
      }
   }
   
   /**
    * Helper method to choose pits if given player as char.
    * @param side player 'a' or 'b'
    * @return pits of that player
    */
   private Pit [] getPits(char side)
   {
	   if (side == 'a')
		   return a;
	   else if (side == 'b')
		   return b;
	   
	   return null;
   }
   
   /**
    * Helper method to choose mancala if given player as char.
    * @param player player 'a' or 'b'
    * @return mancala of that player
    */   
   private Mancala getMancala(char player)
   {
	   if (player == 'a')
		   return mancalaA;
	   else if (player == 'b')
		   return mancalaB;
	   
	   return null;
   }
   
   /**
    * Helper method to get char of opposite player.
    * @param player player 'a' or 'b'
    * @return opposite player 'b' or 'a'
    */
   private char getOppositePlayer(char player)
   {
	   return (player == 'a' ? 'b' : 'a');
   }
   
   /**
    * Helper method to get opposite pit number.
    * @param pit number to get opposite of
    * @return opposite pit number
    */
   private int getOppositePit(int pit)
   {
	   switch (pit)
	   {
	   		case 0:
	   			return 5;
	   		case 1:
	   			return 4;
	   		case 2:
	   			return 3;
	   		case 3:
	   			return 2;
	   		case 4:
	   			return 1;
	   		case 5:
	   			return 0;
	   		default:
	   			return -1;
	   }		   
   }
   
   /**
    * Saves the state of the board in order to support undo function.
    */
   private void saveState()
   {
	   // A0-A5 = 0-5, AM = 6, B0-B5 = 7-12, BM = 13
	   for (int i = 0; i < 6; i++)
		   undoValues[i] = getPitValue('a', i);
	   undoValues[6] = getMancalaValue('a');
	   for (int i = 7; i < 13; i++)
		   undoValues[i] = getPitValue('b', i-7);
	   undoValues[13] = getMancalaValue('b');
	   undoPlayer = currentPlayer;	
	   if (undoCount > 0)
		   undoActive = true;
   }
}