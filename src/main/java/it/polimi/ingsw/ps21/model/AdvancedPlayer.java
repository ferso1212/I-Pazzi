package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public class AdvancedPlayer extends Player {
	private ArrayList<LeaderCard> leaderCards;
	private AdvancedModifier advModifier;
	
	public AdvancedPlayer(String name, Properties properties, int[] militaryForTerritoryReq)
	{
		super(name, properties, militaryForTerritoryReq);
	}
	
	public ArrayList<LeaderCard> getActivatedLeaderCards()
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
