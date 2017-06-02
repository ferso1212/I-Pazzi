package it.polimi.ingsw.ps21.model.match;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class InitialRoundMatch extends Match {
	private final static Logger LOGGER = Logger.getLogger(InitialRoundMatch.class.getName());
	
	protected InitialRoundMatch(Match previousState) {
		super(previousState);
		era = era + 1;
		round = 1;
		throwDices();
		notifyObservers();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtraAction doAction(Action action) {
		ExtraAction extraAction = new NullAction();
		try {
					extraAction = nextAction.execute(order.element(),this);
				} catch (NotExecutableException | NotOccupableException | RequirementNotMetException| InsufficientPropsException e) {
					LOGGER.log(Level.WARNING, "Not activable action", e);
					
				}
				notifyObservers();
				return extraAction;
	}

	@Override
	public Match setNextPlayer() {
		order.poll();
		if (!(order.isEmpty())) return this;
		FinalRoundMatch newState = new FinalRoundMatch(this);
	}
	
	

}
