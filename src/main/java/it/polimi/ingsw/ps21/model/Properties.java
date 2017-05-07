package it.polimi.ingsw.ps21.model;

/**This class is used to store mutable resource and points values.
*For example, it is used to store player's resources and points, modifiers on resources and points, etc. 
*@author fabri
 */
public class Properties {
	
	/**The properties[] array store the values in its first 7 cells.
	 * Values stored in each cell are: 0_Coins, 1_Wood, 2_Stone, 3_Servants, 4_Privileges, 5_MilitaryPoints, 6_FaithPoints, 7_VictoryPoints
	 */
	private int[] properties;
	
	/**This constructor accepts an array of 8 integer numbers, each one corresponding to a resource or points value, and stores those values in the object. 
	 * The values in the array must be sorted in this way: 0_Coins, 1_Wood, 2_Stone, 3_Servants, 4_Privileges, 5_MilitaryPoints, 6_FaithPoints, 7_VictoryPoints
	 * 
	 * @param values array of integer values (resources and points values) , sorted in order to match the following semantics: 0_Coins, 1_Wood, 2_Stone, 3_Servants, 4_Privileges, 5_MilitaryPoints, 6_FaithPoints, 7_VictoryPoints
	 */
	Properties(int[] values)
	{
		this.properties= new int[8];
		for(int i=0; i<8; i++)
		{
			this.properties[i]=values[i];
		}
	}
	
	/**This constructor accepts 8 different integer parameters, one for each value to store in the object.
	 * 
	 * @param coins number of coins to store in the object
	 * @param wood	number of wood pieces to store in the object
	 * @param stone	number of stone pieces to store in the object
	 * @param servants number of servants to store in the object
	 * @param privileges number of privileges to store in the object
	 * @param militaryPoints number of Military Points to store in the object
	 * @param faithPoints number of Faith Points to store in the object
	 * @param victoryPoints number of Victory Points to store in the object
	 */
	Properties(int coins, int wood, int stone, int servants, int privileges, int militaryPoints, int faithPoints, int victoryPoints)
	{
		this.properties= new int[8];
		this.properties[0]=coins;
		this.properties[1]=wood;
		this.properties[2]=stone;
		this.properties[3]=servants;
		this.properties[4]=privileges;
		this.properties[5]=militaryPoints;
		this.properties[6]=faithPoints;
		this.properties[7]=victoryPoints;
	}
	
	/**Returns the value of Wood resources. 
	 * @return pieces of wood stored.*/
	public int getWood()
	{
		return properties[1];
	}
	
	/**Returns the value of Stone resources. 
	 * @return number of stones.*/
	public int getStone()
	{
		return properties[2];
	}
	
	/**Returns the value of Coins. 
	 * @return number of coins.*/
	public int getCoins()
	{
		return properties[0];
	}
	
	/**Returns the value of Servants. 
	 * @return number of servants.*/
	public int getServants()
	{
		return properties[3];
	}
	
	/**Returns the value of Privileges. 
	 * @return number of privileges.*/
	public int getPrivileges()
	{
		return properties[4];
	}
	
	/**Returns the value of Victory Points. 
	 * @return number of victory points.*/
	public int getVictoryPoints()
	{
		return properties[7];
	}
	
	/**Returns the value of Military points. 
	 * @return number of military points.*/
	public int getMilitaryPoints()
	{
		return properties[5];
	}
	
	/**Returns the value of Faith points. 
	 * @return number of faith points.*/
	public int getFaithPoints()
	{
		return properties[6];
	}
	
	/**Increases the value of Wood stored in the object by num units. 
	 * @param num number of wood pieces to add.*/
	public void addWood(int num)
	{
		properties[1] += num;
	}
	
	/**Increases the value of stones stored in the object by num units. 
	 * @param num number of stones to add.*/
	public void addStone(int num)
	{
		properties[2] += num;
	}
	
	/**Increases the number of coins stored in the object by num units. 
	 * @param num number of Coins to add.*/
	public void addCoins(int num)
	{
		properties[1] += num;
	}
	
	/**Increases the value of Servants stored in the object by num units. 
	 * @param num number of Servants to add.*/
	public void addServants(int num)
	{
		properties[3] += num;
	}
	
	/**Increases the value of Victory Points stored in the object by num units. 
	 * @param num number of Victory Points to add.*/
	public void addVictoryPoints(int num)
	{
		properties[7] += num;
	}
	
	/**Increases the value of Military Points stored in the object by num units. 
	 * @param num number of Military Points to add.*/
	public void addMilitaryPoints(int num)
	{
		properties[5] += num;
	}
	
	/**Increases the value of Faith Points stored in the object by num units.
	 * @param num number of Faith Points to add. */
	public void addFaithPoints(int num)
	{
		properties[6] += num;
	}
	
	/**Increases the value of Privileges stored in the object by num units.
	 * @param num number of Privileges to add. */
	public void addPrivileges(int num)
	{
		properties[4] += num;
	}
}
