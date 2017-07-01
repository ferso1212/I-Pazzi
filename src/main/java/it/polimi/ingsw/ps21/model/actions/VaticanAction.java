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

/**
 * This class handles the choice and assignement of an excommunication in the Vatican Round of match
 * @author gullit
 *
 */
public class VaticanAction extends Action {

	private VaticanChoice vaticanChoice;

	public VaticanAction(PlayerColor playerId) {
		super(playerId);
		this.updateCounter = 2;
	}

	/**
	 * When executed first time this method returns an excommunication message if the player doesn't have the possibility
	 * to support the church, else it returns a vatican choice that will be notified to the UserHandler.
	 * When executed second time if the player has chosen to support the church returns an AcceptedAction, if he has choosen
	 * to not support the church it returns an ExcommunicationMessage and if he has not visited the message it returns a Refused Action message
	 * 
	 */
	@Override
	public Message update(Player player, Match match) {
		switch (this.updateCounter) {
		case 2: {
			if (player.getProperties().getProperty(PropertiesId.FAITHPOINTS).getValue() >= match.getBoard()
					.getExcommunicationRequirement(match.getPeriod())) {
				this.updateCounter--;
				this.vaticanChoice = new VaticanChoice(player.getId(),
						match.getBoard().getExcommunications()[match.getPeriod() - 1]);
				return this.vaticanChoice;
			} else{
				this.vaticanChoice = new VaticanChoice(player.getId(),
						match.getBoard().getExcommunications()[match.getPeriod() - 1]);
				vaticanChoice.setChosen(false);
				vaticanChoice.setVisited();
				this.updateCounter=0;
				return new ExcommunicationMessage(player.getId(), match.getBoard().getExcommunications()[match.getPeriod() - 1].toString());
			}
		}

		case 1: {
			this.updateCounter--;
			if ((this.vaticanChoice.isVisited())) {
				if (!this.vaticanChoice.getChosen())
				return new ExcommunicationMessage(player.getId(), match.getBoard().getExcommunications()[match.getPeriod() - 1].toString());
				
			} else
				return new RefusedAction(player.getId());
			
		}
		
		case 0: {
			//this case is reached the second time the update method is called on this action,  if the player has not enough faith points
			//OR the second time the third method is called on this action
			return new AcceptedAction(player.getId());
		}

		default:
			return new RefusedAction(player.getId());
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match)
			throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		
		if(this.vaticanChoice.getChosen()) {
		
			if ((player instanceof AdvancedPlayer) && (((AdvancedPlayer)player).getAdvMod().hasVaticanSupportBonus())){
				player.getProperties().increaseProperties(new ImmProperties(0,0,0,0,5));
			}
			
			player.getProperties().getProperty(PropertiesId.FAITHPOINTS).setValue(0);
			 
			return new ExtraAction[0];
			
		}else{
			player.addExcommunication(match.getBoard().getExcommunications()[match.getPeriod() - 1]);
			return new ExtraAction[0];
			
		}
	}

}
