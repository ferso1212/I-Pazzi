package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.board.Space;
import it.polimi.ingsw.ps21.model.board.WorkSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class WorkAction extends Action {

	private WorkSpace space;
	private FamilyMember famMember;
	private int actionValue;
	private WorkMessage workMessage;

	public WorkAction(PlayerColor playerId, WorkSpace space, FamilyMember famMember, int possibleServants) {
		super(playerId);
		this.space = space;
		this.famMember = famMember;
		this.possibleServants = possibleServants;
		this.updateCounter = 1;
	}

	@Override
	public Message update(Player player, Match match) {
		
		if (this.space.equals(match.getBoard().getMultipleWorkSpace(this.space.getWorkType()))){
			this.actionValue = (player.getFamily().getMemberValueWithServants(this.possibleServants, this.famMember.getColor())) - match.getBoard().getMultipleWorkSpace(this.space.getWorkType()).getDiceMalus();
		} else if (this.space.equals(match.getBoard().getSingleWorkSpace(this.space.getWorkType()))) {
			this.actionValue = (player.getFamily().getMemberValueWithServants(this.possibleServants, this.famMember.getColor()));
		}
				
		
		switch (this.updateCounter) {
		
		case 1: {
			if (!space.isOccupable(player, famMember))  return new RefusedAction(player.getId(), "Action refused: this space is already occupied");
					//&& (!famMember.isUsed()) && (this.actionValue >= space.getDiceRequirement())) 
			if(famMember.isUsed()) return new RefusedAction(player.getId(), "Action refused: you have already used this member!");
			if((this.actionValue < space.getDiceRequirement()))		return new RefusedAction(player.getId(), "Action refused: unsufficient dice value.");
			if (((match.getNumberPlayers() == 3) || (match.getNumberPlayers() == 4))
						&& ((famMember.getColor() == MembersColor.WHITE) || (famMember.getColor() == MembersColor.BLACK)
								|| (famMember.getColor() == MembersColor.ORANGE))
						&& !((this.checkOccupant(match, famMember, space) == MembersColor.NEUTRAL) || (this.checkOccupant(match, famMember, space) == null))) {
					//refuse action because in the other space there is a colored member
					return new RefusedAction(player.getId(), "You can't place a coloured member in this space because you have another colored member in the other space.");
				}
				try {
					ArrayList<DevelopmentCard> cardWithCost = new ArrayList<DevelopmentCard>();
					ArrayList<DevelopmentCard> cardWithoutCost = new ArrayList<DevelopmentCard>();

					for (DevelopmentCard c : player.getActivableWorks(this.actionValue, this.space.getWorkType())) {
						for (EffectSet e : c.getPossibleEffects()){
							if (e.getTotalCost().isNull())
								cardWithoutCost.add(c);
							cardWithCost.add(c);
						}
					}

					this.workMessage = new WorkMessage(player.getId(),
							cardWithCost.toArray(new DevelopmentCard[0]), cardWithoutCost.toArray(new DevelopmentCard[0]));
				} catch (IllegalCardTypeException e) {
					return new RefusedAction(player.getId());
				}
				this.updateCounter--;
				return this.workMessage;
		
		}

		case 0:
		{
			ImmProperties totalCost = new ImmProperties(0);
			for (int i=0 ; i < this.workMessage.getChosenCardsAndEffects().length; i++){
				if (this.workMessage.getChosenCardsAndEffects()[i] != 0){
					totalCost = totalCost.sum(this.workMessage.getChoices()[i].getPossibleEffects()[this.workMessage.getChosenCardsAndEffects()[i]-1].getTotalCost());
				}
			}
			if (player.checkProperties(totalCost))
				return new AcceptedAction(player.getId());
			else return new RefusedAction(player.getId(), "You don't have enough properties");
		}

		default:{
			return new RefusedAction(player.getId());
		}
		}
	}

	private MembersColor checkOccupant(Match match, FamilyMember famMember, Space space) throws IllegalArgumentException {
		if (space == match.getBoard().getSingleWorkSpace(WorkType.HARVEST)) {
			for (FamilyMember f : match.getBoard().getMultipleWorkSpace(WorkType.HARVEST).getOccupants()) {
				if (famMember.getOwnerId() == f.getOwnerId())
					return f.getColor();
				return null;
			}
		} else if (space == match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION)) {
			for (FamilyMember f : match.getBoard().getMultipleWorkSpace(WorkType.PRODUCTION).getOccupants()) {
				if (famMember.getOwnerId() == f.getOwnerId())
					return f.getColor();
				return null;
			}
		} else if (space == match.getBoard().getMultipleWorkSpace(WorkType.HARVEST)) {
			if (famMember.getOwnerId() == match.getBoard().getSingleWorkSpace(WorkType.HARVEST).getOccupant()
					.getOwnerId())
				return match.getBoard().getSingleWorkSpace(WorkType.HARVEST).getOccupant().getColor();
			return null;
		} else if (space == match.getBoard().getMultipleWorkSpace(WorkType.PRODUCTION)) {
			if (famMember.getOwnerId() == match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION).getOccupant()
					.getOwnerId())
				return match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION).getOccupant().getColor();
			return null;
		}
		return null;
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {

		super.payServants(player, this.possibleServants);
		
		ArrayList<ExtraAction> activatedEffects = new ArrayList<ExtraAction>();

		match.getBoard().placeMember(player, this.famMember, this.space);

		for (int i = 0; i < workMessage.getChosenCardsAndEffects().length; i++) {
			if (workMessage.getChosenCardsAndEffects()[i] != 0) {
				activatedEffects.addAll(workMessage.getChoices()[i].getPossibleEffects()[workMessage.getChosenCardsAndEffects()[i] - 1].activate(player));
			}
		}
		
		for (int i = 0; i < workMessage.getcardsToActivateWithoutChoice().length; i++) {
			activatedEffects.addAll(workMessage.getcardsToActivateWithoutChoice()[i].getPossibleEffects()[0].activate(player));
		}
		
		player.getProperties().increaseProperties(player.getPersonalBonusTile().getTileBonus(this.space.getWorkType(), this.actionValue));

		return activatedEffects.toArray(new ExtraAction[0]);
	}

}