package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

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
	public boolean isOccupable(FamilyMember member) {
		if ((!this.type.equals(MultipleSpaceType.COUNCIL)) && (occupants.contains(member))){
			return false;
		}
		return true;
	}

	@Override
	public void occupy(Player player) {
		return occupants.add(player);
	}

	
	
	 
}
