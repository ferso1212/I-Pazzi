package it.polimi.ingsw.ps21;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;

import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import junit.framework.*;

public class TestMatchFactory extends TestCase {
	
	private MatchFactory testedBuilder;
	
	public TestMatchFactory( String testName ) 
    {
        super( testName );
        try {
			testedBuilder =  MatchFactory.instance();
		} catch (ParserConfigurationException | IOException e) {
			fail("CreationFiled");
		}
    }
	
	public static Test suite(){
		return new TestSuite(TestMatchFactory.class);
	}
	
    public void testApp()
    {
        assert(checkDeckMaking());
        
    }

	private boolean checkDeckMaking() {
		boolean ok =true;
		Deck testDeck;
		try {
			testDeck = testedBuilder.makeDeck();
			if (testDeck.isEmpty()) return false;
			else
			{
				return true;
			}
		} catch (BuildingDeckException e) {
			// TODO Auto-generated catch block
			return false;
			}
		
	}

}
