package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;

public class PickAnotherCardAction extends ExtraAction {
	private int diceReq;
	private DevelopmentCardType types[];

	public PickAnotherCardAction(int diceReq, DevelopmentCardType...cardTypes){
		this.diceReq = diceReq;
		if (cardTypes.length!=0) this.types = cardTypes;
		else this.types = DevelopmentCardType.values();
	}
	
}
