package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */

public class NoMilitaryForTerritory extends PermanentLeaderEffect {

	public NoMilitaryForTerritory(Requirement requirement) {
		super();
	}
	

	@Override
	public void activate(Player player) {
		// TODO need a specific Modifier in player
	}

	@Override
	public String getType() {
		return new String(this.getClass().getName());
	}


	@Override
	public String getDesc() {
		return new String("You don't have to satisfy the Military Points requirement to pick a Territory Card");
	}

}
