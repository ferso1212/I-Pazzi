package it.polimi.ingsw.ps21.controller;

import java.util.Map;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController {
	private Map<Player, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match matchState;
	
	public MatchController(UnsettedMatch match, UserHandler...handlers) {
		super();
		this.matchState = match;
		match.startMatch();
		ImmProperties[] initialProps= MatchFactory.instance().makeInitialProperties();
		int i=0;
		for(UserHandler player: handlers)
		{
			PlayerProperties newProperties= new PlayerProperties(initialProps[i]);
			Player newPlayer=new Player();
			handlersMap.put(key, value)
		}
	}
	
	
}

