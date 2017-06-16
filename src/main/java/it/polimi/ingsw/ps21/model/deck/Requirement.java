package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class Requirement implements Serializable{
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
	
	public String toString()
	{
		StringBuilder b= new StringBuilder();
		b.append("You need to have " + properties.toString());
		b.append("\n");
		b.append(cardsNumber.toString());
		return b.toString();
	}
}
