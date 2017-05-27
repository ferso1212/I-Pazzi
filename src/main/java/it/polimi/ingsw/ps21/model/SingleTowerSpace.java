package it.polimi.ingsw.ps21.model;

public class SingleTowerSpace<T extends DevelopmentCard> extends SingleSpace {

	private T card;

	public SingleTowerSpace(int diceRequirement, ImmProperties instantBonus, FamilyMember member, SingleSpaceType type,
			T card) {
		super(diceRequirement, instantBonus, member, type);
		this.card = card;
	}

	public T getCard() {
		return card;
	}

	public void setCard(T card) {
		this.card = card;
	}

}
