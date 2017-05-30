package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test to check Match class
 * @author gullit
 */
public class TestMatch {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	
	private Match testedMatch;
    /**
     * @return the suite of tests being tested
     */
    /**
     * Rigourous Test :-)
     */
    
    @Before
    public void setUp(){
        Player testPlayers[] = new Player[3];
        testPlayers[0] = new Player("daniele", new PlayerProperties(), 1);
        testPlayers[1] = new Player("antonio", new PlayerProperties(), 2);
        testPlayers[2] = new Player("giada",  new PlayerProperties(), 3);
        testedMatch = new Match(testPlayers);
    }
    
    @Test
    public void testApp()
    {
        assert(checkThrows());
    }

	private boolean checkThrows() {
		boolean ok = true;
		for (int i=0; i< 10; i++){
			testedMatch.throwDices();
			int tempResult[] = testedMatch.getDices();
			ok = ok && tempResult[0] <= 6 && tempResult[0] > 0
					&& tempResult[1] <= 6 && tempResult[1] > 0
					&& tempResult[2] <= 6 && tempResult[2] > 0;
		}
		return ok;
	}
}
