package it.polimi.ingsw.ps21.model.match;

public class StartingMatch extends MatchState {

	
	public StartingMatch(Match match){
		super(match);
	}
	@Override
	public MatchState goNext() {
		return new MatchEra(match, 1);
	}

}
