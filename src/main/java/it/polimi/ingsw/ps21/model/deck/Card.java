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
	protected ArrayList<Requirement> requires;
	protected Requirement chosenReq;	
	protected ImmProperties cost;
	
	/**
	 * Constructor for a card that provide only one cost and only one requirement
	 * @param name
	 * @param req
	 * @param cost
	 */
	public Card(String name, Requirement... reqs, ImmProperties... costs){
		this.name = name;
		this.requires = new ArrayList<Requirement>();
		
	}
	
	public abstract Requirement getRequirement() throws Exception;
	
}
