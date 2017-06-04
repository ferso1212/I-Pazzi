package it.polimi.ingsw.ps21.controller;

import java.util.Map;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController {
	private Map<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match matchState;
	
	public MatchController(UnsettedMatch match, UserHandler...handlers) {
		super();
		this.matchState = match;
		for(UserHandler player: handlers)
		{
			this.handlersMap.put(player.getPlayerId(), player);
		}
		match.startMatch();
	
	}
	
	
}

