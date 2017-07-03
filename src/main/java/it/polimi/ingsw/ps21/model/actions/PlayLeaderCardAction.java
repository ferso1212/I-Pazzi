package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class PlayLeaderCardAction extends Action{
	
	private LeaderCard cardToPlay;

	public PlayLeaderCardAction(PlayerColor playerId, LeaderCard cardToPlay) {
		super(playerId);
		this.cardToPlay = cardToPlay;
	}

	@Override
	public Message update(Player player, Match match) {
		if ((player.checkCardRequirements(this.cardToPlay)) && (!this.cardToPlay.getEffect().isActivated())){
			return new AcceptedAction(player.getId());
		}return new RefusedAction(player.getId());
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
		ArrayList<ExtraAction> returnAction = new ArrayList<ExtraAction>();
		returnAction.add(this.cardToPlay.getEffect().activate((AdvancedPlayer)player));
		return returnAction.toArray(new ExtraAction[0]);
	}
}