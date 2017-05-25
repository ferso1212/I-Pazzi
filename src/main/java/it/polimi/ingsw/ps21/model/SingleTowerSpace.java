package it.polimi.ingsw.ps21.model;

public class SingleTowerSpace <T extends DevelopmentCard> extends SingleSpace {
	
		public SingleTowerSpace(int diceRequirement, ImmProperties instantBonus, SpaceType type, Player occupant, T card) {
		super(diceRequirement, instantBonus, type, occupant);
		this.card = card;
	}

	private T card;

	public T getCard() {
		return card;
	}

	public void setCard(T card) {
		this.card = card;
	}
	
	

}
