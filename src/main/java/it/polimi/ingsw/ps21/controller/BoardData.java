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
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.FamilyMember;

public class BoardData implements Serializable{
	private DevelopmentCard[][] cards;
	private FamilyMemberData[][] towerSpaces;
	private int[][] towerRequirements;
	private ImmProperties[][] towerBonuses;
	private FamilyMemberData[] market;
	private ImmProperties[] marketBonuses;
	private int[] marketPrivileges;
	private int[] faithTrackBonus;
	private int[] militaryBonuses;
	private FamilyMemberData singleHarvestSpace;
	private FamilyMemberData singleProductionSpace;
	private FamilyMemberData[] multipleHarvestSpace;
	private FamilyMemberData[] multipleProductionSpace;
	private Map<DevelopmentCardType, int[]> cardsBonus;
	private Excommunication[] excommunications;

	
	public BoardData(Board board)
	{	//---COPIES THE CARDS IN THE TOWER SPACES
		this.cards = new DevelopmentCard[4][4];
		this.excommunications = board.getExcommunications();
		this.towerSpaces=new FamilyMemberData[board.getTower(DevelopmentCardType.BUILDING).FLOORS_NUM][DevelopmentCardType.values().length];
		this.towerBonuses=new ImmProperties[board.getTower(DevelopmentCardType.BUILDING).FLOORS_NUM][DevelopmentCardType.values().length];
		this.towerRequirements = new int[board.getTower(DevelopmentCardType.BUILDING).FLOORS_NUM][DevelopmentCardType.values().length];
		int towerIndex=0;
		for(DevelopmentCardType cardType: DevelopmentCardType.values()) //cycles through the towers
		{
			Tower tower=board.getTower(cardType);
			for(int floor=0; floor<4; floor++) //cycles through the floors of each tower
			{
				this.cards[floor][towerIndex]=tower.getTowerSpace(floor).getCard(); 
				this.towerSpaces[floor][towerIndex]=new FamilyMemberData(tower.getTowerSpace(floor).getOccupant());
				this.towerBonuses[floor][towerIndex]=tower.getTowerSpace(floor).getInstantBonus();
				this.towerRequirements[floor][towerIndex] = tower.getTowerSpace(floor).getDiceRequirement();
			}
			towerIndex++;
		}
		//---
		
		//copies market places
		market= new FamilyMemberData[board.getMarketPlaces().length];
		marketBonuses = new ImmProperties[board.getMarketPlaces().length];
		marketPrivileges = new int[board.getMarketPlaces().length];
		for(int i=0; i<board.getMarketPlaces().length; i++)
		{
			market[i]=new FamilyMemberData(board.getMarketSpace(i).getOccupant());
			marketBonuses[i] = board.getMarketPlaces()[i].getInstantBonus();
			marketPrivileges[i] = board.getMarketPlaces()[i].getNumberOfPrivileges();
		}
		
		//copies faith track bonuses
		this.faithTrackBonus= new int[board.getTrackBonuses().getFaithTrackSize()];
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
		this.multipleHarvestSpace= new FamilyMemberData[board.getMultipleWorkSpace(WorkType.HARVEST).getOccupants().size()];
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

	
	public ImmProperties[] getMarketBonuses() {
		return marketBonuses;
	}

	public int[] getMarketPrivileges() {
		return marketPrivileges;
	}
	
	public int[][] getTowerRequirements(){
		return this.towerRequirements;
	}
	
	public Excommunication[] getExcommunications(){
		return this.excommunications;
		
	}
	
}
