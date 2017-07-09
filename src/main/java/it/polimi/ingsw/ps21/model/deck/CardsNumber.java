package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.EnumMap;

public class CardsNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2256983333710826042L;
	private EnumMap<DevelopmentCardType, Integer> cardsNums;


/**
 * TERRITORY("Territory"), CHARACTER("Character"), BUILDING("Building"), VENTURE("Venture");
 * @author gullit
 *
 */
	public CardsNumber(int... values) {
		cardsNums = new EnumMap<>(DevelopmentCardType.class);
		int i = 0, val = 0;
		for (DevelopmentCardType cardType : DevelopmentCardType.values()) {
			if (i < values.length)
				val = values[i];
			else
				val = 0;
			cardsNums.put(cardType, val);
			i++;
		}
	}

	public int getCardsNumReq(DevelopmentCardType type) {
		return cardsNums.get(type);
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		for (DevelopmentCardType cardType : DevelopmentCardType.values()) {
			if (cardsNums.get(cardType) != 0) {
				b.append("\tYou need to have " + cardsNums.get(cardType) + " " + cardType.toString() + " cards;");
			}
			//else b.append("\tYou don't need any " + cardType.toString());

		}
		return b.toString();
	}
}
