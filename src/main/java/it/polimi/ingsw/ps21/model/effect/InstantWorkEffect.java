package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.ExtraWorkAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */
public class InstantWorkEffect extends InstantLeaderEffect {

	private WorkType type;
	private int diceValue;
	public InstantWorkEffect(Requirement reqs[], WorkType type, int diceValue) {
		super(reqs);
		this.type = type;
		this.diceValue = diceValue;
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		return new ExtraWorkAction(player.getId(), 0, type);

	}

	@Override
	public String getType() {
		return "WorkEffectPermanentLeaderEffect";
	}

	@Override
	public String getDesc() {
		return "You can execute one extra " + type + "action with the diceValue of " + diceValue ;
	}

}
