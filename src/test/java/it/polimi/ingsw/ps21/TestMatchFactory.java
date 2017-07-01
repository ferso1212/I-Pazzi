package it.polimi.ingsw.ps21;


import java.util.Map;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.ExcommunicationDeck;
import it.polimi.ingsw.ps21.model.deck.LeaderDeck;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import junit.framework.*;

public class TestMatchFactory extends TestCase {
	
	private MatchFactory testedBuilder;
	

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
		assert(checkDeckMaking());
		assert(checkExcommunications());
        assert(checkPrivileges());
        assert(checkMarketBonus());
        assert(checkInitialProperties());
        assert(checkCardAddingRequirement());
        assert(checkExcomReq());
        assert(checkTimeouts());
        assert(checkTowerBonuses());
        assert(checkTrackBonuses());
        assert(checkMarketPrivileges());
        assert(checkCouncil());
        assert(checkTiles());
   }

	private boolean checkTiles() {
		PersonalBonusTile testsimpletile = testedBuilder.makeSimpleTile();
		if (testsimpletile.getTileBonus(WorkType.HARVEST, 1).isNull()) return false;
		if (testsimpletile.getTileBonus(WorkType.PRODUCTION, 1).isNull()) return false;
		PersonalBonusTile testadvtiles[] = testedBuilder.makeAdvancedTiles();
		if(testadvtiles.length!=4) return false;
		for (PersonalBonusTile t: testadvtiles) {
			if (testsimpletile.getTileBonus(WorkType.HARVEST, 6).isNull()) return false;
			if (testsimpletile.getTileBonus(WorkType.PRODUCTION, 6).isNull()) return false;
		}
		return true;
	}

	private boolean checkCouncil() {
		if(testedBuilder.makeCouncilPrivileges()!=1) return false;
		if(testedBuilder.makeCouncilBonuses().isNull()) return false;
		return true;
		
	}

	private boolean checkExcommunications() {
		try {
			ExcommunicationDeck deck = testedBuilder.makeExcommunicationDeck();
			return true;
		} catch (BuildingDeckException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean checkMarketPrivileges() {
		int result[] = testedBuilder.makeMarketPrivileges();
		if (result.length!=4 || result == null)  return false;
		return true;
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
		if (round >0  && lobby > 0 ) return true;
		else return false;
	}

	private boolean checkExcomReq() {
		int result[] = testedBuilder.makeExcommunicationRequirements();
		if (result[0] == excomReqValues[0] && result[1] == excomReqValues[1] && result[2] == excomReqValues[2]) return true;
		else return false;
	}

	private boolean checkDeckMaking() {
		Deck testDeck;
		LeaderDeck testLeaderdeck;
		try {
			if (testedBuilder == null) return false;
			testDeck = testedBuilder.makeDeck();
			if (testDeck.isEmpty()) return false;
			testLeaderdeck = testedBuilder.makeLeaderDeck();
			if (testLeaderdeck.isEmpty()) return false;
			{
				System.out.println(testDeck.toString());
				System.out.println(testLeaderdeck.toString());
				return true;
			}
		} catch (BuildingDeckException e) {
				return false;
			}		
	}
	
	private boolean checkPrivileges() {
		ImmProperties[] test = testedBuilder.makePrivileges() ;
		for (ImmProperties p: test){
			if (p == null) return false;
		}
		return true;
	}
	
	private boolean checkInitialProperties() {
		ImmProperties[] test = testedBuilder.makeInitialProperties() ;
		for (ImmProperties p: test){
			if (p == null) return false;
		}
		return true;
	}
	
	private boolean checkMarketBonus() {
		ImmProperties[] test = testedBuilder.makeMarketBonuses() ;
		for (ImmProperties p: test){
			if (p == null) return false;
		}
		if (test.length!= 4) return false;
		return true;
	}
	
	private boolean checkCardAddingRequirement() {
		Map<DevelopmentCardType, Requirement[]> test = testedBuilder.makeCardAddingRequirements() ;
		return (test != null);
	}
}
