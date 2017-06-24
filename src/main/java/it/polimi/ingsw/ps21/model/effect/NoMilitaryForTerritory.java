package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */

public class NoMilitaryForTerritory extends PermanentLeaderEffect {

	public NoMilitaryForTerritory(Requirement requirements[]) {
		super(requirements);
	}
	

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		player.getDeck().setNoAddingRequirement(DevelopmentCardType.TERRITORY);
		return new NullAction(player.getId());
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
