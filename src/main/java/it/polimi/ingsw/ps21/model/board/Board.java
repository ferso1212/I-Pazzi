package it.polimi.ingsw.ps21.model.board;

import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;


public class Board {
	
	
	protected EnumMap<DevelopmentCardType , Tower> towers;
	protected int[] faithTrack;
	protected int militaryBonus1;
	protected int militaryBonus2;
	protected Space[] marketPlaces;
	protected SingleSpace singleHarvPlace;
	protected SingleSpace singleProdPlace;
	protected MultipleSpace multipleHarvPlace;
	protected MultipleSpace multipleProdPlace;
	protected MultipleSpace councilPalace;
	protected int[] greenCardBonus;
	protected int[] yellowCardBonus;
	protected int[] blueCardBonus;
	protected int[] purpleCardBonus;
	
	
	
	public Board(EnumMap<DevelopmentCardType, Tower> towers, int[] faithTrack, int militaryBonus1, int militaryBonus2,
			Space[] marketPlaces, SingleSpace singleHarvPlace, SingleSpace singleProdPlace,
			MultipleSpace multipleHarvPlace, MultipleSpace multipleProdPlace, MultipleSpace councilPalace,
			int[] greenCardBonus, int[] yellowCardBonus, int[] blueCardBonus, int[] purpleCardBonus) {
		super();
		this.towers = towers;
		this.faithTrack = faithTrack;
		this.militaryBonus1 = militaryBonus1;
		this.militaryBonus2 = militaryBonus2;
		this.marketPlaces = marketPlaces;
		this.singleHarvPlace = singleHarvPlace;
		this.singleProdPlace = singleProdPlace;
		this.multipleHarvPlace = multipleHarvPlace;
		this.multipleProdPlace = multipleProdPlace;
		this.councilPalace = councilPalace;
		this.greenCardBonus = greenCardBonus;
		this.yellowCardBonus = yellowCardBonus;
		this.blueCardBonus = blueCardBonus;
		this.purpleCardBonus = purpleCardBonus;
	}



	public void addToCouncil(Player player, FamilyMember member) throws NotOccupableException{
		councilPalace.occupy(member);
	}
	
	public void placeCards (int era , Deck deck){
		for (DevelopmentCardType type : DevelopmentCardType.values()){
			Tower myTower = towers.get(type);
			for (int i=1; i<5; i++ ){
				myTower.getTowerSpace(i).setCard(deck.getCard(era , type));
			}
		}
	}
	
	public void removeCardsAndMembers (){
		for (DevelopmentCardType type : DevelopmentCardType.values()){
			Tower myTower = towers.get(type);
			for (int i=1; i<5; i++ ){
				myTower.getTowerSpace(i).setCard(null);
			}
		}
	}
	
	
	

	

}
