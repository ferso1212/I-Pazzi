package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;

public abstract class Round {
	
	protected Queue<Player> order;
	
	protected int orangeDice;
	protected int blackDice;
	protected int whiteDice;
	
	public Round (){
		order = new ArrayDeque<>();
		throwDices();		
	}
	
	public abstract int getRoundNumber();
	
	public void throwDices(){
		Random generator = new Random();
		orangeDice = (int) generator.nextInt(5) + 1;
		blackDice = (int) generator.nextInt(5) + 1;
		whiteDice = (int) generator.nextInt(5) + 1;
	}
	

	
	
	public abstract void setPlayers(Player...players);
	
	public abstract Player getPlayer();
	
	public abstract ExtraAction doAction(Action action, Match match);
	
	public abstract Round setNextPlayer();

}
