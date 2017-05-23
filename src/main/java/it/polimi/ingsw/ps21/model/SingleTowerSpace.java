package it.polimi.ingsw.ps21.model;

public class SingleTowerSpace <T extends DevelopmentCard> extends SingleSpace {
	
	private T card;

	public SingleTowerSpace(Player occupant, int requirement, ImmProperties instantBonus, SingleSpaceType type,
			T card) {
		super(occupant, requirement, instantBonus, type);
		this.card = card;
	}

	public T getCard() {
		return card;
	}

	public void setCard(T card) {
		this.card = card;
	}

	
	

}
