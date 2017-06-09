package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

/**
 * @author daniele
 *
 */

public class EndedMatch extends Match {

	public EndedMatch(Match finishedMatch){
		super(finishedMatch);
		Map<Player, Integer> militaryBonus = calculateMilitaryWinner();
		Map<Player, Integer> playerPositions = calculateWinner(militaryBonus); 

	}
	
	@Override
	public ExtraAction[] doAction(Action nextAction) {
			return null;
	}

	/* TODO Choose if it has to be implemented @Override
	public Match getCopy() throws CloneNotSupportedException {
		Match clone = new EndedMatch(this);
		
		// TODO implement Board.clone() clone.board = this.board.clone();
		clone.observers = new ArrayList<>();
		for (Observer o: observers){
			clone.observers.add(o);
		}
		// TODO clone other variables
		return clone;
	} */
	@Override
	public Match setNextPlayer(){
		return this;
	}
	
	private Map<Player, Integer> calculateMilitaryWinner(){
		Map<Player, Integer> result = new HashMap<>();
		//calculate 1 and second value
		int values[] = new int[players.size()];
		for (int i=0; i<values.length; i++){
			values[i]=0;
		}
		for (Player p: players.values()){
			for (int j=0; j<values.length; j++)
			 if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() >= values[j]) 
				 {
				 	values[j] = p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue();
				  }
		}
		int numWinners = 0;
		for(Player p:players.values()){
			for (int j=0; j<values.length;j++)
			if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() == values[j]) 
			{
				if(j==0){
					result.put(p, 1);
					numWinners ++;
				}
				else if ( j== 1 && numWinners<2) result.put(p, 2);
					else result.put(p, j+1);
			}
		}
		return result;
	}
	
	private Map<Player, Integer> calculateWinner(Map<Player, Integer> militaryBonus) {
		Map<Player, Integer> result = new HashMap<>();
		// Calculate value orders
		int values[] = new int[players.size()];
		for (int i=0; i<values.length; i++){
			values[i]=0;
		}
		for (Player p: players.values()){
			for (int j=0; j<values.length; j++)
			 if (p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p)) >= values[j]) 
				 {
				 	values[j] = p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p));
				  }
		}
		for(Player p:players.values()){
			for (int j=0; j<values.length;j++)
			if (p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p)) == values[j]) 
				result.put(p, j+1);
		}
		return null;
	}
}
