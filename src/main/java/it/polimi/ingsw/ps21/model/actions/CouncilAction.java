package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class CouncilAction extends Action{

	public CouncilAction(Match match, Player player) {
		super(match, player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLegal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute() throws NotExecutableException, NotOccupableException, RequirementNotMetException {
		// TODO Auto-generated method stub
		
	}
	
	

}
