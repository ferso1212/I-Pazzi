package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.CouncilPalace;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilAction extends Action{

	private CouncilPalace council;
	private FamilyMember famMember;
	private CouncilChoice councilChoice;
		
	public CouncilAction(PlayerColor playerId, FamilyMember famMember, int possibleServants) {
		super(playerId);
		this.famMember = famMember;
		this.possibleServants = possibleServants;
		this.updateCounter = 1;
	}
	
	
	@Override
	public Message update(Player player, Match match) {
		switch (this.updateCounter) {
		case 1:
		{
			this.council = match.getBoard().getCouncilPalace();
			if((this.famMember.getColor() == MembersColor.NEUTRAL)){
				return new RefusedAction(player.getId(), "You can't place the Neutral member in the council palace!");
			}
			
			if (((this.famMember.getValue() + possibleServants) >= this.council.getDiceRequirement()) && (!famMember.isUsed())){
				this.councilChoice = new CouncilChoice(player.getId(), council.getCouncilPrivileges());
				this.updateCounter--;
				return this.councilChoice;
			} else return new RefusedAction(player.getId());
		}
		
		case 0:
		{
			return new AcceptedAction(player.getId());
		}


		default:
			return new RefusedAction(player.getId());
		}
		
	}
	
	
	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
		super.payServants(player, this.possibleServants);
		
		match.getBoard().placeMember(player, this.famMember, this.council);
		
		player.getProperties().increaseProperties(this.council.getInstantBonus());
		
		for (ImmProperties p : this.councilChoice.getPrivilegesChosen()){
			player.getProperties().increaseProperties(p);
		}
		
		ExtraAction[] returnActions = new ExtraAction[1];
		returnActions[0] = new NullAction(player.getId());
		return returnActions;
	}

}
