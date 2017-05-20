package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public abstract class Card {
	protected String name;

	protected ArrayList<Requirement> requires;
	protected Requirement chosenReq;	
	public Card(String name, Requirement req){
		this.name = name;
		this.requires = new ArrayList<Requirement>();
		chosenReq = req;
		requires.add(req);
	}
		
	public Card(String name, Requirement reqs[]){
		this.name = name;
		this.requires = new ArrayList<Requirement>();
		for (Requirement r: reqs)	{
			requires.add(r);
		}
	}
	
	public abstract Requirement getRequirement() throws Exception;
}
