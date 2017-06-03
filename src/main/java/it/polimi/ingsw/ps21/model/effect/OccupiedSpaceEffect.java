package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */

public class OccupiedSpaceEffect extends PermanentLeaderEffect{

	public OccupiedSpaceEffect(Requirement req) {
		super(req);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		player.getAdvMod().setNoPayOccupiedTower(true);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return new String(this.getClass().getName());
	}

	@Override
	public String getDesc() {
		
		return new String("You can place your family members in action spaces that are already occupied.");
	}

}
