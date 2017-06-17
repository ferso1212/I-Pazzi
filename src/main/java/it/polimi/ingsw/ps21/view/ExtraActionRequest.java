package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ExtraActionRequest extends Message {
	
	private ExtraActionData[] possibleActions;
	public ExtraActionRequest(PlayerColor playerID, ExtraActionData[] possibleActions){
		super(playerID);
		this.message = "You have to choose one Extra Action";
		this.possibleActions = possibleActions;
	}
	
	public ExtraActionData[] getPossibilities(){
		return this.possibleActions;
		
	}

}
