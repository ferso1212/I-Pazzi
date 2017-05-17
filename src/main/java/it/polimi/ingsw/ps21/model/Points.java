package it.polimi.ingsw.ps21.model;

public class Points {
	private int victoryPoints;
	private int militaryPoints;
	private int faithPoints;
	
	public Points(int victory, int military, int faith){
		victoryPoints = victory;
		militaryPoints = military;
		faithPoints = faith;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public int getMilitaryPoints() {
		return militaryPoints;
	}

	public void setMilitaryPoints(int militaryPoints) {
		this.militaryPoints = militaryPoints;
	}

	public int getFaithPoints() {
		return faithPoints;
	}

	public void setFaithPoints(int faithPoints) {
		this.faithPoints = faithPoints;
	}

	
}
