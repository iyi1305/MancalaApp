
public class Mancala
{
   private int stoneAmount;
   
   /**
    * Constructor for Mancala.
    */
   public Mancala()
   {
      stoneAmount = 0;
   }
   
   /**
    * Add stones to this mancala.
    * @param amount amount of stones to add
    */
   public void addStones(int amount)
   {
      stoneAmount += amount;
   }
   
   /**
    * Get stone count from mancala.
    * @return the amount of stones in the mancala
    */
   public int getAmount()
   {
      return stoneAmount;
   }
   
   /**
    * Set the stones in the mancala to a specific number.
    * @param stone number to set the stones to
    */
   public void setStones(int stone)
   {
	   stoneAmount = stone;
   }
}