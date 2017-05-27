package it.polimi.ingsw.ps21.model;

import java.util.*;


/**Used to store the status of each player.
 * It stores the following datas of a player:
 * <li> Name
 * <li>Cards (Territory Cards, Building Cards, Character Cards and Venture Cards)
 * <li> Number of council privileges acquired during this Player's round
 * <li>Socket
 * <li>Properties: resources (wood, coins, servants, stones) and points (military points, victory points and faith points).
 * <li>Modifiers of player's actions 
 * @author fabri
 *
 */
public class Player {
	protected String name;
	protected String id;
	//Since there is not a "Personal Board" class, player's cards are stored here
	protected PropertiesSet properties; 
	protected ModifiersSet modifiers;
	protected PersonalBonusTile personalBonusTile;
	private Family family;
	protected PlayerColor color;
	protected PlayerDeck devCards;

	/**Returns an object that contains the values of the resources (stone, wood, servants and coins) and points (military points, faith points and victory points) of the player. 
	 * 
	 * @return object containing the player's properties.
	 */
	public PropertiesSet getProperties()
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
	 * Used to retrieve the cards that the player can activate when he does a harvest/production action.
	 * @param value the value of the harvest/production action, which determines which cards can be activated.
	 * @param workType HARVEST or PRODUCTION
	 * @return an ArrayList of DevelopmentCards, containing the harvest/production cards that the player can activate.
	 * @throws IllegalArgumentException if workType argument is not one of HARVEST or PRODUCTION.
	 */
	public ArrayList<DevelopmentCard> getActivableWorks(int value, WorkType workType) throws IllegalArgumentException
	{
		ArrayList<DevelopmentCard> output = new ArrayList<DevelopmentCard>(); //output is the ArrayList that the method will return
		output.clear();
		if(workType==WorkType.HARVEST) 
		{
			value += this.modifiers.getWorkMods().getHarvMod();
			ArrayList<TerritoryCard> input=this.devCards.getCards(DevelopmentCardType.TERRITORY);	
			for(TerritoryCard card: input)
			{
				if(value >= card.getPermanentEffect().getReq()) {output.add(card);}
			}
			return output;
			
		}
		else if(workType==WorkType.PRODUCTION) 
		{
			value += this.workMod.getProdMod();
			ArrayList<BuildingCard> input = yellowCards;
			
			for(BuildingCard card: input)
			{
				if(value >= card.getPermanentEffect().getReq()) {output.add(card);}
			}
			return output;
		}
		else throw new IllegalArgumentException();
		
	}
	
	public int getMemberValue(FamilyMember member, DevelopmentCard card)
	{
		return (member.getValue() + this.modifiers.getDiceMods().getDiceMod(card.getCardType()).getValue());
	
	}
	
	
	

	
	/**This method can be used to check whether the player meets specific requirements on the number of territory cards, building cards, venture cards and character cards.
	 * 
	 * @param req
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of cards required), FALSE if not.
	 */
	public boolean checkCards(CardsNumber req)
	{
		if(req.getBlueCardNum()>this.devCards.countCards(DevelopmentCardType.CHARACTER)) return false;
		if(req.getYellowCardNum()>this.devCards.countCards(DevelopmentCardType.BUILDING)) return false;
		if(req.getGreenCardNum()>this.devCards.countCards(DevelopmentCardType.TERRITORY)) return false;
		if(req.getPurpleCardNum()>this.devCards.countCards(DevelopmentCardType.VENTURE)) return false;
		return true;
	}
	
	/**This method can be used to check whether the player meets specific requirements on his properties.
	 * 
	 * @param necessaryResources
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of resources required), FALSE if not.
	 */
	public boolean checkProperties(ImmProperties necessaryProperties)
	{
		for(Property prop: this.properties.getProperties())
		{
			if(prop.getValue()<necessaryProperties.getPropertyValue(prop.getId())) return false;
		}
		return true;
	}
	

	
	
	
	/**Resets the value of all the family member at the start of a new round.
	 * 
	 */
	public void roundReset()
	{
		this.family.getMember(MembersColor.WHITE).setValue(0);
		this.family.getMember(MembersColor.ORANGE).setValue(0);
		this.family.getMember(MembersColor.BLACK).setValue(0);
		this.family.getMember(MembersColor.NEUTRAL).setValue(0);
	}
	
	//TODO
	public boolean vaticanSupport()
	{
		
	}
	
	
	//TODO : handle discount modifiers
	public void payCard(DevelopmentCard card)
	{
		/*DevelopmentCardType cardType = null;
		if(card instanceof TerritoryCard) cardType= DevelopmentCardType.TERRITORY;
		else if(card instanceof BuildingCard) cardType= DevelopmentCardType.BUILDING;
		else if(card instanceof VentureCard) cardType= DevelopmentCardType.VENTURE;
		else if(card instanceof CharacterCard) cardType= DevelopmentCardType.CHARACTER;
		try {
			int tempCost;
			if(tempCost > 0) this.properties.addCoins(-tempCost);
			
			this.properties.addServants(-card.getRequirement().getCost().getServants());
			this.properties.addWood(-card.getRequirement().getCost().getWood());
			this.properties.addStone(-card.getRequirement().getCost().getStone());
			this.properties.addFaithPoints(-card.getRequirement().getCost().getFaithPoints());
			this.properties.addMilitaryPoints(-card.getRequirement().getCost().getMilitaryPoints());
			this.properties.addVictoryPoints(-card.getRequirement().getCost().getVictoryPoints());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
	}
	
	//TODO implement
	public void makeAction()
	{
		return;
	}
	
	//TODO implement
	public void makeAction(ActionType action)
	{
		return;
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



	public Player(String name, PropertiesSet properties, int[] militaryForTerritoryReq, String id) 
	{
		super();
		this.name = name;
		this.properties = properties;
		this.id=id;
	}
	
	public PersonalBonusTile getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}
	
	
	//TODO
	public int finalVictoryPoints()
	{
		return 0;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	
}
