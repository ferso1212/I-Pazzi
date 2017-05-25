package it.polimi.ingsw.ps21.model;

public class PermanentLeaderCard extends LeaderCard {
	
	private Effect permanentEffect;
	
	public PermanentLeaderCard(String name, Requirement req, Effect perm){
		super(name, req);
		permanentEffect = perm;
	}

}
