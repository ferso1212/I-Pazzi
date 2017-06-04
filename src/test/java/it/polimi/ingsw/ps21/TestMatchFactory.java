package it.polimi.ingsw.ps21;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;

import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import junit.framework.*;

public class TestMatchFactory extends TestCase {
	
	private MatchFactory testedBuilder;
	
	public TestMatchFactory( String testName ) 
    {
        super( testName );
        try {
			testedBuilder =  MatchFactory.instance();
		} catch (ParserConfigurationException e) {
			fail("CreationFiled");
		}
    }
	
	public static Test suite(){
		return new TestSuite(TestMatchFactory.class);
	}
	
    public void testApp()
    {
        assert(checkDeckMaking());
        assert(checkPrivileges());
        assert(checkMarketBonus());
        assert(checkInitialProperties());
        assert(checkCardAddingRequirement());
        
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
	
	private boolean checkPrivileges() {
		ImmProperties[] test = testedBuilder.makePrivileges() ;
		return (test != null);
	}
	
	private boolean checkInitialProperties() {
		ImmProperties[] test = testedBuilder.makeInitialProperties() ;
		return (test != null);
	}
	
	private boolean checkMarketBonus() {
		ImmProperties[] test = testedBuilder.makeMarketBonuses() ;
		return (test != null);
	}
	
	private boolean checkCardAddingRequirement() {
		Map<DevelopmentCardType, Requirement[]> test = testedBuilder.makeCardAddingRequirements() ;
		return (test != null);
	}
}
