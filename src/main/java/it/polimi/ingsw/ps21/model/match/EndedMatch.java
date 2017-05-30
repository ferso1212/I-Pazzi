package it.polimi.ingsw.ps21.model.match;

import it.polimi.ingsw.ps21.model.actions.Action;

public class EndedMatch extends Match {

	public EndedMatch(Match finishedMatch){
		super(finishedMatch);
	}
	@Override
	public Match makeAction(Action nextAction) throws MatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
