package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.board.Board;


/**
 * Class that represent all information about match and commnicates with Controller
 * 
 * @author gullit
 *
 */
public class Match extends Observable implements Cloneable {
	private MatchState currentMatch;
	private ArrayList<Observer> observers;
	private Player players[];
	private Board board;
	private final String greenCardsFileName = "deck_greencards.xml" ;
	private final String yellowCardsFileName = "deck_yellowcards.xml";
	private final String blueCardsFileName = "deck_bluecards.xml";
	private final String purpleCardsFileName = "deck_purplecards.xml";
	
	public Match(Player players[]){
		currentMatch = new StartingMatch(this);
		observers = new ArrayList<Observer>();
		this.players = players;
		board = new Board();
	}
	
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	public void goNext(){
		currentMatch = currentMatch.goNext();
	}

	public void initializeMatch(){
		
	}
	
	
	public Match getCopy() throws CloneNotSupportedException{
		return (Match) this.clone();
	}
}