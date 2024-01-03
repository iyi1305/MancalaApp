
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

//Concrete class for the Strategy pattern
public class RectangleFormat implements StyleFormatter
{
   /**
    * Get this style's mancala shape.
	* @return shape object to be used
	*/
   public Shape getMancalaShape()
   {
      return new Rectangle2D.Double(25, 0, GameBoard.MANCALA_WIDTH, 
    		  GameBoard.MANCALA_HEIGHT);
   }

   /**
    * Get this style's pit shape.
    * @return shape object to be used
    */
   public Shape getPitShape()
   {
      return new Rectangle2D.Double(5, 5, GameBoard.PIT_WIDTH, 
    		  GameBoard.PIT_HEIGHT);
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
	   
	   ArrayList<Rectangle2D.Double> shapeList = 
		   new ArrayList<Rectangle2D.Double>();
	   
	   int dimension = (int) (Math.sqrt(stoneAmount) + 1);
	   int width = GameBoard.PIT_WIDTH / (dimension) - 7;
	   int height = GameBoard.PIT_HEIGHT / (dimension) - 7;
	   
	   for (int i = 0; i < stoneAmount; i++)
	   {
		   int xMod = (i % dimension);
		   int yMod = (i / dimension);
		   int x = xMod * (width + 4) + 10;
		   int y = yMod * (height + 4) + 10;
		   shapeList.add(new Rectangle2D.Double(x, y, width, height));
	   }
	   
	   Rectangle2D.Double [] rectList = 
		   new Rectangle2D.Double[shapeList.size()];
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
	   
	   ArrayList<Rectangle2D.Double> shapeList = 
		   new ArrayList<Rectangle2D.Double>();
	   
	   int dimension = (int) (Math.sqrt(stoneAmount) + 1);
	   int width = GameBoard.MANCALA_WIDTH / (dimension) - 7;
	   int height = GameBoard.MANCALA_HEIGHT / (dimension) - 7;
	   
	   for (int i = 0; i < stoneAmount; i++)
	   {
		   int xMod = (i % dimension);
		   int yMod = (i / dimension);
		   int x = xMod * (width + 4) + 30;
		   int y = yMod * (height + 4) + 5;
		   shapeList.add(new Rectangle2D.Double(x, y, width, height));
	   }
	   
	   Rectangle2D.Double [] rectList = 
		   new Rectangle2D.Double[shapeList.size()];
	   rectList = shapeList.toArray(rectList);
	   return rectList;
   }

   /**
    * Get this style's outline color. 
    * @return color object to be used
    */
   public Color getColor()
   {
      return Color.RED;
   }
   
   /**
    * Get this style's fill color.
    * @return color object to be used
    */
   public Color getFillColor()
   {
	   return Color.ORANGE;
   }
}