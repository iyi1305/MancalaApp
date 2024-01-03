public class Mancala {
    private int stoneCount;
    
    public Mancala() {
       stoneCount = 0;
    }
    
    public void incrementStones(int stonesToAdd) {
       stoneCount += stonesToAdd;
    }
    
    public int countStones() {
       return stoneCount;
    }
    
    public void adjustStones(int newStoneCount) {
       stoneCount = newStoneCount;
    }
 }
 