package it.polimi.ingsw.ps21.model;

/**This class is used to store mutable resource and points values.
*For example, it is used to store player's resources and points.
*@author fabri
 */
public class Properties {
	
	/**The properties are stored in two different objects: a Resources object to store stones, wood pieces, coins and servants; a Points object to store Faith Points, Military Points and Victory Points.
	 */
	private Resources resources;
	private Points points;
	private Resources resMod;
	private Points pointsMod;
	
	
	/**This constructor accepts 8 different integer parameters, one for each value to store in the object.
	 * 
	 * @param coins number of coins to store in the object
	 * @param wood	number of wood pieces to store in the object
	 * @param stone	number of stone pieces to store in the object
	 * @param servants number of servants to store in the object
	 * @param militaryPoints number of Military Points to store in the object
	 * @param faithPoints number of Faith Points to store in the object
	 * @param victoryPoints number of Victory Points to store in the object
	 */
	Properties(int coins, int wood, int stone, int servants, int militaryPoints, int faithPoints, int victoryPoints)
	{
		this.resources.setCoins(coins);
		this.resources.setWood(wood);
		this.resources.setStone(stone);
		this.resources.setServants(servants);
		this.points.setFaithPoints(faithPoints);
		this.points.setMilitaryPoints(militaryPoints);
		this.points.setVictoryPoints(victoryPoints);
	}
	
	/**Returns the value of Wood resources. 
	 * @return pieces of wood stored.*/
	public int getWood()
	{
		return this.resources.getWood();
	}
	
	/**Returns the value of Stone resources. 
	 * @return number of stones.*/
	public int getStone()
	{
		return this.resources.getStone();
	}
	
	/**Returns the value of Coins. 
	 * @return number of coins.*/
	public int getCoins()
	{
		return this.resources.getCoins();
	}
	
	/**Returns the value of Servants. 
	 * @return number of servants.*/
	public int getServants()
	{
		return this.resources.getServants();
	}
	
	
	/**Returns the value of Victory Points. 
	 * @return number of victory points.*/
	public int getVictoryPoints()
	{
		return this.points.getVictoryPoints();
	}
	
	/**Returns the value of Military points. 
	 * @return number of military points.*/
	public int getMilitaryPoints()
	{
		return this.points.getMilitaryPoints();
	}
	
	/**Returns the value of Faith points. 
	 * @return number of faith points.*/
	public int getFaithPoints()
	{
		return this.points.getFaithPoints();
	}
	
	/**Increases the value of Wood stored in the object by num units. 
	 * @param num number of wood pieces to add.*/
	public void addWood(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.resources.setWood(this.resources.getWood() + num + this.resMod.getWood());
	}
	
	/**Increases the value of stones stored in the object by num units. 
	 * @param num number of stones to add.*/
	public void addStone(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.resources.setStone(this.resources.getStone() + num + this.resMod.getStone());
	}
	
	/**Increases the number of coins stored in the object by num units. 
	 * @param num number of Coins to add.*/
	public void addCoins(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.resources.setCoins(this.resources.getCoins() + num + this.resMod.getCoins());
	}
	
	/**Increases the value of Servants stored in the object by num units. 
	 * @param num number of Servants to add.*/
	public void addServants(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.resources.setServants(this.resources.getServants() + num + this.resMod.getServants());
	}
	
	/**Increases the value of Victory Points stored in the object by num units. 
	 * @param num number of Victory Points to add.*/
	public void addVictoryPoints(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.points.setVictoryPoints(this.points.getVictoryPoints() + num + this.pointsMod.getVictoryPoints());
	}
	
	/**Increases the value of Military Points stored in the object by num units. 
	 * @param num number of Military Points to add.*/
	public void addMilitaryPoints(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.points.setMilitaryPoints(this.points.getMilitaryPoints() + num + this.pointsMod.getMilitaryPoints());
	}
	
	/**Increases the value of Faith Points stored in the object by num units.
	 * @param num number of Faith Points to add. */
	public void addFaithPoints(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		this.points.setFaithPoints(this.points.getFaithPoints() + num + this.pointsMod.getFaithPoints());
	}
	
	public void payCoins(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.resources.getCoins() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.resources.setCoins(temp);
		
	}
	
	public void payWood(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.resources.getWood() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.resources.setWood(temp);
	}
	
	public void payStone(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.resources.getStone() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.resources.setStone(temp);
	}

	public void payServants(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.resources.getServants() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.resources.setServants(temp);
	}
	
	public void payMilitaryPoints(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.points.getMilitaryPoints() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.points.setMilitaryPoints(temp);
	}
	
	public void payVictoryPoints(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.points.getVictoryPoints() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.points.setVictoryPoints(temp);
	}
	public void payFaithPoints(int num) throws IllegalArgumentException
	{
		if (num < 0) throw new IllegalArgumentException();
		int temp = this.points.getFaithPoints() - num;
		if (temp < 0) throw new IllegalArgumentException();
		this.points.setFaithPoints(temp);
	}
	/**
	 * @return the resources
	 */


	/**
	 * @return the resMod
	 */
	public Resources getResMod() {
		return resMod;
	}

	/**
	 * @return the pointsMod
	 */
	public Points getPointsMod() {
		return pointsMod;
	}
	
	
	
	
}
