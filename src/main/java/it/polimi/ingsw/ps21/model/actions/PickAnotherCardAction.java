package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.NoActivablePermanentEffectMessage;
import it.polimi.ingsw.ps21.controller.PickAnotherCardMessage;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public class PickAnotherCardAction extends ExtraAction {

	private int diceReq;
	private DevelopmentCardType types[];
	private PickAnotherCardMessage message;
	private CostChoice costMessage;
	private EffectChoice effectMessage;
	private ArrayList<ExtraAction> extraActionFromInstantEffect = new ArrayList<ExtraAction>();
	private ArrayList<ExtraAction> extraActionFromPermanentEffect = new ArrayList<ExtraAction>();

	public PickAnotherCardAction(Player player, int diceReq, DevelopmentCardType... cardTypes) {
		super(player.getId());
		this.diceReq = diceReq;
		if (cardTypes.length != 0)
			this.types = cardTypes;
		else
			this.types = DevelopmentCardType.values();
		this.updateCounter = 3;
		this.data = new ExtraActionData(this);
	}

	@Override
	public Message update(Player player, Match match) {
		
		switch (this.updateCounter) {
		case 3:{
			
			ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
			for (DevelopmentCardType t : this.types){
				for (SingleTowerSpace s : match.getBoard().getTower(t).getTower()){
					if((this.diceReq >= s.getDiceRequirement()) && (s.getCard()!=null) && (player.checkCardRequirements(s.getCard())) && (player.getProperties().getPayableRequirementsAndCosts(s.getCard().getCosts()).size() > 0)){
						cards.add(s.getCard());
					}
				}
			}
			if(cards.size() > 0){
				this.message = new PickAnotherCardMessage(player.getId(), cards.toArray(new DevelopmentCard[0]));
				this.updateCounter--;
				return this.message;
			} else return new RefusedAction(player.getId());
		}
		
		case 2:{
			if (this.message.getCardChosen() != null){
				this.costMessage = new CostChoice(player.getId(), player.getProperties().getPayableRequirementsAndCosts(this.message.getCardChosen().getCosts()));
				this.updateCounter--;
				return this.costMessage;
		}
			else return new RefusedAction(player.getId());
		}
		
		case 1:{
			
			if (this.message.getCardChosen().getCardType().equals(DevelopmentCardType.CHARACTER) && (this.costMessage.getChosen() != null)) {

				ArrayList<EffectSet> activableEffects = new ArrayList<EffectSet>();

				for (EffectSet e : this.message.getCardChosen().getPossibleEffects()) {
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
		
		case 0:{
			if(this.costMessage.getChosen() != null){
				return new AcceptedAction(player.getId());
			}	
			return new RefusedAction(player.getId());
		}
			

		default:
			return new RefusedAction(player.getId());
		}
		

	}

	@Override
	public ExtraAction[] activate(Player player, Match match) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException{

		SingleTowerSpace selectedSpace = new SingleTowerSpace();
		for (DevelopmentCardType t : this.types) {
			for (SingleTowerSpace s : match.getBoard().getTower(t).getTower()) {
				if (s.getCard() == this.message.getCardChosen()) {
					selectedSpace = s;
				}
			}
		}

		player.payCard(selectedSpace.getCard().getCardType(), this.costMessage.getChosen()); // Player paga il costo della carta

		selectedSpace.setCard(null); // rimuove carta dallo spazio-torre

		player.getDeck().addCard(this.message.getCardChosen()); // aggiunta della carta al
													// deck del player

		this.extraActionFromInstantEffect = this.message.getCardChosen().getInstantEffect().activate(player);

		if (this.message.getCardChosen().getCardType().equals(DevelopmentCardType.CHARACTER)) {
			this.extraActionFromPermanentEffect = this.effectMessage.getEffectChosen().activate(player);
		}

		this.extraActionFromInstantEffect.addAll(extraActionFromPermanentEffect);

		return this.extraActionFromInstantEffect.toArray(new ExtraAction[0]);
	}
	
	public DevelopmentCardType[] getTypes(){
		return this.types;
	}

	public int getDice(){
		return this.diceReq;
	}
}
