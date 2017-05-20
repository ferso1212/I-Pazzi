package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public class Deck {
	private SubDeck<TerritoryCard> greenCards;
	private SubDeck<BuildingCard> yellowCards;
	private SubDeck<CharacterCard> blueCards;
	private SubDeck<VentureCard> purpleCards;
	
	public Deck(String fileConfiguration){
		greenCards = new SubDeck<TerritoryCard>();
		yellowCards = new SubDeck<BuildingCard>();
		blueCards = new SubDeck<CharacterCard>();
		purpleCards = new SubDeck<VentureCard>();
	}
	
	public void shuffle(){
	
	}
	
}
