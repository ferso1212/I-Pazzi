package it.polimi.ingsw.ps21.model;

public class Requirement {
	private CardsNumber cardsNumber;
	private ImmProperties properties;
	private ImmProperties cost;
	
	public Requirement(CardsNumber card, ImmProperties prop){
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
	
	public ImmProperties getCost()
	{
		return this.cost;
	}

}
