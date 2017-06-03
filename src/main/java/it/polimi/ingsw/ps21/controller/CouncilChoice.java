package it.polimi.ingsw.ps21.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilChoice extends Message {
	
	private int numberOfChoices;
	private ImmProperties[] privilegesChosen;
	private final ImmProperties[] privilegesValues;
	
	public CouncilChoice(int numberOfPrivileges) {
		this.message="You have to choose a Council Privilege";
		this.numberOfChoices = numberOfPrivileges;
		this.privilegesChosen = new ImmProperties[numberOfChoices];
		MatchFactory matchFactory;
		try {
			matchFactory = MatchFactory.instance();
			this.privilegesValues=matchFactory.makePrivileges();
		} catch (ParserConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
