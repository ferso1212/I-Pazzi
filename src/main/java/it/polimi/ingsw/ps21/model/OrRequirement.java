package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public class OrRequirement extends Requirement {
	private ArrayList<Requirement> requirements;
	
	public OrRequirement(Requirement[] reqs){
		super(new CardsNumber(0,0,0,0),new ImmProperties(0,0,0));
		requirements = new ArrayList<Requirement>();
		for (Requirement r: reqs){
			requirements.add(r);
		}
	}
}
