package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.model.player.MembersColor;

public class MarketActionData extends ActionData{
	private int space;
	private MembersColor familyMember;
	
	public MarketActionData(int space, MembersColor familyMember) {
		super();
		this.space = space;
		this.familyMember = familyMember;
	}

	public int getSpace() {
		return space;
	}

	public MembersColor getFamilyMember() {
		return familyMember;
	}
	

}
