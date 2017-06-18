package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
/*
 * To be implemented
 */
public class ChurcSupport extends PermanentLeaderEffect {

	public ChurcSupport(Requirement req){
		super(req);
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		// TODO Auto-generated method stub
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "ChurchSupportLeaderEffect";
	}

	@Override
	public String getDesc() {
		return "TO BE Implemented";
	}

}
