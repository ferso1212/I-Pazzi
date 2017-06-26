package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.FinalExcomModifier;

public class FinalExcomModifierTest {

	@Test
	public void test() {
		FinalExcomModifier mod= new FinalExcomModifier();
		mod.setCardsNumBonus(DevelopmentCardType.BUILDING, false);
		assertEquals(false, mod.getsCardsNumBonus(DevelopmentCardType.BUILDING));
		assertEquals(true, mod.getsCardsNumBonus(DevelopmentCardType.TERRITORY));
		mod.setMilitaryDivisorVPointsReduction(1);
		assertEquals(1, mod.getMilitaryDivisorVPointsReduction());
		mod.setNoVentureCardsFinalVictoryPointsBonus(true);
		assertEquals(true, mod.getNoVentureCardsFinalVictoryPointsBonus());
		mod.setVictoryPointsReductionDivisor(2);
		assertEquals(2, mod.getVictoryPointsReductionDivisor());
		mod.setvPointsReductionBuildingStoneDivisor(3);
		assertEquals(3, mod.getvPointsReductionBuildingStoneDivisor());
		mod.setvPointsReductionBuildingWoodDivisor(5);
		assertEquals(5, mod.getvPointsReductionBuildingWoodDivisor());
		mod.setvPointsReductionResDivisor(7);
		assertEquals(7, mod.getvPointsReductionResDivisor());
	}

}
