package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;

public class InitialRound extends Round {

	@Override
	public Player getPlayer() {
		return order.peek();
	}

	@Override
	public ExtraAction doAction(Action action, Match match) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Round setNextPlayer() {
		order.poll();
		if (order.isEmpty()) return new FinalRound();
		else return this;
	}

	@Override
	public void setPlayers(Player... players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRoundNumber() {
		return 1;
	}


}
