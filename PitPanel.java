import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class representing a pit display panel in a Mancala game.
 */
public class PitPanel extends JPanel implements ChangeListener, MouseListener {
    private int stoneAmount;
    private char pitSide;
    private int pitNumber;
    private MancalaModel mancalaModel;
    private BoardFormatter format;

    /**
     * Constructor for the PitPanel.
     * 
     * @param mancalaModel Model this panel is attached to
     * @param pitSide      Side of the board this pit is on ('a' or 'b')
     * @param pitNumber    Pit number (0-5)
     */
    public PitPanel(MancalaModel mancalaModel, char pitSide, int pitNumber) {
        this.mancalaModel = mancalaModel;
        this.pitSide = pitSide;
        this.pitNumber = pitNumber;
        this.addMouseListener(this);

        stoneAmount = mancalaModel.getPitValue(pitSide, pitNumber);
    }

    /**
     * Sets the visual format of the pit panel.
     * 
     * @param format The BoardFormatter to be used
     */
    public void setFormat(BoardFormatter format) {
        this.format = format;
    }

    /**
     * Paints the pit panel.
     * 
     * @param g The Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(format.getColor());
        g2.draw(format.getPitShape());
        g2.setColor(format.getFillColor());
        for (Shape s : format.getPitStoneShapes(stoneAmount))
            g2.fill(s);
    }

    /**
     * Updates the panel based on changes in the model.
     * 
     * @param e The ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        stoneAmount = mancalaModel.getPitValue(pitSide, pitNumber);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Perform actions when the pit is clicked, like highlighting or animations
        // This could be a visual indication that a pit has been selected
        // Note: Actual game logic might be executed in mousePressed
        highlightPit();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Change cursor to indicate the pit is clickable
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Optional: Apply a visual effect to indicate interactivity
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Revert the cursor to default
        setCursor(Cursor.getDefaultCursor());
        // Optional: Remove any highlights or visual effects applied in mouseEntered
        setBorder(null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Trigger game logic associated with selecting a pit
        // This could involve updating the model based on the selected pit
        if (mancalaModel.isTurnValid(pitSide, pitNumber)) {
            mancalaModel.doTurn(pitSide, pitNumber);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle any clean-up or UI updates needed after a click
        // This might involve stopping animations or removing temporary visual effects
    }

    /**
     * Method to visually highlight the pit when clicked.
     */
    private void highlightPit() {
        // Example: Change the background color to indicate selection
        setBackground(Color.YELLOW);
        // Use a timer to remove the highlight after a brief period
        Timer timer = new Timer(500, event -> {
            setBackground(null); // Revert the background color
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
}

