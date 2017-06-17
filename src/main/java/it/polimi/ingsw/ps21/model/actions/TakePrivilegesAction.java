package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public class TakePrivilegesAction extends ExtraAction {
	
	private CouncilChoice councilChoice;
	private int numberOfPrivileges;
	private int updateCounter = 1;

	public TakePrivilegesAction(PlayerColor playerId, int numberOfPrivileges) {
		super(playerId);
		this.numberOfPrivileges = numberOfPrivileges;
		this.data = new ExtraActionData(this);
	}

	@Override
	public Message update(Player player, Match match) {
		switch (this.updateCounter) {
		case 1:
		{
			this.councilChoice = new CouncilChoice(player.getId(), numberOfPrivileges);
			this.updateCounter--;
			return councilChoice;
		}
		
		case 0:
		{
			if (this.councilChoice.getPrivilegesChosen().length == this.numberOfPrivileges)
				return new AcceptedAction(player.getId());
			return new RefusedAction(player.getId());
		}

		default:
			return new RefusedAction(player.getId());
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match)
			throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		for(int i=0; i<councilChoice.getNumberOfChoices(); i++){
			player.getProperties().increaseProperties(this.councilChoice.getPrivilegesChosen()[i]);
		}
		ArrayList<ExtraAction> returnExtraAction = new ArrayList<ExtraAction>();
		returnExtraAction.add(new NullAction(player.getId()));
		return returnExtraAction.toArray(new ExtraAction[0]);
	}
	
	public int getNumberOfPrivileges(){
		return this.numberOfPrivileges;
	}
	
}