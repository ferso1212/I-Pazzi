package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.AdvSingleMarketSpace;
import it.polimi.ingsw.ps21.model.board.AdvSingleWorkSpace;
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
	private MarketOccupants[] marketOccupants;
	private ImmProperties[] marketBonuses;
	private int[] marketPrivileges;
	private int[] faithTrackBonus;
	private int[] militaryBonuses;
	private FamilyMemberData singleHarvestSpaceOccupant;
	private FamilyMemberData singleProductionSpaceOccupant;
	private FamilyMemberData singleHarvestSpaceOtherOccupant;
	private FamilyMemberData singleProductionSpaceOtherOccupant;
	private FamilyMemberData[] multipleHarvestSpace;
	private FamilyMemberData[] multipleProductionSpace;
	private FamilyMemberData[] council;
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
		marketOccupants= new MarketOccupants[board.getMarketPlaces().length];
		marketBonuses = new ImmProperties[board.getMarketPlaces().length];
		marketPrivileges = new int[board.getMarketPlaces().length];
		for(int i=0; i<board.getMarketPlaces().length; i++)
		{
			if(board.isAdvanced()) marketOccupants[i]=new MarketOccupants(new FamilyMemberData(board.getMarketSpace(i).getOccupant()), new FamilyMemberData(((AdvSingleMarketSpace)board.getMarketSpace(i)).getOtherOccupant()));
			else marketOccupants[i]=new MarketOccupants(new FamilyMemberData(board.getMarketSpace(i).getOccupant()), null);
			marketBonuses[i] = board.getMarketPlaces()[i].getInstantBonus();
			marketPrivileges[i] = board.getMarketPlaces()[i].getNumberOfPrivileges();
		}
		
		//copies member in council palace
		this.council = new FamilyMemberData[board.getCouncilPalace().getOccupants().size()];
		FamilyMember[] occupants = board.getCouncilPalace().getOccupants().toArray(new FamilyMember[0]);
		for (int i=0; i< council.length; i++){
			council[i] = new FamilyMemberData(occupants[i]);
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
		if(board.isAdvanced())
		{
			this.singleHarvestSpaceOtherOccupant=new FamilyMemberData(((AdvSingleWorkSpace)board.getSingleWorkSpace(WorkType.HARVEST)).getOtherOccupant());
			this.singleProductionSpaceOtherOccupant=new FamilyMemberData(((AdvSingleWorkSpace)board.getSingleWorkSpace(WorkType.PRODUCTION)).getOtherOccupant());

		}
		this.singleHarvestSpaceOccupant=new FamilyMemberData(board.getSingleWorkSpace(WorkType.HARVEST).getOccupant());
		this.singleProductionSpaceOccupant=new FamilyMemberData(board.getSingleWorkSpace(WorkType.PRODUCTION).getOccupant());
		
		this.multipleHarvestSpace= new FamilyMemberData[board.getMultipleWorkSpace(WorkType.HARVEST).getOccupants().size()];
		int i=0;
		for(FamilyMember occupant: board.getMultipleWorkSpace(WorkType.HARVEST).getOccupants())
		{
			multipleHarvestSpace[i]=new FamilyMemberData(occupant);
			i++;
		}
		
		this.multipleProductionSpace= new FamilyMemberData[board.getMultipleWorkSpace(WorkType.PRODUCTION).getOccupants().size()];
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

	public MarketOccupants[] getMarket() {
		return marketOccupants;
	}

	public int[] getFaithTrackBonus() {
		return faithTrackBonus;
	}

	public int[] getMilitaryBonuses() {
		return militaryBonuses;
	}

	public FamilyMemberData getSingleHarvestSpace() {
		return singleHarvestSpaceOccupant;
	}

	public FamilyMemberData getSingleProductionSpace() {
		return singleProductionSpaceOccupant;
	}
	
	public FamilyMemberData getSingleHarvestSpaceOtherOccupant() {
		return singleHarvestSpaceOtherOccupant;
	}

	public FamilyMemberData getSingleProductionSpaceOtherOccupant() {
		return singleProductionSpaceOtherOccupant;
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
	
	public FamilyMemberData[] getCouncilOccupants(){
		return this.council;
	}
	
}
