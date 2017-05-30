package it.polimi.ingsw.ps21.model.player;

import java.util.*;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;
import it.polimi.ingsw.ps21.model.properties.Property;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.Card;


/**Used to store the status of each player.
 * It stores the following datas of a player:
 * <li> Name: a string containing the player's name
 * <li> Id: an auto-generated integer number, different for each player, that identifies the player in the match
 * <li> Player's deck, containing the cards he acquired during the game (Territory Cards, Building Cards, Character Cards and Venture Cards)
 * <li> Personal bonus tile
 * <li> Color: one of the possible values of the PlayerColor enum
 * <li> Properties: resources (wood, coins, servants, stones) and points (military points, victory points and faith points).
 * <li> Modifiers of player's actions 
 * <li> Family: the player's family members.
 * @author fabri
 *
 */
public class Player {
	protected String name;
	protected int id;
	//Since there is not a "Personal Board" class, player's cards are stored here
	protected PlayerProperties properties; 
	protected ModifiersSet modifiers;
	protected PersonalBonusTile personalBonusTile;
	private Family family;
	protected PlayerColor color;
	protected PlayerDeck devCards;

	/**Returns an object that contains the values of the resources (stone, wood, servants and coins) and points (military points, faith points and victory points) of the player. 
	 * 
	 * @return object containing the player's properties.
	 */
	public PlayerProperties getProperties()
	{
		return this.properties;
	}
	
	
	/**
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Used to retrieve the work cards that the player can activate when he performs a harvest/production action.
	 * @param value the value of the harvest/production action, which determines which cards can be activated.
	 * @param workType HARVEST or PRODUCTION
	 * @return an ArrayList of DevelopmentCards, containing the harvest/production cards that the player can activate.
	 * @throws IllegalArgumentException if workType argument is not one of HARVEST or PRODUCTION.
	 */
	public ArrayList<DevelopmentCard> getActivableWorks(int value, WorkType workType) throws IllegalCardTypeException
	{
		ArrayList<DevelopmentCard> output= new ArrayList<DevelopmentCard>();
		
		//convert WorkType in DevelopmentCardType to pass it to the PlayerDeck.getCards(cardType) method
		DevelopmentCardType cardType = null;
		if(workType==WorkType.HARVEST) cardType=DevelopmentCardType.TERRITORY;
		else if(workType==WorkType.PRODUCTION) cardType=DevelopmentCardType.BUILDING;
		else throw new IllegalCardTypeException();
		
		//modify action value according to work action modifier
		int modVal=value + this.getModifiers().getWorkMods().getWorkMod(workType);
		
		//get Harvest or Production cards belonging to the player
		ArrayList<DevelopmentCard> input = this.getDeck().getCards(cardType);
		
		//For each card in the list of the player's harvest/production cards, checks if the value is equal or greater
		//than its Dice Requirement: if the check returns true, the card is added to the output list.
		for(DevelopmentCard c: input)
		{
			if(c instanceof TerritoryCard)
			{
				if(modVal>=(((TerritoryCard)c).getDiceRequirement())) output.add(c);
			}
			
			if(c instanceof BuildingCard)
			{
				if(modVal>=(((BuildingCard)c).getDiceRequirement())) output.add(c);
			}
		}
		return output;
	}
	
	/**Returns the dice value required to acquire a specific card.
	 * This method takes into account also the modifiers that modifies the value according to the type of the card.
	 * @param member Family Member used to acquire the card
	 * @param card	Card to acquire.
	 * @return the dice value required to acquire a specific card.
	 */
	public int getMemberValue(FamilyMember member, DevelopmentCard card)
	{
		return (member.getValue() + this.modifiers.getDiceMods().getDiceMod(card.getCardType()).getValue());
	
	}
	
	
	
	/**Checks whether the player meets specific requirements on his properties.
	 * 
	 * @param necessaryResources
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of resources required), FALSE if not.
	 */
	public boolean checkProperties(ImmProperties necessaryProperties)
	{
		for(Property prop: this.properties.getPropertiesSet().getProperties())
		{
			if(prop.getValue()<necessaryProperties.getPropertyValue(prop.getId())) return false;
		}
		return true;
	}
	
	
	
	/**Pays the chosen cost of the card, taking into account the discount modifiers and the payment modifiers
	 * @param card
	 * @param cost
	 * @throws InsufficientPropsException
	 */
	public void payCard(DevelopmentCardType cardType, ImmProperties cost) throws InsufficientPropsException
	{		
		//PropertiesSet contenente gli sconti per il tipo di carta associato alla carta passata come parametro
		PropertiesSet discountPropsSet= this.modifiers.getDiscountsMods().getDiscount(cardType).getPropertiesDisc();
		
		if(this.properties.payProperties(cost, discountPropsSet)==false)
		{throw new InsufficientPropsException();
		}
		
	}
	
	
	/**
	 * @return the modifiers
	 */
	public ModifiersSet getModifiers() {
		return modifiers;
	}


	/**
	 * @return the familyMembers
	 */
	public Family getFamily() {
		return family;
	}

	/**Player constructor. 
	 * @param name A String containing the name of the player.
	 * @param startingProperties The resources and points that the player will have at the beginning of the match.
	 * @param id an integer that identifies the player.
	 */
	public Player(String name, PlayerProperties startingProperties, int id) 
	{
		this.name = name;
		this.properties = startingProperties;
		this.id=id;
	}
	
	/**Provides the personal bonus tile of the player.
	 * The personal bonus tile contains additional bonuses activated when work actions are performed.
	 * @return the personal bonus tile of the player.
	 */
	public PersonalBonusTile getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}
	
	
	
	/**Checks if the player has enough properties (points, resources and number of cards) to meet the requirements passed as argument.
	 * @param req requirements that are compared to the player's properties
	 * @return true if the player's properties are greater than the requirements (the requirements are met); otherwise false
	 */
	public boolean checkRequirement(Requirement req)
	{
		//checks cards number requirement
		if(!this.devCards.checkCardsNumReq(req.getCardsNumber())) return false;
		
		//checks properties requirements
		if(!this.checkProperties(req.getProperties())) return false;
		
		return true; 
	}
	
	/**Between all the requirements (in OR) of the card, this method returns only the ones that are met by the player.
	 * @param card the card 
	 * @return the card's requirements that are met by the player.
	 */
	public ArrayList<Requirement> metCardRequirements(Card card)
	{
		ArrayList<Requirement> output= new ArrayList<Requirement>();
		for(Requirement req: card.getRequirements())
		{
			if(this.checkRequirement(req)) output.add(req);
		}
		return output;
	}
	
	/**Checks the card's requirements in OR and returns TRUE if at least one of them is met by the player.
	 * @param card
	 * @return TRUE if the player has enough points/resources to acquire the card
	 */
	public boolean checkCardRequirements(Card card)
	{
		for(Requirement req: card.getRequirements())
		{
			if(this.checkRequirement(req)) return true;
		}
		return false;
	}
	
	/**
	 * @return the player's id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the devCards
	 */
	public PlayerDeck getDeck() {
		return devCards;
	}


	/**
	 * @return the color
	 */
	public PlayerColor getColor() {
		return color;
	}

	
	
}
