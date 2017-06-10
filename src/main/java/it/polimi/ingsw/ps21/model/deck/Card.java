package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
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
	 * This constructor is required by development card serialization
	 */
	public Card(){
		name = "";
		possibleRequirement = new ArrayList<>();
	}
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
	
	public Card(String name) {
		this.name = name;
	}
	
	public abstract Card clone();

	public Requirement[] getRequirements(){
		return possibleRequirement.toArray(new Requirement[0]);
	}

}
