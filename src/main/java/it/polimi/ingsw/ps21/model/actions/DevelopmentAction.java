package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;

/**
 * This class is used to allow the player to take a development card from the board
 * and add it to his cards. 
 **/
public class DevelopmentAction extends Action {
	
	protected SingleTowerSpace<DevelopmentCard> space;
	protected FamilyMember famMember;

	public DevelopmentAction(Match m, Player p, SingleTowerSpace<DevelopmentCard> space, FamilyMember famMember, Board board) {
	super(m, p);
	this.space = space;
	this.famMember = famMember;
	}

	/**Returns a boolean that specifies if player has got the necessary resources to take a specific development card and if the family member has the necessary value to occupies the relative space on the tower. 
	 * 
	 * @return boolean indicating whether the action is legal or not.
	 */
	@Override
	public boolean isLegal() {
		if ((player.checkRequirements(space.getcard().getRequirement())) && (famMember.getValue() >= space.getRequirement()) && ()){
			return true;
		}
		return false;
	}
	
	/**Returns a boolean that specifies if the card has been correctly assigned to the player. 
	 * 
	 * @return boolean indicating if the action has taken place correctly.
	 */
	@Override
	public boolean execute() {
		boolean controlAddCard;
		boolean controlPlaceMember;
		
		}
		
	}
	
	
	

}
