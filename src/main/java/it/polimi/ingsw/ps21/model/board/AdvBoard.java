package it.polimi.ingsw.ps21.model.board;

import java.util.EnumMap;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class AdvBoard extends Board{
	
	private EnumMap<DevelopmentCardType, AdvTower> towers;
	private AdvSingleMarketSpace[] marketPlaces;
	private AdvSingleWorkSpace singleHarvPlace;
	private AdvSingleWorkSpace singleProdPlace;
	
	public AdvBoard(int playerNumber) throws ParserConfigurationException, BuildingDeckException {
		super(playerNumber);
		
		this.marketPlaces = new AdvSingleMarketSpace[playerNumber];
		this.singleHarvPlace = new AdvSingleWorkSpace( 1, new ImmProperties(0), WorkType.HARVEST);
		this.singleProdPlace = new AdvSingleWorkSpace(1, new ImmProperties(0), WorkType.PRODUCTION);
	}
	
	public void placeCards(int era, Deck deck) throws IllegalCardException {
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			AdvTower myTower = towers.get(type);
			for (int i = 1; i < 5; i++) {
				myTower.getTowerSpace(i).setCard(deck.getCard(era, type));
			}
		}
	}	
	
	public void removeCardsAndMembers() {
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			AdvTower myTower = towers.get(type);
			for (int i = 1; i < 5; i++) {
				myTower.getTowerSpace(i).reset();
			}
		}
	}
	
	public void newSetBoard(int era){
		this.removeCardsAndMembers();
			for (DevelopmentCardType type : DevelopmentCardType.values()){
			AdvTower t = this.towers.get(type);
			for (AdvSingleTowerSpace floor : t.getTower()){
				try{
					floor.setCard(developmentDeck.getCard(era, type));
				}catch (IllegalCardException e){
					//era sbagliata
				}
			}
		}
	}
		
	

}
