/**
   COPYRIGHT (C). All Rights Reserved.
   @author Jonathan Sagabaen
   @version 1.00

   Main Game Board frame.
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameBoard extends JFrame implements ChangeListener
{
   public static final int DEFAULT_WIDTH = 800;   
   public static final int DEFAULT_HEIGHT = 600;
   public static final int MANCALA_WIDTH = 100;
   public static final int MANCALA_HEIGHT = 400;
   public static final int PIT_WIDTH = 60;
   public static final int PIT_HEIGHT = 145;
   
   private MancalaGame game;
   private MancalaModel mancalaModel;
   boolean winCheck = false;
   
   /**
    * Constructor for GameBoard class.
    * @param game the game this board is attached to
    * @param mancalaModel the model this board is attached to
    * @param format the format to use for this board
    */
   public GameBoard(MancalaGame game, MancalaModel mancalaModel, 
		   BoardFormatter format)
   {
	   this.game = game;
	   this.mancalaModel = mancalaModel;
	   
	   // Add this board to model's view list
	   mancalaModel.attach(this);
	   
	   // Top status bar
	   TopPanel top = new TopPanel(game, mancalaModel);
	   mancalaModel.attach(top);
     
      //Initializing PitPanels and MancalaPanels and attaching it to the model
       MancalaPanel mancalaA = new MancalaPanel(mancalaModel, 'a');
       mancalaA.setFormat(format);
       mancalaModel.attach(mancalaA);
       PitPanel[] a = new PitPanel[6];
       for (int i = 0; i < 6; i++)
       {
          a[i] = new PitPanel(mancalaModel, 'a', i);
          a[i].setFormat(format);
          mancalaModel.attach(a[i]);
       }
       
       MancalaPanel mancalaB = new MancalaPanel(mancalaModel, 'b');
       mancalaB.setFormat(format);
       mancalaModel.attach(mancalaB);
       PitPanel[] b = new PitPanel[6];
       for (int i = 0; i < 6; i++)
       {
          b[i] = new PitPanel(mancalaModel, 'b', i);
          b[i].setFormat(format);
          mancalaModel.attach(b[i]);
       }
       
       //Group the PitPanels together to form two rows
       JPanel middlePits = new JPanel();
       GridLayout middleLayout = new GridLayout(2,6);
       middleLayout.setHgap(17);
       middlePits.setLayout(middleLayout);
       for (int i = 5; i >= 0; i--)
          middlePits.add(b[i]);
       for (int i = 0; i < 6; i++)
          middlePits.add(a[i]);       
       
       JPanel mainPanel = new JPanel();
       mainPanel.setLayout(new BorderLayout());
       top.setPreferredSize(new Dimension(800, 100));
       mainPanel.add(top, BorderLayout.PAGE_START);
       mancalaB.setPreferredSize(new Dimension(150, 500));
       mainPanel.add(mancalaB, BorderLayout.LINE_START);
       middlePits.setPreferredSize(new Dimension(500, 500));
       mainPanel.add(middlePits, BorderLayout.CENTER);
       mancalaA.setPreferredSize(new Dimension(150, 500));
       mainPanel.add(mancalaA, BorderLayout.LINE_END);
       
       add(mainPanel);    
       
	   setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	   setTitle("Mancala");
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setResizable(false);
	   setVisible(true);	   
   }
   
   /**
    * Controller that causes a repaint and checks to see if there is a winner.
    */
   public void stateChanged(ChangeEvent e) 
   {
		repaint();
		if (!winCheck)
		{
			winCheck = true;
			char winner = mancalaModel.checkWinner();
			if (winner != 'c')
			{
				repaint();
				game.endGame(winner);
			}
			else
				winCheck = false;
		}
   }   
}