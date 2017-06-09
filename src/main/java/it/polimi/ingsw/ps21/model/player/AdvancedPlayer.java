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
			if(card.isActivated()) output.add(card);
		}
		return output;
	}
	
	public ArrayList<LeaderCard> getActiveAndClonableLeaderCards()
	{
		ArrayList<LeaderCard> output= new ArrayList<LeaderCard>();
		for(LeaderCard card: leaderCards)
		{
			if(card.isActivated()&&card.isClonable()) output.add(card);
		}
		return output;
	}
	
	
	
	public AdvancedModifier getAdvMod()
	{
		return this.advModifier;
	}
	
	public void setPersonalBonusTile(PersonalBonusTile chosenTile)
	{
		this.personalBonusTile=chosenTile;
	}
	
	public void addLeaderCard(LeaderCard cardToAdd)
	{
		leaderCards.add(cardToAdd);
	}
}
