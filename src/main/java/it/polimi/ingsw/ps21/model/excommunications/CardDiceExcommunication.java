package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;

/**Class associated to the excommunication that reduces the action value when picking a card of a specific type.
 * @author fabri
 *
 */
public class CardDiceExcommunication extends Excommunication{
	private DevelopmentCardType cardType;
	private int diceMalus; //if it's a malus, this value must be positive!

	/**Activates the excommunication: its effects are stored in the player's dice modifiers.
	 */
	@Override
	public void activate(Player player) {
		player.getModifiers().getDiceMods().getDiceMod(cardType).setValue(- diceMalus);
	}
	
	/**
	 * Constructs the object.
	 * @param id the card id
	 * @param period the card period
	 * @param cardType the card type on which the malus should be applied.
	 * @param malus the value that will be added to the action value. If you want this to be a malus, this value must be positive.
	 */
	public CardDiceExcommunication(int id, int period, DevelopmentCardType cardType, int diceMalus) {
		super(id, period);
		this.cardType = cardType;
		this.diceMalus = diceMalus;
	}
	
	
	/**Returns a string that describes the excommunication
	 */
	@Override
	public String toString()
	{
		return "Each time you pick a " + this.cardType.toString() + " the action value gets reduced by " +this.diceMalus + " units.";
	}

	
	

		
	
}
