package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public abstract class ExtraAction extends Action{

	protected ExtraActionData data;
	protected int mainActionId;

	public ExtraAction(PlayerColor playerId) {
		super(playerId);
		this.mainActionId=0;
	}
	
	public ExtraActionData getData(){
		return this.data;
	}

	public int getMainActionId() {
		return mainActionId;
	}

	public void setMainActionId(int mainActionId) {
		this.mainActionId = mainActionId;
	}

	
}
