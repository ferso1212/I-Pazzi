package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class AdvancedMatch extends Match {

	private EnumMap<PlayerColor, AdvancedPlayer> players;
	private int currentPlayer;
	private ArrayList<AdvancedPlayer> order;
	
	public AdvancedMatch(Match previousMatch) {
		super(previousMatch);
	}
	
	public AdvancedMatch(PlayerColor...colors) throws InvalidIDException, BuildingDeckException{
		if (colors.length>4) throw new InvalidIDException();
		MatchFactory builder = MatchFactory.instance();
		ImmProperties[] initialProperties = builder.makeInitialProperties();
		players = new EnumMap<>(PlayerColor.class);
		order = new ArrayList<>();
		ArrayList<AdvancedPlayer> tempPlayer = new ArrayList<>();
		board = new Board(colors.length, false);
		board.getDeck().shuffle();
		extraActions = new ArrayList<>();
		for (int i=0; i<colors.length; i++){
			players.put(colors[i], new AdvancedPlayer(colors[i], new PlayerProperties(0)));
			players.get(colors[i]).getProperties().increaseProperties(initialProperties[i]);
			tempPlayer.add(players.get(colors[i]));
		}
		for (int j=0; j<4; j++)
			for (int i=0; i<tempPlayer.size(); i++)
				order.add(tempPlayer.get(i));
		currentPlayer=0;
		period = 1;
		round = RoundType.INITIAL_ROUND;
		throwDices();
		board.newSetBoard(period);
		setChanged();
	}

}
