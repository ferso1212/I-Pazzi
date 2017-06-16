package it.polimi.ingsw.ps21.model.player;

/**Stores the color and the value of each family member.
 * 
 * @author fabri
 *
 */
public class FamilyMember {
	protected int value;
	protected MembersColor color;
	protected int modifier;
	protected PlayerColor ownerId;
	protected boolean used;
	
	/**
	 * Returns the value of the family member, which is the number on the corresponding dice + the number of servants used in the action + the value of the modifier.
	 * @return the value of the family member
	 */
	public int getValue()
	{
		return (this.value + this.modifier);
	}
	
	/**Increases the value of the Member by the number of servants used when placed.
	 * 
	 * @param servants number of servants used in the action
	 */
	public void increaseValue(int servants)
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
	public FamilyMember(MembersColor color, PlayerColor id)
	{
		this.value=0;
		this.color=color;
		this.modifier=0;
		this.ownerId=id;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**Sets the member's modifier value. This value is added to the returned value of the getValue() method.
	 * @param modifier the modifier to set
	 */
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	
	public void increaseModifier(int valueToAdd)
	{
		this.modifier+=valueToAdd;
	}

	/**
	 * @return the playerId
	 */
	public PlayerColor getOwnerId() {
		return this.ownerId;
	}

	/**
	 * @return the used
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
	
	
}
