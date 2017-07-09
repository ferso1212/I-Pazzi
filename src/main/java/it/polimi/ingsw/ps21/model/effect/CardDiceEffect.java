		package it.polimi.ingsw.ps21.model.effect;

import java.util.HashSet;
import java.util.Set;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CardDiceEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4358702211817855889L;
	private int cardDiceValue;
	private Set<DevelopmentCardType> types;

	public CardDiceEffect(int diceValue, DevelopmentCardType... types)
			throws TooManyArgumentException {
		super(new ImmProperties(0));
		this.types = new HashSet<>();
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
		return new NullAction(player.getId());
	}
	

	@Override
	public String getType() {
		return "Card Dice Effect";
	}

	@Override
	public String getDesc() {
		StringBuilder output= new StringBuilder("Increase the action value by " + this.cardDiceValue + " units when a ");
		for(DevelopmentCardType type: types)
		{output.append(type.toString() + " card ");
		if(type.ordinal()<DevelopmentCardType.values().length -1 ) output.append("or a ");
		}
		output.append(" is picked from a tower space.");
		return output.toString();
	}
}
