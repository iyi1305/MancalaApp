# Java Mancala App
JavaMancalaApp is a graphical implementation of the Mancala board game. It's a two-player game featuring a board with pits and beads. The aim is to collect as many beads as possible by making strategic moves on the board.

## Description
This project brings the traditional Mancala game to life with a Java-based graphical user interface (GUI). The application is built following the Model-View-Controller (MVC) design pattern. The GUI is crafted using Java Swing, providing an interactive and visually appealing interface for players.

Players engage in the game by selecting pits on the GUI, which then triggers the distribution of stones according to Mancala's rules. The game logic is robust, handling game states, player actions, and outcomes seamlessly. Visual feedback is provided for every action, keeping players informed of the current game state, scores, and progress. The application is designed to support multiple rounds and determines the winner at the end.

## Getting Started 
## Getting Started

### Dependencies

* This program is implemented in Java and uses the Swing library for GUI components. It does not require any additional libraries or dependencies. It should work on any system with Java support.

### Executing the Program

1. Build the program:
   ```
   javac src/main/java/*.java -d bin
   ```

2. To run the GUI version of the game, execute the following command:
   ```
   java -cp bin MancalaGame 
   ```
3. The program will launch the GUI interface for the Mancala game.

4. The GUI displays the choice between display the stones as en allipse or rectangular.

5. Each game button will activate a board consisting of different pits and their numbers.

6. Players can take turns by clicking on the pits to distribute stones.

7. The GUI provides visual feedback on the game state, scores, and declares the winner at the end of the specified rounds.

## Author Information

Author: Shofowora Iyioluwa
Email: iyioluwashofowora@gmail.com


