package it.polimi.ingsw.ps21.model.match;

public class MatchEra extends MatchState {
	private int era;
	
	public MatchEra(Match match, int i){
		super(match);
		era = i;
	}
	@Override
	public MatchState goNext() {
		return new MatchEra(match, era + 1);
	}

}
