package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Map;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;

public class WorkMessage extends Message{
	
	private ArrayList<DevelopmentCard> choices;
	private int[] chosenCardsAndEffects;
	private PlayerProperties clonedPlayerProperties;
		
	public WorkMessage(String message, ArrayList<DevelopmentCard> choices, PlayerProperties clonedPlayerProperties) {
		super(message);
		this.choices = choices;
		this.clonedPlayerProperties = clonedPlayerProperties;
	}
	
	public int[] getChosenCardsAndEffects() {
		return chosenCardsAndEffects;
	}
	
	public boolean setChosenCardsAndEffects(int[] playerChoices) {
		for (int i=0; i<playerChoices.length; i++){
			PlayerProperties totalCosts = null;
			if(chosenCardsAndEffects[i] != 0){
				totalCosts = totalCostes.increaseProperties(choices.get(i).getPossibleEffects()[chosenCardsAndEffects[i]]);
			}
		}
		if clonedPlayerProperties.greaterOrEqual(totalCosts){
			this.chosenCardsAndEffects = playerChoices;
			return true;
		}
		return false;
	}
	
	public Map<DevelopmentCard, Effect[]> getChoices() {
		return choices;
	}
	
	
	

}
