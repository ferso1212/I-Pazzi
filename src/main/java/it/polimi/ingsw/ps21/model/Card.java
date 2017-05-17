package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public abstract class Card {
	protected String name;
	protected ArrayList<Requirement> requires;
	
	public Card(String name, Requirement reqs[]){
		this.name = name;
		this.requires = new ArrayList<Requirement>();
		
	}
	
	public Requirement getRequirement(){
		return requires.a
	}
}
