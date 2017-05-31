package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.player.Player;

public class UnsettedMatch extends Match {

	public UnsettedMatch() throws ParserConfigurationException {
		super();
		MatchBuilder.initialize("", "", "", "");
		players = new EnumMap<>(PlayerColor.class);
		orangeDice = 0;
		blackDice = 0;
		whiteDice = 0;
		board.setDeck(MatchBuilder.makeDeck());
	}
	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO
		return this;
	}
	
	/**
	 * 
	 * @param newPlayer
	 * @throws InvalidIDException It means that this ID is already taken
	 * @throws CompleteMatchException
	 */
	public void addPlayer(Player newPlayer) throws InvalidIDException, CompleteMatchException{
		if (players.containsKey(newPlayer.getId())) throw new InvalidIDException();
		players.put(newPlayer.getId(), newPlayer);
		if (players.containsKey(PlayerColor.BLUE) && players.containsKey(PlayerColor.GREEN) && players.containsKey(PlayerColor.RED) && players.containsKey(PlayerColor.YELLOW))
			throw new CompleteMatchException();
		
	}
	
	/**
	 * 
	 * @return Will created a new InitialRound match with era = 1
	 */
	public Match startMatch(){
		List<Player> shuffledPlayers = new ArrayList<Player>(players.values());
		Collections.shuffle(shuffledPlayers);
		for (int i=0; i <4 ; i++){
			for (Player p: shuffledPlayers){
				order.add(p);
			}
		}
		throwDices();
		return new InitialRoundMatch(this);
		
	}
	
	
	@Override
	public Match makeAction(Action nextAction) {
		return this;
	}
	
	

}
