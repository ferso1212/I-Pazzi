package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class VaticanSupport extends Match {

	private Map<Player, Boolean> supportChoices;
	private Queue<Player> supportedPlayers;
	
	public VaticanSupport(Match previousMatch) {
		super(previousMatch);
		supportedPlayers = new ArrayDeque<>();
		for(Player p: players.values())
		{	
		if (board.getExcommunicationRequirement(period) < p.getProperties().getProperty(PropertiesId.FAITHPOINTS).getValue())	
			{/*setta scomunica*/}
		else supportedPlayers.add(p);
		}
		notifyObservers();
	}
	
	@Override
	public ExtraAction[] doAction(Action nextAction) {
		try {
			return nextAction.execute(order.element(), this);
		} catch (NotExecutableException | NotOccupableException | RequirementNotMetException
				| InsufficientPropsException e) {
			return null;
		}
		
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new VaticanSupport(this);
	}

	@Override
	public Match setNextPlayer() {
		supportedPlayers.poll();
		if (supportedPlayers.isEmpty()){
		for (Player p: players.values()){
			if (!(supportChoices.containsKey(p))) return this;
		}
		if (period < 3){
			Queue<FamilyMember> temp = board.getCouncilPalace().getOccupants();
			ArrayList<Player> newOrder = new ArrayList<>();
			for (FamilyMember f: temp){
				Player player = players.get(f.getOwnerId());
				if (newOrder.contains(player));
				else newOrder.add(player);
			}
			order = new ArrayDeque<>();
			for (int i=0; i<4; i++)
			for ( int j = newOrder.size() -1 ; j>=0; i--){ // Crea l'ordine del nuovo round
				order.add(newOrder.get(j));
			}
			board.newSetBoard(period + 1);
			return new InitialRoundMatch(this);
		}	
		else return new EndedMatch(this);
		}
		else return this;
	}
	
	

}
