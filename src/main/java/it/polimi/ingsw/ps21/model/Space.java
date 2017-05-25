package it.polimi.ingsw.ps21.model;

import java.util.Queue;

public abstract class Space {
	
	protected int diceRequirement;
	protected ImmProperties instantBonus;
	
	public Space(int diceRequirement, ImmProperties instantBonus) {
		
		this.diceRequirement = diceRequirement;
		this.instantBonus = instantBonus;
	}

	public abstract Player getOccupant();
	
	public abstract Queue<Player> getOccupants();

	public ImmProperties getInstantBonus() {
		return instantBonus;
	}
	
	public int getDiceRequirement() {
		return diceRequirement;
	}
	
	public abstract boolean occupy(Player player);


}
