package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class LeaderCopyMessage extends Message {

	private LeaderCard[] possibilities;
	private LeaderCard chosen; 
	public LeaderCopyMessage(PlayerColor dest, LeaderCard[] possibilities) {
		super(dest);
		this.possibilities = possibilities;
		
	}
	
	public void setChosenCard(LeaderCard chosen){
		this.chosen = chosen;
	}
	
	
	public LeaderCard[] getPossibilities(){
		return this.possibilities;
	}
	
	public LeaderCard getChosenCard(){
		return this.chosen;
	}

}
