package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilEffect extends Effect {

	private int numberPrivileges;
	public CouncilEffect(ImmProperties cost, int privileges) {
		super(cost);
		numberPrivileges = privileges;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExtraAction activate(Player player) {
		 return new TakePrivilegesAction(player.getId(), numberPrivileges);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
