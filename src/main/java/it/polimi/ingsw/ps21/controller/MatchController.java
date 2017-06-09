package it.polimi.ingsw.ps21.controller;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController extends Observable implements Observer{
	private EnumMap<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match match;
	
	public MatchController(UnsettedMatch match, UserHandler...handlers) {
		super();
		this.match = match;
		handlersMap= new EnumMap<>(PlayerColor.class);
		for(UserHandler handler: handlers)
		{
			this.handlersMap.put(handler.getPlayerId(), handler);
			this.addObserver(handler);
			handler.addObserver(this);
			
			
		}
		match.addObserver(this);
		this.match=match.startMatch();
	}

	
	public void gameLoop()
	{
		
	}
	
	public void roundLoop()
	{
		match.getBoard().getDeck().shuffle();
		
	}
	
	private void nextAction()
	{
		if(match.getExtraActions().isEmpty())
		{
			this.match=match.setNextPlayer();
		}
		else
		{
			notifyObservers(match.getExtraActions()); //requests to choose an extra action to perform
		}
	}
	
	@Override
	public void update(Observable source, Object arg)
	{
		if(source!=match && !handlersMap.containsValue(source))
		{
			throw new IllegalArgumentException();
		}
		if(source==handlersMap.get(currentPlayer.getId()) && (arg instanceof ExtraAction))
		{
			ExtraAction action=(ExtraAction)arg;
			Message mess= action.isLegal(currentPlayer, match);
			notifyObservers(mess);
		}
	}
	
}

