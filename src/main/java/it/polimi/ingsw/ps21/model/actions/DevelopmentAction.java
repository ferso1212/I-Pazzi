package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;

/**
 * This class is used to allow the player to take a development card from the
 * board and add it to his cards.
 **/
public class DevelopmentAction extends Action {

	protected SingleTowerSpace<DevelopmentCard> space;
	protected FamilyMember famMember;

	public DevelopmentAction(Match m, Player p, SingleTowerSpace<DevelopmentCard> space, FamilyMember famMember,
			Board board) {
		super(m, p);
		this.space = space;
		this.famMember = famMember;
	}

	/**
	 * Returns a boolean that specifies if player has got the necessary
	 * resources to take a specific development card and if the family member
	 * has the necessary value to occupies the relative space on the tower.
	 * 
	 * @return boolean indicating whether the action is legal or not.
	 */
	@Override
	public boolean isLegal() {
		if ((player.checkRequirements(space.getcard().getRequirement()))
				&& (famMember.getValue() >= space.getRequirement()) && space.isOccupable(famMember)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean that specifies if the card has been correctly assigned
	 * to the player.
	 * 
	 * @return boolean indicating if the action has taken place correctly.
	 */
	@Override
	public void execute() throws NotExecutableException, NotOccupableException, RequirementNotMetException { 
																					
		player.getDeck().addCard(space.getCard());   // aggiunta della carta al deck del player, potrebbe 
		
		space.occupy(famMember); // piazzare il familiare
		
		
		
		if (!player.getModifiers().getActionMods().noPlacementBonus()) {
			player.getProperties().increaseProperties(space.getInstantBonus()); // Aggiungi le risorse dell'istant-bonus dello space, se è permesso
		}
		
		player.payCard(space.getCard());    //Player paga il costo della carta
		
	}

}