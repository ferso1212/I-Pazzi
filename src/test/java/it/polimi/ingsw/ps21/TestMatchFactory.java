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
	
	private final int timeoutLobbyValue = 50000;
	private final int timeoutRoundValue = 40000;
	private final int excomReqValues[] = {3, 4,5};
	
	public TestMatchFactory( String testName ) 
    {
        super( testName );
        testedBuilder =  MatchFactory.instance();
    }
	
	public static Test suite(){
		return new TestSuite(TestMatchFactory.class);
	}
	
    public void testApp()
    {
		// TODO reconfigure deck file assert(checkDeckMaking());
        assert(checkPrivileges());
        assert(checkMarketBonus());
        assert(checkInitialProperties());
        assert(checkCardAddingRequirement());
        assert(checkExcomReq());
        assert(checkTimeouts());
        assert(checkTowerBonuses());
        assert(checkTrackBonuses());
    }

	private boolean checkTrackBonuses() {
		testedBuilder.makeTrackBonuses();
		return true;
	}

	private boolean checkTowerBonuses() {
		boolean ok = true;
		Map<DevelopmentCardType, ImmProperties[]> bonuses =  testedBuilder.makeTowersBonus();
		for (DevelopmentCardType t: DevelopmentCardType.values()){
			ImmProperties i[] = bonuses.get(t);
			if (i.length == 4) ok = ok && true;
			else ok = ok && false;
		}
		return ok;
	}

	private boolean checkTimeouts() {
		int round = testedBuilder.makeTimeoutRound();
		int lobby = testedBuilder.makeTimeoutServer();
		if (round == timeoutRoundValue && lobby == timeoutLobbyValue ) return true;
		else return false;
	}

	private boolean checkExcomReq() {
		int result[] = testedBuilder.makeExcommunicationRequirements();
		if (result[0] == excomReqValues[0] && result[1] == excomReqValues[1] && result[2] == excomReqValues[2]) return true;
		else return false;
	}

	private boolean checkDeckMaking() {
		boolean ok =true;
		Deck testDeck;
		try {
			if (testedBuilder == null) return false;
			testDeck = testedBuilder.makeDeck();
			if (testDeck.isEmpty()) return false;
			else
			{
				return true;
			}
		} catch (BuildingDeckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
