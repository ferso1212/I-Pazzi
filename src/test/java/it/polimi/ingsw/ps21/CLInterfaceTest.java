package it.polimi.ingsw.ps21;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.CLInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CLInterfaceTest {

	private CLInterface testCli;
	private PlayerColor validPlayers[] = {PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW};
	private Match testMatch;
	@Before 
	public void setUp(){
		testCli = new CLInterface(1);
		testCli.setID(PlayerColor.BLUE);
		try {
			testMatch = new Match(validPlayers);
		} catch (InvalidIDException | BuildingDeckException e) {
			fail("Error creating test match");
		} 
	}
	
	@Test
	public void test() {
		assert(checkShowInfo());
		assert(checkSingleChoice());
		
	}
	
	private boolean checkShowInfo() {
		testCli.showInfo("Testing interface");
		testCli.showMessage(new AcceptedAction(PlayerColor.BLUE));
		testCli.showMessage(new RefusedAction(PlayerColor.BLUE));
		testCli.updateView(new MatchData(testMatch));
		return true;
	}

	private boolean checkSingleChoice() {
		// Testa un'unica scelta
		ArrayList<ImmProperties> testCosts = new ArrayList<>();
		testCosts.add(new ImmProperties(0,1));
		CostChoice testChoice = new CostChoice(PlayerColor.BLUE, testCosts);
		testChoice.setChosen(testCli.reqCostChoice(testChoice.getChoices()));
		if (testChoice.getChosen() != testChoice.getChoices().get(0)) return false;
		return true;
	}

}
