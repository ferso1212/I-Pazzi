package it.polimi.ingsw.ps21.model.actions;

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
		
	public CouncilAction(PlayerColor playerId, CouncilPalace council, FamilyMember famMember) {
		super(playerId);
		this.council = council;
		this.famMember = famMember;
	}
	
	
	@Override
	public Message update(Player player, Match match) {
		
		if(this.famMember.getColor() == MembersColor.NEUTRAL){
			return new RefusedAction(player.getId(), "You can't place the Neutral member in the council palace!");
		}
		
		if ((this.council.isOccupable(player, famMember)) && (!famMember.isUsed())){
			this.councilChoice = new CouncilChoice(player.getId(), council.getCouncilPrivileges());
			return this.councilChoice;
		} else return new RefusedAction(player.getId());
	}
	
	
	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
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
