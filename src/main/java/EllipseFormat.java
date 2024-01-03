import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * The EllipseFormat class implements the StyleFormatter interface,
 * providing ellipse-based shapes for the game board elements.
 */
public class EllipseFormat implements StyleFormatter {

   /**
    * Returns a rounded rectangle shape for the mancala.
    * 
    * @return The Shape object for the mancala.
    */
   public Shape getMancalaShape() {
      return new RoundRectangle2D.Double(
         25, 0, GameBoard.MANCALA_WIDTH, GameBoard.MANCALA_HEIGHT, 40, 40);
   }

   /**
    * Returns a rounded rectangle shape for the pit.
    * 
    * @return The Shape object for the pit.
    */
   public Shape getPitShape() {
      return new RoundRectangle2D.Double(
         5, 5, GameBoard.PIT_WIDTH, GameBoard.PIT_HEIGHT, 30, 30);
   }

   /**
    * Creates and returns an array of shapes for stones in a pit.
    * 
    * @param stoneAmount The number of stones to draw.
    * @return An array of Shape objects.
    */
   public Shape[] getPitStoneShapes(int stoneAmount) {
      return createStoneShapes(stoneAmount, GameBoard.PIT_WIDTH, GameBoard.PIT_HEIGHT, 10);
   }

   /**
    * Creates and returns an array of shapes for stones in a mancala.
    * 
    * @param stoneAmount The number of stones to draw.
    * @return An array of Shape objects.
    */
   public Shape[] getMancalaStoneShapes(int stoneAmount) {
      return createStoneShapes(stoneAmount, GameBoard.MANCALA_WIDTH, GameBoard.MANCALA_HEIGHT, 30);
   }

   /**
    * Returns the outline color.
    * 
    * @return The Color object for the outline.
    */
   public Color getColor() {
      return Color.BLUE;
   }

   /**
    * Returns the fill color.
    * 
    * @return The Color object for the fill.
    */
   public Color getFillColor() {
      return Color.CYAN;
   }

   // Helper method to create stone shapes
   private Shape[] createStoneShapes(int stoneAmount, int widthBound, int heightBound, int offset) {
      if (stoneAmount == 0) {
         return new Shape[] { new Rectangle2D.Double(0, 0, 0, 0) };
      }

      ArrayList<Ellipse2D.Double> shapes = new ArrayList<>();
      int dimension = (int) Math.sqrt(stoneAmount) + 1;
      int width = widthBound / dimension - 7;
      int height = heightBound / dimension - 7;

      for (int i = 0; i < stoneAmount; i++) {
         int x = (i % dimension) * (width + 4) + offset;
         int y = (i / dimension) * (height + 4) + offset;
         shapes.add(new Ellipse2D.Double(x, y, width, height));
      }

      return shapes.toArray(new Ellipse2D.Double[shapes.size()]);
   }
}
