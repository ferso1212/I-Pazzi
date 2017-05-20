package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public abstract class Card {
	protected String name;
	protected ArrayList<Requirements> requires;
	
	public Card(String name, Requirements reqs[]){
		this.name = name;
		this.requires = new ArrayList<Requirements>();
		
	}
	
	public Requirements getRequirement(){
		return requires.a
	}
}
