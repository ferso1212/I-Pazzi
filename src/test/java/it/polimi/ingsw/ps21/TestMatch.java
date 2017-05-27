package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test to check Match class
 * @author gullit
 */
public class TestMatch 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	
	private Match testedMatch;
	
    public TestMatch( String testName )
    {
        super( testName );
        Player testPlayers[] = new Player[3];
        testPlayers[0] = new Player("daniele", new PlayerProperties(), "rosso");
        testPlayers[1] = new Player("antonio", new PlayerProperties(), "blu");
        testPlayers[2] = new Player("giada",  new PlayerProperties(), "verde");
        testedMatch = new Match(testPlayers);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestMatch.class );
    }

    /**
     * Rigourous Test :-)
     */
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
