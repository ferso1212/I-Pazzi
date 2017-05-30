package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.model.player.*;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

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
     * @throws ParserConfigurationException 
     */
    
    @Before
    public void setUp() throws ParserConfigurationException{
        Map<PlayerColor, Player> testPlayers = new HashMap<>();
        testPlayers.put( PlayerColor.BLUE, new Player("daniele", new PlayerProperties(), 1));
        testPlayers.put( PlayerColor.GREEN, new Player("antonio", new PlayerProperties(), 2));
        testPlayers.put( PlayerColor.YELLOW, new Player("giada",  new PlayerProperties(), 3));
        testedMatch = new UnsettedMatch();
        for (Player p: testPlayers.values()){
        	((UnsettedMatch )testedMatch).addPlayer()
        }
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
