package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.actions.Action;

public class EndedMatch extends Match {

	public EndedMatch(Match finishedMatch){
		super(finishedMatch);
	}
	@Override
	public Match makeAction(Action nextAction) throws MatchException {
			throw new MatchException();
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		Match clone = new EndedMatch(this);
		
		// TODO implement Board.clone() clone.board = this.board.clone();
		clone.observers = new ArrayList<>();
		for (Observer o: observers){
			clone.observers.add(o);
		}
		// TODO clone other variables
		return clone;
	}

}
