import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EllipticalStyle implements StyleFormatter {

   public Shape drawMancala() {
      return new RoundRectangle2D.Double(20, 10, GameBoard.MANCALA_WIDTH, 
              GameBoard.MANCALA_HEIGHT, 50, 50);
   }

   public Shape drawPit() {
      return new RoundRectangle2D.Double(10, 10, GameBoard.PIT_WIDTH, 
              GameBoard.PIT_HEIGHT, 25, 25);
   }

   public Shape[] pitStones(int stones) {
      return generateStoneShapes(stones, GameBoard.PIT_WIDTH, GameBoard.PIT_HEIGHT);
   }

   public Shape[] mancalaStones(int stones) {
      return generateStoneShapes(stones, GameBoard.MANCALA_WIDTH, GameBoard.MANCALA_HEIGHT);
   }

   private Shape[] generateStoneShapes(int stones, int containerWidth, int containerHeight) {
      if (stones == 0) {
         return new Shape[] { new Rectangle2D.Double(0, 0, 0, 0) };
      }

      int dimension = (int) Math.sqrt(stones) + 1;
      int stoneWidth = containerWidth / dimension - 5;
      int stoneHeight = containerHeight / dimension - 5;

      List<Shape> shapes = IntStream.range(0, stones).mapToObj(i -> {
         int x = (i % dimension) * (stoneWidth + 3) + 10;
         int y = (i / dimension) * (stoneHeight + 3) + 10;
         return new Ellipse2D.Double(x, y, stoneWidth, stoneHeight);
      }).collect(Collectors.toList());

      return shapes.toArray(new Shape[0]);
   }

   public Color outlineColor() {
      return Color.RED;
   }

   public Color innerColor() {
      return Color.ORANGE;
   }
}
