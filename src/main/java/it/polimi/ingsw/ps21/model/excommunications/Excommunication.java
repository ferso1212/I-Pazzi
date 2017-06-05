package it.polimi.ingsw.ps21.model.excommunications;
import it.polimi.ingsw.ps21.model.player.Player;

/**General abstract class for excommunications.
 * All the excommunications implement the 'activate' method.
 * @author fabri
 *
 */
public abstract class Excommunication {
	
	protected int cardId;
	protected int cardEra;
	
	public int getId()
	{
		return this.cardId;
	}
	
	public int getEra()
	{
		return this.getEra();
	}
	
	/**Activates the excommunication: its effects are stored in the player's modifiers.
	 */
	public abstract void activate(Player player);
	
	
	/**Returns a string that describes the excommunication
	 */
	public abstract String toString();

}
