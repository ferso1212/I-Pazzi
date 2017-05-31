package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

/**
 * This class is created in the view and is checked in the controller
 * The execution is done by Model, in action class 
 * 
 **/
public abstract class Action {
	protected Match match;
	protected Player player;
	
	public Action(Match match, Player player){
		this.match = match;
		this.player = player;
	}
	
	public abstract boolean isLegal();
	
	public abstract void execute() throws NotExecutableException, NotOccupableException, RequirementNotMetException, InsufficientPropsException ;
}
