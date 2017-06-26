package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class RequirementAndCost implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6731044968714319518L;
	private Requirement requirement;
	protected ImmProperties costs;
	
	public RequirementAndCost(Requirement requirement, ImmProperties costs) {
		super();
		this.requirement = requirement;
		this.costs = costs;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public ImmProperties getCosts() {
		return costs;
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		String req= requirement.toString();
		if(req.isEmpty()) b.append("\nRequirements: No requirements");
		else b.append("\n-Requirements: " + req);
		String c = costs.toString();
		if(c.isEmpty()) b.append("\n-Costs: No costs.");
		else b.append("\n-Costs: " + c);
		return b.toString();
	}
	
	

}
