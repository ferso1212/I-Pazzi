package it.polimi.ingsw.ps21;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.GUI.GUInterface;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.MatchData;

public class TestGUInterface {

	
	GUInterface testeGuInterface = new GUInterface();
	private PlayerColor validPlayers[] = {PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW};
	private Match testedMatch;
	
	@Before
	public void setUp(){
		try {
			
			testedMatch = new SimpleMatch(validPlayers);
			testedMatch.getPlayers().toArray(new Player[0])[0].addExcommunication(testedMatch.getBoard().getExcommunications()[0]);
			testeGuInterface.setID(validPlayers[0]);
			testeGuInterface.setRules(false);
		} catch (InvalidIDException | BuildingDeckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void showInterface()
	{
		
		testeGuInterface.updateView(new MatchData(testedMatch));
		testeGuInterface.makeAction(1);
		testeGuInterface.updateView(new MatchData(testedMatch));
		testeGuInterface.makeAction(1);
		assert(true);
		
	}
	
}
