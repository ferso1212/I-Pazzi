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
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


public class DevelopmentAction extends Action {

	private FamilyMember famMember;
	private CostChoice costMessage;
	private EffectChoice effectMessage;
	private DevelopmentCardType tower;
	private int floor;
	private ArrayList<ExtraAction> extraActionFromInstantEffect = new ArrayList<ExtraAction>();
	private ArrayList<ExtraAction> extraActionFromPermanentEffect = new ArrayList<ExtraAction>();

	public DevelopmentAction(PlayerColor playerId, FamilyMember famMember, DevelopmentCardType tower, int floor) {
		super(playerId);
		this.famMember = famMember;
		this.tower = tower;
		this.floor = floor;
		this.updateCounter=2;
	}

	@Override
	public Message update(Player player, Match match) {

		SingleTowerSpace space = match.getBoard().getTower(this.tower).getTowerSpace(floor);

		switch (this.updateCounter) {
		case 2: {
			if (( (space.isOccupable(player, famMember)) && player.checkCardRequirements(space.getCard())) 
					&& (famMember.getValue() >= space.getDiceRequirement())	&& (!famMember.isUsed())
					&& (player.checkRequirement(player.getDeck().getAddingCardRequirement(space.getCard())))
					&& (player.getProperties().getPayableRequirementsAndCosts(space.getCard().getCosts()).size() > 0)) {
				this.costMessage = new CostChoice(player.getId(), player.getProperties().getPayableRequirementsAndCosts(space.getCard().getCosts()));
				this.updateCounter--;
				return this.costMessage;
			} else
				return new RefusedAction(player.getId());
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
						this.effectMessage = new EffectChoice(player.getId(), activableEffects.toArray(new EffectSet[0]));
						this.updateCounter--;
						return this.effectMessage;
					} else{
						this.updateCounter--;
						return new NoActivablePermanentEffectMessage(player.getId());
					}
				} else {
					if (this.costMessage.getChosen() != null){
						this.updateCounter--;
						return new AcceptedAction(player.getId());
					} else return new RefusedAction(player.getId());
					
				}
			}
		
		case 0:
		{
			if (this.effectMessage.getEffectChosen() != null)
				return new AcceptedAction(player.getId());
			else return new RefusedAction(player.getId());
		}

		default:
			return new RefusedAction(player.getId());
		}
	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException{
		
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
		
		if (selectedCard.getCardType().equals(DevelopmentCardType.VENTURE)){
			this.extraActionFromPermanentEffect = this.effectMessage.getEffectChosen().activate(player);
		}
		
		this.extraActionFromInstantEffect.addAll(extraActionFromPermanentEffect);
		
		return this.extraActionFromInstantEffect.toArray(new ExtraAction[0]);
		}

}
