package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleTowerSpace extends SingleSpace {

	protected DevelopmentCard card;
	protected static final ImmProperties REOCCUPY_TOWER_COST = new ImmProperties(3);

	public SingleTowerSpace(int diceRequirement, ImmProperties instantBonus) {
		super(diceRequirement, instantBonus);
		this.card = null;
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

	public ImmProperties[] getCostsCard(Tower tower) {
		if (tower.isOccupied()){
			ImmProperties[] costsCopy = new ImmProperties[this.card.getCosts().length];
		for (int i=0; i<this.card.getCosts().length; i++){
			costsCopy[i] = this.card.getCosts()[i].sum(REOCCUPY_TOWER_COST);
		}
		return costsCopy;
		}
		return this.card.getCosts();
	}


}
