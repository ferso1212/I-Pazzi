package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;


public class AdvancedPlayer extends Player {
	public AdvancedPlayer(String name, PlayerProperties properties, int id) {
		super(name, properties, id);
		// TODO Auto-generated constructor stub
	}

	private ArrayList<LeaderCard> leaderCards;
	private AdvancedModifier advModifier;
	
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
