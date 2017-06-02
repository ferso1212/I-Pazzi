package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class InitialRoundMatch extends Match {
	private final static Logger LOGGER = Logger.getLogger(InitialRoundMatch.class.getName());
	
	protected InitialRoundMatch(Match previousState) {
		super(previousState);
		period = period + 1;
		round = 1;
		throwDices();
		Queue<FamilyMember> temp = board.getCouncilPalace().getOccupants();
		ArrayList<Player> newOrder = new ArrayList<>();
		for (FamilyMember f: temp){
			Player player = players.get(f.getOwnerId());
			if (newOrder.contains(player));
			else newOrder.add(player);
		}
		order = new ArrayDeque<>();
		for (int i=0; i<4; i++)
		for ( int j = newOrder.size() -1 ; j>=0; i--){ // Crea l'ordine del nuovo round
			order.add(newOrder.get(j));
		}
		board.newSetBoard(period);
		notifyObservers();
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtraAction[] doAction(Action action) {
		ExtraAction[] extraActionPool;
		try {
					extraActionPool = action.execute(order.element(),this);
				} catch (NotExecutableException | NotOccupableException | RequirementNotMetException| InsufficientPropsException e) {
					LOGGER.log(Level.WARNING, "Not activable action", e);
					return null;
				}
				notifyObservers();
				return extraActionPool;
	}

	@Override
	public Match setNextPlayer() {
		order.poll();
		if (!(order.isEmpty())) return this;
		return new FinalRoundMatch(this);
	}
	
	

}
