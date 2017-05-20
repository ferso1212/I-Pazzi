package it.polimi.ingsw.ps21.model;

public class Requirements {
	private CardsNumber cardsNumber;
	private ImmProperties properties;
	
	public Requirements(CardsNumber card, ImmProperties prop){
		cardsNumber = card;
		properties = prop;
		
	}
	
	public Resources getResources(){
		return properties.getResources();
	}
	
	public Points getPoints(){
		return properties.getPoints();
	}
	
	public CardsNumber getCardsNumber(){
		return cardsNumber;
	}

}
