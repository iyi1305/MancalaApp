

import java.awt.Color;
import java.awt.Shape;

public interface StyleFormatter
{
   /**
    * Get this style's outline color. 
    * @return color object to be used
    */
   public Color getColor();
   
   /**
    * Get this style's fill color.
    * @return color object to be used
    */
   public Color getFillColor();
   
   /**
    * Get this style's pit shape.
    * @return shape object to be used
    */
   public Shape getPitShape();
   
   /**
    * Get this style's mancala shape.
    * @return shape object to be used
    */
   public Shape getMancalaShape();
   
   /**
    * Get the shapes to draw stones in a pit for this style.
    * @param stoneAmount number of stones to draw
    * @return array of shape objects to be used
    */
   public Shape [] getPitStoneShapes(int stoneAmount);
   
   /**
    * Get the shapes to draw stones in a mancala for this style.
    * @param stoneAmount number of stones to draw
    * @return array of shape objects to be used
    */
   public Shape [] getMancalaStoneShapes(int stoneAmount);
}