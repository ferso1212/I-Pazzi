package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.PickAnotherCardAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.effect.PickAnotherCard;

public class ExtraActionData implements Serializable {
	
	private ExtraActionType type;
	private String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5392011958639846013L;
		
	
	public ExtraActionData(PickAnotherCardAction action){
		this.type = ExtraActionType.PICK_ANOTHER_CARD;
		DevelopmentCardType[] types = action.getTypes();
		this.message = "Pick another card: choose one card of ";
		int i;
		for ( i=0; i<types.length-1 ; i++){
			this.message = this.message.concat(types[i] + " or ");
		}
		this.message = this.message.concat(types[i] + " with the dice requirement of " + action.getDice());
	}

	public ExtraActionData(TakePrivilegesAction action){
		this.type = ExtraActionType.PICK_ANOTHER_CARD;
		this.message = "Take Privileges: Choose " + action.getNumberOfPrivileges() + " differnt privileges";
	}
	public ExtraActionData(NullAction action){
		this.type = ExtraActionType.NULL_ACTION;
		this.message = "Null Action: nothing to do";
	}
	public ExtraActionType getType(){
		return this.type;
	}
	
	public String getDescription(){
		return this.message;
	}

}
