package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;

public class DevelopmentCardTest {

	@Test
	public void test() {
		try {
			Deck cards= MatchFactory.instance().makeDeck();
			DevelopmentCard c= cards.getCard(1, DevelopmentCardType.VENTURE);
			System.out.println(c.toString());
		} catch (BuildingDeckException e) {
			fail();
		} catch (IllegalCardException e) {
			fail();
		}
	}

}
