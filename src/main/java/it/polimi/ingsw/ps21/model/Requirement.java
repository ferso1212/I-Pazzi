package it.polimi.ingsw.ps21.model;

public class Requirement {
	private CardsNumber cardsNumber;
	private ImmProperties properties;
	
	public Requirement(CardsNumber card, ImmProperties prop){
		cardsNumber = card;
		properties = prop;
		
	}
	
	public ImmProperties getProperties(){
		return this.properties;
	}
	
	public CardsNumber getCardsNumber(){
		return cardsNumber;
	}
	


}
