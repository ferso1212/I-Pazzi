package it.polimi.ingsw.ps21;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;

import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchBuilder;
import junit.framework.*;

public class TestMatchBuilder extends TestCase {
	
	private MatchBuilder testedBuilder;
	
	public TestMatchBuilder( String testName )
    {
        super( testName );
        try {
			testedBuilder = new MatchBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
    }
	
	public static Test suite(){
		return new TestSuite(TestMatchBuilder.class);
	}
	
    public void testApp()
    {
        assert(checkDeckMaking());
        
    }

	private boolean checkDeckMaking() {
		boolean ok =true;
		Deck testDeck;
		try {
			for (int i=0; i<10; i++)
				{
				testDeck = testedBuilder.makeDeck();
				ok = ok && !testDeck.isEmpty();
				}
		} catch (BuildingDeckException e) {
			e.printStackTrace(System.err);
			System.out.print(e.getMessage());
			return false;
		}
		return ok;
	}

}
