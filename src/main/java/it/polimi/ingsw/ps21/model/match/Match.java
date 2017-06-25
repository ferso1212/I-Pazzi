package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.VaticanAction;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.EndData;


/**
 * Class that represent all information about match and communicates with Controller
 * this match control players order for the action execution and setup board and players for every round
 * It also decides randomly dice values for each round and set them automatically for every family member
 * 
 *  At the end of the match it also calculate the winner
 *  Using notifyObservers() it notify to controller every change in match 
 * 
 * @author gullit
 *
 */
public abstract class Match extends Observable {
	
	protected Board board;
	protected int orangeDice;
	protected int blackDice;
	protected int whiteDice;
	protected int period; 
	protected RoundType round;
	protected boolean ended = false;
	protected int currentPlayer;
	protected EndData statistics;
	
	public Match(){
	}
	
	public Match(Match previousMatch){
		this.board = previousMatch.board;
		this.blackDice = previousMatch.blackDice;
		this.orangeDice = previousMatch.orangeDice;
		this.whiteDice = previousMatch.whiteDice;
		this.period = previousMatch.period;
		this.round = previousMatch.round;
		this.ended = previousMatch.ended;
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
	public int getPeriod(){
		return period;
	}
	
	public RoundType getRound(){
		return round;
	}
	
	public abstract Player getCurrentPlayer();
	
	public abstract ExtraAction[] doAction(Action action) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException, VaticanRoundException;
	
	/**
	 * @return TRUE if no actions are left in the queue (all players have performed all their placement action in the round)
	 */
	public abstract RoundType setNextPlayer();

	public abstract Match getCopy() throws CloneNotSupportedException;
	
	public int[] getDices() {
		int returnValues[] = new int[3];
		returnValues[0] = orangeDice;
		returnValues[1] = blackDice;
		returnValues[2] = whiteDice;
		return returnValues;
	 }
	
	public abstract int getNumberPlayers();


	public abstract List<ExtraAction> getExtraActions();
	
	public boolean isEnded(){
		return ended;
	}
	
	public abstract PlayerColor[] getOrderQueue();
	
	public abstract Collection<Player> getPlayers();
	
	public int getOrangeDice() {
		return orangeDice;
	}

	public int getWhiteDice() {
		return whiteDice;
	}

	public int getBlackDice() {
		return blackDice;
	}
	
    public abstract void nextRound();

	public EndData getResult(){
		return this.statistics;
	}
}
	
