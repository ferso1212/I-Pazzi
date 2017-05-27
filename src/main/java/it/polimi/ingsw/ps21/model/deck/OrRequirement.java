package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

public class OrRequirement {
	private ArrayList<Requirement> choices;
	private Requirement chosenRequirement;
	
	
	public OrRequirement(Requirement... reqs) {
		for(int i = 0; i< reqs.length; i++){
			choices.add(reqs[i]);
		}
		chosenRequirement = reqs[0];
	}
	
	public void addRequirement(Requirement newReq) {
		choices.add(newReq);
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
