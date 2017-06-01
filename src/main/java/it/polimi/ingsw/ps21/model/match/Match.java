package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;


/**
 * Class that represent all information about match and communicates with Controller
 * 
 * @author gullit
 *
 */
public abstract class Match extends Observable {
	protected ArrayList<Observer> observers;
	protected EnumMap<PlayerColor, Player> players;
	protected Queue<Player> order;
	protected Board board;
	protected int orangeDice;
	protected int blackDice;
	protected int whiteDice;
	protected int era; 
	protected int round;
	
	public  Match(){
		this.observers = new ArrayList<>();
		// this.board = new Board(MatchBuilder.);
		order = new ArrayDeque<>();
		players = new EnumMap<>(PlayerColor.class);
	}
	
	public Match(Match previousMatch){
		this.board = previousMatch.board;
		this.blackDice = previousMatch.blackDice;
		this.orangeDice = previousMatch.orangeDice;
		this.whiteDice = previousMatch.whiteDice;
		this.observers = previousMatch.observers;
		this.players = previousMatch.players;
		this.order = previousMatch.order;
		this.era = previousMatch.era;
		this.round = previousMatch.round;
	}
	
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	
	public void throwDices(){
		Random generator = new Random();
		orangeDice = (int) generator.nextInt(5) + 1;
		blackDice = (int) generator.nextInt(5) + 1;
		whiteDice = (int) generator.nextInt(5) + 1;
	}
	
	public Board getBoard() {
		return board;
	}
	
	/**
	 * This method is needed by Action.isLegal()
	 * @return First Player in order without removing it
	 */
	
	public Player getCurrentPlayer(){
		return order.element();
	}
	
	public abstract Match makeAction(Action nextAction) throws MatchException;
	
	public abstract Match getCopy() throws CloneNotSupportedException;

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
	public void update(){
		
	}
}
	