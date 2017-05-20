package it.polimi.ingsw.ps21.model;

public class CardsNumber {
	private int greenCardNum;
	private int blueCardNum;
	private int yellowCardNum;
	private int purpleCardNum;
	
	public CardsNumber(int green, int yellow, int blue, int purple){
		greenCardNum = green;
		blueCardNum = blue;
		yellowCardNum = yellow;
		purpleCardNum = purple;
	}

	public int getGreenCardNum() {
		return greenCardNum;
	}

	public int getBlueCardNum() {
		return blueCardNum;
	}

	public int getYellowCardNum() {
		return yellowCardNum;
	}

	public int getPurpleCardNum() {
		return purpleCardNum;
	}
	
	
}
