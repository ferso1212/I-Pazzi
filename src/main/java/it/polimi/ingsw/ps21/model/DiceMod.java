package it.polimi.ingsw.ps21.model;

public class DiceMod {
	
	private DevelopmentCardType cardType;
	private int value;

	public DiceMod(DevelopmentCardType type)
	{
		this.cardType=type;
		this.value=0;
	}
	
	public DevelopmentCardType getType()
	{
		return this.cardType;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
