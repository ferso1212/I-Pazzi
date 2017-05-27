package it.polimi.ingsw.ps21.model;

public abstract class Space {
	
	protected int diceRequirement;
	protected ImmProperties instantBonus;
	
	public Space(int diceRequirement, ImmProperties instantBonus) {
		
		this.diceRequirement = diceRequirement;
		this.instantBonus = instantBonus;
	}

	public abstract boolean isOccupable(FamilyMember member);

	public ImmProperties getInstantBonus() {
		return instantBonus;
	}
	
	public int getDiceRequirement() {
		return diceRequirement;
	}
	
	public abstract void occupy(FamilyMember member) throws NotOccupableException;


}
