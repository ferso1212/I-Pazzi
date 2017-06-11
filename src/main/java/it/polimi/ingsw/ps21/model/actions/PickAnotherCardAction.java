package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.SingleTowerSpace;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class PickAnotherCardAction extends ExtraAction {
	private int diceReq;
	private DevelopmentCardType types[];

	public PickAnotherCardAction(Player player, int diceReq, DevelopmentCardType...cardTypes){
		super(player.getId());
		this.diceReq = diceReq;
		if (cardTypes.length!=0) this.types = cardTypes;
		else this.types = DevelopmentCardType.values();
	}

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

	@Override
	public ExtraAction[] execute(Player player, Match match) throws NotExecutableException, NotOccupableException,
			RequirementNotMetException, InsufficientPropsException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
