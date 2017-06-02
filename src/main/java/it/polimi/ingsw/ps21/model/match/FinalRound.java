package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;

public class FinalRound extends Round {

	@Override
	public void setPlayers(Player... players) {
		// TODO Auto-generated method stub

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
		order.poll();
		if (order.isEmpty()) return new VaticanRound();
		else return this;
	}

	@Override
	public int getRoundNumber() {
		// TODO Auto-generated method stub
		return 2;
	}


}
