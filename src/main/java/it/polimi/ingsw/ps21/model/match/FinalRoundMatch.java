package it.polimi.ingsw.ps21.model.match;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.actions.Action;
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
		// TODO Auto-generated constructor stub
	}
	@Override
	public Match makeAction(Action nextAction)  {
		// if (nextAction.isLegal())
				try {
					nextAction.execute();
				} catch (NotExecutableException | NotOccupableException | RequirementNotMetException| InsufficientPropsException e) {
					LOGGER.log(Level.WARNING, "Not activable action", e);
					return this;
				}
				order.poll();
				notifyObservers();
				if (order.isEmpty()) return nextState();
				else return this;
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
		// TODO Auto-generated method stub
		return null;
	}

}
