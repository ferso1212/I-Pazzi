package it.polimi.ingsw.ps21.model.player;

import java.util.EnumMap;

public class AdvFamily  extends Family{
	private EnumMap<MembersColor, AdvFamilyMember> members;
	public AdvFamily(PlayerColor playerId) {
		super(playerId);
		this.members=new EnumMap<MembersColor, AdvFamilyMember>(MembersColor.class);
		for(MembersColor mColor: MembersColor.values())
		{
			this.members.put(mColor, new AdvFamilyMember(mColor, playerId));
		}
	}

	
	
	public AdvFamilyMember getMember(MembersColor color)
	{
		return this.members.get(color);
	}
	

}
