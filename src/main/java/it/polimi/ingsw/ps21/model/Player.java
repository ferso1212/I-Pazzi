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
	//Since there is not a "Personal Board" class, player's cards are stored here
	protected ArrayList<TerritoryCard> greenCards;
	protected ArrayList<BuildingCard> yellowCards;
	protected ArrayList<CharacterCard> blueCards;
	protected ArrayList<VentureCard> purpleCards;
	protected Properties properties; 
	protected ActionModifier actionMod; 
	protected FinalExcomModifier finalModifiers;
	protected PersonalBonusTile personalBonusTile;
	private Family family;
	protected AllDiscounts discounts;
	protected int[] militaryForTerritoryReq; //Contains the number of military points required to acquire territory cards. 
	//For example, militaryForTerritoryReq[0] is the number of military points required to acquire the first territory card.
	
	protected PlayerColor color;
	protected WorkModifier workMod;
	/**Returns an object that contains the values of the resources (stone, wood, servants and coins) and points (military points, faith points and victory points) of the player. 
	 * 
	 * @return object containing the player's properties.
	 */
	public Properties getProperties()
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



	/**Returns the number of the cards of a specified type owned by the player.
	 * 
	 * @param type type of the cards to count. It can be a value of the CardType enum, except for EXCOMMUNICATION and LEADER. Accepted values are TERRITORY, BUILDING, CHARACTER and VENTURE.
	 * @return
	 * @throws IllegalArgumentException when 'type' parameter is EXCOMMUNICATION, LEADER or not a member of the CardType enum.
	 */
	public int countCards(CardType type) throws IllegalArgumentException
	{
		switch(type)
		{
		case CHARACTER: return this.blueCards.size();
		case TERRITORY: return this.greenCards.size();
		case BUILDING: return this.yellowCards.size();
		case VENTURE: return this.purpleCards.size();
		default: throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Used to retrieve the cards that the player can activate when he does a harvest/production action.
	 * @param value the value of the harvest/production action, which determines which cards can be activated.
	 * @param workType HARVEST or PRODUCTION
	 * @return an ArrayList of DevelopmentCards, containing the harvest/production cards that the player can activate.
	 * @throws IllegalArgumentException if workType argument is not one of HARVEST or PRODUCTION.
	 */
	public ArrayList<DevelopmentCard> getWorkCards(int value, WorkType workType) throws IllegalArgumentException
	{
		ArrayList<DevelopmentCard> output = new ArrayList<DevelopmentCard>(); //output is the ArrayList that the method will return
		output.clear();
		if(workType==WorkType.HARVEST) 
		{
			value += this.workMod.getHarvMod();
			ArrayList<TerritoryCard> input=greenCards;	
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
	
	public int getMemberValue(FamilyMember member, DevelopmentCard card) throws IllegalArgumentException
	{
		if(card instanceof TerritoryCard) return (member.getValue() + this.actionMod.getCardPickingValueMod(DevelopmentCardType.TERRITORY));
		if(card instanceof BuildingCard) return (member.getValue() + this.actionMod.getCardPickingValueMod(DevelopmentCardType.BUILDING));
		if(card instanceof CharacterCard) return (member.getValue() + this.actionMod.getCardPickingValueMod(DevelopmentCardType.CHARACTER));
		if(card instanceof VentureCard) return (member.getValue() + this.actionMod.getCardPickingValueMod(DevelopmentCardType.VENTURE));
		throw new IllegalArgumentException();
	}
	
	
	
	/**This method can be used to check whether the player meets specific requirements on victory points, faith points and/or military points.
	 * 
	 * @param necessaryPoints An object containing the values of victory points, faith points and/or military points that the player should satisfy.
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of points required), FALSE if not.
	 */
	public boolean checkPoints(Points necessaryPoints)
	{
		if(this.properties.getFaithPoints() < necessaryPoints.getFaithPoints()) return false;
		if(this.properties.getVictoryPoints() < necessaryPoints.getVictoryPoints()) return false;
		if(this.properties.getMilitaryPoints() < necessaryPoints.getMilitaryPoints()) return false;
		return true;
	}
	
	/**This method can be used to check whether the player meets specific requirements on the number of territory cards, building cards, venture cards and character cards.
	 * 
	 * @param req
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of cards required), FALSE if not.
	 */
	public boolean checkCards(CardsNumber req)
	{
		if(req.getBlueCardNum()>this.countCards(CardType.CHARACTER)) return false;
		if(req.getYellowCardNum()>this.countCards(CardType.BUILDING)) return false;
		if(req.getGreenCardNum()>this.countCards(CardType.TERRITORY)) return false;
		if(req.getPurpleCardNum()>this.countCards(CardType.VENTURE)) return false;
		return true;
	}
	
	/**This method can be used to check whether the player meets specific requirements on the resources he owns.
	 * 
	 * @param necessaryResources
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of resources required), FALSE if not.
	 */
	public boolean checkResources(Resources necessaryResources)
	{
		if(this.properties.getCoins() < necessaryResources.getCoins()) return false;
		if(this.properties.getServants() < necessaryResources.getServants()) return false;
		if(this.properties.getStone() < necessaryResources.getStone()) return false;
		if(this.properties.getWood() < necessaryResources.getWood()) return false;
		return true;
	}
	
	/**This method can be used to check whether the player meets specific requirements on the resources, points and cards he owns.
	 * 
	 * @param requirements
	 * @return TRUE if the player satisfies the requirements (i.e. : he has at least the number of resources, points and cards required), FALSE if not.
	 */
	public boolean checkRequirements(Requirement requirements)
	{
		if(this.checkPoints(requirements.getPoints())==false) return false;
		if(this.checkCards(requirements.getCardsNumber())==false) return false;
		if(this.checkResources(requirements.getResources())==false) return false;
		return true;
	} 
	
	/**Adds a DevelopmentCard to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	public boolean addCard(DevelopmentCard card)
	{
		if(card instanceof TerritoryCard) return this.addGreenCard((TerritoryCard) card);
		if(card instanceof BuildingCard) return this.addYellowCard((BuildingCard) card);
		if(card instanceof VentureCard) return this.addPurpleCard((VentureCard) card);
		if(card instanceof CharacterCard) return this.addBlueCard((CharacterCard) card);
		return false;
	} 
	
	/**Adds a territory card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	private boolean addGreenCard(TerritoryCard card)
	{
		this.greenCards.add(card);
		return true;
		
	}
	
	
	/**Adds a building card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	private boolean addYellowCard(BuildingCard card)
	{
		this.yellowCards.add(card);
		return true;
		
	} 
	
	/**Adds a venture card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	private boolean addPurpleCard(VentureCard card)
	{
		this.purpleCards.add(card);
		return true;
		
	}
	
	/**Adds a character card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	public boolean addBlueCard(CharacterCard card)
	{
		this.blueCards.add((CharacterCard) card);
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
	
	public boolean checkMilitaryForTerritory()
	{
		if(this.getProperties().getMilitaryPoints()>=this.militaryForTerritoryReq[greenCards.size()]) return true;
		else return false;
	}
	
	public void payForOccupiedTower()
	{
		
	}
	
	//TODO : handle discount modifiers
	public void payCard(DevelopmentCard card)
	{
		DevelopmentCardType cardType = null;
		if(card instanceof TerritoryCard) cardType= DevelopmentCardType.TERRITORY;
		else if(card instanceof BuildingCard) cardType= DevelopmentCardType.BUILDING;
		else if(card instanceof VentureCard) cardType= DevelopmentCardType.VENTURE;
		else if(card instanceof CharacterCard) cardType= DevelopmentCardType.CHARACTER;
		try {
			int tempCost;
			tempCost= card.getRequirement().getCost().getCoins() + this.getDiscountMod().getDevMod(cardType);
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
		}
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
	 * @return the curModifiers. curModifiers includes all the modifiers that modify actions during the game
	 */
	public ActionModifier getActionMod() {
		return actionMod;
	}



	/**
	 * @return the familyMembers
	 */
	public Family getFamily() {
		return family;
	}



	public Player(String name, Properties properties, int[] militaryForTerritoryReq) 
	{
		super();
		this.name = name;
		this.properties = properties;
		this.militaryForTerritoryReq = militaryForTerritoryReq;
	}
	
	public PersonalBonusTile getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}
	
	public void setWorkMod(WorkType type, int mod) throws IllegalArgumentException
	{
		if(type==WorkType.HARVEST) this.workMod.setHarvestModifier(mod);
		if(type==WorkType.PRODUCTION) this.workMod.setProductionModifier(mod);
		else throw new IllegalArgumentException();
	
	}
	
	public int finalVictoryPoints()
	{
		
	}


	/**
	 * @return the discountMod
	 */
	public DiscountModifier getDiscountMod() {
		return discountMod;
	}
	
	
}
