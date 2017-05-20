package it.polimi.ingsw.ps21.model;

/**This class is used to store immutable resource and points values.
*For example, it is used to store card effects on resources and points.
*This class has 2 different constructors: the first is used when the values to store in the object are saved into a sorted array, whereas the second
*constructor uses one different parameter for each value to store in the object.
*@author fabri
 */
public class ImmProperties {
	private final Resources resources;
	private final Points points;
	
	/**This constructor accepts 7 different integer parameters, one for each value to store in the object.
	 * 
	 * @param coins number of coins to store in the object
	 * @param wood	number of wood pieces to store in the object
	 * @param stone	number of stone pieces to store in the object
	 * @param servants number of servants to store in the object
	 * @param militaryPoints number of Military Points to store in the object
	 * @param faithPoints number of Faith Points to store in the object
	 * @param victoryPoints number of Victory Points to store in the object
	 */
	public ImmProperties(int coins, int wood, int stone, int servants, int militaryPoints, int faithPoints, int victoryPoints)
	{
		points = new Points(victoryPoints, militaryPoints, faithPoints);
		resources = new Resources(wood, servants, stone, coins);		
	}
	
	/**This constructor can be used when the object is used to store resources (Coins, wood, stone and servants).
	 * Military Points, Victory Points and Faith Points are automatically set to 0.
	 * 
	 * @param coins number of coins to store in the object
	 * @param wood	number of wood pieces to store in the object
	 * @param stone	number of stone pieces to store in the object
	 * @param servants number of servants to store in the object
	 * 
	 */
	public ImmProperties(int coins, int wood, int stone, int servants)
	{
		resources = new Resources(wood, servants, stone, coins);
		points = new Points(0,0,0);
	}
	
	/**This constructor can be used when the object is used to store points (Victory Points, Faith Points and Military Points).
	 * Stones, Wood Pieces and and Servants are automatically set to 0.
	 * 
	 * @param militaryPoints number of Military Points to store in the object
	 * @param faithPoints number of Faith Points to store in the object
	 * @param victoryPoints number of Victory Points to store in the object
	 */
	public ImmProperties(int militaryPoints, int faithPoints, int victoryPoints)
	{
		resources = new Resources(0, 0, 0, 0);
		points = new Points(victoryPoints, militaryPoints, faithPoints);
	}
	
	
	/**Returns the number of Wood pieces. 
	 * @return pieces of wood stored.*/
	public int getWood()
	{
		return resources.getWood();
	}
	
	/**Returns the value of Stones. 
	 * @return number of stones.*/
	public int getStone()
	{
		return resources.getStone();
	}
	
	/**Returns the number of Coins. 
	 * @return number of coins.*/
	public int getCoins()
	{
		return resources.getCoins();
	}
	
	/**Returns the number of Servants. 
	 * @return number of servants.*/
	public int getServants()
	{
		return resources.getServants();
	}
	
	/**Returns the value of Victory Points. 
	 * @return number of victory points.*/
	public int getVictoryPoints()
	{
		return points.getVictoryPoints();
	}
	
	/**Returns the value of Military points. 
	 * @return number of military points.*/
	public int getMilitaryPoints()
	{
		return points.getMilitaryPoints();
	}
	
	/**Returns the value of Faith points. 
	 * @return number of faith points.*/
	public int getFaithPoints()
	{
		return points.getFaithPoints();
	}
	
	/**Returns Resources object. 
	 * @return resources object in requirement.*/
	public Resources getResources(){
		return resources;
	}
	
	/**Returns Points object. 
	 * @return points object in requirement.*/
	public Points getPoints(){
		return points;
	}
}
