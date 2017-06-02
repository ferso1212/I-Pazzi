package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

/**
 * This class is used to allow the player to take a development card from the
 * board and add it to his cards.
 **/
public class DevelopmentAction extends Action {

	private SingleTowerSpace space;
	private FamilyMember famMember;
	private CostChoice choosenCost;

	public DevelopmentAction(PlayerColor playerId, SingleTowerSpace space, FamilyMember famMember) {
		super(playerId);
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
	public Message isLegal(Player player, Match match) {

		if ((player.checkCardRequirements(space.getCard())) && (famMember.getValue() >= space.getDiceRequirement())
				&& space.isOccupable(player, famMember) && (!famMember.isUsed())
				&& (player.checkRequirement(player.getDeck().getAddingCardRequirement(space.getCard())))
				&& (player.getProperties().getPayableCosts(space.getCard().getCosts()).size() > 0)) {
			return new CostChoice(player.getProperties().getPayableCosts(space.getCard().getCosts()));
		}
		return new RefusedAction();		
	}

	/**
	 * Returns a boolean that specifies if the card has been correctly assigned
	 * to the player.
	 * 
	 * @return boolean indicating if the action has taken place correctly.
	 */
	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {

		match.getBoard().placeMember(player, famMember, space);

		if (!player.getFamily().useMember(famMember)) {
			throw new NotExecutableException(); //
		}

		if (!player.getModifiers().getActionMods().noPlacementBonus()) {
			player.getProperties().increaseProperties(space.getInstantBonus()); // Aggiungi
																				// le
																				// risorse
																				// dell'instant-bonus
																				// dello
																				// space,
																				// se
																				// è
																				// permesso
		}

		player.payCard(space.getCard().getCardType(), space.getCard().getCosts()[choosenCost]); // Player
																								// paga
																								// il
																								// costo
																								// della
																								// carta

		DevelopmentCard selectedCard = space.getCard();

		space.setCard(null); // rimuove carta dallo spazio-torre

		player.getDeck().addCard(space.getCard()); // aggiunta della carta al
													// deck del player, potrebbe

		try {
			selectedCard.getInstantEffect().activate(player);
		} catch (UnchosenException e) {
			System.out.println("Unchosen Requirement of Instant Effect");
		}

		switch (selectedCard.getCardType()) {
		case CHARACTER: {
			try {
				selectedCard.getPossibleEffects()[choosenCost].activate(player);
			} catch (UnchosenException e) {
				System.out.println("Unchosen Requirement of Permanent Effect");
			}
		}
			break;

		default:
			break;
		}

	}

}