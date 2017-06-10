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

}
