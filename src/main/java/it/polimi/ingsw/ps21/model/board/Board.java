package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.player.MembersColor;

public class Board {
	
	
	private SingleTowerSpace<TerritoryCard> territoryTower;
	private SingleTowerSpace<BuildingCard> buildingTower;
	private SingleTowerSpace<CharacterCard> characterTower;
	private SingleTowerSpace<VentureCard> ventureTower;
	private int[] faithTrack;
	private int militaryBonus1;
	private int militaryBonus2;
	private Space[] marketPlaces;
	private Space singHarvPlace;
	private Space singProdPlace;
	private MultipleSpace multiHarvPlace;
	private MultipleSpace multiProdPlace;
	private MultipleSpace councilPalace;
	
	
	public board (){
		
	}
	
	public boolean addToCouncil(Player player, MembersColor member) {
		
	}
	

	

}
