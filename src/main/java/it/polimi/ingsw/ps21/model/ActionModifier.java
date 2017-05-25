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

	
	private boolean delayFirstAction;
	private boolean noMarketAction;
	private boolean noPlacementBonus;

	/**
	 * CurrentModifier class constructor. Initializes all the values to 0.
	 * 
	 */
	public ActionModifier() {
	

		this.delayFirstAction=false;
		this.noMarketAction=false;
		this.noPlacementBonus=false;
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
