package it.polimi.ingsw.ps21.model.player;

import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.DevelopmentCardType;

public class DiscountsSet {
 private EnumMap<DevelopmentCardType, CardDiscount> discounts;
	
	public CardDiscount getDiscount(DevelopmentCardType type)
	{
		return discounts.get(type);
	}
	
	public DiscountsSet()
	{
		this.discounts=new EnumMap<DevelopmentCardType, CardDiscount>(DevelopmentCardType.class);
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			this.discounts.put(cardType, new CardDiscount());
		}
	}
}
