package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.ExcommunicationMessage;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class VaticanAction extends Action {

	private VaticanChoice vaticanChoice;

	public VaticanAction(PlayerColor playerId) {
		super(playerId);
		this.updateCounter = 1;
	}

	@Override
	public Message update(Player player, Match match) {
		switch (this.updateCounter) {
		case 1: {
			if (player.getProperties().getProperty(PropertiesId.FAITHPOINTS).getValue() >= match.getBoard()
					.getExcommunicationRequirement(match.getPeriod())) {
				this.updateCounter--;
				this.vaticanChoice = new VaticanChoice(player.getId(),
						match.getBoard().getExcommunications()[match.getPeriod() - 1]);
				return this.vaticanChoice;
			} else
				return new ExcommunicationMessage(player.getId());
		}

		case 0: {
			if ((this.vaticanChoice.isVisited()) && (!this.vaticanChoice.getChosen())) {
				return new ExcommunicationMessage(player.getId());
			} else
				return new RefusedAction(player.getId());
		}

		default:
			return new RefusedAction(player.getId());
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match)
			throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
		ArrayList<ExtraAction> extraActionList = new ArrayList<>();
		
		if(this.vaticanChoice.getChosen()) {
		
			if ((player instanceof AdvancedPlayer) && (((AdvancedPlayer)player).getAdvMod().hasVaticanSupportBonus())){
				player.getProperties().increaseProperties(new ImmProperties(0,0,0,0,5));
			}
			
			player.getProperties().getProperty(PropertiesId.FAITHPOINTS).setValue(0);
			 
			extraActionList.add(new NullAction(player.getId()));
			return extraActionList.toArray(new ExtraAction[0]);
		}else{
			
			match.getBoard().getExcommunications()[match.getPeriod() - 1].activate(player);
			
			extraActionList.add(new NullAction(player.getId()));
			return extraActionList.toArray(new ExtraAction[0]);
		}
	}

}
