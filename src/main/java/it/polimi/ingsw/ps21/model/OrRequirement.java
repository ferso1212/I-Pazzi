package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public class OrRequirement extends Requirement {
	private ArrayList<Requirement> choices;
	
	
	public OrRequirement(Requirement[] reqs) throws LoopRequirementException{
		super(null, null);
		for (Requirement r: reqs){
			if (r instanceof OrRequirement) throw new LoopRequirementException();
			choices.add(r);
		}
	}
	
	public void addPossibiliy(Requirement newReq) throws LoopRequirementException{
		if (newReq instanceof OrRequirement) throw new LoopRequirementException(); 
		choices.add(newReq);
	}
	
	public Requirement[] getChoices(){
		return (Requirement[]) choices.toArray();
	}
	
	public void setChoice(Requirement chosenReq) throws IllegalChoiceException{
		if (!(choices.contains(chosenReq))) throw new IllegalChoiceException();	
		this.properties = chosenReq.properties;
		this.cardsNumber = chosenReq.cardsNumber;
	}
}
