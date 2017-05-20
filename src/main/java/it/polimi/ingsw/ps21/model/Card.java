package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public abstract class Card {
	protected String name;

	protected ArrayList<Requirements> requires;
	protected Requirements chosenReq;	
	public Card(String name, Requirements req){
		this.name = name;
		this.requires = new ArrayList<Requirements>();
		chosenReq = req;
		requires.add(req);
		
	public Card(String name, Requirements reqs[]){
		this.name = name;
		this.requires = new ArrayList<Requirements>();
		for (Requirements r: reqs)	{
			requires.add(r);
		}
	}
	
	public abstract Requirements getRequirement();
}
