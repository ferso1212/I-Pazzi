package it.polimi.ingsw.ps21.view;

import java.io.Serializable;
import java.util.Map;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class EndData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2312307224061599446L;
	private Map<PlayerColor, Integer> playersFinalPoints;

	public EndData(Map<PlayerColor, Integer> playersFinalPoints) {
		super();
		this.playersFinalPoints = playersFinalPoints;
	}

	public Map<PlayerColor, Integer> getPlayersFinalPoints() {
		return playersFinalPoints;
	}
	
	
	
}
