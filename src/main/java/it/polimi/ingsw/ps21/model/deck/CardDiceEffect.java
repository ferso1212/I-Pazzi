package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.CardType;

public class CardDiceEffect extends Effect {

	private int cardDiceValue;
	private CardType cardType;
	private boolean allType;
	
	public CardDiceEffect(Requirement req, int diceValue, CardType card){
		super(req);
		cardDiceValue = diceValue;
		cardType = card;
	}
	
	public boolean activate(Player player) {
		
		return false;
	}

}
