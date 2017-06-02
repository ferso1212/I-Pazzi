package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;

public abstract class PermanentLeaderCard extends LeaderCard {
	
	private Effect permanentEffect;
	
	public PermanentLeaderCard(String name, Requirement reqs[], PermanentLeaderEffect perm){
		super(name, reqs, (Effect) perm);
	}

	public abstract void activate(Player player);
	
	public void resetActivation(){
	}

}
