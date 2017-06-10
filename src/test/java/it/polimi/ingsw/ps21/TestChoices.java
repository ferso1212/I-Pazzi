package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.UnacceptedChooser;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.effect.CouncilEffect;
import it.polimi.ingsw.ps21.model.effect.DiscountEffect;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.effect.PropEffect;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.Visitor;


public class TestChoices{

	private EffectChoice testedEffectChoice;
	private VaticanChoice testedVaticanChoice;
	private boolean validation;
	private int testcase =0; 
	
	@Before
	public void setUp(){
			EffectSet testset[] = new EffectSet[2];
			testset[0] = new EffectSet(new PropEffect(new ImmProperties(0), new ImmProperties(3,0,1)), new CouncilEffect(new ImmProperties(0), 2));
			testedEffectChoice = new EffectChoice(PlayerColor.BLUE, testset);
			testedVaticanChoice = new VaticanChoice(PlayerColor.BLUE);
			// TODO add other choices
	}
	
	@Test
	public void testValidation() {
		assert(checkEffectChoice());
		assert(checkVaticanChoice());
	}

	private boolean checkEffectChoice() {
		validation = true;		
		testedEffectChoice.setEffectChosen(testedEffectChoice.getPossibleEffects()[0]);
		if (testedEffectChoice.getPossibleEffects()[0] == testedEffectChoice.getEffectChosen()) validation = validation && true;
		else validation = validation && false;
		testedEffectChoice.setVisited();
		if (testedEffectChoice.isVisited()==true) validation = validation && true;
		else validation = validation && false;
		return validation;
	}

	public boolean checkVaticanChoice(){
		boolean validation = true;
		testedVaticanChoice.setChosen(true);
		if (testedVaticanChoice.getChosen() ==  true) validation = validation && true;
		else validation = validation && false;
		testedVaticanChoice.setVisited();
		if (testedVaticanChoice.isVisited() == true ) validation = validation && true;
		else validation = validation && false;
		return validation;
	}
	
	@After
	public void clean(){
		testedEffectChoice = null;
		
	}
}
