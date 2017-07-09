package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;


/*
 * 
 */
public class NoPayOccupiedTower extends PermanentLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5073631457294189975L;

	public NoPayOccupiedTower(Requirement reqs[]) {
		super(reqs);
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		player.getAdvMod().setNoPayOccupiedTower(true);
		return new NullAction(player.getId());

	}

	@Override
	public String getType() {
		return "No Pay Occupied Tower Effect";
	}

	@Override
	public String getDesc() {
		return new String("You don't have to pay the additional cost that should be payed to occupy a space in a tower where there is already anoyher member of your family.");
	}

}
