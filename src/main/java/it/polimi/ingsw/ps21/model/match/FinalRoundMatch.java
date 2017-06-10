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
