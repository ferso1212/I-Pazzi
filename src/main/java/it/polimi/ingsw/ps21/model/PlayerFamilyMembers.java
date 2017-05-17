package it.polimi.ingsw.ps21.model;

/**Used to store the set of the Family Members of a Player
 * Exposes methods to set, increase and get the value of each family member.
 * @author fabri
 *
 */
public class PlayerFamilyMembers {
	private FamilyMember[] members;
	
	/**
	 * The members are stored in an array of 4 element, each one of the FamilyMember type.
	 * The constructor initializes the array with 4 Family Members, each one with his color.
	 */
	public PlayerFamilyMembers()
	{
		this.members= new FamilyMember[4];
		members[0]= new FamilyMember(MembersColor.WHITE);
		members[1]= new FamilyMember(MembersColor.ORANGE);
		members[2]= new FamilyMember(MembersColor.BLACK);
		members[3]= new FamilyMember(MembersColor.NEUTRAL);
	}
	
	/**
	 * Sets the value of the orange family member.
	 * @param value to set.
	 */
	public void setOrangeValue(int value)
	{
		this.members[1].setValue(value);
	}
	
	/**
	 * Sets the value of the white family member.
	 * @param value to set.
	 */
	public void setWhiteValue(int value)
	{
		this.members[0].setValue(value);
	}
	
	/**
	 * Sets the value of the black family member.
	 * @param value to set.
	 */
	public void setBlackValue(int value)
	{
		this.members[2].setValue(value);
	}
	
	/**
	 * Sets the value of the neutral family member.
	 * @param value to set.
	 */
	public void setNeutralValue(int value)
	{
		this.members[3].setValue(value);
	}
	
	/** Gets the value of the orange family member.
	 */
	public int getOrangeValue()
	{
		return this.members[1].getValue();
	}
	
	/** Gets the value of the black family member.
	 */
	public int getBlackValue()
	{
		return this.members[2].getValue();
	}
	
	/** Gets the value of the white family member.
	 */
	public int getWhiteValue()
	{
		return this.members[0].getValue();
	}
	
	/** Gets the value of the neutral family member.
	 */
	public int getNeutralValue()
	{
		return this.members[3].getValue();
	}
	
	/**Increases the value of the Orange Member by the number of servants used when placed.
	 * 
	 * @param servants
	 */
	public void addOrangeValue(int servants)
	{
		this.members[1].addValue(servants);
	}
	
	/**Increases the value of the White Member by the number of servants used when placed.
	 * 
	 * @param servants
	 */
	public void addWhiteValue(int servants)
	{
		this.members[0].addValue(servants);
	}
	
	/**Increases the value of the Black Member by the number of servants used when placed.
	 * 
	 * @param servants
	 */
	public void addBlackValue(int servants)
	{
		this.members[2].addValue(servants);
	}
	
	/**Increases the value of the Neutral Member by the number of servants used when placed.
	 * 
	 * @param servants
	 */
	public void addNeutralValue(int servants)
	{
		this.members[3].addValue(servants);
	}
	
	/** Resets all the Family members' values to 0.
	 * 
	 */
	public void roundReset()
	{
		this.members[0].setValue(0);
		this.members[1].setValue(0);
		this.members[2].setValue(0);
		this.members[3].setValue(0);
	
	}
}
