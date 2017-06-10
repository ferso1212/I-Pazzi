package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
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

public class PlayLeaderCard extends Action{
	
	private LeaderCard cardToPlay;

	public PlayLeaderCard(PlayerColor playerId, LeaderCard cardToPlay) {
		super(playerId);
		this.cardToPlay = cardToPlay;
	}

	@Override
	public Message isLegal(Player player, Match match) {
		if ((player.checkCardRequirements(this.cardToPlay)) && (!this.cardToPlay.getEffect().isActivated())){
			return new AcceptedAction(player.getId());
		}return new RefusedAction(player.getId());
	}

	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {
		
		ExtraAction returnAction = this.cardToPlay.getEffect().activate((AdvancedPlayer)player);
		return 
	}
	
	

}
