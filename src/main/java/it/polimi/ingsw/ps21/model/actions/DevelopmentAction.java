package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
s
/**
 * This class is used to allow the player to take a development card from the
 * board and add it to his cards.
 **/
public class DevelopmentAction extends Action {

	private SingleTowerSpace space;
	private FamilyMember famMember;
	private int choosenCost;

	public DevelopmentAction(Match match, Player player, SingleTowerSpace space, FamilyMember famMember,
			Board board) {
		super(match, player);
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
		
		if ((player.checkCardRequirements(space.getCard()))
				&& (famMember.getValue() >= space.getDiceRequirement()) && space.isOccupable(player, famMember)
				&& (!famMember.isUsed())) {
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
<<<<<<< HEAD
	public void execute() throws NotExecutableException, NotOccupableException, RequirementNotMetException,  InsufficientPropsException {

=======
	public void execute() throws NotExecutableException, NotOccupableException, RequirementNotMetException {
		// Choose cost 
>>>>>>> 428018f1b88943539cb5c463688ecfb86224f584
		player.getDeck().addCard(space.getCard()); // aggiunta della carta al
													// deck del player, potrebbe

		this.match.getBoard().placeMember(player, famMember, space);

		if (!player.getFamily().useMember(famMember)) {
			throw new NotExecutableException(); //
		}

		if (!player.getModifiers().getActionMods().noPlacementBonus()) {
			player.getProperties().increaseProperties(space.getInstantBonus()); // Aggiungi le risorse dell'instant-bonus dello space, se è permesso
		}

		player.payCard(space.getCard().getCardType(), space.getCard().getCosts()[choosenCost]); // Player paga il costo della carta

	}

}