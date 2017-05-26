package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;
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
	public Card(String name, Requirement req, ImmProperties cost){
		this.name = name;
		this.requires = new ArrayList<Requirement>();
		chosenReq = req;
		requires.add(req);
	}
	/**
	 * Constructor for card that provide more than one requirement, one of them will be choosed by player
	 * @param name
	 * @param reqs
	 * @param cost
	 */
	public Card(String name, Requirement reqs[], ImmProperties cost){
		this.name = name;
		this.requires = new ArrayList<Requirement>();
		for (Requirement r: reqs)	{
			requires.add(r);
		}
		this.cost = cost;
	}
	
	public abstract Requirement getRequirement() throws Exception;
	
	
}
