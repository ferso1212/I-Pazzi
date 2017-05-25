package it.polimi.ingsw.ps21.model;

import java.util.Queue;

public class MultipleSpace extends Space{
	
	protected Queue<Player> occupants;
	protected int diceMalus;
	
	public MultipleSpace(int diceRequirement, ImmProperties instantBonus, SpaceType type, Queue<Player> occupants,
			int diceMalus) {
		super(diceRequirement, instantBonus, type);
		this.occupants = occupants;
		this.diceMalus = diceMalus;
	}

	public Queue<Player> getOccupants() {
		return occupants;
	}

	public boolean occupy(Player player){
		switch (type) {
		case value: COUNCIL
		
			
			break;

		default:
			break;
		}
	}

	public int getDiceMalus() {
		return diceMalus;
	}
	
	 
}
