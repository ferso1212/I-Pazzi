package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class NullLeaderEffect extends LeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 679607764881833644L;

	public NullLeaderEffect() {
		super(new Requirement(new CardsNumber(0), new ImmProperties(0)));
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		return new NullAction(player.getId());
	}

	@Override
	public void resetActivation() {
		this.activated = false;
	}

	@Override
	public String getType() {
		return "Null Effect";
	}

	@Override
	public String getDesc() {
		return "This effect does nothing";
	}

}
