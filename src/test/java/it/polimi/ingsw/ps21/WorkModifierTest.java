package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.WorkModifier;

public class WorkModifierTest {
	private WorkModifier mod;
	
	@Before
	public void setUp()
	{
		mod= new WorkModifier();
	}
	
	@Test
	public void test()
	{
		this.mod.setHarvestModifier(5);
		assertEquals(5, this.mod.getWorkMod(WorkType.HARVEST));
		
		this.mod.setProductionModifier(3);
		assertEquals(3, this.mod.getWorkMod(WorkType.PRODUCTION));
		
		this.mod.setWorkMod(WorkType.HARVEST, 2);
		assertEquals(2, this.mod.getWorkMod(WorkType.HARVEST));
		
		this.mod.setWorkMod(WorkType.PRODUCTION, 1);
		assertEquals(1, this.mod.getWorkMod(WorkType.PRODUCTION));
	}

	
}
