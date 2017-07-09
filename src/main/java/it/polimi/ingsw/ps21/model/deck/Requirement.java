package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class Requirement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5844689299069254646L;
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
		String req= properties.toString();
		if (!req.isEmpty()) b.append("You need to have " + req);
		b.append("\n" + cardsNumber.toString());
		return b.toString();
	}
}
