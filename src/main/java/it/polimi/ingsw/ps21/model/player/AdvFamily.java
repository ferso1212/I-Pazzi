package it.polimi.ingsw.ps21.model.player;

import it.polimi.ingsw.ps21.model.MembersColor;

public class AdvFamily  extends Family{
	private AdvFamilyMember orange;
	private AdvFamilyMember black;
	private AdvFamilyMember white;
	private AdvFamilyMember neutral;
	
	public AdvFamilyMember getMember(MembersColor color) throws IllegalArgumentException
	{
		switch(color)
		{
		case WHITE: return this.white;
		case ORANGE: return this.orange;
		case BLACK: return this.black;
		case NEUTRAL: return this.neutral;
		default: throw new IllegalArgumentException();
		}
	}
	

}
