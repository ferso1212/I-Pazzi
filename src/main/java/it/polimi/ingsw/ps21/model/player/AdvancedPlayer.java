package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;

/**
 * Used to store the status of each player in an advanced match. It stores the following datas of a
 * player:
 * <li>Player's deck, containing the cards he acquired during the game
 * (Territory Cards, Building Cards, Character Cards and Venture Cards)
 * <li>Leader cards
 * <li>Personal bonus tile
 * <li>Color (id): one of the possible values of the PlayerColor enum, used also to
 * identify the player
 * <li>Properties: resources (wood, coins, servants, stones) and points
 * (military points, victory points and faith points).
 * <li>Modifiers of player's actions, including the advanced modifiers
 * <li>Family: the player's family members.
 * 
 * @author fabri
 *
 */
public class AdvancedPlayer extends Player {
	
	private ArrayList<LeaderCard> leaderCards;
	private AdvancedModifier advModifier;
	
	/**
	 * 
	 * @param id the color of the player
	 * @param properties the initial properties
	 */
	public AdvancedPlayer(PlayerColor id, PlayerProperties properties) {
		super(id, properties);
		this.leaderCards=new ArrayList<>();
		this.advModifier= new AdvancedModifier();
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
	
	public boolean checkCardRequirements(LeaderCard card)
	{
		for (Requirement req : card.getRequirements()) {
			if (this.checkRequirement(req)) return true;
		}
		return false;
	}
}
