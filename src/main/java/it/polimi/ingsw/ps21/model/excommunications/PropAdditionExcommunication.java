package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

/**Used to store the excommunications that reduce the value acquired each time the player acquires resources or points.
 * @author fabri
 *
 */
public class PropAdditionExcommunication extends Excommunication{
	private ImmProperties additionMalus;

	/**Activates the excommunication: its effects are stored in the player's properties.
	 */
	@Override
	public void activate(Player player) {
		for(PropertiesId propId: additionMalus.getPropertiesIds())
		{
			player.getProperties().getProperty(propId).setAdditionModifier(- additionMalus.getPropertyValue(propId));
		}
		
		
		
	}

	/**Constructs the object.
	 * 
	 * @param additionMalus these values will be subtracted each time the corresponding property is being acquired by the player.
	 */
	public PropAdditionExcommunication(int id, int period, ImmProperties additionMalus) {
		super(id, period);
		this.additionMalus = additionMalus;
	}

	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString(){
		StringBuilder output= new StringBuilder();
		for(PropertiesId propId: additionMalus.getPropertiesIds())
		{
			output.append("Each time you acquire " + propId.toString() + " you receive " + additionMalus.getPropertyValue(propId) + " less of them. \n");
		}
		return output.toString();
	}
	
	

}
