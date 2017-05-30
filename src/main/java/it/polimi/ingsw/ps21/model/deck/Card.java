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
	
	/**
	 * Constructor for a card that provide only one cost and only one requirement
	 * @param name
	 * @param req
	 * @param cost
	 */
	public Card(String name, Requirement reqs[]){
		this.name = name;
		possibleRequirement = new ArrayList<>();
		for (Requirement r: reqs){
			possibleRequirement.add(r);
		}
	}
	
	public Card(String name, Requirement req) {
		this.name = name;
		possibleRequirement = new ArrayList<>();
		possibleRequirement.add(req);
	}

	public Requirement[] getRequirements(){
		return (Requirement []) possibleRequirement.toArray();
	}

}
