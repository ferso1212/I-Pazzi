package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PropEffect extends Effect {

	private ImmProperties bonus;
	
	public PropEffect(OrRequirement req, ImmProperties bonus) {
		super(req);
		this.bonus = bonus;
	}
	
	@Override
	public void activate(Player player) {
		player.getProperties().increaseProperties(bonus);
	}

}
