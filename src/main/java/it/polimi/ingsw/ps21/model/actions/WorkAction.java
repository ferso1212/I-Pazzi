package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.Space;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class WorkAction extends Action {

	private Space space;
	private FamilyMember famMember;
	private EffectChoice effectChoice;
	private WorkMessage workMessage;

	public WorkAction(PlayerColor playerId, Space space, FamilyMember famMember) {
		super(playerId);
		this.space = space;
		this.famMember = famMember;
	}

	@Override
	public Message isLegal(Player player, Match match) {

		if ((famMember.getValue() >= space.getDiceRequirement()) && (space.isOccupable(player, famMember))
				&& (!famMember.isUsed())){
			
		} else return new RefusedAction();
	}

	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {
		// TODO Auto-generated method stub
		return null;
	}

}