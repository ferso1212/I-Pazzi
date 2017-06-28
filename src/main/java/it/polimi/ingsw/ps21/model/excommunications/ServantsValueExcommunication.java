package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;

/**Used to store the excommunications that reduce the value of each servant (i. e. : more servants are needed to increase the action value by 1.)
 * @author fabri
 *
 */
public class ServantsValueExcommunication extends Excommunication{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8062050530390572204L;
	private int servantsForOne;
	

	/**Activates the excommunication: its effects are stored in the player's family.
	 */
	@Override
	public void activate(Player player) {
		player.getFamily().setServantsForOne(this.servantsForOne);
	}

	/**Constructs the object for the excommunication that decreases the servants' value.
	 * 
	 * @param servantsForOne number of servants required to increase the action value by one.
	 */
	public ServantsValueExcommunication(int id, int period, int servantsForOne) {
		super(id, period);
		this.servantsForOne = servantsForOne;
	}
	
	/**Returns a string that describes the excommunication
	 */
	public String toString()
	{
		return "In order to increase the action value by 1 unit, you need to use " + this.servantsForOne + " servants.";
	}
	

}
