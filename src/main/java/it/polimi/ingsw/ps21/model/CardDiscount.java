package it.polimi.ingsw.ps21.model;

public class CardDiscount {
	private int diceDiscount;
	private PropertiesSet propDiscounts;
	
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
	public PropertiesSet getPropertiesDisc() {
		return this.propDiscounts;
	}
	/**
	 * @return the pointsDiscount
	 */
	
	
	public CardDiscount()
	{
		this.diceDiscount=0;
		this.propDiscounts=new PropertiesSet();
	}
}
