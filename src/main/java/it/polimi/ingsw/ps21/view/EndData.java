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
	private PlayerColor winner;

	public EndData(Map<PlayerColor, Integer> playersFinalPoints) {
		super();
		this.playersFinalPoints = playersFinalPoints;
		Integer maxValue = this.playersFinalPoints.values().parallelStream().reduce(0, ((x, j) -> Math.max(x,j)));
		for (PlayerColor p: playersFinalPoints.keySet()) {
			if (playersFinalPoints.get(p).compareTo(maxValue)==0){
				winner = p;
				break;
			}
		}
	}

	public Map<PlayerColor, Integer> getPlayersFinalPoints() {
		return playersFinalPoints;
	}
	
	public PlayerColor getWinner(){
		return winner;
	}
	
	
}
