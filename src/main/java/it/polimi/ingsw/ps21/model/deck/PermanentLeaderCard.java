package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;

public class PermanentLeaderCard extends LeaderCard {
	
	private Effect permanentEffect;
	
	public PermanentLeaderCard(String name, OrRequirement req, PermanentLeaderEffect perm){
		super(name, req, (Effect) perm);
		
	}

	@Override
	public Effect getEffect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activate(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetAction() {
		// TODO Auto-generated method stub
		
	}

}
