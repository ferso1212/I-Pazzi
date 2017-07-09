package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.LorenzoIlMagnificoEffect;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class PlayLeaderCardAction extends Action {

	private LeaderCard cardToPlay;
	private int updateCounter;
	private LeaderCopyMessage message;

	public PlayLeaderCardAction(PlayerColor playerId, LeaderCard cardToPlay, int actionId) {
		super(playerId, actionId);
		this.cardToPlay = cardToPlay;
		updateCounter = 1;
	}

	@Override
	public Message update(Player player, Match match) {
		switch (updateCounter) {

		case 1:
			if ((player.checkCardRequirements(this.cardToPlay)) && (!this.cardToPlay.getEffect().isActivated())) {

				if (cardToPlay.getEffect() instanceof LorenzoIlMagnificoEffect) {
					ArrayList<LeaderCard> activatedCards = new ArrayList<>();
					Player players[] = match.getPlayers().toArray(new Player[0]);
					for (int i = 0; i < players.length; i++) {
						if (players[i].getId() != player.getId()) {
							AdvancedPlayer current = (AdvancedPlayer) players[i];
							for (int j = 0; j < current.getLeaders().length; j++) {
								if (current.getLeaders()[j].isClonable())
									activatedCards.add(current.getLeaders()[j]);
							}
						}
					}
					if (activatedCards.size() == 0)
						return new RefusedAction(player.getId(), "There isn't any Leader Card to be copied", this.actionId);
					message = new LeaderCopyMessage(player.getId(), activatedCards.toArray(new LeaderCard[0]), this.actionId);
					updateCounter--;
					return message;
				}
				return new AcceptedAction(player.getId(), this.actionId);
			}
			return new RefusedAction(player.getId(), this.actionId);
		case 0: { // leader choosen
			if (message.isVisited() && message.getChosenCard() != null)
				return new AcceptedAction(player.getId(), this.actionId);
			else
				return new RefusedAction(player.getId(), this.actionId);
		}
		default:
			return new RefusedAction(player.getId(), this.actionId);
		}

	}

	@Override
	public ExtraAction[] activate(Player player, Match match)
			throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {

		ArrayList<ExtraAction> returnAction = new ArrayList<ExtraAction>();
		returnAction.add(this.cardToPlay.getEffect().activate((AdvancedPlayer) player));
		for(ExtraAction a: returnAction)
		{
			a.setActionId(actionId);
		}
		return returnAction.toArray(new ExtraAction[0]);
	}
}