package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;

public class UnsettedMatch extends Match {

	
	public UnsettedMatch() throws ParserConfigurationException {
		super();
		players = new EnumMap<>(PlayerColor.class);
		orangeDice = 0;
		blackDice = 0;
		whiteDice = 0;
	}
	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO getCopy
		return this;
	}
	
	/**
	 * 
	 * @param newPlayer
	 * @throws InvalidIDException It means that this ID is already taken
	 * @throws CompleteMatchException
	 */
	public synchronized void addPlayer(String playerName, PlayerColor newPlayer) throws InvalidIDException, CompleteMatchException{
		if (players.containsKey(newPlayer)) throw new InvalidIDException();
		players.put(newPlayer, new Player(playerName, new PlayerProperties(0), newPlayer));
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
	public ExtraAction[] doAction(Action action) {
		return null;
	}
	@Override
	public Match setNextPlayer() {
		return this;
	}

}
