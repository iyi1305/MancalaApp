import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class represents a visual component (panel) in a Mancala game application.
 * It extends JPanel and implements ChangeListener to update its display
 * in response to changes in the game's model.
 */
public class MancalaPanel extends JPanel implements ChangeListener {
    private MancalaModel mancalaModel;  // The game model this panel is associated with
    private char playerMancala;         // Identifier for the player ('a' or 'b')
    private int stoneAmount;            // Number of stones in the player's Mancala
    private BoardFormatter format;      // Formatter for styling the Mancala display

    /**
     * Constructs a MancalaPanel.
     * 
     * @param mancalaModel  The game model associated with this panel
     * @param playerMancala The player identifier ('a' or 'b') for this Mancala
     */
    public MancalaPanel(MancalaModel mancalaModel, char playerMancala) {
        this.mancalaModel = mancalaModel;
        this.playerMancala = playerMancala;
        stoneAmount = mancalaModel.getMancalaValue(playerMancala);
    }

    /**
     * Sets the display format for this panel.
     * 
     * @param format The BoardFormatter to apply to this panel
     */
    public void setFormat(BoardFormatter format) {
        this.format = format;
    }

    /**
     * Paints the component. This method is called when the panel needs to be drawn.
     * 
     * @param g The Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw Mancala shape
        g2.setColor(format.getColor());
        g2.draw(format.getMancalaShape());

        // Fill stones
        g2.setColor(format.getFillColor());
        for (Shape stoneShape : format.getMancalaStoneShapes(stoneAmount)) {
            g2.fill(stoneShape);
        }

        // Draw player label
        g2.setColor(Color.BLACK);
        g2.drawString("Player " + Character.toUpperCase(playerMancala), 50, GameBoard.MANCALA_HEIGHT + 20);
    }

    /**
     * Called when the observed model changes state. Updates the stone count.
     * 
     * @param e The event object representing the change
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        stoneAmount = mancalaModel.getMancalaValue(playerMancala);
        repaint();
    }
}
