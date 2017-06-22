package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/*
 * To be implemented
 */
public class DoubleResources extends PermanentLeaderEffect {

	public DoubleResources(Requirement reqs[]) {
		super(reqs);
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		player.getAdvMod().setDoubleResources(true);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return new String("Double resources on instant effects");
	}

	@Override
	public String getDesc() {
		return new String("Each time you receive a properties bonus from the instant effect of a development card, you receive the bonus twice.");
	}



}
