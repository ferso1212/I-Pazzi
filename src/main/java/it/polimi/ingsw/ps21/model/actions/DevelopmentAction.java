package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
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

	private FamilyMember famMember;
	private CostChoice choosenCost;
	private EffectChoice effectChoice;
	private ArrayList<ExtraAction> extraActionFromInstantEffect = new ArrayList<ExtraAction>();
	private ArrayList<ExtraAction> extraActionFromPermanentEffect = new ArrayList<ExtraAction>();
	private DevelopmentCardType tower;
	private int floor;

	public DevelopmentAction(PlayerColor playerId, DevelopmentCardType tower, int floor, FamilyMember famMember) {
		super(playerId);
		this.tower = tower;
		this.floor = floor;
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
		
		SingleTowerSpace space = match.getBoard().getTower(this.tower).getTowerSpace(floor);

		if ((player.checkCardRequirements(space.getCard())) && (famMember.getValue() >= space.getDiceRequirement())
				&& (space.isOccupable(player, famMember)) && (!famMember.isUsed())
				&& (player.checkRequirement(player.getDeck().getAddingCardRequirement(space.getCard())))
				&& (player.getProperties().getPayableCosts(space.getCostsCard(match.getBoard().getTower(this.tower))).size() > 0)) {
			try {
				return new CostChoice(player.getId(), player.getProperties().getPayableCosts(space.getCostsCard(match.getBoard().getTower(this.tower))), player.getProperties().clone());
			} catch (CloneNotSupportedException e) {
				return new RefusedAction(player.getId());
			}
		}
		return new RefusedAction(player.getId());
	}

	/**
	 * Returns a boolean that specifies if the card has been correctly assigned
	 * to the player.
	 * 
	 * @return boolean indicating if the action has taken place correctly.
	 */
	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException,
			RequirementNotMetException, InsufficientPropsException {
		
		SingleTowerSpace space = match.getBoard().getTower(this.tower).getTowerSpace(floor);

		if (!player.getFamily().useMember(famMember)) {
			throw new NotExecutableException(); //
		}
		
		if (match.getBoard().placeMember(player, famMember, space)){
			throw new NotExecutableException();
		}

		if (!player.getModifiers().getActionMods().noPlacementBonus()) {
			player.getProperties().increaseProperties(space.getInstantBonus()); // Aggiungi le risorse dell'instant-bonus dello space, se è permesso
		}

		player.payCard(space.getCard().getCardType(), choosenCost.getChosen()); // Player paga il costo della carta

		DevelopmentCard selectedCard = space.getCard();

		space.setCard(null); // rimuove carta dallo spazio-torre

		player.getDeck().addCard(space.getCard()); // aggiunta della carta al deck del player

		this.extraActionFromInstantEffect = selectedCard.getInstantEffect().activate(player);
		
		if (selectedCard.getCardType().equals(DevelopmentCardType.CHARACTER)){
			this.extraActionFromPermanentEffect = effectChoice.getEffectChosen().activate(player);
		}
		
		this.extraActionFromInstantEffect.addAll(extraActionFromPermanentEffect);
		
		return this.extraActionFromInstantEffect.toArray(new ExtraAction[0]);
		}
	}