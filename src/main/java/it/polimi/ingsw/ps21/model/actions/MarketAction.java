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
	
	public MarketAction(PlayerColor playerId, SingleMarketSpace space, int possibleServants, FamilyMember famMember) {
		super(playerId);
		this.space = space;
		this.famMember = famMember;
		this.possibleServants = possibleServants;
	}

	@Override
	public Message update(Player player, Match match) {
		
		switch (this.updateCounter) {
		case 1:
		{
			if (player.getModifiers().getActionMods().marketActionForbidden()){
				return new RefusedAction(player.getId(), "You can't place a family member in a market space because you have an excommunication!");
			}
			
			if (!(((player.getFamily().getMemberValueWithServants(this.possibleServants, this.famMember.getColor())) >= space.getDiceRequirement()))) return new RefusedAction(player.getId(), "Dice value of member isn't enough");
			if (!(space.isOccupable(player, famMember))) return new RefusedAction(player.getId(), "You can't use this family member in this place");
			if (!(!famMember.isUsed())) return new RefusedAction(player.getId(), "This family member is already used");
//			if ((space.getNumberOfPrivileges() > 0) && (match.getBoard().getCouncilPalace().checkPlayer(player))){
//					this.councilChoice = new CouncilChoice(player.getId(), space.getNumberOfPrivileges());
//					this.updateCounter--;
//					return this.councilChoice;
//				}
			return new AcceptedAction(player.getId());
		}
		
		case 0:
		{
//			if (this.councilChoice.getPrivilegesChosen().length == space.getNumberOfPrivileges())
				return new AcceptedAction(player.getId());
//			return new RefusedAction(player.getId(), "You have choosen a wrong number of privileges");
		}

		default:
			return new RefusedAction(player.getId(),  "Unhandled case");
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
		super.payServants(player, possibleServants, this.famMember.getColor());
		
		match.getBoard().placeMember(player, this.famMember, this.space);
		
		player.getProperties().increaseProperties(this.space.getInstantBonus());
		
		if (this.space.getNumberOfPrivileges() > 0){
			for (ImmProperties p : this.councilChoice.getPrivilegesChosen()){
				player.getProperties().increaseProperties(p);
			}
		}
		
		ExtraAction[] returnActions = new ExtraAction[1];
		returnActions[0] = new TakePrivilegesAction(player.getId(), space.getNumberOfPrivileges());
		return returnActions;
	}
	
	
}
