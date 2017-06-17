package it.polimi.ingsw.ps21.model.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public abstract class ExtraAction extends Action{

	protected ExtraActionData data;

	public ExtraAction(PlayerColor playerId) {
		super(playerId);
	}
	
	public ExtraActionData getData(){
		return this.data;
	}

}
