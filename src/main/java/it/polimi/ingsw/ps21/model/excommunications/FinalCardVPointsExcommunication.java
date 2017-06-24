package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.Player;

/**
 * Used to store excommunications that forbid the player to get victory points at the end of the game from cards of a specific type.
 * @author fabri
 *
 */
public class FinalCardVPointsExcommunication extends Excommunication{
	
	private DevelopmentCardType cardType;

	/**Activates the excommunication: its effects are stored in the player's Final Excommunications modifier.
	 */
	@Override
	public void activate(Player player) {
		player.getModifiers().getFinalMods().setCardsNumBonus(cardType, false);

	}

	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString() {
		
		return "At the end of the game, you don't get victory points from the number of " + cardType.toString() + " you have. \n";
		
		
	}
	
	/**Constructor
	 * 
	 * @param id an integer that univocally identifies the excommunication tile
	 * @param period if placed on the board, the excommunication is evaluated in this period
	 * @param cardType if!=VENTURE, at the end of the game this excommunication will prevent the player from getting the final victory points bonuses from the number of cards of that type he has.
	 * else at the end of the game this excommunication will prevent the player from getting the final victory points bonuses from the venture cards' permanent effect.
	 */
	public FinalCardVPointsExcommunication(int id, int period, DevelopmentCardType cardType) {
		super(id, period);
		this.cardType=cardType;
		
	}
	
	

}
