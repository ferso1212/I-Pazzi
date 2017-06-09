package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
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
		notifyObservers();
	}
	
	@Override
	public ExtraAction[] doAction(Action nextAction)  {
		ExtraAction[] extraActionPool;
		try {
			extraActionPool = nextAction.execute(order.element(),this);
		} catch (NotExecutableException e) {
			notifyObservers(new RefusedAction("Impossible to execute this action"));
			return null;
		} catch (NotOccupableException e) {
			notifyObservers(new RefusedAction("You cannot'occupy this place"));
			return null;
		} catch (RequirementNotMetException e) {
			notifyObservers(new RefusedAction("You doesn't satisfy requirement to execute this action"));
			return null;
		} catch (InsufficientPropsException e) {
			notifyObservers(new RefusedAction("You doesn't have enough properties to execute this action"));
			return null;
		}
		notifyObservers();
		return extraActionPool;
	}

	/* @Override
	public Match getCopy() throws CloneNotSupportedException {
		return this;
	} */
	@Override
	public Match setNextPlayer() {
		if (order.isEmpty()) return new VaticanSupport(this);
		else return this;
	}

}
