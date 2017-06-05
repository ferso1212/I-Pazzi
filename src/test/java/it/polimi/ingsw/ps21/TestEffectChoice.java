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
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.Visitor;


public class TestEffectChoice implements Visitor{

	private EffectChoice testedEffectChoice;
	private boolean validation;
	private int testcase =0; 
	
	public TestEffectChoice(){
		System.out.print("Testing EffectChoice class");
	}
	
	@Before
	public void setUp(){
			EffectSet testset[] = new EffectSet[2];
			testset[0] = new EffectSet(new PropEffect(new ImmProperties(0), new ImmProperties(3,0,1)), new CouncilEffect(new ImmProperties(0), 2));
			testedEffectChoice = new EffectChoice(testset);
		
	}
	
	@Test
	public void testValidation() {
		assert(checkValidation());
	}

	private boolean checkValidation() {
		validation = true;		
		testedEffectChoice.accept(this);
		choose(testedEffectChoice);
		
		return validation;
	}

	public void choose(EffectChoice effect) {
		effect.setChosen(0);
		if (testedEffectChoice.getChoices()[0] == testedEffectChoice.getChosen()) validation = true;
		else validation = false;
		
	}
	
	@After
	public void clean(){
		testedEffectChoice = null;
		
	}

	@Override
	public void visit(VaticanChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CostChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CouncilChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EffectChoice choice) {
		return;
		
	}

	@Override
	public void visit(WorkMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AcceptedAction message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RefusedAction message) {
		// TODO Auto-generated method stub
		
	}

}
