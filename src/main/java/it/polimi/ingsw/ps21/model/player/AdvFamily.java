package it.polimi.ingsw.ps21.model.player;

import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.MembersColor;

public class AdvFamily  extends Family{
	public AdvFamily(String playerId) {
		super(playerId);
	}

	private EnumMap<MembersColor, AdvFamilyMember> members;
	
	public AdvFamilyMember getMember(MembersColor color)
	{
		return this.members.get(color);
	}
	

}
