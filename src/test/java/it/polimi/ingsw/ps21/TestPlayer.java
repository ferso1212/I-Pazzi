package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.MatchController;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.IllegalCardTypeException;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class TestPlayer {
	private static final Logger LOGGER = Logger.getLogger(TestPlayer.class.getName());
	private Player player;
	private Deck deck;
	
	@Before
	public void setUp()
	{
		this.player= new Player(PlayerColor.BLUE, new PlayerProperties(10,10,10,10,10,10));
		try {
			this.deck=MatchFactory.instance().makeDeck();
		} catch (BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Unable to build deck from file in Player test", e);
			fail();
		}
	}

	@Test
	public void test() {
		assertEquals(PlayerColor.BLUE, player.getId());
		assertEquals(10, player.getFinalVictoryPoints(MatchFactory.instance().makeTrackBonuses(), MatchFactory.instance().makeCardBonus(), 4));
		assertNotEquals(null, player.getPersonalBonusTile());
		assertNotEquals(null, player.getFamily());
		assertNotEquals(null, player.getModifiers());
		assertEquals(false, player.checkRequirement(new Requirement(new CardsNumber(1,2,3,4), new ImmProperties(1,2,3))));
		try {
			DevelopmentCard card= deck.getCard(1, DevelopmentCardType.TERRITORY);
			assertEquals(true, player.checkCardRequirements(card));
			assertNotEquals(0, player.metCardRequirements(card).size());
			player.getDeck().addCard(card);
			
		} catch (RequirementNotMetException e) {
			LOGGER.log(Level.SEVERE, "URequirement not met for the card", e);
			fail();
		} catch (IllegalCardException e) {
			LOGGER.log(Level.SEVERE, "Illegal card", e);
			fail();
			e.printStackTrace();
		} /*catch (IllegalCardTypeException e) {
			LOGGER.log(Level.SEVERE, "Illegal card type", e);
			fail();
		}*/
	
	}

}
