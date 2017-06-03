package it.polimi.ingsw.ps21.controller;

public class VaticanChoice extends Message{
	
	private boolean chosen;

	public VaticanChoice(String message, boolean chosen) {
		this.message = "You have to choose to support or not the church (don't do it, Satan is beatiful)";
		this.chosen = chosen;
	}

	public boolean getChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

}
