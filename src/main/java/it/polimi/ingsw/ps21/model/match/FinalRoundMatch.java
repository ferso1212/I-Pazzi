package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;

/**
 * This state reprent Match in a final Round of an Era
 * @author gullit
 *
 */


public class FinalRoundMatch extends Match {
	private final static Logger LOGGER = Logger.getLogger(FinalRoundMatch.class.getName());
	
	public FinalRoundMatch(Match prevState) {
		super(prevState);
		round = 2;
		throwDices();
		Queue<FamilyMember> temp = board.getCouncilPalace().getOccupants();
		ArrayList<Player> newOrder = new ArrayList<>();
		for (FamilyMember f: temp){
			Player player = players.get(f.getOwnerId());
			if (newOrder.contains(player));
			else newOrder.add(player);
		}
		order = new ArrayDeque<>();
		for (int i=0; i<4; i++)
		for ( int j = newOrder.size() -1 ; j>=0; i--){ // Crea l'ordine del nuovo round
			order.add(newOrder.get(j));
		}
		board.newSetBoard(period);
		notifyObservers();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ExtraAction[] doAction(Action nextAction)  {
		ExtraAction[] extraActionPool;
		try {
			extraActionPool = nextAction.execute(order.element(),this);
		} catch (NotExecutableException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (NotOccupableException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (RequirementNotMetException e) {
			// TODO Auto-generated catch block
			return null ;
		} catch (InsufficientPropsException e) {
			// TODO Auto-generated catch block
			return null;
		}
		notifyObservers();
		return extraActionPool;
	}


	private Match nextState() {
		for (int i=0; i<4; i++){
			for (FamilyMember p: board.getCouncilPalace().getOccupants())
			{
				// TODO need a method that return playerOrder in councilPalace
			}
			
		}
		for (Player p: players.values()){
			// vatican Support
			board.resetFaithPoints(p);			
		}
		return new InitialRoundMatch(this);
	}
	@Override
	public Match getCopy() throws CloneNotSupportedException {
		return this;
	}
	@Override
	public Match setNextPlayer() {
		for (int i=0; i<4; i++){
			for (FamilyMember p: board.getCouncilPalace().getOccupants())
			{
				// TODO need a method that return playerOrder in councilPalace
			}
			
		}
		for (Player p: players.values()){
			board.resetFaithPoints(p);			
		}
		return new InitialRoundMatch(this);
	}

}
