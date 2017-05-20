package it.polimi.ingsw.ps21.model;

public class Space {
	
	protected Player occupant;
	protected int requirement;
	protected ImmProperties instantBonus;
	protected SpaceType type;
	
	public Space(Player occupant, int requirement, ImmProperties instantBonus, SpaceType type){
		
		this.occupant=occupant;
		this.requirement=requirement;
		this.instantBonus=instantBonus;
		this.type=type;
		
	}

	public Player getOccupant() {
		return occupant;
	}

	public ImmProperties getInstantBonus() {
		return instantBonus;
	}
	
	public int getRequirement() {
		return requirement;
	}
	
	public boolean occupy(Player player) {
		this.occupant=player;
		if(this.occupant==player)
			return true;
		return false;
	}

	public SpaceType getType() {
		return type;
	}
	
	
	
	

}
