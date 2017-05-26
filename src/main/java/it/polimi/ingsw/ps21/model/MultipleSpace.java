package it.polimi.ingsw.ps21.model;

import java.util.Queue;

public class MultipleSpace extends Space{
	
	private Queue<Player> occupants;
	private int diceMalus;
	private MultipleSpaceType type;
	
	

	public MultipleSpace(int diceRequirement, ImmProperties instantBonus, Queue<Player> occupants, int diceMalus,
			MultipleSpaceType type) {
		super(diceRequirement, instantBonus);
		this.occupants = occupants;
		this.diceMalus = diceMalus;
		this.type = type;
	}

	public Queue<Player> getOccupants() {
		return occupants;
	}

	@Override
	public boolean isOccupable(Player player) {
		if ((!this.type.equals(MultipleSpaceType.COUNCIL)) && (occupants.contains(player))){
			return false;
		}
		return true;
	}

	@Override
	public boolean occupy(Player player) {
		return occupants.add(player);
	}

	
	
	 
}
