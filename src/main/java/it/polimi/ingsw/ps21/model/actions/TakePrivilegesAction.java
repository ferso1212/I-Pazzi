package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class TakePrivilegesAction extends ExtraAction {
	
	private CouncilChoice councilChoice;
	private int numberOfPrivileges;

	public TakePrivilegesAction(PlayerColor playerId, int numberOfPrivileges) {
		super(playerId);
		this.numberOfPrivileges = numberOfPrivileges;
	}

	@Override
	public Message isLegal(Player player, Match match) {
		this.councilChoice = new CouncilChoice(player.getId(), numberOfPrivileges);
		return councilChoice;
	}

	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {
		for(int i=0; i<councilChoice.getNumberOfChoices(); i++){
			player.getProperties().increaseProperties(this.councilChoice.getPrivilegesChosen()[i]);
		}
		ArrayList<ExtraAction> returnExtraAction = new ArrayList<ExtraAction>();
		returnExtraAction.add(new NullAction(player.getId()));
		return returnExtraAction.toArray(new ExtraAction[0]);
	}
	
	
	
	

}
