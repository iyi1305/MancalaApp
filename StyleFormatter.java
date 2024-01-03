import java.awt.Color;
import java.awt.Shape;

public interface StyleFormatter
{
   /**
    * Retrieves the primary color for the outline. 
    * @return the Color for the outline
    */
   Color outlineColor();
   
   /**
    * Retrieves the secondary color for the inside.
    * @return the Color for the fill
    */
   Color innerColor();
   
   /**
    * Shape representation for each pit.
    * @return the designated Shape
    */
   Shape drawPit();
   
   /**
    * Shape representation for the Mancala.
    * @return the designated Shape
    */
   Shape drawMancala();
   
   /**
    * Array of shapes representing stones in the pit.
    * @param stones count of stones to represent
    * @return an array of Shapes for stones
    */
   Shape[] pitStones(int stones);
   
   /**
    * Array of shapes representing stones in the Mancala.
    * @param stones count of stones to represent
    * @return an array of Shapes for stones
    */
   Shape[] mancalaStones(int stones);
}
