package it.polimi.ingsw.ps21.model;

public class CardDiscount {
	private int diceDiscount;
	private Resources resDiscount;
	private Points pointsDiscount;
	/**
	 * @return the diceDiscount
	 */
	public int getDiceDiscount() {
		return diceDiscount;
	}
	/**
	 * @param diceDiscount the diceDiscount to set
	 */
	public void setDiceDiscount(int diceDiscount) {
		this.diceDiscount = diceDiscount;
	}
	/**
	 * @return the resDiscount
	 */
	public Resources getResDiscount() {
		return resDiscount;
	}
	/**
	 * @return the pointsDiscount
	 */
	public Points getPointsDiscount() {
		return pointsDiscount;
	}
	
	public CardDiscount()
	{
		this.diceDiscount=0;
		this.resDiscount=new Resources(0,0,0,0);
		this.pointsDiscount= new Points(0,0,0);
	}
}
