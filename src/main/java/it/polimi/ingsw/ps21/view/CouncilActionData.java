package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.model.player.MembersColor;

public class CouncilActionData extends ActionData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3149495713482498732L;
	private MembersColor familyMember;
	private int[] chosenPrivileges;
	
	public CouncilActionData(MembersColor familyMember, int[] chosenPrivileges) {
		super();
		this.familyMember = familyMember;
		this.chosenPrivileges = chosenPrivileges;
	}
	
}
