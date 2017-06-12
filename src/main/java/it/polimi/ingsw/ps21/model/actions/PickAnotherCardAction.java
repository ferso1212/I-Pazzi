package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.PickAnotherCardMessage;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.board.Tower;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class PickAnotherCardAction extends ExtraAction {
	
	private int diceReq;
	private DevelopmentCardType types[];
	private PickAnotherCardMessage message;

	public PickAnotherCardAction(Player player, int diceReq, DevelopmentCardType...cardTypes){
		super(player.getId());
		this.diceReq = diceReq;
		if (cardTypes.length!=0) this.types = cardTypes;
		else this.types = DevelopmentCardType.values();
	}

	@Override
	public Message isLegal(Player player, Match match) {
		
		ArrayList<DevelopmentCard> cards = new ArrayList<DevelopmentCard>();
		for (DevelopmentCardType t : DevelopmentCardType.values()){
			for (SingleTowerSpace s : match.getBoard().getTower(t).getTower()){
				if((this.diceReq >= s.getDiceRequirement()) && (s.getCard()!=null) && (player.checkCardRequirements(s.getCard())) && (player.getProperties().getPayableCosts(s.getCard().getCosts()).size() > 0)){
					cards.add(s.getCard());
				}
			}
		}
		this.message = new PickAnotherCardMessage(player.getId(), cards.toArray(new DevelopmentCard[0]));
		return this.message;
	}

	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {
		
		ArrayList<ExtraAction> extraActionFromInstantEffect = new ArrayList<ExtraAction>();
		ArrayList<ExtraAction> extraActionFromPermanentEffect = new ArrayList<ExtraAction>();
		for (DevelopmentCardType t : DevelopmentCardType.values()){
			for (SingleTowerSpace s : match.getBoard().getTower(t).getTower()){
				if (this.message.getCardChosen() == s.getCard()){
					DevelopmentCard card = s.getCard();
					player.getDeck().addCard(s.getCard());
					s.setCard(null);
					extraActionFromInstantEffect = card.getInstantEffect().activate(player);
					if (card.getCardType().equals(DevelopmentCardType.CHARACTER)){
						this.extraActionFromPermanentEffect = effectChoice.getEffectChosen().activate(player);
					}
				}
			}
		}
	}
	
}
