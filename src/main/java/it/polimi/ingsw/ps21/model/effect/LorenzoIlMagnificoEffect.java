package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/* 
 * To be Implemented
 */
public class LorenzoIlMagnificoEffect extends PermanentLeaderEffect {
	public LorenzoIlMagnificoEffect() {
		super();
	}
	private LeaderCard chosenLeaderCopy;
	
	@Override
	public void activate(Player player) throws UnchosenException {
		if (chosenLeaderCopy == null) throw new UnchosenException();
		chosenLeaderCopy.activate(player);
	}
	
	public void setChoose(LeaderCard copied){
		chosenLeaderCopy = copied;
	}

	@Override
	public String getType() {
		return new String("Clone Leader card");
	}

	@Override
	public String getDesc() {
		return new String("Copy the skill of another leader played by an opponent. The chosen skill cannot be changed.");
	}
}
