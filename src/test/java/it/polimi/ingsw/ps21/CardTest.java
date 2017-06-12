package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.effect.PrivilegeEffect;
import it.polimi.ingsw.ps21.model.effect.PropEffect;
import it.polimi.ingsw.ps21.model.effect.WorkDiceEffect;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CardTest {

	private final RequirementAndCost testReq = new RequirementAndCost(new Requirement(new CardsNumber(0,0,0), new ImmProperties(1,1,2,3)), 
														new ImmProperties(1,1,1,2));
	private final EffectSet testInstantEffect = new EffectSet(new PropEffect(new ImmProperties(0,0,0,0), new ImmProperties(2,2,2,2)));
	private final EffectSet testPermanentEffect1 = new EffectSet(new WorkDiceEffect(4, WorkType.HARVEST));
	private final EffectSet testPermanentEffect2 = new EffectSet(new PrivilegeEffect(2));
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void creatingTest() {
		assert(checkBuildingCreation());
		assert(checkTerritoryCreation());
		assert(checkCharacterCreation());
		assert(checkVentureCreation());
		// assert(checkExcommunicationCreation());
	}

	private boolean checkExcommunicationCreation() {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean checkVentureCreation() {
		VentureCard testVenture = new VentureCard("test", 2, testReq, testInstantEffect, testPermanentEffect1);
		if (testVenture.getCardType()!=DevelopmentCardType.VENTURE) return false;
		if (testVenture.getCosts()[0]!=testReq) return false;
		return true;
	}

	private boolean checkCharacterCreation() {
		CharacterCard testCharacter = new CharacterCard("Test", 3, testReq, testInstantEffect, testPermanentEffect1, testPermanentEffect2);
		if (testCharacter.getCardType()!=DevelopmentCardType.CHARACTER) return false;
		if (testCharacter.getPossibleEffects().length!= 2) return false;
		if (testCharacter.getCosts()[0]!= testReq) return false;
		return true;
		
	}

	private boolean checkTerritoryCreation() {
		TerritoryCard testTerritory = new TerritoryCard("Test", 2, 3, testInstantEffect, testPermanentEffect1);
		if (testTerritory.getEra()!=2) return false;
		if (testTerritory.getDiceRequirement()!=3) return false;
		if (testTerritory.getCosts().length != 1) return false;
		if (testTerritory.getPossibleEffects().length!=1) return false;
		try {
			if (testTerritory.getChosenPemanentEffect()!= testPermanentEffect1) return false;
		} catch (UnchosenException e) {
			System.out.print("Expected exception, everithing allright");
		}
		return true;
	}

	private boolean checkBuildingCreation() {
		BuildingCard testBuilding= new BuildingCard("test", 1, testReq, 2, testInstantEffect, testPermanentEffect1);
		try {
			if (testBuilding.getEra() != 1) return false;
			if (testBuilding.getCardType()!=DevelopmentCardType.BUILDING) return false;
			if (testBuilding.getInstantEffect()!= testInstantEffect) return false;
			if (testBuilding.getDiceRequirement() != 2) return false;
			if (testBuilding.getChosenPemanentEffect()!= testPermanentEffect1) return false;
		} catch (UnchosenException e) {
			System.out.println("Expected exception, everithing allright");
		}
		if (testBuilding.getCosts().length!=1 || testBuilding.getCosts()[0] != testReq) return false;
		if (testBuilding.getCosts().length!=1 || testBuilding.getCosts()[0] != testReq) return false;
		testBuilding = new BuildingCard("test", 1, testReq, 3, testInstantEffect, testPermanentEffect1, testPermanentEffect2);
		if (testBuilding.getPossibleEffects().length!= 2)  return false;
		return true;
	}

}
