package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.player.AdvancedModifier;

public class AdvancedModifierTest {

	@Test
	public void test() {
		AdvancedModifier mod= new AdvancedModifier();
		assertEquals(false, mod.hasBuildingCardDiscount());
		assertEquals(false, mod.acquiresDoubleResources());
		assertEquals(false, mod.hasNoMilitaryForTerritory());
		assertEquals(false, mod.notPaysOccupiedTower());
		assertEquals(false, mod.canReoccupyPlaces());
		assertEquals(false, mod.hasVaticanSupportBonus());
		
		mod.setBuildingCardDiscount(true);
		assertEquals(true, mod.hasBuildingCardDiscount());
		mod.setDoubleResources(true);
		assertEquals(true, mod.acquiresDoubleResources());
		mod.setNoMilitaryForTerritory(true);
		assertEquals(true, mod.hasNoMilitaryForTerritory());
		mod.setNoPayOccupiedTower(true);
		assertEquals(true, mod.notPaysOccupiedTower());
		mod.setReoccupyPlaces(true);
		assertEquals(true, mod.canReoccupyPlaces());
		mod.setVaticanSupportBonus(true);
		assertEquals(true, mod.hasVaticanSupportBonus());
		
	}

}
