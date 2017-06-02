package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;

public class EndedMatch extends Match {

	public EndedMatch(Match finishedMatch){
		super(finishedMatch);
	}
	@Override
	public ExtraAction doAction(Action nextAction) {
			return new NullAction();
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
	@Override
	public Match setNextPlayer(){
		return this;
	}

}
