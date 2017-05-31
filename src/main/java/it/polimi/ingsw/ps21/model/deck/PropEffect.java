package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PropEffect extends Effect {

	private ImmProperties bonus;
	
	public PropEffect(Requirement reqs[], ImmProperties bonus) {
		super(reqs);
		this.bonus = bonus;
	}
	
	public PropEffect(Requirement req, ImmProperties bonus) {
		super(req);
		this.bonus = bonus;
	}
	
	
	/**
	 * Check if at least one Requirement is satisfied by player
	 */
	@Override
	public boolean isActivable(Player player) {
		boolean check = false;
		for (Requirement r: req){
			check = check || player.checkRequirement(r); // Basta che uno dei requisiti sia attivabile
		}
		return check;
	}
	@Override
	public void activate(Player player) {
		player.getProperties().increaseProperties(bonus);
	}

}
