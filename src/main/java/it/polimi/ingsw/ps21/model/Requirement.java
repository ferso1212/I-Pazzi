package it.polimi.ingsw.ps21.model;

public class Requirement {
	private CardNumber cardNumber;
	private ImmProperties properties;
	
	public Requirement(CardNumber card, ImmProperties prop){
		cardNumber = card;
		properties = prop;
		
	}
	
	public Resources getResources(){
		return properties.getResources();
	}
	
	public Points getPoints(){
		return properties.getPoints();
	}
	
	public CardNumber getCardNumber(){
		return cardNumber;
	}

}
