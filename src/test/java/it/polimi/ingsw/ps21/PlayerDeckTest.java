package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumMap;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerDeck;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PlayerDeckTest {

	@Test
	public void test() {
		EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> decksMap = new EnumMap<>(DevelopmentCardType.class);
		ArrayList<DevelopmentCard> cards= new ArrayList<>();
		cards.add(new TerritoryCard("TestCard", 1,3, new EffectSet(), new EffectSet()));
		decksMap.put(DevelopmentCardType.TERRITORY, cards);
		EnumMap<DevelopmentCardType, Requirement[]> requirements= new EnumMap<>(DevelopmentCardType.class);
		Requirement[] reqs= new Requirement[1];
		reqs[0]=new Requirement(new CardsNumber(0), new ImmProperties(0));
		requirements.put(DevelopmentCardType.BUILDING, reqs);
		PlayerDeck deck= new PlayerDeck(decksMap, requirements);
	}

}
