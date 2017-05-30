package it.polimi.ingsw.ps21.model.deck;

import java.util.Set;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.Player;



public class CardDiceEffect extends Effect {

	private int cardDiceValue;
	private Set<DevelopmentCardType> types;
	
	public CardDiceEffect(Requirement reqs[], int diceValue, DevelopmentCardType...types) throws TooManyArgumentException{
		super(reqs);
		if (types.length > 2) throw new TooManyArgumentException();
		cardDiceValue = diceValue;
		if (types.length == 0){
			for (DevelopmentCardType d: DevelopmentCardType.values()){
				this.types.add(d);
			}
		}
		for(DevelopmentCardType w: types){
			this.types.add(w);
		}
		

	}
	
	@Override
	public void activate(Player player) {
		for (DevelopmentCardType devType: types) {
			player.getModifiers().getDiceMods().getDiceMod(devType).setValue(cardDiceValue);
		}		
	}
}
