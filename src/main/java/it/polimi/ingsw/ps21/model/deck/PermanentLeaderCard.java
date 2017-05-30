package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;

public abstract class PermanentLeaderCard extends LeaderCard {
	
	private Effect permanentEffect;
	
	public PermanentLeaderCard(String name, Requirement reqs[], PermanentLeaderEffect perm){
		super(name, reqs, (Effect) perm);
		;	
	}

	@Override
	public Effect getEffect() {
		return this.permanentEffect;
	}

	public abstract void activate(Player player);
}
