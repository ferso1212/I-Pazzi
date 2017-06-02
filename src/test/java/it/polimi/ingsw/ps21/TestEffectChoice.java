package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.UnacceptedChooser;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.effect.DiscountEffect;
import it.polimi.ingsw.ps21.model.effect.PropEffect;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.Chooser;


public class TestEffectChoice implements Chooser{

	private EffectChoice testedEffectChoice;
	private boolean validation;
	private int testcase =0; 
	
	public TestEffectChoice(){
		System.out.print("Testing EffectChoice class");
	}
	
	@Before
	public void setUp(){
		try {
			testedEffectChoice = new EffectChoice(new PropEffect(new Requirement(new CardsNumber(), new ImmProperties()), new ImmProperties(1,1,1,1)),
													new DiscountEffect(new Requirement(new CardsNumber(), new ImmProperties()), 
															new ImmProperties(0,0,1,0), DevelopmentCardType.TERRITORY));
		} catch (TooManyArgumentException e) {
			testedEffectChoice = null;
		}
		
	}
	
	@Test
	public void testValidation() {
		assert(checkValidation());
	}

	private boolean checkValidation() {
		validation = true;
		choose(testedEffectChoice);		
		testedEffectChoice.acceptChooser(this);
		choose(testedEffectChoice);
		return validation;
	}

	public void choose(EffectChoice effect) {
		try {
			effect.setChoosen(this, effect.getPossibilities()[0]);
		} catch (UnacceptedChooser e) {
			if (testcase == 0) validation = validation && true;
			else validation = validation && false;
			testcase += 1; 
			return;
		}
		if (testcase == 1) validation = validation && true;
		else validation = validation && false;
		testcase = 0; 
		
	}
	
	@After
	public void clean(){
		testedEffectChoice = null;
		
	}

}
