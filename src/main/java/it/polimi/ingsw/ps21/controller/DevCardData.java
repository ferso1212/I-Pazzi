package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class DevCardData implements Serializable {
	private String name;
	private ArrayList<Requirement> requirementsInOr;
	private int era;
	private SimultaneousEffectsSetData[] possiblePermanentEffects;
	private SimultaneousEffectsSetData[] possibleInstantEffects;
	private ImmProperties[] costsInOr;
	

}
