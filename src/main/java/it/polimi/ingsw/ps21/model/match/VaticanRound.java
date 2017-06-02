package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;

public class VaticanRound extends Round {

	@Override
	public int getRoundNumber() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void setPlayers(Player... players) {
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtraAction doAction(Action action, Match match) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Round setNextPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

}
