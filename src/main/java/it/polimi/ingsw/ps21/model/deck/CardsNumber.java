package it.polimi.ingsw.ps21.model.deck;

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
	
	public int getCardsNumReq(DevelopmentCardType type) {
		switch (type) {
		case TERRITORY: return this.greenCardNum;
		case BUILDING: return this.yellowCardNum;
		case VENTURE: return this.purpleCardNum;
		case CHARACTER: return this.blueCardNum;
		default: return 0;
			
		}
	}
	
}
