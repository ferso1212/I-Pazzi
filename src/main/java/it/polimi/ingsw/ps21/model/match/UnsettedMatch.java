package it.polimi.ingsw.ps21.model.match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.UserHandler;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;

public class UnsettedMatch extends Match {
	private MatchFactory matchFactory;
	public UnsettedMatch() throws ParserConfigurationException{
		super();
		players = new EnumMap<>(PlayerColor.class);
		orangeDice = 0;
		blackDice = 0;
		whiteDice = 0;
		this.matchFactory=MatchFactory.instance();
	}

	@Override
	public Match getCopy() throws CloneNotSupportedException {
		// TODO getCopy
		return this;
	}

	/**
	 * 
	 * @param newPlayer
	 * @throws InvalidIDException
	 *             It means that this ID is already taken
	 * @throws CompleteMatchException
	 */
	public synchronized void addPlayer(PlayerColor newPlayer)
			throws InvalidIDException, CompleteMatchException {
		if (players.containsKey(newPlayer))
			throw new InvalidIDException();
		if (players.containsKey(PlayerColor.BLUE) && players.containsKey(PlayerColor.GREEN)
				&& players.containsKey(PlayerColor.RED) && players.containsKey(PlayerColor.YELLOW))
			throw new CompleteMatchException();
		players.put(newPlayer, new Player(new PlayerProperties(0), newPlayer));
	}

	/**
	 * 
	 * @return Will created a new InitialRound match with era = 1
	 */
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
	public ExtraAction[] doAction(Action action) {
		return null;
	}

	@Override
	public Match setNextPlayer() {
		return this;
	}

	public void waitPlayers() {

	}
}
