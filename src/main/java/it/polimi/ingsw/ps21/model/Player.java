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
	private static final int MAX_WORK_CARDS =6;
	private String name;
	//Since there is not a "Personal Board" class, player's cards are stored here
	private ArrayList<TerritoryCard> greenCards;
	private ArrayList<BuildingCard> yellowCards;
	private ArrayList<CharacterCard> blueCards;
	private ArrayList<VentureCard> purpleCards;
	private int roundCouncilBonus; //number of Council Privileges acquired during this Player's round
	private Properties properties; 
	private CurrentModifier curModifiers; 
	private FinalExcomModifier finalModifiers;
	private Observer Observer;
	private PlayerFamilyMembers familyMembers;
	
	/**Returns an object that contains the values of the resources (stone, wood, servants and coins) and points (military points, faith points and victory points) of the player. 
	 * 
	 * @return object containing the player's properties.
	 */
	public Properties getProperties()
	{
		return this.properties;
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
	 * @throws IllegalArgumentException if workType argument is not one fo HARVEST or PRODUCTION.
	 */
	public ArrayList<DevelopmentCard> getWorkCards(int value, WorkType workType) throws IllegalArgumentException
	{
	
		if(workType==WorkType.HARVEST) 
		{
			ArrayList<TerritoryCard> input;
			input=greenCards;
			ArrayList<DevelopmentCard> output = new ArrayList<DevelopmentCard>();
			output.clear();
			for(TerritoryCard card: input)
			{
				if(value >= card.getEffect().getReq()) {output.add(card);}
			}
			return output;
			
		}
		else if(workType==WorkType.PRODUCTION) 
		{
			ArrayList<BuildingCard> input;
			input=yellowCards;
			ArrayList<DevelopmentCard> output = new ArrayList<DevelopmentCard>();
			output.clear();
			for(BuildingCard card: input)
			{
				if(value >= card.getEffect().getReq()) {output.add(card);}
			}
			return output;
		}
		else throw new IllegalArgumentException();
		
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
	public boolean checkRequirements(Requirements requirements)
	{
		if(this.checkPoints(requirements.getPoints())==false) return false;
		if(this.checkCards(requirements.getCardsNumber())==false) return false;
		if(this.checkResources(requirements.getResources())==false) return false;
		return true;
	} 
	
	/**Adds a territory card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	public boolean addCard(TerritoryCard card)
	{
		this.greenCards.add(card);
		return true;
		
	}
	
	/**Adds a building card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	public boolean addCard(BuildingCard card)
	{
		this.yellowCards.add(card);
		return true;
		
	}
	
	/**Adds a venture card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	public boolean addCard(VentureCard card)
	{
		this.purpleCards.add(card);
		return true;
		
	}
	
	/**Adds a character card to the player's personal board.
	 * 
	 * @param card to add
	 * @return
	 */
	public boolean addCard(CharacterCard card)
	{
		this.blueCards.add(card);
		return true;
		
	}
	
	/**Resets the value of all the family member at the start of a new round.
	 * 
	 */
	public void roundReset()
	{
		this.familyMembers.setBlackValue(0);
		this.familyMembers.setOrangeValue(0);
		this.familyMembers.setWhiteValue(0);
		this.familyMembers.setNeutralValue(0);
	}
	
	public boolean vaticanSupport()
	{
		
	}
	
	public boolean checkMilitaryForTerritory()
	{
		
	}
	
	public void payForOccupiedTower()
	{
		
	}
	
	public void payRequirements(Requirements req)
	{
		this.properties.addCoins(-req.getCost().getCoins());
		this.properties.addServants(-req.getCost().getServants());
		this.properties.addWood(-req.getCost().getWood());
		this.properties.addStone(-req.getCost().getStone());
		this.properties.addFaithPoints(-req.getCost().getFaithPoints());
		this.properties.addMilitaryPoints(-req.getCost().getMilitaryPoints());
		this.properties.addVictoryPoints(-req.getCost().getVictoryPoints());
	}
	
	public void makeAction()
	{
		
	}
	
	public void makeAction(ActionType action)
	{
		
	}



	/**
	 * @return the curModifiers. curModifiers includes all the modifiers that modify actions during the game
	 */
	public CurrentModifier getCurrentModifiers() {
		return curModifiers;
	}



	/**
	 * @return the familyMembers
	 */
	public PlayerFamilyMembers getPlayerFamilyMembers() {
		return familyMembers;
	}
	
	
	
}
