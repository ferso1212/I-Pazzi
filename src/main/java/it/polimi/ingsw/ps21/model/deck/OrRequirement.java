package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

public class OrRequirement {
	private ArrayList<Requirement> choices;
	private Requirement chosenRequirement;
	
	
	public OrRequirement(Requirement... reqs) {
		for(int i = 0; i< reqs.length; i++){
			choices.add(reqs[i]);
		}
		if (reqs.length!=0) chosenRequirement = reqs[0];
	}
	
	public void addRequirement(Requirement newReq) {
		choices.add(newReq);
		if (choices.size()-1 == 0) chosenRequirement = newReq; //Set by default first requirement to default one
	}
	
	public Requirement[] getChoices(){
		return (Requirement[]) choices.toArray();
	}
	
	public void setChoice(Requirement chosenReq) throws IllegalChoiceException{
		if (!(choices.contains(chosenReq))) throw new IllegalChoiceException();	
		this.chosenRequirement = chosenReq;
	}
	
	public Requirement getRequirement(){
		return chosenRequirement;
	}
}
