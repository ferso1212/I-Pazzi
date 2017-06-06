package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;

/*
 * This implementation of match must contains only advanced Player
 * 
 */

public class AdvancedMatch extends Match {

	@Override
	public ExtraAction[] doAction(Action action) {
		return null;
	}

	@Override
	public Match setNextPlayer() {
		return null;
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		return null;
	}

}
