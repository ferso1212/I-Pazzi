package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ActionRequest extends Message{
	
	private Action choosenAction;
	private int id;

	public ActionRequest(PlayerColor dest, int id) {
		super(dest);
		this.id=id;
	}

	public Action getChoosenAction() {
		return choosenAction;
	}

	public void setChoosenAction(Action choosenAction) {
		this.choosenAction = choosenAction;
	}

	public int getId() {
		return id;
	}
	
	

}
