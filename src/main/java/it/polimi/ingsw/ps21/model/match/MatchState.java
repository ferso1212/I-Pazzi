package it.polimi.ingsw.ps21.model.match;

public abstract class MatchState {
	protected Match match;
	
	public MatchState(Match match){
		this.match = match;
	}

	public abstract MatchState goNext();
	
}
