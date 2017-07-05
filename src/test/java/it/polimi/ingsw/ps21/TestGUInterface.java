package it.polimi.ingsw.ps21;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.GUI.GUInterface;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class TestGUInterface {

	
	GUInterface testeGuInterface = new GUInterface();
	private PlayerColor validPlayers[] = {PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW};
	private Match testedMatch;
	
	@Before
	public void setUp(){
		try {
			testedMatch = new SimpleMatch(validPlayers);
			testeGuInterface.setID(validPlayers[0]);
			testeGuInterface.setRules(false);
		} catch (InvalidIDException | BuildingDeckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void showInterface()
	{
		testeGuInterface.updateView(new MatchData(testedMatch));
		testeGuInterface.makeAction(1);
		assert(true);
		
	}
	
}
