package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.player.Player;

public class FinalRoundMatch extends Match {
	
	public FinalRoundMatch(Match prevState) {
		super();
		this.players = prevState.players;
		this.order = prevState.order;
		this.observers = prevState.observers;
		throwDices();
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

	private Match nextState() {
		for (int i=0; i<4; i++){
			for (Player p: board.getCouncilPalace().getOccupants()));
			// TODO need a method that return playerOrder in councilPalace
		}
		for (Player p: players){
			// vatican Support
			board.resetFaithPoints(p);			
		}
		
		return new InitialRoundMatch(this);
	}
	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
