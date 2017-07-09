package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.ExtraWorkAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;


/*
 * To be implemented
 */
public class InstantWorkEffect extends InstantLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9044020793604777794L;
	private WorkType type;
	private int diceValue;
	public InstantWorkEffect(Requirement reqs[], WorkType type, int diceValue) {
		super(reqs);
		this.type = type;
		this.diceValue = diceValue;
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		return new ExtraWorkAction(player.getId(), 0, type);

	}

	@Override
	public String getType() {
		return "Instant Work Leader Effect";
	}

	@Override
	public String getDesc() {
		return "You can execute one extra " + type + "action with the diceValue of " + diceValue ;
	}

}
