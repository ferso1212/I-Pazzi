package it.polimi.ingsw.ps21.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Match extends Observable {
	private MatchState currentMatch;
	private ArrayList<Observer> observers;
	private Board board;
	
	public Match(Player players[]){
		currentMatch = new StartingMatch(this);
		observers = new ArrayList<Observer>();
	}
	
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	public void goNext(){
		currentMatch = currentMatch.goNext();
	}

	public void initializeDeck(File file){
		
		
	}
}
