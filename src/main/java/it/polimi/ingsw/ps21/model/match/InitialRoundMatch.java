package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class InitialRoundMatch extends Match {

	
	public InitialRoundMatch(Match previousState) {
		super(previousState);
		era = era + 1;
		round = 1;
		throwDices();
		notifyObservers();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Match makeAction(Action nextAction) {
		// if (nextAction.isLegal())
		try {
			nextAction.execute();
		} catch (NotExecutableException | NotOccupableException | RequirementNotMetException e) {
			return this;
		}
		order.poll();
		notifyObservers();
		if (order.isEmpty()) return nextState();
		else return this;
	}
	
	private Match nextState(){
		for (int i=0; i<4; i++){
			for (Player p: board.getCouncilPalace().getOccupants()));
			// TODO need a method that return playerOrder in councilPalace
		}
		return new FinalRoundMatch(this);
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
