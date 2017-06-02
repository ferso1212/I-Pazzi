package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

/**
 * This class is created in the view and is checked in the controller
 * The execution is done by Model, in action class 
 * 
 **/
public abstract class Action {
	protected PlayerColor playerId;
	
	public Action(PlayerColor playerId) {
		super();
		this.playerId = playerId;
	}

	public abstract Message isLegal(Player player, Match match);
	
	public abstract ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException, RequirementNotMetException, InsufficientPropsException ;
}
