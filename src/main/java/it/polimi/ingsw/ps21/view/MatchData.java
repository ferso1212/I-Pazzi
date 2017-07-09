package it.polimi.ingsw.ps21.view;

import java.io.Serializable;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;


public class MatchData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8013358517040401334L;
	private int period;
	private RoundType round;
	private PlayerColor[] playersOrder;
	private BoardData board;
	private PlayerData[] players;
	private int orangeDice;
	private int whiteDice;
	private int blackDice;
	
	
	public int getPeriod(){
		return period;
	}

	public RoundType getRound() {
		return round;
	}

	public PlayerColor[] getPlayersOrder() {
		return playersOrder;
	}

	public BoardData getBoard() {
		return board;
	}

	public PlayerData[] getPlayers() {
		return players;
	}
	
	public MatchData(Match match){
		this.period=match.getPeriod();
		this.round=match.getRound();
		this.playersOrder=match.getOrderQueue();
		Player[] players= match.getPlayers().toArray(new Player[0]);
		this.players=new PlayerData[players.length];
		for(int i=0; i<players.length; i++)
		{
			this.players[i]= new PlayerData(players[i]);
		}
		this.orangeDice=match.getOrangeDice();
		this.blackDice= match.getBlackDice();
		this.whiteDice= match.getWhiteDice();
		this.board=new BoardData(match.getBoard());
		
	}
	
	public int getOrangeDice() {
		return orangeDice;
	}

	public int getWhiteDice() {
		return whiteDice;
	}

	public int getBlackDice() {
		return blackDice;
	}

	

}
