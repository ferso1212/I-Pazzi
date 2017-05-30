package it.polimi.ingsw.ps21.model.match;

import java.util.Map;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.player.Player;

public class VaticanSupport extends Match {

	public Map<Player, Boolean> supportChoices;
	
	public VaticanSupport(Match previousMatch) {
		super(previousMatch);
	}
	
	@Override
	public Match makeAction(Action nextAction) throws UnsoppertedActionException {
		throw new UnsoppertedActionException();
	}
	
	
	
	public Match nextState() throws UnchosenException{
		for (Player p: players.values()){
			if (!(supportChoices.containsKey(p))) throw new UnchosenException();
		}
		if (era < 3){
			board.nextEra(era + 1);
			return new InitialRoundMatch(this);
		}	
		else return new EndedMatch(this);
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
