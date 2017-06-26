package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class Space {
	
	protected int diceRequirement;
	protected ImmProperties instantBonus;
	
	public Space(int diceRequirement, ImmProperties instantBonus) {
		
		this.diceRequirement = diceRequirement;
		this.instantBonus = instantBonus;
	}

	public ImmProperties getInstantBonus() {
		return instantBonus;
	}
	
	public int getDiceRequirement() {
		return diceRequirement;
	}
	
	public abstract boolean isOccupable(Player player, FamilyMember member) ;
	
	public abstract void occupy(Player player, FamilyMember member) throws NotOccupableException;
	
	public abstract void reset();

}
