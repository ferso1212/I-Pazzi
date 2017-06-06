package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.CLInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CLInterfaceTest {

	private CLInterface testCli;
	
	
	@Before 
	public void setUp(){
		testCli = new CLInterface();
	}
	
	@Test
	public void test() {
		assert(true);
		assert(checkChoice());
	}
	
	private boolean checkChoice() {
		// Testa un'unica scelta
		ArrayList<ImmProperties> testCosts = new ArrayList<>();
		testCosts.add(new ImmProperties(0,1));
		CostChoice testChoice = new CostChoice(testCosts);
		testCli.reqChoice(testChoice);
		if (testChoice.getChosen() == testChoice.getChoices().get(0)) return true;
		else return false;
	}

	@After
	public void show(){
		testCli.showMessage(new AcceptedAction("Test Succefull"));
		testCli.showMessage(new RefusedAction());
	}

}
