package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.*;

public class GameLogic {
	private Player currentPlayer;
	private Match controlledMatch;
	private Queue<Player> players;
	private ArrayList<LeaderCard> availableLeaderCards;
	private int round
		
	public GameLogic(Match currentMatch){ // Creata all'inizio di una partita
		controlledMatch = currentMatch;
		players = currentMatch.getPlayersOrder();
	}
	
	public GameLogic(AdvancedMatch currentMatch){
		controlledMatch = currentMatch;
		players = currentMatch.getPlayersOrder();
		availableLeaderCards = currentMatch.getLeaderCards();
		
	}
	
	Player orderDef(){
		player = currentMatch.getPlayersOrder();
	}
	
	Player defWinner(){
		
	}
	
	private void roundLoop(){
		//initial Era round
		
		//final Era Round
	}
	
	private initialRound(){
		
		
	}
	
	private void distributeLeaderCard(){
		ArrayList<ArrayList<LeaderCard>> decks = new ArrayList<ArrayList<LeaderCard>>();
		int i = 0;
		for  (LeaderCard l: availableLeaderCards){ // Crea i sottodeck da distribuire ai giocatori
			decks.addAt
		}
	}
}
