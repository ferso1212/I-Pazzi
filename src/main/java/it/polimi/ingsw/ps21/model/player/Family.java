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
	public Family(String playerId)
	{
		this.members=new EnumMap<MembersColor, FamilyMember>(MembersColor.class);
		for(MembersColor mColor: MembersColor.values())
		{
			this.members.put(mColor, new FamilyMember(mColor, playerId));
		}
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
	
	public void useMember(FamilyMember membToUse)
	{
		members.remove(membToUse.getColor());
	}
	
	/** Resets all the Family members' values to 0.
	 * 
	 */
	public void roundReset()
	{
	for(FamilyMember m: this.members.values())
		this.members.put(mColor, new FamilyMember(mColor, playerId));
	}
}
