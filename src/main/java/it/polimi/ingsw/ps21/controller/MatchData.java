package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;


//Dovrebbe contenere al suo interno anche le informazioni sulla board e sui giocatori aggregati, ma non so se possono essere serializzati

public class MatchData implements Serializable {
	
	private int era;
	private int round;
	private int[] playersOrder;
	
	public int getEra(){
		return era;
	}

	public int getRound() {
		return round;
	}

	public int[] getPlayersOrder() {
		return playersOrder;
	}
	
}
