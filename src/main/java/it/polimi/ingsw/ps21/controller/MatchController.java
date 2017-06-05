package it.polimi.ingsw.ps21.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController implements Observer{
	private Map<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match matchState;
	
	public MatchController(UnsettedMatch match, UserHandler...handlers) {
		super();
		this.matchState = match;
		handlersMap= new HashMap<>();
		for(UserHandler handler: handlers)
		{
			this.handlersMap.put(handler.getPlayerId(), handler);
			match.addObserver(handler);
			handler.addObserver(this);
			
			
		}
		match.addObserver(this);
		match.startMatch();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void gameLoop()
	{
		
	}
	
	public void roundLoop()
	{
		
	}
	
}

