package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public class Deck {
	protected SubDeck<TerritoryCard> greenCards;
	protected SubDeck<BuildingCard> yellowCards;
	protected SubDeck<CharacterCard> blueCards;
	protected SubDeck<VentureCard> purpleCards;
	
	public Deck(){
		greenCards = new SubDeck<TerritoryCard>();
		yellowCards = new SubDeck<BuildingCard>();
		blueCards = new SubDeck<CharacterCard>();
		purpleCards = new SubDeck<VentureCard>();
	}
	

	public void addCard(Card card) throws Exception{
		if(card instanceof TerritoryCard) greenCards.addCard((TerritoryCard) card, ((DevelopmentCard) card).getEra());
		if(card instanceof BuildingCard) yellowCards.addCard((BuildingCard) card, ((DevelopmentCard) card).getEra());
		if(card instanceof CharacterCard) blueCards.addCard((CharacterCard) card, ((DevelopmentCard) card).getEra());
		if(card instanceof VentureCard) purpleCards.addCard((VentureCard) card, ((DevelopmentCard) card).getEra());
		throw new RuntimeException("Illegal Card");
	}
	
	public void shuffle(){
	
	}
	
	
	
}
