import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BoardView extends JFrame implements ChangeListener {
   private MancalaGame gameInstance;
   private MancalaModel gameData;
   private boolean checkWinnerFlag = false;

   public BoardView(MancalaGame game, MancalaModel gameData, StyleFormatter formatter) {
      this.gameInstance = game;
      this.gameData = gameData;
      gameData.addView(this);

      DisplayPanel statusDisplay = new DisplayPanel(gameInstance, gameData);
      gameData.addView(statusDisplay);
      
      setupGamePanels(formatter);
      
      setTitle("Mancala Game");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      pack();
      setVisible(true);
   }

   private void setupGamePanels(StyleFormatter formatter) {
      PlayerAreaPanel playerA = createPlayerArea('A', formatter);
      PlayerAreaPanel playerB = createPlayerArea('B', formatter);
      JPanel pitArea = setupPits(playerA, playerB, formatter);
      
      JPanel container = new JPanel(new BorderLayout());
      container.add(statusDisplay, BorderLayout.PAGE_START);
      container.add(playerB, BorderLayout.LINE_START);
      container.add(pitArea, BorderLayout.CENTER);
      container.add(playerA, BorderLayout.LINE_END);
      add(container);
   }

   private PlayerAreaPanel createPlayerArea(char playerType, StyleFormatter formatter) {
      PlayerAreaPanel area = new PlayerAreaPanel(gameData, playerType);
      area.applyStyle(formatter);
      gameData.addView(area);
      return area;
   }

   private JPanel setupPits(PlayerAreaPanel playerA, PlayerAreaPanel playerB, StyleFormatter formatter) {
      JPanel pits = new JPanel(new GridLayout(2, 6, 10, 0));
      PitPanel[] pitPanels = new PitPanel[6];
      for (int i = 0; i < pitPanels.length; i++) {
         pitPanels[i] = new PitPanel(gameData, playerType, i);
         pitPanels[i].applyStyle(formatter);
         gameData.addView(pitPanels[i]);
         pits.add(pitPanels[i]);
      }
      return pits;
   }

   public void stateChanged(ChangeEvent e) {
      repaint();
      if (!checkWinnerFlag) {
         checkWinnerFlag = true;
         char winner = gameData.determineWinner();
         if (winner != 'c') {
            repaint();
            gameInstance.finishGame(winner);
         } else {
            checkWinnerFlag = false;
         }
      }
   }
}
