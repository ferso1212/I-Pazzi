package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;

/*
 * To be implemented
 */

public class OccupiedSpaceEffect extends PermanentLeaderEffect{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6859501731910539388L;

	public OccupiedSpaceEffect(Requirement reqs[]) {
		super(reqs);
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
