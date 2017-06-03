package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PropEffect extends Effect {

	private ImmProperties bonus;
	
	public PropEffect(ImmProperties cost, ImmProperties bonus) {
		super(cost);
		this.bonus = bonus;
	}
	
	
	/**
	 * Check if at least one Requirement is satisfied by player
	 */
	@Override
	public boolean isActivable(Player player) {
		boolean check = false;
		check = player.getProperties().greaterOrEqual(cost); // Basta che uno dei requisiti sia attivabile
		return check;
	}
	@Override
	public ExtraAction activate(Player player) {
		player.getProperties().increaseProperties(bonus);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return new String(this.getClass().getName());
	}

	@Override
	public String getDesc() {
		String output= new String("You instantly acquire ");
		output.concat(bonus.toString());
		return output;
	}

}
