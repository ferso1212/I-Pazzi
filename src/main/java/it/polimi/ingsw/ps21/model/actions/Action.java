package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public abstract class Action {
	
	protected PlayerColor playerId;
	protected int updateCounter;
	
	public Action(PlayerColor playerId) {
		super();
		this.playerId = playerId;
	}

	public abstract Message update(Player player, Match match);
	
	public abstract ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException;

}
