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
	protected ArrayList<Requirement> possibleRequirement;
	protected ArrayList<ImmProperties> possibleCost;
	
	/**
	 * Constructor for a card that provide only one cost and only one requirement
	 * @param name
	 * @param req
	 * @param cost
	 */
	public Card(String name, Requirement reqs[], ImmProperties costs[]){
		this.name = name;
		possibleRequirement = new ArrayList<>();
		for (Requirement r: reqs){
			possibleRequirement.add(r);
		}
		possibleCost = new ArrayList<>();
		for (ImmProperties i: costs){
			possibleCost.add(i);
		}
	}
	
	public Requirement[] getRequirement(){
		return (Requirement []) possibleRequirement.toArray();
	}
	
	public ImmProperties[] getCosts(){
		return (ImmProperties []) possibleCost.toArray();
	}
}
