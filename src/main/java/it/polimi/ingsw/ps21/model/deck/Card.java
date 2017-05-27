package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;
/**
 * Class that implements an abstraction of a card
 * @author daniele
 *
 */
public abstract class Card {
	protected String name;
	protected OrRequirement requirement;
	protected OrCosts cost;
	
	/**
	 * Constructor for a card that provide only one cost and only one requirement
	 * @param name
	 * @param req
	 * @param cost
	 */
	public Card(String name, OrRequirement req, OrCosts cost){
		this.name = name;
		this.requirement = req;
		this.cost = cost;
		
	}
	
	public OrRequirement getRequirement(){
		return requirement;
	}
	
	public OrCosts getCosts(){
		return cost;
	}
}
