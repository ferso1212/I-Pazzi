package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;

public class MarketOccupants implements Serializable{
	private FamilyMemberData firstOccupant;
	private FamilyMemberData secondOccupant;
	
	public MarketOccupants(FamilyMemberData firstOccupant, FamilyMemberData secondOccupant) {
		super();
		this.firstOccupant = firstOccupant;
		this.secondOccupant = secondOccupant;
	}

	public FamilyMemberData getFirstOccupant() {
		return firstOccupant;
	}

	public FamilyMemberData getSecondOccupant() {
		return secondOccupant;
	}
	
	
}
