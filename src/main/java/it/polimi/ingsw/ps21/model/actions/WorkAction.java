package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.Space;
import it.polimi.ingsw.ps21.model.board.WorkSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class WorkAction extends Action {

	private WorkSpace space;
	private FamilyMember famMember;
	private WorkMessage workMessage;

	public WorkAction(PlayerColor playerId, WorkSpace space, FamilyMember famMember) {
		super(playerId);
		this.space = space;
		this.famMember = famMember;
		this.updateCounter = 1;
	}

	@Override
	public Message upload(Player player, Match match) {
		
		switch (this.updateCounter) {
		
		case 1: {
			if ((space.isOccupable(player, famMember)) && (!famMember.isUsed())) {

				if (((match.getNumberPlayers() == 3) || (match.getNumberPlayers() == 4))
						&& ((famMember.getColor() == MembersColor.WHITE) || (famMember.getColor() == MembersColor.BLACK)
								|| (famMember.getColor() == MembersColor.ORANGE))
						&& !((this.checkOccupant(match, famMember, space) == MembersColor.NEUTRAL)
								|| (this.checkOccupant(match, famMember, space) == null))) {
					return new RefusedAction(player.getId());
				}
				try {
					ArrayList<DevelopmentCard> cardWithCost = new ArrayList<DevelopmentCard>();

					for (DevelopmentCard c : player.getActivableWorks(this.famMember.getValue(), this.space.getWorkType())) {
						
						if (c.getPossibleEffects().length > 1)
							multipleEffectCard.add(c);
						else if (c.getPossibleEffects().length == 1)
							singleEffectCard.add(c);
					}

					this.workMessage = new WorkMessage(player.getId(),
							multipleEffectCard.toArray(new DevelopmentCard[0]));
				} catch (IllegalCardTypeException e) {
					return new RefusedAction(player.getId());
				} catch (CloneNotSupportedException e) {
					return new RefusedAction(player.getId());
				}
				this.updateCounter--;
				return this.workMessage;
			} else
				return new RefusedAction(player.getId());
		}

			break;

		default:
			break;
		}
	}

	@Override
	public Message isLegal(Player player, Match match) {

		if ((space.isOccupable(player, famMember)) && (!famMember.isUsed())) {

			if (((match.getNumberPlayers() == 3) || (match.getNumberPlayers() == 4))
					&& ((famMember.getColor() == MembersColor.WHITE) || (famMember.getColor() == MembersColor.BLACK)
							|| (famMember.getColor() == MembersColor.ORANGE))
					&& !((this.checkOccupant(match, famMember, space) == MembersColor.NEUTRAL)
							|| (this.checkOccupant(match, famMember, space) == null))) {
				return new RefusedAction(player.getId());
			}
			try {
				this.workMessage = new WorkMessage(player.getId(),
						player.getActivableWorks(this.famMember.getValue(), this.space.getWorkType()),
						player.getProperties().clone());
			} catch (IllegalCardTypeException e) {
				return new RefusedAction(player.getId());
			} catch (CloneNotSupportedException e) {
				return new RefusedAction(player.getId());
			}
			return this.workMessage;
		} else
			return new RefusedAction(player.getId());
	}

	private MembersColor checkOccupant(Match match, FamilyMember famMember, Space space)
			throws IllegalArgumentException {
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
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {

		if (this.space == match.getBoard().getMultipleWorkSpace(this.space.getWorkType())) {
			this.famMember
					.increaseValue(-match.getBoard().getMultipleWorkSpace(this.space.getWorkType()).getDiceMalus());
		}

		ArrayList<ExtraAction> activatedEffects = new ArrayList<ExtraAction>();

		match.getBoard().placeMember(player, this.famMember, this.space);

		for (int i = 0; i < workMessage.getChosenCardsAndEffects().length; i++) {
			if (workMessage.getChosenCardsAndEffects()[i] != 0) {
				activatedEffects.addAll(workMessage.getChoices().get(i)
						.getPossibleEffects()[workMessage.getChosenCardsAndEffects()[i] - 1].activate(player));
			}
		}

		return activatedEffects.toArray(new ExtraAction[0]);
	}

}