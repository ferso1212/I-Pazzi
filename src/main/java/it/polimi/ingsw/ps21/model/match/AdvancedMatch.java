package it.polimi.ingsw.ps21.model.match;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;

/*
 * This implementation of match must contains only advanced Player
 * 
 */

public class AdvancedMatch extends Match {

	public AdvancedMatch() throws ParserConfigurationException, BuildingDeckException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExtraAction[] doAction(Action action) {
		return null;
	}

	@Override
	public Match setNextPlayer() {
		return null;
	}

 /* TODO	@Override
	public Match getCopy() throws CloneNotSupportedException {
		return null;
	}
	*/

}
