package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;

public class PrivilegesBonus extends InstantLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3816683596688361836L;
	private int numberPrivileges;
	public PrivilegesBonus(Requirement reqs[], int numberOfPrivileges) {
		super(reqs);
		this.numberPrivileges = numberOfPrivileges;
	}
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		return new TakePrivilegesAction(player.getId(), numberPrivileges);
	}

	@Override
	public String getType() {
		return "Privileges Bonus Leader Effect" ;
	}

	@Override
	public String getDesc() {
		return "You can pick " + numberPrivileges + " privileges";
	}

}
