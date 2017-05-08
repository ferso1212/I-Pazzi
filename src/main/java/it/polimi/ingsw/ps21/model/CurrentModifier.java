package it.polimi.ingsw.ps21.model;

import it.polimi.ingsw.ps21.CardType;

/**
 * This class is used to store modifiers that may have effects on each action of
 * the player. Modifiers are induced by excommunications and by permanent
 * effects of development cards. 
 * It is possible to store the following kinds of
 * modifiers:
 * <li>Properties modifiers: modifiers that increase/reduce the amount of
 * resources or points acquired each time the player acquires points or
 * resources.
 * <li>Harvest modifiers: modifiers that increase/reduce the harvest action
 * value
 * <li>Production modifiers: modifiers that increase/reduce the production
 * action value
 * <li>Dice modifiers: modifiers that increase or reduce the value of a dice
 * <li>Dice modifier according to card color: modifiers that increase or reduce
 * the value of a dice when used to acquire a card with a specific colour
 * 
 * @author fabri
 *
 */
public class CurrentModifier {

	private ImmProperties propModifier;
	private int harvestModifier;
	private int productionModifier;
	private int whiteDiceModifier;
	private int orangeDiceModifier;
	private int blackDiceModifier;
	private int[] cardDiceModifier;

	/**
	 * CurrentModifier class constructor. Initializes all the values to 0.
	 * 
	 */
	public CurrentModifier() {
		this.propModifier = new ImmProperties(0, 0, 0, 0, 0, 0, 0, 0);
		this.harvestModifier = 0;
		this.productionModifier = 0;
		this.whiteDiceModifier = 0;
		this.orangeDiceModifier = 0;
		this.blackDiceModifier = 0;
		this.cardDiceModifier = new int[4];
		this.cardDiceModifier[0] = 0;
		this.cardDiceModifier[1] = 0;
		this.cardDiceModifier[2] = 0;
		this.cardDiceModifier[3] = 0;
	}

	/**
	 * Returns the value of the production modifier. Production modifiers are
	 * modifiers that increase/reduce the production action value
	 * 
	 * @return value of the production modifier.
	 */
	public int getProdMod() {
		return this.productionModifier;
	}

	/**
	 * Returns the value of the harvest modifier. Harvest modifiers are
	 * modifiers that increase/reduce the harvest action value
	 * 
	 * @return value of the harvest modifier.
	 */
	public int getHarvMod() {
		return this.harvestModifier;
	}

	/**
	 * Returns an object containing the properties modifiers. Property modifiers
	 * are modifiers that increase/reduce the amount of resources or points
	 * acquired each time the player acquires points or resources.
	 * 
	 * @return An object that contains property modifiers.
	 */
	public ImmProperties getPropMod() {
		return this.propModifier;
	}

	/**
	 * Returns the value of the white dice modifier. Each time the player rolls
	 * the white dice, this modifier changes the value of the white dice.
	 * 
	 * @return value of the white dice modifier.
	 */
	public int getWhiteDiceMod() {
		return this.whiteDiceModifier;
	}

	/**
	 * Returns the value of the black dice modifier. Each time the player rolls
	 * the black dice, this modifier changes the value of the black dice.
	 * 
	 * @return value of the black dice modifier.
	 */
	public int getBlackDiceMod() {
		return this.blackDiceModifier;
	}

	/**
	 * Returns the value of the orange dice modifier. Each time the player rolls
	 * the orange dice, this modifier changes the value of the orange dice.
	 * 
	 * @return value of the orange dice modifier.
	 */
	public int getOrangeDiceMod() {
		return this.orangeDiceModifier;
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire green cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire green cards
	 */
	public int getGreenModifier() {
		return this.cardDiceModifier[0];
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire yellow cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire yellow cards
	 */
	public int getYellowModifier() {
		return this.cardDiceModifier[1];
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire purple cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire purple cards
	 */
	public int getPurpleModifier() {
		return this.cardDiceModifier[2];
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire blue cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire blue cards
	 */
	public int getBlueModifier() {
		return this.cardDiceModifier[3];
	}
	
	/**Returns the value of the modifier of the dice whose color is specified in the 'color' parameter
	 * 
	 * @param color of the dice to which the modifier is associated. It can be a value of the MembersColor enum, apart from NEUTRAL. Accepted values are: ORANGE, BLACK, WHITE.
	 * @return value of the modifier of the dice whose color is specified in the 'color' parameter
	 * @throws IllegalArgumentException when 'color' parameter in NEUTRAL or not a member of the MembersColor enum.
	 */
	public int getDiceMod(MembersColor color) throws IllegalArgumentException
	{
		switch(color)
		{
		case NEUTRAL: throw new IllegalArgumentException();
		case WHITE: return this.getWhiteDiceMod(); 
		case ORANGE: return this.getOrangeDiceMod();
		case BLACK: return this.getBlackDiceMod();
		default: throw new IllegalArgumentException();
		}
	}
	
	/**Returns the value of the modifier of the cards whose color is specified in the 'color' parameter
	 * 
	 * @param type of the card to which the modifier is associated. It can be a value of the CardType enum, except for EXCOMMUNICATION and LEADER. Accepted values are TERRITORY, BUILDING, CHARACTER and VENTURE.
	 * @return value of the modifier of the card whose type is specified in the 'type' parameter
	 * @throws IllegalArgumentException when 'type' parameter is EXCOMMUNICATION, LEADER or not a member of the CardType enum.
	 */
	public int getCardMod(CardType type) throws IllegalArgumentException
	{
		switch(type)
		{
		case CHARACTER: return this.getBlueModifier();
		case TERRITORY: return this.getGreenModifier();
		case BUILDING: return this.getYellowModifier();
		case VENTURE: return this.getPurpleModifier();
		default: throw new IllegalArgumentException();
		}
	}
}
