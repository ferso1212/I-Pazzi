package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
/*
 * To be implemented
 */
public class ChurcSupport extends PermanentLeaderEffect {

	public ChurcSupport(){
		super();
	}

	@Override
	public ExtraAction activate(Player player) {
		// TODO Auto-generated method stub
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "ChurchSupportLeaderEffect";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
