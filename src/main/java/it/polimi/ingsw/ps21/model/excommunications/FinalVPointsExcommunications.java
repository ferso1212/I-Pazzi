package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;

/**
 * Used to store excommunications that forbid the player to get victory points at the end of the game from cards of a specific type.
 * @author fabri
 *
 */
public class FinalVPointsExcommunications extends Excommunication{
	
	private boolean noGreenPoints;
	private boolean noYellowPoints;
	private boolean noBluePoints;

	/**Activates the excommunication: its effects are stored in the player's Final Excommunications modifier.
	 */
	@Override
	public void activate(Player player) {
		if(noGreenPoints) player.getModifiers().getFinalMods().setNoGreenPoints(noGreenPoints);
		if(noYellowPoints) player.getModifiers().getFinalMods().setNoYellowPoints(noYellowPoints);
		if(noBluePoints) player.getModifiers().getFinalMods().setNoBluePoints(noBluePoints);
		
	}

	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString() {
		StringBuilder output= new StringBuilder();
		if(noGreenPoints) output.append("At the end of the game, you don't get victory points from Territory Cards. \n");
		if(noYellowPoints) output.append("At the end of the game, you don't get victory points from Building Cards. \n");
		if(noBluePoints) output.append("At the end of the game, you don't get victory points from Character Cards.");
		return output.toString();
	}
	
	/**Constructor
	 * 
	 * @param noGreenPoints if true, at the end of the game you don't get victory points from Territory Cards.
	 * @param noYellowPoints if true, at the end of the game you don't get victory points from Building Cards.
	 * @param noBluePoints if true, at the end of the game you don't get victory points from Character Cards.
	 */
	public FinalVPointsExcommunications(int id, int period, boolean noGreenPoints, boolean noYellowPoints, boolean noBluePoints) {
		super(id, period);
		this.noGreenPoints = noGreenPoints;
		this.noYellowPoints = noYellowPoints;
		this.noBluePoints = noBluePoints;
	}
	
	

}
