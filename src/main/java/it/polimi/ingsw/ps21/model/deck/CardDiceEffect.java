package it.polimi.ingsw.ps21.model.deck;

import java.util.Set;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.Player;



public class CardDiceEffect extends Effect {

	private int cardDiceValue;
	private Set<DevelopmentCardType> types;
	
	public CardDiceEffect(OrRequirement req, int diceValue, WorkType...types) throws TooManyArgumentException{
		super(req);
		if (types.length > 2) throw new TooManyArgumentException();
		cardDiceValue = diceValue;
		if (types.length == 0){
			this.types.add(DevelopmentCardType.BUILDING);
			this.types.add(DevelopmentCardType.TERRITORY);
		}
		for(WorkType w: types){
			if(w == WorkType.HARVEST) this.types.add(DevelopmentCardType.BUILDING);
			else this.types.add(DevelopmentCardType.TERRITORY);
		}
		

	}
	
	@Override
	public void activate(Player player) {
		for (DevelopmentCardType worktype: types) {
			player.getModifiers().getDiceMods().getDiceMod(worktype).setValue(cardDiceValue);
		}		
	}
}
