package it.polimi.ingsw.ps21.model;

import java.util.EnumMap;

public class DiscountsSet extends Modifier {
 private EnumMap<DevelopmentCardType, CardDiscount> discounts;
	
	public CardDiscount getDiscount(DevelopmentCardType type)
	{
		return discounts.get(type);
	}
	
	
}
