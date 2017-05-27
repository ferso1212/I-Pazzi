package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class OrCosts {
	
	private ArrayList<ImmProperties> choices;
	private ImmProperties chosenCost;
	
	/**
	 * Class can be constructed with an arbitrary number of costs, but the first one is chosen by default
	 * unless setChoice 
	 * @param costs
	 */
	
	public OrCost(ImmProperties... costs){
		chosenCost = costs[0];
		choices = new ArrayList<ImmProperties>();
		for(ImmProperties i: costs)	{
			choices.add(i);
		}
	}
	
	public ImmProperties[] getChoices(){
		return (ImmProperties[]) choices.toArray();
	}
	
	public void addChoice(ImmProperties newCost){
		choices.add(newCost);
	}
	
	public void setChoice(ImmProperties chosen) throws IllegalChoiceException{
		if (!(choices.contains(chosen))) throw new IllegalChoiceException();
		else chosenCost = chosen;
	}
	
	public ImmProperties getCost(){
		return chosenCost
	}

}
