package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class FamilyMemberData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5515471363627537763L;
	private MembersColor color;
	private int value;
	private boolean used;
	private boolean exists;
	private PlayerColor ownerId;
	
	public FamilyMemberData(FamilyMember member)
	{
		if(member==null) exists=false;
		else {
		this.color=member.getColor();
		this.value=member.getValue();
		this.used= member.isUsed();
		this.ownerId=member.getOwnerId();
		this.exists=true;}
	}

	public MembersColor getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	public boolean isUsed() {
		return used;
	}

	public boolean exists() {
		return this.exists;
	}
	
	public String toString()
	{
		if(!exists) return "FREE";
		else
		{
			return this.color + " member of player " + this.ownerId + " with value " + this.value;
		}
	}
	
	public PlayerColor getOwnerId()
	{
		return this.ownerId;
	}
}
