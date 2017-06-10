package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.Player;

/**Used to store excommunications that modify the value of the work actions.
 * @author fabri
 *
 */
public class WorkExcommunication extends Excommunication{
	private WorkType workType;
	private int malus;
	
	/**Activates the excommunication: its effects are stored in the player's work modifier.
	 */
	@Override
	public void activate(Player player) {
	 player.getModifiers().getWorkMods().setWorkMod(workType, malus);
		
	}

	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString() {
		return "Each time you perform a "+ workType.toString() + " action, the value of that action is reduced by " + this.malus + " units.";
	}
	/**Constructs the excommunication that reduces a work action value.
	 * @param d
	 * @param period
	 * @param workType the type of work that should be penalized. Can be HARVEST or PRODUCTION
	 * @param malus the value that will be subtracted from the original action value, each time the penalized work action is performed.
	 */
	public WorkExcommunication(int id, int period, WorkType workType, int malus) {
		super(id, period);
		this.workType = workType;
		this.malus = malus;
	}
	
	

}
