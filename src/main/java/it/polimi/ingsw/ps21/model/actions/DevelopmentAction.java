package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.NoActivablePermanentEffectMessage;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;


public class DevelopmentAction extends Action {

	private FamilyMember famMember;
	private CostChoice costMessage;
	private EffectChoice effectMessage;
	private DevelopmentCardType tower;
	private int floor;
	private ArrayList<ExtraAction> extraActionFromInstantEffect = new ArrayList<ExtraAction>();
	private ArrayList<ExtraAction> extraActionFromPermanentEffect = new ArrayList<ExtraAction>();

	public DevelopmentAction(PlayerColor playerId, FamilyMember famMember, int possibleServants, DevelopmentCardType tower, int floor, int actionId) {
		super(playerId, actionId);
		this.famMember = famMember;
		this.possibleServants = possibleServants;
		this.tower = tower;
		this.floor = floor;
		this.updateCounter=2;
	}

	@Override
	public Message update(Player player, Match match) {

		SingleTowerSpace space = match.getBoard().getTower(this.tower).getTowerSpace(floor);

		switch (this.updateCounter) {
		case 2: {
			
			if ((player.getProperties().getPayableRequirementsAndCosts(space.getCard().getCosts()).isEmpty()) || ((match.getBoard().getTower(this.tower).isOccupied()) && (player.getProperties().getPayableRequirementsAndCosts(space.getCard().getOccupiedTowerCosts()).isEmpty()) )) return new RefusedAction(player.getId(), "You can't pay any of possible costs", actionId);
			if(!(space.isOccupable(player, famMember))) return new RefusedAction(player.getId(), "You can't place this family member in this space", actionId);
			try {
				if(player.getDeck().getCards(space.getCard().getCardType()).size()>=6) return new RefusedAction(player.getId(), "You have the max number of "+ space.getCard().getCardType().toString() +" cards", actionId);
			} catch (IllegalCardTypeException e) {
				return new RefusedAction(player.getId(), "Illegal Card Type", actionId);
			}
			if(!(player.checkCardRequirements(space.getCard()))) return new RefusedAction(player.getId(), "You don't meet card requirements", actionId);
			if (!((player.getFamily().getMemberValueWithServants(this.possibleServants, this.famMember.getColor())) + player.getModifiers().getDiceMods().getDiceMod(tower).getValue() >= space.getDiceRequirement())) return new RefusedAction(player.getId(), "Dice value of family member isn't enough", actionId) ;
			if (!(!famMember.isUsed())) return new RefusedAction(player.getId(), "This family member is already used", actionId);
			if (!(player.checkRequirement(player.getDeck().getAddingCardRequirement(space.getCard())))) return new RefusedAction(player.getId(), "You don't meet requirement for adding this card", actionId);
			
		    if ((!(match.getBoard().getTower(this.tower).isOccupied())) || ((player instanceof AdvancedPlayer) && ((AdvancedPlayer)player).getAdvMod().notPaysOccupiedTower()))
					this.costMessage = new CostChoice(player.getId(), player.getProperties().getPayableRequirementsAndCosts(space.getCard().getCosts()), actionId);
			else if ((match.getBoard().getTower(this.tower).isOccupied())){
					this.costMessage = new CostChoice(player.getId(), player.getProperties().getPayableRequirementsAndCosts(space.getCard().getOccupiedTowerCosts()), actionId);
			}
				this.updateCounter--;
				return this.costMessage;
		}
		case 1: {
				if (space.getCard().getCardType().equals(DevelopmentCardType.CHARACTER) && (this.costMessage.getChosen() != null)) {

					ArrayList<EffectSet> activableEffects = new ArrayList<EffectSet>();

					for (EffectSet e : space.getCard().getPossibleEffects()) {
						if (player.checkProperties(e.getTotalCost().sum(this.costMessage.getChosen()))) {
							activableEffects.add(e);
						}
					}
					if (activableEffects.size() != 0) {
						this.effectMessage = new EffectChoice(player.getId(), activableEffects.toArray(new EffectSet[0]), actionId);
						this.updateCounter--;
						return this.effectMessage;
					} else{
						this.updateCounter--;
						return new NoActivablePermanentEffectMessage(player.getId(), actionId);
					}
				} else {
					if (this.costMessage.getChosen() != null){
						this.updateCounter--;
						return new AcceptedAction(player.getId(), actionId);
					} else return new RefusedAction(player.getId(), "You heven't chosen a cost", actionId);
					
				}
			}
		
		case 0:
		{
			if (this.effectMessage.getEffectChosen() != null)
				return new AcceptedAction(player.getId(), actionId);
			else return new RefusedAction(player.getId(), "You haven't chosen an effect", actionId);
		}

		default:
			return new RefusedAction(player.getId(), "Unhandled case", actionId);
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException{
		
		super.payServants(player, this.possibleServants);
		
		SingleTowerSpace space = match.getBoard().getTower(this.tower).getTowerSpace(floor);

		if (!player.getFamily().useMember(famMember)) {
			throw new NotExecutableException(); //
		}
		
		if (!match.getBoard().placeMember(player, famMember, space)){
			throw new NotExecutableException();
		}

		if (!player.getModifiers().getActionMods().noPlacementBonus()) {
			player.getProperties().increaseProperties(space.getInstantBonus()); // Aggiungi le risorse dell'instant-bonus dello space, se è permesso
		}

		player.payCard(space.getCard().getCardType(), this.costMessage.getChosen()); // Player paga il costo della carta

		DevelopmentCard selectedCard = space.getCard();

		space.setCard(null); // rimuove carta dallo spazio-torre

		player.getDeck().addCard(selectedCard); // aggiunta della carta al deck del player

		this.extraActionFromInstantEffect = selectedCard.getInstantEffect().activate(player);
		
		if (selectedCard.getCardType().equals(DevelopmentCardType.CHARACTER)){
			this.extraActionFromPermanentEffect = this.effectMessage.getEffectChosen().activate(player);
		}
		
		
		
		this.extraActionFromInstantEffect.addAll(extraActionFromPermanentEffect);
		
		for (ExtraAction a : extraActionFromInstantEffect)
		{
			a.setActionId(actionId);
		}
		return this.extraActionFromInstantEffect.toArray(new ExtraAction[0]);
		}

}
