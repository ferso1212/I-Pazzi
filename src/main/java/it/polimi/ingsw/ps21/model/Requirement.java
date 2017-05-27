package it.polimi.ingsw.ps21.model;

public class Requirement {
	protected CardsNumber cardsNumber;
	protected ImmProperties properties;
	
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
