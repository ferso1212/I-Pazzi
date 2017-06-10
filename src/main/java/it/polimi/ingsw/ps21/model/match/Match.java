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

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;


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
	protected ArrayList<ExtraAction> extraActions;
	protected Board board;
	protected int orangeDice;
	protected int blackDice;
	protected int whiteDice;
	protected int period; 
	protected int round;
	
	public Match(){
		
	}
	
	public Match(Match previousMatch){
		this.board = previousMatch.board;
		this.blackDice = previousMatch.blackDice;
		this.orangeDice = previousMatch.orangeDice;
		this.whiteDice = previousMatch.whiteDice;
		this.observers = previousMatch.observers;
		this.players = previousMatch.players;
		this.order = previousMatch.order;
		this.period = previousMatch.period;
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
		return order.peek();
	}
	
	public ExtraAction[] doAction(Action action){
		ExtraAction[] extraActionPool;
		try {
			extraActionPool = action.execute(order.element(),this);
		} catch (NotExecutableException e) {
			notifyObservers(new RefusedAction(getCurrentPlayer().getId(), "Impossible to execute this action"));
			return null;
		} catch (NotOccupableException e) {
			notifyObservers(new RefusedAction(getCurrentPlayer().getId(), "You cannot'occupy this place"));
			return null;
		} catch (RequirementNotMetException e) {
			notifyObservers(new RefusedAction(getCurrentPlayer().getId(), "You doesn't satisfy requirement to execute this action"));
			return null;
		} catch (InsufficientPropsException e) {
			notifyObservers(new RefusedAction(getCurrentPlayer().getId(), "You doesn't have enough properties to execute this action"));
			return null;
		}
		notifyObservers();
		return extraActionPool;
	}
	
	public abstract Match setNextPlayer(); 

	// public abstract Match getCopy() throws CloneNotSupportedException;
	
	public int[] getDices() {
		int returnValues[] = new int[3];
		returnValues[0] = orangeDice;
		returnValues[1] = blackDice;
		returnValues[2] = whiteDice;
		return returnValues;
	 }
	
	public int getNumberPlayers(){
		return this.players.size();
	}


	public ArrayList<ExtraAction> getExtraActions() {
		return extraActions;
	}
	
	
}
	