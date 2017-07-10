package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.ServantsChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public class ExtraWorkAction extends ExtraAction {

	private int valueOfAction;
	private WorkType workType;
	private WorkMessage workMessage;
	private ServantsChoice servantsMessage;

	public ExtraWorkAction(PlayerColor playerId, int valueOfAction, WorkType workType) {
		super(playerId);
		this.valueOfAction = valueOfAction;
		this.workType = workType;
		this.updateCounter = 2;
		this.data = new ExtraActionData(this);

	}

	@Override
	public Message update(Player player, Match match) {
		switch (this.updateCounter) {
		case 2: {
			this.servantsMessage = new ServantsChoice(player.getId(),
					player.getProperties().getProperty(PropertiesId.SERVANTS).getValue(), this.actionId);
			this.updateCounter--;
			return this.servantsMessage;
		}
		case 1: {
			if (!servantsMessage.isVisited())
				return new RefusedAction(playerId, actionId);
			this.valueOfAction += servantsMessage.getNumberOfServants();
			try {
				ArrayList<DevelopmentCard> cardWithCost = new ArrayList<DevelopmentCard>();
				ArrayList<DevelopmentCard> cardWithoutCost = new ArrayList<DevelopmentCard>();

				for (DevelopmentCard c : player.getActivableWorks(this.valueOfAction, this.workType)) {
					boolean costFound = false;
					for (EffectSet e : c.getPossibleEffects()) {
						if (!e.getTotalCost().isNull() && !costFound) {
							costFound = true;
						}
						if (costFound)
							cardWithCost.add(c);
						else
							cardWithoutCost.add(c);
					}
				}

				this.workMessage = new WorkMessage(player.getId(), cardWithCost.toArray(new DevelopmentCard[0]),
						cardWithoutCost.toArray(new DevelopmentCard[0]), this.actionId);

			} catch (IllegalCardTypeException e) {
				return new RefusedAction(player.getId(), this.actionId);
			}
			this.updateCounter--;
			return this.workMessage;
		}

		case 0: {
			ImmProperties totalCost = new ImmProperties(0);
			for (int i = 0; i < this.workMessage.getChosenCardsWithCost().length; i++) {
				if (this.workMessage.getChosenCardsWithCost()[i] != 0) {
					totalCost = totalCost.sum(this.workMessage.getCardsWithCost()[i]
							.getPossibleEffects()[this.workMessage.getChosenCardsWithCost()[i] - 1].getTotalCost());
				}
			}
			if (player.checkProperties(totalCost))
				return new AcceptedAction(player.getId(), this.actionId);
			else
				return new RefusedAction(player.getId(), "You don't have enough properties", this.actionId);
		}

		default:
			return new RefusedAction(player.getId(), "Unhandled case", this.actionId);
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) {

		player.getProperties().getProperty(PropertiesId.SERVANTS).payValue(servantsMessage.getNumberOfServants());

		ArrayList<ExtraAction> activatedEffects = new ArrayList<ExtraAction>();

		for (int i = 0; i < workMessage.getChosenCardsWithCost().length; i++) {
			if (workMessage.getChosenCardsWithCost()[i] != 0) {
				activatedEffects.addAll(workMessage.getCardsWithCost()[i]
						.getPossibleEffects()[workMessage.getChosenCardsWithCost()[i] - 1].activate(player));
			}
		}

		for (int i = 0; i < workMessage.getcardsToActivateWithoutChoice().length; i++) {
			if (workMessage.getChosenCardsWithoutCost()[i] != 0)
				activatedEffects.addAll(workMessage.getcardsToActivateWithoutChoice()[i]
						.getPossibleEffects()[workMessage.getChosenCardsWithoutCost()[i] - 1].activate(player));
		}

		return activatedEffects.toArray(new ExtraAction[0]);
	}

	public int getValueOfAction() {
		return valueOfAction;
	}

	public WorkType getWorkType() {
		return workType;
	}

}
