package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.MultiplierType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.effect.CouncilBonus;
import it.polimi.ingsw.ps21.model.effect.MultiplierEffect;
import it.polimi.ingsw.ps21.model.effect.NullLeaderEffect;
import it.polimi.ingsw.ps21.model.effect.WorkBonus;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;

public class TestEffects {
	private Player stdPl;
	private AdvancedPlayer advPl;
	
	@Before
	public void setUp()
	{
		this.stdPl=new Player(PlayerColor.BLUE, new PlayerProperties(new PropertiesSet(10,10,10,10,10,10,10)));
		this.advPl=new AdvancedPlayer(PlayerColor.RED, new PlayerProperties(new PropertiesSet(10,10,10,10,10,10,10)));

	}

	@Test
	public void councilTest() {
		Requirement[] reqs= {new Requirement(new CardsNumber(0), new ImmProperties(0))};
		CouncilBonus eff= new CouncilBonus(reqs, 1);
		assertEquals("Council Bonus", eff.getType());
		assertEquals("You have to choose "+ 1 + " privileges", eff.getDesc());
		ExtraAction extra= eff.activate(advPl);
		assert(extra instanceof TakePrivilegesAction);
	}
	
	@Test
	public void workBonusTest()
	{
		Requirement[] reqs= {new Requirement(new CardsNumber(0), new ImmProperties(0))};
		WorkBonus eff= new WorkBonus(reqs,WorkType.HARVEST, 2);
		assertEquals("WorkBonusEffect", eff.getType());
		assertEquals("This effect add a permanent bonus value of "+ 2 + " on dice value for effect of "+ WorkType.HARVEST.toString() + " card", eff.getDesc());
		eff.activate(advPl);
		assert(advPl.getModifiers().getWorkMods().getWorkMod(WorkType.HARVEST)==2);
	}

	@Test
	public void nullLeaderEffectTest()
	{
		NullLeaderEffect eff= new NullLeaderEffect();
		assertEquals("Null Effect", eff.getType());
		assertEquals("This effect does nothing", eff.getDesc());
		assert(eff.activate(advPl) instanceof NullAction);
	}
	
	@Test
	public void multiplierEffect()
	{
		int i=1;
		for(MultiplierType type: MultiplierType.values())
		{
		MultiplierEffect eff = new MultiplierEffect(new ImmProperties(0), new ImmProperties(0, 1), type, 2);
		eff.activate(stdPl);
		if(type!=MultiplierType.GREEN_CARD && type!=MultiplierType.BLUE_CARD && type!=MultiplierType.YELLOW_CARD && type!=MultiplierType.PURPLE_CARD)
		{assertEquals(10+ 20*i, stdPl.getProperties().getProperty(PropertiesId.WOOD).getValue());
		i++;}
		else assert(true);
		}
	}
}
