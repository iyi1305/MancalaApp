import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TopPanel extends JPanel implements ChangeListener
{
	private JTextField statusText;
	private JButton undoButton;
	private JButton resetButton;
	private MancalaModel model;
	private MancalaGame game;
	
	/**
	 * Constructor.
	 * @param g the game this panel is for
	 * @param m the model this panel is attached to
	 */
	public TopPanel(MancalaGame g, MancalaModel m)
	{
		model = m;
		game = g;
		statusText = new JTextField(55);
		statusText.setEditable(false);
		undoButton = new JButton("Undo");
		resetButton = new JButton("Reset");
		this.setLayout(new FlowLayout());
		undoButton.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						model.undo();
					}
				});
		undoButton.setEnabled(false);
		resetButton.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the game?", "Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.YES_OPTION)
						{
							game.initGame();
						}		
					}
				});
		add(statusText);
		add(undoButton);
		add(resetButton);
	}
	
	/**
	 * Update the panel state.
	 */
	public void stateChanged(ChangeEvent arg0) 
	{
		if (model.canUndo())
			this.undoButton.setEnabled(true);
		else
			this.undoButton.setEnabled(false);
		
		String text = "Player " + Character.toUpperCase(model.getCurrPlayer()) + "'s Turn | " + model.getUndoCount() + " Undos Remain";
		statusText.setText(text);
	}
}