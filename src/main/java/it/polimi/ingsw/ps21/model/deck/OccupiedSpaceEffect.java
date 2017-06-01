package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */

public class OccupiedSpaceEffect extends PermanentLeaderEffect{

	@Override
	public void activate(Player player) {
		// TODO Auto-generated method stub
		
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
