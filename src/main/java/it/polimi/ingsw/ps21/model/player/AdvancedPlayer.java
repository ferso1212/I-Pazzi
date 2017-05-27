package it.polimi.ingsw.ps21.model.player;

import it.polimi.ingsw.ps21.model.properties.PropertiesSet;

public class AdvancedPlayer extends Player {
	public AdvancedPlayer(String name, PropertiesSet properties, String id) {
		super(name, properties, id);
		// TODO Auto-generated constructor stub
	}

	private ArrayList<LeaderCard> leaderCards;
	private AdvancedModifier advModifier;
	
	public ArrayList<LeaderCards> getActivatedLeaderCards()
	{
		
	}
	
	public LeaderCard chooseLeaderCardToCopy()
	{
		
	}
	
	public AdvancedModifier getAdvMod()
	{
		return this.advModifier;
	}
	
}
