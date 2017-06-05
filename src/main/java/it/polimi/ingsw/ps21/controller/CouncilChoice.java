package it.polimi.ingsw.ps21.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class CouncilChoice extends Message {
	
	private int numberOfChoices;
	private ImmProperties[] privilegesChosen;
	private ImmProperties[] privilegesValues;
	
	public CouncilChoice(int numberOfPrivileges) {
		this.message="You have to choose a Council Privilege";
		this.numberOfChoices = numberOfPrivileges;
		this.privilegesChosen = new ImmProperties[numberOfChoices];
		MatchFactory matchFactory;
		try {
			matchFactory = MatchFactory.instance();
			this.privilegesValues=matchFactory.makePrivileges();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			this.privilegesValues = new ImmProperties[4];
			privilegesValues[0] = new ImmProperties(new Property(PropertiesId.COINS, 2));
			privilegesValues[1] = new ImmProperties(new Property(PropertiesId.STONES, 1), new Property(PropertiesId.WOOD, 1));
			privilegesValues[2] = new ImmProperties(new Property(PropertiesId.SERVANTS, 3));
			privilegesValues[3] = new ImmProperties(new Property(PropertiesId.FAITHPOINTS, 1));
			
		}

		
	}

	public ImmProperties[] getPrivilegesChosen() {
		return privilegesChosen;
	}

	public void setPrivilegesChosen(ImmProperties[] privilegesChosen) {
		this.privilegesChosen = privilegesChosen;
	}

	public int getNumberOfChoices() {
		return numberOfChoices;
	}

	public ImmProperties[] getPrivilegesValues() {
		return privilegesValues;
	}

	

}
