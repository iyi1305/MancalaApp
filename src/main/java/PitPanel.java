import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PitPanel extends JPanel implements ChangeListener, MouseListener
{
   private int stoneAmount;
   private char pitSide;
   private int pitNumber;
   private MancalaModel mancalaModel;
   private BoardFormatter format;
  
   /**
    * Constructor.
    * @param mancalaModel model this panel is attached to
    * @param pitSide side this pit is on
    * @param pitNumber number of pit
    */
   public PitPanel(MancalaModel mancalaModel, char pitSide, int pitNumber)
   {
      this.mancalaModel = mancalaModel;
      this.pitSide = pitSide;
      this.pitNumber = pitNumber;
      this.addMouseListener(this);
      
      stoneAmount = mancalaModel.getPitValue(pitSide, pitNumber);
   }
   
   /**
    * Set panel format.
    * @param format format to be set to
    */
   public void setFormat(BoardFormatter format)
   {
      this.format = format;
   }
   
   /**
    * Display panel.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(format.getColor());
      g2.draw(format.getPitShape());
      g2.setColor(format.getFillColor());
      for (Shape s : format.getPitStoneShapes(stoneAmount))
    	  g2.fill(s);
   }
   
   /**
    * Update panel based on model state change.
    */
   public void stateChanged(ChangeEvent e)
   {
      stoneAmount = mancalaModel.getPitValue(pitSide, pitNumber);
   }
   
   public void mouseClicked(MouseEvent arg0)
   {
      // TODO Auto-generated method stub
   }

   public void mouseEntered(MouseEvent e)
   {
      // TODO Auto-generated method stub
   }

   public void mouseExited(MouseEvent e)
   {
      // TODO Auto-generated method stub
   }

   /**
    * Controller to do a turn when clicking on pit.
    */
   public void mousePressed(MouseEvent e)
   {
      mancalaModel.doTurn(pitSide, pitNumber);
   }

   public void mouseReleased(MouseEvent e)
   {
      // TODO Auto-generated method stub
   }
}