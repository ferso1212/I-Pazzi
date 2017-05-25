package it.polimi.ingsw.ps21.model;



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
public class ActionModifier {

	
	private int greenCardDiceMod; //modifier that increases or reduces the value of a dice when used to acquire green cards
	private int blueCardDiceMod;  //modifier that increases or reduces the value of a dice when used to acquire blue cards
	private int yellowCardDiceMod; //modifier that increases or reduces the value of a dice when used to acquire yellow cards
	private int purpleCardDiceMod; ////modifier that increases or reduces the value of a dice when used to acquire purple cards
	private boolean delayFirstAction;
	private boolean noMarketAction;
	private boolean noPlacementBonus;

	/**
	 * CurrentModifier class constructor. Initializes all the values to 0.
	 * 
	 */
	public ActionModifier() {
	
		this.greenCardDiceMod=0;
		this.blueCardDiceMod=0;
		this.yellowCardDiceMod=0;
		this.purpleCardDiceMod=0;
		this.delayFirstAction=false;
		this.noMarketAction=false;
		this.noPlacementBonus=false;
	}

	

	
	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire green cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire green cards
	 */
	private int getGreenModifier() {
		return this.greenCardDiceMod;
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire yellow cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire yellow cards
	 */
	private int getYellowModifier() {
		return this.yellowCardDiceMod;
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire purple cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire purple cards
	 */
	private int getPurpleModifier() {
		return this.purpleCardDiceMod;
	}

	/**
	 * Returns the value of the modifier that modifies the value of the dice
	 * used to acquire blue cards.
	 * 
	 * @return value of the modifier that modifies the value of the dice used to
	 *         acquire blue cards
	 */
	private int getBlueModifier() {
		return this.blueCardDiceMod;
	}
	
	
	/**Returns the value of the modifier of the cards whose color is specified in the 'color' parameter
	 * 
	 * @param type of the card to which the modifier is associated. It can be a value of the CardType enum, except for EXCOMMUNICATION and LEADER. Accepted values are TERRITORY, BUILDING, CHARACTER and VENTURE.
	 * @return value of the modifier of the card whose type is specified in the 'type' parameter
	 * @throws IllegalArgumentException when 'type' parameter is EXCOMMUNICATION, LEADER or not a member of the CardType enum.
	 */
	public int getCardPickingValueMod(DevelopmentCardType type) throws IllegalArgumentException
	{
		switch(type)
		{
		case TERRITORY: return this.greenCardDiceMod;
		case BUILDING: return this.yellowCardDiceMod;
		case VENTURE: return this.purpleCardDiceMod;
		case CHARACTER: return this.blueCardDiceMod;
		default: throw new IllegalArgumentException();
		}
	}
	
	
	

	
	public void setCardPickingValueMod(DevelopmentCardType type, int mod) throws IllegalArgumentException
	{
		switch(type)
		{
		case TERRITORY: this.greenCardDiceMod=mod; break;
		case BUILDING: this.yellowCardDiceMod=mod; break;
		case VENTURE: this.purpleCardDiceMod=mod; break;
		case CHARACTER: this.blueCardDiceMod=mod; break;
		default: throw new IllegalArgumentException();
		}
	}




	/**
	 * @return the delayFirstAction
	 */
	public boolean firstActionDelayed() {
		return delayFirstAction;
	}




	/**
	 * @param delayFirstAction the delayFirstAction to set
	 */
	public void setDelayFirstAction(boolean delayFirstAction) {
		this.delayFirstAction = delayFirstAction;
	}




	/**
	 * @return the noMarketAction
	 */
	public boolean marketActionForbidden() {
		return noMarketAction;
	}




	/**
	 * @param noMarketAction the noMarketAction to set
	 */
	public void forbidMarketAction() {
		this.noMarketAction = true;
	}




	/**
	 * @return the noPlacementBonus
	 */
	public boolean noPlacementBonus() {
		return noPlacementBonus;
	}


	/**
	 * @param noPlacementBonus the noPlacementBonus to set
	 */
	public void setNoPlacementBonus() {
		this.noPlacementBonus = true;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}
