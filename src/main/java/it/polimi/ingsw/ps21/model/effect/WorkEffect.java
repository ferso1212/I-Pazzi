package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */
public class WorkEffect extends PermanentLeaderEffect {

	public WorkEffect(Requirement req) {
		super(req);
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public String getType() {
		return "WorkEffectPermanentLeaderEffect";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
