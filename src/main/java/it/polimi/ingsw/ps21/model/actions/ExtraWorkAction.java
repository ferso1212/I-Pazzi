package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class ExtraWorkAction extends ExtraAction {

	private int valueOfAction;
	private WorkType workType;
	private WorkMessage workMessage;

	public ExtraWorkAction(PlayerColor playerId, int valueOfAction, WorkType workType) {
		super(playerId);
		this.valueOfAction = valueOfAction;
		this.workType = workType;
		this.updateCounter = 1;
		
	}

	@Override
	public Message update(Player player, Match match) {
		switch (this.updateCounter) {
		case 1: {
			try {
				ArrayList<DevelopmentCard> cardWithCost = new ArrayList<DevelopmentCard>();
				ArrayList<DevelopmentCard> cardWithoutCost = new ArrayList<DevelopmentCard>();

				for (DevelopmentCard c : player.getActivableWorks(this.valueOfAction, this.workType)) {
					for (EffectSet e : c.getPossibleEffects()) {
						if (e.getTotalCost().isNull())
							cardWithoutCost.add(c);
						cardWithCost.add(c);
					}
				}

				if (cardWithCost.size() == 0)
					return new AcceptedAction(player.getId(), this.actionId);

				this.workMessage = new WorkMessage(player.getId(), cardWithCost.toArray(new DevelopmentCard[0]), cardWithoutCost.toArray(new DevelopmentCard[0]), this.actionId);

			} catch (IllegalCardTypeException e) {
				return new RefusedAction(player.getId(), this.actionId);
			}
			this.updateCounter--;
			return this.workMessage;
		}
		
		case 0:
		{
			ImmProperties totalCost = new ImmProperties(0);
			for (int i=0 ; i < this.workMessage.getChosenCardsWithCost().length; i++){
				if (this.workMessage.getChosenCardsWithCost()[i] != 0){
					totalCost = totalCost.sum(this.workMessage.getCardsWithCost()[i].getPossibleEffects()[this.workMessage.getChosenCardsWithCost()[i]-1].getTotalCost());
				}
			}
			if (player.checkProperties(totalCost))
				return new AcceptedAction(player.getId(), this.actionId);
			else return new RefusedAction(player.getId(), "You don't have enough properties", this.actionId);
		}

		default:
			return new RefusedAction(player.getId(), "Unhandled case", this.actionId);
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match){
		
	ArrayList<ExtraAction> activatedEffects = new ArrayList<ExtraAction>();

	for (int i = 0; i < workMessage.getChosenCardsWithCost().length; i++) {
		if (workMessage.getChosenCardsWithCost()[i] != 0) {
			activatedEffects.addAll(workMessage.getCardsWithCost()[i].getPossibleEffects()[workMessage.getChosenCardsWithCost()[i] - 1].activate(player));
		}
	}
	
	for (int i = 0; i < workMessage.getcardsToActivateWithoutChoice().length; i++) {
		if (workMessage.getChosenCardsWithCost()[i] != 0)
		activatedEffects.addAll(workMessage.getcardsToActivateWithoutChoice()[i].getPossibleEffects()[workMessage.getChosenCardsWithoutCost()[i] -1 ].activate(player));
	}

	return activatedEffects.toArray(new ExtraAction[0]);
	}

}
