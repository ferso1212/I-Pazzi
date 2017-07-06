package it.polimi.ingsw.ps21.model.player;

import java.util.EnumMap;

/**Used to store the set of the Family Members of a Player
 * Exposes methods to set, increase and get the value of each family member.
 * @author fabri
 *
 */
public class Family {
	private EnumMap<MembersColor, FamilyMember> members;
	protected int servantsForOne;
	
	/**
	 * The members are stored in an EnumMap that maps each value of the MembersColor enum to the corresponding Family Member.
	 */
	public Family(PlayerColor playerId)
	{
		this.members=new EnumMap<MembersColor, FamilyMember>(MembersColor.class);
		for(MembersColor mColor: MembersColor.values())
		{
			this.members.put(mColor, new FamilyMember(mColor, playerId));
		}
		this.servantsForOne=1;
	}
	
	/**Returns the Family Member of the specified color.
	 * 
	 * @param color color of the family member required
	 * @return the family member
	 */
	public FamilyMember getMember(MembersColor color)
	{
		return this.members.get(color);
	}
	
	/**If the family has not been used, it sets it as used.
	 * @param membToUse
	 * @return true if it succeeds, false if the Family Member was already used. 
	 */
	public boolean useMember(FamilyMember membToUse)
	{
		if(membToUse.isUsed()) return false;
		else{membToUse.setUsed(true);
		return true;}
	}
	
	/** Resets all the Family members' values to 0 and sets their state to NOT USED.
	 *  This method uses FamilyMember.setValue() to reset the value of the members, hence the value of the fixed members is not resetted.
	 */
	public void roundReset()
	{
		for(FamilyMember m: members.values())
		{
			m.setValue(0);
			m.setUsed(false);
		}
	}
	
	/**Sets the number of servants required to increase by one the value of an action
	 * 
	 * @param servantsForOne
	 */
	public void setServantsForOne(int servantsForOne) {
		this.servantsForOne = servantsForOne;
	}
	
	/**Returns the value of the member, modified by the number of servants used.
	 * You should always use this method when you need to increase the value of a member through servants: if you don't use this method and manually add the servants to the member's value, you won't take into account the modifiers.
	 * @param servants used in the action
	 * @param color color of the family member used.
	 * @return the value of the member, modified by the number of servants used.
	 */
	public int getMemberValueWithServants(int servants, MembersColor color)
	{
		return this.getMember(color).getValue() + (servants/this.servantsForOne);
	}
	
}
