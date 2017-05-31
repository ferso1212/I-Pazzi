package it.polimi.ingsw.ps21.model.deck;



public class Deck implements Cloneable {
	protected SubDeck<TerritoryCard> greenCards;
	protected SubDeck<BuildingCard> yellowCards;
	protected SubDeck<CharacterCard> blueCards;
	protected SubDeck<VentureCard> purpleCards;
	
	public Deck(){
		greenCards = new SubDeck<>();
		yellowCards = new SubDeck<>();
		blueCards = new SubDeck<>();
		purpleCards = new SubDeck<>();
	}
	

	public void addCard(Card card) throws IllegalCardException{
		if(card instanceof TerritoryCard) greenCards.addCard((TerritoryCard) card);
		else 
			if(card instanceof BuildingCard) yellowCards.addCard((BuildingCard) card);
			else 
				if(card instanceof CharacterCard) blueCards.addCard((CharacterCard) card);
				else 
					if(card instanceof VentureCard) purpleCards.addCard((VentureCard) card);
					else	throw new IllegalCardException();
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
	
	
	public DevelopmentCard getCard(int era, DevelopmentCardType type) throws IllegalCardException{
		switch(type){
		case BUILDING:
			return yellowCards.getCard(era);
		case CHARACTER:
			return blueCards.getCard(era);
		case TERRITORY:
			return greenCards.getCard(era);
		case VENTURE:
			return purpleCards.getCard(era);
		default:
			throw new IllegalCardException();
		}
	}
	
	public void shuffle(){
		blueCards.shuffle();
		greenCards.shuffle();
		purpleCards.shuffle();
		yellowCards.shuffle();
	}
	
	@Override
	public String toString(){
		StringBuilder temp = new StringBuilder();
		
		
		return temp.toString();
	}
	
	public boolean isEmpty(){
		return greenCards.isEmpty() && yellowCards.isEmpty() && blueCards.isEmpty() && purpleCards.isEmpty();
	}
	
}
