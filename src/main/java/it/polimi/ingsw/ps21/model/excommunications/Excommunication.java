package it.polimi.ingsw.ps21.model.excommunications;
import java.io.Serializable;

import it.polimi.ingsw.ps21.model.player.Player;

/**General abstract class for excommunications.
 * All the excommunications implement the 'activate' method and store the period in which they should be evaluated/activated.
 * @author fabri
 *
 */
public abstract class Excommunication  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8740305135895892119L;
	protected int id;
	protected int period;
	
	public int getId()
	{
		return this.id;
	}
	
	public int getPeriod()
	{
		return this.period;
	}
	
	/**Activates the excommunication: its effects are stored in the player's modifiers.
	 */
	public abstract void activate(Player player);
	
	
	/**Returns a string that describes the excommunication
	 */
	public abstract String toString();

	public Excommunication(int id, int period) {
		super();
		this.id = id;
		this.period = period;
	}

	
	

}
