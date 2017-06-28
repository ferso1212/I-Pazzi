package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.deck.LeaderDeck;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class TestAdvancedPlayer {

	@Test
	public void test() {
		AdvancedPlayer player= new AdvancedPlayer(PlayerColor.BLUE, new PlayerProperties(10,10,10,10,10,10,10));
		
		try {
			LeaderDeck leadCards= 	MatchFactory.instance().makeLeaderDeck();
			player.addLeaderCard(leadCards.getCard());
			boolean val=player.checkCardRequirements(leadCards.getCard());
		} catch (BuildingDeckException e) {
			fail();
		}
		assertEquals(0, player.getActivatedLeaderCards().size());
		assertNotEquals(null, player.getAdvMod());
		assertEquals(0, player.getActiveAndClonableLeaderCards().size());
		player.setPersonalBonusTile(new PersonalBonusTile(1, new ImmProperties(1,1,1,1,1,1), 1, new ImmProperties(1,1,1,1,1,1), 1));
		
	}

}
