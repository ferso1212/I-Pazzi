package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleTowerSpace extends SingleSpace {

	private DevelopmentCard card;

	public SingleTowerSpace(int diceRequirement, ImmProperties instantBonus, FamilyMember member, SingleSpaceType type,
			DevelopmentCard card) {
		super(diceRequirement, instantBonus, member, type);
		this.card = card;
	}

	public DevelopmentCard getCard() {
		return card;
	}

	public void setCard(DevelopmentCard card) {
		this.card = card;
	}
	
	public void reset(){
		this.occupant=null;
		this.card=null;
	}

}
