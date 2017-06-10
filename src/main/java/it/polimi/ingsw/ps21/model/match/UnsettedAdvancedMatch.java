package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.AdvancedModifier;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/*
 * This implementation of match must contains only advanced Player
 * 
 */

public class UnsettedAdvancedMatch extends UnsettedMatch {

	private MatchFactory matchFactory;
	
	public UnsettedAdvancedMatch() throws BuildingDeckException  {
		this.observers = new ArrayList<>();
		this.board = new Board(blackDice, true);
		order = new ArrayDeque<>();
		players = new EnumMap<>(PlayerColor.class);
		orangeDice = 0;
		blackDice = 0;
		whiteDice = 0;
		this.matchFactory = MatchFactory.instance();	}

	@Override
	public ExtraAction[] doAction(Action action) {
		return null;
	}

	public synchronized void addPlayer(PlayerColor newPlayer)
			throws InvalidIDException, CompleteMatchException {
		if (players.containsKey(newPlayer))
			throw new InvalidIDException();
		if (players.containsKey(PlayerColor.BLUE) && players.containsKey(PlayerColor.GREEN)
				&& players.containsKey(PlayerColor.RED) && players.containsKey(PlayerColor.YELLOW))
			throw new CompleteMatchException();
		players.put(newPlayer, new AdvancedPlayer(newPlayer, new PlayerProperties(0)));
	}

	public Match startMatch() {
		List<Player> shuffledPlayers = new ArrayList<Player>(players.values());
		Collections.shuffle(shuffledPlayers);
		
			ImmProperties[] initialProps = matchFactory.makeInitialProperties();
			order.clear();
			int i = 0;
			for (Player p : shuffledPlayers) {
				p.getProperties().increaseProperties(initialProps[i]);
				i++;
			}
			i = 0;
			while (i < 4) {
				for (Player p : shuffledPlayers) {
					order.add(p);
				}
				i++;
			}

		throwDices();
		notifyObservers("Match Started");
		return new InitialRoundMatch(this);

	}
	
	@Override
	public Match setNextPlayer() {
		return this;
	}
	
	@Override
	public boolean isAdvanced()
	{
		return true;
	}

 /* TODO	@Override
	public Match getCopy() throws CloneNotSupportedException {
		return null;
	}
	*/

}
