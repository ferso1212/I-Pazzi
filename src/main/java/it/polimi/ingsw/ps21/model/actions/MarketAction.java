package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.SingleMarketSpace;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class MarketAction extends Action{
	
	private SingleMarketSpace space;
	private FamilyMember famMember;
	private CouncilChoice councilChoice;
	private int updateCounter = 1;
	
	public MarketAction(PlayerColor playerId, SingleMarketSpace space, FamilyMember famMember,
			CouncilChoice councilChoice) {
		super(playerId);
		this.space = space;
		this.famMember = famMember;
	}

	@Override
	public Message update(Player player, Match match) {
		
		switch (this.updateCounter) {
		case 1:
		{
			if (player.getModifiers().getActionMods().marketActionForbidden()){
				return new RefusedAction(player.getId(), "You can't place a family member in a market space because you have an excommunication!");
			}
			
			if ((space.isOccupable(player, famMember)) && (!famMember.isUsed())){
				if ((space.getNumberOfPrivileges() > 0) && (match.getBoard().getCouncilPalace().checkPlayer(player))){
					this.councilChoice = new CouncilChoice(player.getId(), space.getNumberOfPrivileges());
					this.updateCounter--;
					return this.councilChoice;
				}
				return new AcceptedAction(player.getId());
			} else return new RefusedAction(player.getId());
		}
		
		case 0:
		{
			if (this.councilChoice.getPrivilegesChosen().length == space.getNumberOfPrivileges())
				return new AcceptedAction(player.getId());
			return new RefusedAction(player.getId());
		}

		default:
			return new RefusedAction(player.getId());
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
		match.getBoard().placeMember(player, this.famMember, this.space);
		
		player.getProperties().increaseProperties(this.space.getInstantBonus());
		
		if (this.space.getNumberOfPrivileges() > 0){
			for (ImmProperties p : this.councilChoice.getPrivilegesChosen()){
				player.getProperties().increaseProperties(p);
			}
		}
		
		ExtraAction[] returnActions = new ExtraAction[1];
		returnActions[0] = new NullAction(player.getId());
		return returnActions;
	}
	
	
}
