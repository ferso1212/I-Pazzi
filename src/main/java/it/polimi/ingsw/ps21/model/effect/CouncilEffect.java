package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926295798078835683L;
	private int numberPrivileges;
	public CouncilEffect(ImmProperties cost, int privileges) {
		super(cost);
		numberPrivileges = privileges;
	}

	@Override
	public ExtraAction activate(Player player) {
		 return new TakePrivilegesAction(player.getId(), numberPrivileges);
	}

	@Override
	public String getType() {
		return "Council Effect";
	}

	@Override
	public String getDesc() {
		return "You can choose " +  numberPrivileges + " different Council Privileges";
	}

}
