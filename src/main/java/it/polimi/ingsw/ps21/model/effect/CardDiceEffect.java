package it.polimi.ingsw.ps21.model.effect;

import java.util.Set;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CardDiceEffect extends Effect {

	private int cardDiceValue;
	private Set<DevelopmentCardType> types;

	public CardDiceEffect(ImmProperties cost, int diceValue, DevelopmentCardType... types)
			throws TooManyArgumentException {
		super(cost);
		if (types.length > 2)
			throw new TooManyArgumentException();
		cardDiceValue = diceValue;
		if (types.length == 0) {
			for (DevelopmentCardType d : DevelopmentCardType.values()) {
				this.types.add(d);
			}
		}
		for (DevelopmentCardType w : types) {
			this.types.add(w);
		}

	}

	@Override
	public ExtraAction activate(Player player) {
		for (DevelopmentCardType devType : types) {
			player.getModifiers().getDiceMods().getDiceMod(devType).setValue(cardDiceValue);
		}
		return new NullAction();
	}
	

	@Override
	public String getType() {
		return new String(this.getClass().getName());
	}

	@Override
	public String getDesc() {
		String output= new String("Increase the action value by " + this.cardDiceValue + " units when a ");
		for(DevelopmentCardType type: types)
		{output.concat(type.toString() + " card ");
		if(type.ordinal()<type.values().length -1 ) output.concat("or a ");
		}
		output.concat(" is picked from a tower space.");
		return output;
	}
}
