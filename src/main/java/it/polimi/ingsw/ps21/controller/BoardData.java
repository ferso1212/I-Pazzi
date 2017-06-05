package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.Tower;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;

public class BoardData implements Serializable{
	private DevelopmentCard[][] cards;
	private FamilyMemberData[][] towerSpaces;
	private ImmProperties[][] towerBonuses;
	private FamilyMemberData[] market;
	private int[] faithTrackBonus;
	private int[] militaryBonuses;
	private FamilyMemberData singleHarvestSpace;
	private FamilyMemberData singleProductionSpace;
	private FamilyMemberData[] multipleHarvestSpace;
	private FamilyMemberData[] multipleProductionSpace;
	private Map<DevelopmentCardType, int[]> cardsBonus;
	private int orangeDice;
	private int whiteDice;
	private int blackDice;
	
	public BoardData(Board board, int orangeDice, int blackDice, int whiteDice)
	{	//---COPIES THE CARDS IN THE TOWER SPACES
		int towerIndex=0;
		for(DevelopmentCardType cardType: DevelopmentCardType.values()) //cycles through the towers
		{
			Tower tower=board.getTower(cardType);
			for(int floor=0; floor<4; floor++) //cycles through the floors of each tower
			{
				this.cards[floor][towerIndex]=tower.getTowerSpace(floor + 1).getCard(); //tower.getTowerSpaces() counts the floors starting from 1 instead of 0
				this.towerSpaces[floor][towerIndex]=new FamilyMemberData(tower.getTowerSpace(floor + 1).getOccupant());
				this.towerBonuses[floor][towerIndex]=tower.getTowerSpace(floor+1).getInstantBonus();
			}
			towerIndex++;
		}
		//---
		
		//copies market places
		for(int i=0; i<board.getMarketPlaces().length; i++)
		{
			market[i]=new FamilyMemberData(board.getMarketSpace(i).getOccupant());
		}
		
		//copies faith track bonuses
		for(int i=0; i<board.getTrackBonuses().getFaithTrackSize(); i++)
		{
			faithTrackBonus[i]=board.getTrackBonuses().getFaithBonus(i);
		}
		
		//copies military track bonuses
		this.militaryBonuses=board.getTrackBonuses().getMilitaryBonuses();
		
		//copies harvest and production spaces
		this.singleHarvestSpace=new FamilyMemberData(board.getSingleWorkSpace(WorkType.HARVEST).getOccupant());
		this.singleProductionSpace=new FamilyMemberData(board.getSingleWorkSpace(WorkType.PRODUCTION).getOccupant());
		int i=0;
		for(FamilyMember occupant: board.getMultipleWorkSpace(WorkType.HARVEST).getOccupants())
		{
			multipleHarvestSpace[i]=new FamilyMemberData(occupant);
			i++;
		}
		this.singleProductionSpace=new FamilyMemberData(board.getSingleWorkSpace(WorkType.PRODUCTION).getOccupant());
		i=0;
		for(FamilyMember occupant: board.getMultipleWorkSpace(WorkType.PRODUCTION).getOccupants())
		{
			multipleProductionSpace[i]=new FamilyMemberData(occupant);
			i++;
		}
		
		this.cardsBonus=board.getCardBonus();
		this.orangeDice=orangeDice;
		this.blackDice=blackDice;
		this.whiteDice=whiteDice;
	}

	public DevelopmentCard[][] getCards() {
		return cards;
	}

	public FamilyMemberData[][] getTowerSpaces() {
		return towerSpaces;
	}

	public ImmProperties[][] getTowerBonuses() {
		return towerBonuses;
	}

	public FamilyMemberData[] getMarket() {
		return market;
	}

	public int[] getFaithTrackBonus() {
		return faithTrackBonus;
	}

	public int[] getMilitaryBonuses() {
		return militaryBonuses;
	}

	public FamilyMemberData getSingleHarvestSpace() {
		return singleHarvestSpace;
	}

	public FamilyMemberData getSingleProductionSpace() {
		return singleProductionSpace;
	}

	public FamilyMemberData[] getMultipleHarvestSpace() {
		return multipleHarvestSpace;
	}

	public FamilyMemberData[] getMultipleProductionSpace() {
		return multipleProductionSpace;
	}

	public Map<DevelopmentCardType, int[]> getCardsBonus() {
		return cardsBonus;
	}

	public int getOrangeDice() {
		return orangeDice;
	}

	public int getWhiteDice() {
		return whiteDice;
	}

	public int getBlackDice() {
		return blackDice;
	}
	
	
	
}
