package it.polimi.ingsw.ps21.model.board;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class MultipleWorkSpace extends WorkSpace{
	
	private Queue<FamilyMember> occupants;
	private int diceMalus;

	public MultipleWorkSpace(int diceRequirement, ImmProperties instantBonus, int diceMalus, WorkType workType) {
		super(diceRequirement, instantBonus, workType);
		this.occupants = new LinkedList<>();
		this.diceMalus = diceMalus;
	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if (!(occupants.contains(member)) && !(this.containPlayer(player))){
			return true;
		}
		return false;
	}
	
	@Override
	public void occupy(Player palyer, FamilyMember famMember) {
		occupants.add(famMember);
		famMember.setUsed(true);
	}
	
	public WorkType getWorkType(){
		return this.workType;
	}
	
	public boolean containPlayer(Player player){
		for (FamilyMember f : this.occupants){
			if (f.getOwnerId()==player.getId()){
				return true;
			}
		} return false;
	}

	public Queue<FamilyMember> getOccupants() {
		return occupants;
	}

	public int getDiceMalus() {
		return diceMalus;
	}

	@Override
	public void reset() {
		this.occupants.clear();
		
	}
	
	

}
