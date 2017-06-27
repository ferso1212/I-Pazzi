package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.TileChoice;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class TileChoiceAction extends Action {

	private TileChoice message;
	private int updateCounter;
	
	public TileChoiceAction(PlayerColor dest, TileChoice message) {
		super(dest);
		this.message = message;
		updateCounter = 1;
	}
	
	@Override
	public Message update(Player player, Match match) {
		if (updateCounter==1){
			this.updateCounter--;
			return this.message;
		}
		else return new AcceptedAction(this.playerId);
	}

	@Override
	public ExtraAction[] activate(Player player, Match match)
			throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		PersonalBonusTile choice = message.getChosen();
		AdvancedPlayer aPlayer= (AdvancedPlayer)player;
		ExtraAction[] toReturn= new ExtraAction[1];
		toReturn[0]= new NullAction(player.getId());
		aPlayer.setPersonalBonusTile(choice);
		return toReturn;
	}

}
