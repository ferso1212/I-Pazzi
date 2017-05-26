package it.polimi.ingsw.ps21.model;



/**
 * This class is used to store modifiers that may have effects on members placement or on the order of play. 
 * Modifiers are induced by excommunications and by permanenteffects of development cards. 
 * It is possible to store the following modifiers:
 * <li> delayFirstAction: if true, the first action of the player will be delayed to the end of the round-
 * <li> noMarketAction: if true, the player can't place his family members in the market spaces.
 * <li> noPlacementBonus: if true, the player does not acquire instant bonuses from placing his family members on action spaces.
 * @author fabri
 *
 */
public class ActionModifier{

	
	private boolean delayFirstAction;
	private boolean noMarketAction;
	private boolean noPlacementBonus;

	/**
	 * CurrentModifier class constructor. Initializes all the values to false.
	 * 
	 */
	public ActionModifier() {
	

		this.delayFirstAction=false;
		this.noMarketAction=false;
		this.noPlacementBonus=false;
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
