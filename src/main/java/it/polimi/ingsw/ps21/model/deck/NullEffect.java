package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class NullEffect extends Effect {

	public NullEffect() {
		super(new Requirement(new CardsNumber(0), new ImmProperties(0)));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activate(Player player) throws UnchosenException {
		return;
	}

}
