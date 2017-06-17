package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */
public class NoPayOccupiedTower extends PermanentLeaderEffect {

	public NoPayOccupiedTower(Requirement req) {
		super(req);
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
		return new String("You don't have to pay the additional cost that should be payed to occupy a space in a tower where there is already anoyher member of your family.");
	}

}