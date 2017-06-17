package it.polimi.ingsw.ps21.model.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public abstract class ExtraAction extends Action{


	public ExtraAction(PlayerColor playerId) {
		super(playerId);
	}

}
