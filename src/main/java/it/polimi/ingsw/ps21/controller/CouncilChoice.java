package it.polimi.ingsw.ps21.controller;



import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


public class CouncilChoice extends Message {
	
	private int numberOfChoices;
	private ImmProperties[] privilegesChosen;
	private ImmProperties[] privilegesValues;
	
	public CouncilChoice(PlayerColor destination, int numberOfPrivileges) {
		super(destination);
		this.message="You have to choose a Council Privilege";
		this.numberOfChoices = numberOfPrivileges;
		this.privilegesChosen = new ImmProperties[numberOfChoices];
		MatchFactory matchFactory;
		matchFactory = MatchFactory.instance();
		this.privilegesValues=matchFactory.makePrivileges();

		
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
