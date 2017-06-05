package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.deck.LeaderCard;


public class AdvancedPlayer extends Player {
	
	private ArrayList<LeaderCard> leaderCards;
	private AdvancedModifier advModifier;
	
	public AdvancedPlayer( PlayerColor id, PlayerProperties properties) {
		super(id, properties);
	}

	
	public ArrayList<LeaderCard> getActivatedLeaderCards()
	{
		ArrayList<LeaderCard> output= new ArrayList<LeaderCard>();
		for(LeaderCard card: leaderCards)
		{
			if(card.isClonable()) output.add(card);
		}
		return output;
	}
	
	//TODO implement leader choice
	public LeaderCard chooseLeaderCardToCopy(LeaderCard[] activeLeaders)
	{
		LeaderCard chosen;
		chosen=activeLeaders[0]; //stub
		return chosen;
	}
	
	public AdvancedModifier getAdvMod()
	{
		return this.advModifier;
	}
	
	
	
}
