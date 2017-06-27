package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.player.ActionModifier;

public class ActionModifierTest {

	@Test
	public void test() {
		ActionModifier mod = new ActionModifier();
		assertEquals(false, mod.noPlacementBonus());
		assertEquals(false, mod.firstActionDelayed());
		assertEquals(false, mod.marketActionForbidden());
		
		mod.setNoPlacementBonus();
		assertEquals(true, mod.noPlacementBonus());
		mod.setDelayFirstAction(true);
		assertEquals(true, mod.firstActionDelayed());
		mod.forbidMarketAction();
		assertEquals(true, mod.marketActionForbidden());
	}

}
