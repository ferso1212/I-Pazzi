package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.Player;


/**
 * Class that represent all information about match and communicates with Controller
 * 
 * @author gullit
 *
 */
public class Match extends Observable {
	private MatchState currentMatch;
	private ArrayList<Observer> observers;
	private Player players[];
	private Board board;
	private final String greenCardsFileName = "deck_greencards.xml" ;
	private final String yellowCardsFileName = "deck_yellowcards.xml";
	private final String blueCardsFileName = "deck_bluecards.xml";
	private final String purpleCardsFileName = "deck_purplecards.xml";
	private int orangeDice;
	private int blackDice;
	private int whiteDice;
	
	public Match(Player players[]){
		currentMatch = new StartingMatch(this);
		observers = new ArrayList<Observer>();
		this.players = players;
		board = new Board();
	}
	
	public void setBoard(){
		this.board = board;
	}
	
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	public void goNext(){
		currentMatch = currentMatch.goNext();
	}
	
	
	public void throwDices(){
		orangeDice = (int) Math.random() * 5 + 1;
		blackDice = (int) Math.random() * 5 + 1;
		whiteDice = (int) Math.random() * 5 + 1;
	}

	public void initializeMatch(){
		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Match getCopy() throws CloneNotSupportedException{
		//Player copyOfPlayers[];
		//for (int i =0; i<players.length; i++){
		//	copyOfPlayers[i] = players[i].getCopy();
		//}
		Match copy = new Match(players);
		// copy.setBoard(board.getCopy());
		return copy;
		
	}

	/**
	 * 
	 * @return first value = orangeDice, second value = blackDice, third value = whiteDice 
	 */
	public int[] getDices() {
		int returnValues[] = new int[3];
		returnValues[0] = orangeDice;
		returnValues[1] = blackDice;
		returnValues[2] = whiteDice;
		return returnValues;
	}
}