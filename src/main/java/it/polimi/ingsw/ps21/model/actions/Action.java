package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;

/**
 * This class is created in the view and is checked in the controller
 * The execution is done by Model, in action class 
 * 
 **/
public abstract class Action {
	protected Match match;
	protected Player player;
	
	public Action(Match m, Player p){
		match = m;
		player = p;
	}
	
	public abstract boolean isLegal();
	
	public abstract boolean execute();
}
