package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/* 
 * To be Implemented
 */
public class LorenzoIlMagnifico extends PermanentLeaderEffect {
	public LorenzoIlMagnifico(Requirement req) {
		super(); //should be super(req)
		// TODO Auto-generated constructor stub
	}

	private LeaderCard chosenLeaderCopy;
	
	@Override
	public void activate(Player player) throws UnchosenException {
		if (chosenLeaderCopy == null) throw new UnchosenException();
		chosenLeaderCopy.activate(player);
	}

}
