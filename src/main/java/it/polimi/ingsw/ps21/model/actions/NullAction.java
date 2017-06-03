package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class NullAction extends ExtraAction {

	public NullAction(PlayerColor playerId) {
		super(playerId);
	}

	@Override
	public Message isLegal(Player player, Match match) {
		
		return null;
	}

	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {
		
		return null;
	}
	
	

}
