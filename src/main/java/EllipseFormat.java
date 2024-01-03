

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;


public class EllipseFormat implements BoardFormatter
{
   /**
    * Get this style's mancala shape.
    * @return shape object to be used
    */
   public Shape getMancalaShape()
   {
      return new RoundRectangle2D.Double(25, 0, GameBoard.MANCALA_WIDTH, 
    		  GameBoard.MANCALA_HEIGHT, 40, 40);
   }

   /**
    * Get this style's pit shape.
    * @return shape object to be used
    */
   public Shape getPitShape()
   {
      return new RoundRectangle2D.Double(5, 5, GameBoard.PIT_WIDTH, 
    		  GameBoard.PIT_HEIGHT, 30, 30);
   }
   
   /**
    * Get the shapes to draw stones in a pit for this style.
    * @param stoneAmount number of stones to draw
    * @return array of shape objects to be used
    */
   public Shape [] getPitStoneShapes(int stoneAmount)
   {
	   if (stoneAmount == 0)
		   return new Shape [] { new Rectangle2D.Double(0,0,0,0) };
	   
	   ArrayList<Ellipse2D.Double> shapeList = 
		   new ArrayList<Ellipse2D.Double>();
	   
	   int dimension = (int) (Math.sqrt(stoneAmount) + 1);
	   int width = GameBoard.PIT_WIDTH / (dimension) - 7;
	   int height = GameBoard.PIT_HEIGHT / (dimension) - 7;
	   
	   for (int i = 0; i < stoneAmount; i++)
	   {
		   int xMod = (i % dimension);
		   int yMod = (i / dimension);
		   int x = xMod * (width + 4) + 10;
		   int y = yMod * (height + 4) + 10;
		   shapeList.add(new Ellipse2D.Double(x, y, width, height));
	   }
	   
	   Ellipse2D.Double [] rectList = new Ellipse2D.Double[shapeList.size()];
	   rectList = shapeList.toArray(rectList);
	   return rectList;
   }
   
   /**
    * Get the shapes to draw stones in a mancala for this style.
    * @param stoneAmount number of stones to draw
    * @return array of shape objects to be used
    */   
   public Shape [] getMancalaStoneShapes(int stoneAmount)
   {
	   if (stoneAmount == 0)
		   return new Shape [] { new Rectangle2D.Double(0,0,0,0) };
	   
	   ArrayList<Ellipse2D.Double> shapeList = 
		   new ArrayList<Ellipse2D.Double>();
	   
	   int dimension = (int) (Math.sqrt(stoneAmount) + 1);
	   int width = GameBoard.MANCALA_WIDTH / (dimension) - 7;
	   int height = GameBoard.MANCALA_HEIGHT / (dimension) - 7;
	   
	   for (int i = 0; i < stoneAmount; i++)
	   {
		   int xMod = (i % dimension);
		   int yMod = (i / dimension);
		   int x = xMod * (width + 4) + 30;
		   int y = yMod * (height + 4) + 5;
		   shapeList.add(new Ellipse2D.Double(x, y, width, height));
	   }
	   
	   Ellipse2D.Double [] rectList = new Ellipse2D.Double[shapeList.size()];
	   rectList = shapeList.toArray(rectList);
	   return rectList;
   }
   
   /**
    * Get this style's outline color. 
    * @return color object to be used
    */   
   public Color getColor()
   {
      return Color.BLUE;
   }
   
   /**
    * Get this style's fill color.
    * @return color object to be used
    */
   public Color getFillColor()
   {
	   return Color.CYAN;
   }

}