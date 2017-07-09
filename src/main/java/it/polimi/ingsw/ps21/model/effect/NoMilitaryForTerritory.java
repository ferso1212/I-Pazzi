package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;


/*
 * To be implemented
 */

public class NoMilitaryForTerritory extends PermanentLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5716674298143695477L;


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
		return "No Military For Territry Effect";
	}


	@Override
	public String getDesc() {
		return "You don't have to satisfy the Military Points requirement to pick a Territory Card";
	}

}
