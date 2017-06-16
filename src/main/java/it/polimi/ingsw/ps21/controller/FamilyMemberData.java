package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;

public class FamilyMemberData implements Serializable {
	private MembersColor color;
	private int value;
	private boolean used;
	private boolean exists;
	
	public FamilyMemberData(FamilyMember member)
	{
		if(member==null) exists=false;
		else {
		this.color=member.getColor();
		this.value=member.getValue();
		this.used= member.isUsed();}
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
			return this.color + " member with value " + this.value;
		}
	}
}
