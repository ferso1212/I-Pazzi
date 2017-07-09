package it.polimi.ingsw.ps21.model.actions;


import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.LeaderChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class LeaderChoiceAction extends Action{
	private LeaderChoice message;

	public LeaderChoiceAction(PlayerColor playerId, LeaderChoice mess) {
		super(playerId);
		this.updateCounter=1;
		this.message=mess;
		
	}
	
	@Override
	public Message update(Player player, Match match)
	{
		if(this.updateCounter==1)
		{
			
			this.updateCounter--;
			return this.message;
		}
		else return new AcceptedAction(player.getId());
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws RequirementNotMetException, InsufficientPropsException {
		AdvancedPlayer aPlayer= (AdvancedPlayer)player;
		aPlayer.addLeaderCard(this.message.getChosenCard());
		ExtraAction[] toReturn= new ExtraAction[1];
		toReturn[0]= new NullAction(player.getId());
		return toReturn;
	}
	
	

}
