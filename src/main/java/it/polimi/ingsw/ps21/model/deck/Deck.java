package it.polimi.ingsw.ps21.model.deck;



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
		if(card instanceof TerritoryCard) greenCards.addCard((TerritoryCard) card);
		else if(card instanceof BuildingCard) yellowCards.addCard((BuildingCard) card);
			else if(card instanceof CharacterCard) blueCards.addCard((CharacterCard) card);
				else if(card instanceof VentureCard) purpleCards.addCard((VentureCard) card);
					else	throw new RuntimeException("Illegal Card");
	}
	
	public void setGreenDeck(SubDeck<TerritoryCard> greenDeck){
		greenCards = greenDeck;
		
	}
	
	public void setPurpleDeck(SubDeck<VentureCard> purpleDeck){
		purpleCards = purpleDeck;
	}
	
	public void setBlueDeck(SubDeck<CharacterCard> blueDeck){
		blueCards = blueDeck;
	
	}
	
	public void setYellowDeck(SubDeck<BuildingCard> yellowDeck){
		yellowCards = yellowDeck;
	}
	
	
	public DevelopmentCard getCard(int era, DevelopmentCardType type){
		switch(type){
		case BUILDING:
			return yellowCards.getCard(era);
		case CHARACTER:
			return blueCards.getCard(era);
		case TERRITORY:
			return greenCards.getCard(era);
		case VENTURE:
			purpleCards.getCard(era);
		default:
			throw new RuntimeException("Illegal Card Type");
		}
	}
	
	public void shuffle(){
		blueCards.shuffle();
		greenCards.shuffle();
		purpleCards.shuffle();
		yellowCards.shuffle();
	}
	
	public String toString(){
		StringBuilder temp = new StringBuilder();
		
		
		return temp.toString();
	}
	
	public boolean isEmpty(){
		return (greenCards.isEmpty() && yellowCards.isEmpty() && blueCards.isEmpty() && purpleCards.isEmpty());
	}
	
}
