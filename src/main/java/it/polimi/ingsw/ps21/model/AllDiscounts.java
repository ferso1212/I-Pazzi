package it.polimi.ingsw.ps21.model;

public class AllDiscounts {
	private CardDiscount territoryDisc;
	private CardDiscount buildingDisc;
	private CardDiscount characterDisc;
	private CardDiscount ventureDisc;
	
	public CardDiscount getCardDiscount(DevelopmentCardType type) throws IllegalArgumentException
	{
		switch(type)
		{
		case BUILDING: return this.buildingDisc;
		case TERRITORY: return this.territoryDisc;
		case CHARACTER: return this.characterDisc;
		case VENTURE: return this.ventureDisc;
		default: throw new IllegalArgumentException();
		}
	}
	
}
