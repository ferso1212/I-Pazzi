package it.polimi.ingsw.ps21.model.deck;

import java.util.EnumMap;

public class CardsNumber {
	private EnumMap<DevelopmentCardType, Integer> cardsNums;
	
	public CardsNumber(int...values){
		cardsNums=new EnumMap<>(DevelopmentCardType.class);
		int i=0, val=0;
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
			{if(i<values.length) val=values[i];
			else val=0;
			cardsNums.put(cardType, val);
			}
	}

	
	
	public int getCardsNumReq(DevelopmentCardType type) {
		return cardsNums.get(type);
}
}
