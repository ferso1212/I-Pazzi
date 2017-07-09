package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;

/**Used to store excommunications that modify the value of the dices.
 * @author fabri
 *
 */
public class DiceExcommunication extends Excommunication {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4617431756922309770L;
	private int whiteDiceMalus;
	private int orangeDiceMalus;
	private int blackDiceMalus;

	/**Activates the excommunication: its effects are stored in the player's family members.
	 */
	@Override
	public void activate(Player player) {
		player.getFamily().getMember(MembersColor.WHITE).increaseModifier(- whiteDiceMalus);
		player.getFamily().getMember(MembersColor.ORANGE).increaseModifier(- orangeDiceMalus);
		player.getFamily().getMember(MembersColor.BLACK).increaseModifier(- blackDiceMalus);
		
	}

	/**Constructs the excommunication that decreases the values of the dices.
	 * @param id
	 * @param period
	 * @param whiteDiceMalus value that will be decreased from the white family member when the excommunication is active.
	 * @param orangeDiceMalus value that will be decreased from the orange family member when the excommunication is active.
	 * @param blackDiceMalus value that will be decreased from the black family member when the excommunication is active.
	 */
	public DiceExcommunication(int id, int period, int whiteDiceMalus, int orangeDiceMalus, int blackDiceMalus) {
		super(id, period);
		this.whiteDiceMalus = whiteDiceMalus;
		this.orangeDiceMalus = orangeDiceMalus;
		this.blackDiceMalus = blackDiceMalus;
	}

	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString()
	{
		StringBuilder output= new StringBuilder();
		output.append("Each time you use your white family member, his value is reduced by " + this.whiteDiceMalus + " units.\n");
		output.append("Each time you use your black family member, his value is reduced by " + this.blackDiceMalus + " units.\n");
		output.append("Each time you use your orange family member, his value is reduced by " + this.orangeDiceMalus + " units.\n");
		return output.toString();
	}
	
}
