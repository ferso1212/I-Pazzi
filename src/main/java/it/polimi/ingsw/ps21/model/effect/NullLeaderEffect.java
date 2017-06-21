package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;

public class NullLeaderEffect extends LeaderEffect {

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetActivation() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Null Effect";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "This effect does nothing";
	}

}
