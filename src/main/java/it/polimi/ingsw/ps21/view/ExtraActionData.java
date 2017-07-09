package it.polimi.ingsw.ps21.view;

import java.io.Serializable;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.PickAnotherCardAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;

public class ExtraActionData implements Serializable {
	
	private int actionId;
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
		this.type = ExtraActionType.TAKE_PRIVILEGES;
		this.message = "Take Privileges: Choose " + action.getNumberOfPrivileges() + " different privileges";
	
	}
	public ExtraActionData(NullAction action){
		this.type = ExtraActionType.NULL_ACTION;
		this.message = "Null Action: nothing to do";

	}
	
	public ExtraActionData(ExtraWorkAction action)
	{
		
	}
	
	public ExtraActionType getType(){
		return this.type;
	}
	
	public String getDescription(){
		return this.message;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	

}
