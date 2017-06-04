package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class MultipleSpace extends Space{
	
	protected Queue<FamilyMember> occupants;
	protected int diceMalus;
	
	

	public MultipleSpace(int diceRequirement, ImmProperties instantBonus, int diceMalus, Queue<FamilyMember> occupants) {
		super(diceRequirement, instantBonus);
		this.occupants = occupants;
		this.diceMalus = diceMalus;
	}

	public Queue<FamilyMember> getOccupants() {
		return occupants;
	}
	
	public boolean containPlayer(Player player){
		for (FamilyMember f : this.occupants){
			if (f.getOwnerId()==player.getId()){
				return true;
			}
		} return false;
	}
	 
}
