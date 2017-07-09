package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;

/**Used to store excommunication that modify the actions flow.
 * @author fabri
 *
 */
public class ActionExcommunication extends Excommunication{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4281245256904085406L;
	private boolean delayFirstAction;
	private boolean noMarketAction;
	
	
	/**Activates the excommunication: its effects are stored in the player's action modifiers.
	 */
	@Override
	public void activate(Player player) {
		if(delayFirstAction) player.getModifiers().getActionMods().setDelayFirstAction(true);
		if(noMarketAction) player.getModifiers().getActionMods().forbidMarketAction();
		
		
	}
	
	/**Constructs the action excommunication object.
	 * 
	 * @param delayFirstAction set true if this excommunication should delay the first action of the player to the end of the round, each round.
	 * @param noMarketAction set true if this excommunication should forbid the player to perform a market action.
	 */
	public ActionExcommunication(int id, int period, boolean delayFirstAction, boolean noMarketAction) {
		super(id, period);
		this.delayFirstAction = delayFirstAction;
		this.noMarketAction = noMarketAction;
		
	}

	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString() {
		StringBuilder output= new StringBuilder();
		if(delayFirstAction) output.append("Each round, you skip your first action. You'll peform that action at the end of the round");
		if(noMarketAction) output.append("You can't place your family members in the market spaces");
		return output.toString();
	}
	
	
	

}
