package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
/**
 * This Leader Effect increase player properties when activated (one time for round)
 * @author gullit
 *
 */
public class PropertiesBonus extends InstantLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -35292649495692343L;
	private ImmProperties bonus;
	public PropertiesBonus(Requirement reqs[], ImmProperties bonus) {
		super(reqs);
		this.bonus = bonus;
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		player.getProperties().increaseProperties(bonus);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Properties Bonus Effect";
	}

	@Override
	public String getDesc() {
		return "This effect increase player properties of " + bonus + " when activated";
	}

}
