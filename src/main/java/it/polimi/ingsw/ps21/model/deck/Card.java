package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Class that implements an abstraction of a card
 * @author daniele
 *
 */
public abstract class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2129457007775569871L;
	protected String name;
	protected ArrayList<RequirementAndCost> possibleRequirement;
	
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
	public Card(String name, RequirementAndCost reqs[]){
		this.name = name;
		possibleRequirement = new ArrayList<>();
		if (reqs.length==0) possibleRequirement.add(new RequirementAndCost(new Requirement(new CardsNumber(0), new ImmProperties(0)), 
																		new ImmProperties(0)));
		else
			for (RequirementAndCost r: reqs) possibleRequirement.add(r);
			

	}
	
	public Card(String name, RequirementAndCost req){
		this.name = name;
		possibleRequirement = new ArrayList<>();
		possibleRequirement.add(req);
	}
	
	public Card(String name) {
		this.name = name;
		possibleRequirement = new ArrayList<>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public abstract Card clone();

	public RequirementAndCost[] getRequirements(){
		return possibleRequirement.toArray(new RequirementAndCost[0]);
	}

	@Override
	public String toString(){
		StringBuilder temp = new StringBuilder();
		temp.append("Card:\n-Name: " + this.name);
		temp.append("\n-Possible Requirement: ");
		for (RequirementAndCost r:possibleRequirement){
			temp.append("\n\t- Requirement: " + r.getRequirement().toString());
			temp.append("\n\t- Cost: " +  r.getCosts().toString());
		}		
		return temp.toString();
		
	}
}
