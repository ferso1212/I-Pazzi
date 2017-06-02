package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */
public class NoPayOccupiedTower extends PermanentLeaderEffect {

	public NoPayOccupiedTower() {
		super();
	}

	@Override
	public void activate(Player player) {
		// To be implemented

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
