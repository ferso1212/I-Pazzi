package it.polimi.ingsw.ps21.model;

/**Stores the color and the value of each family member.
 * 
 * @author fabri
 *
 */
public class FamilyMember {
	private int value;
	private MembersColor color;
	
	/**
	 * Returns the value of the family member, which is the number on the corresponding dice + the number of servants used in the action.
	 * @return the value of the family member
	 */
	public int getValue()
	{
		return this.value;
	}
	
	/**Increases the value of the Member by the number of servants used when placed.
	 * 
	 * @param servants number of servants used in the action
	 */
	public void addValue(int servants)
	{
		this.value += servants;
	}
	
	/**Returns the color of the family member.
	 * 
	 * @return the color of the family member.
	 */
	public MembersColor getColor()
	{
		return this.color;
	}

	/**
	 * Sets the inner value of the new member to 0 and its color to the one passed as parameter.
	 * @param color the color to set in the new family member
	 */
	public FamilyMember(MembersColor color)
	{
		this.value=0;
		this.color=color;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
