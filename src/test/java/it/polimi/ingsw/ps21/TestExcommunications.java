package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.excommunications.ActionExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.CardDiceExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.DiceExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.FinalCardVPointsExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.FinalVPointsExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.PropAdditionExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.ServantsValueExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.VenturePointsExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.WorkExcommunication;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class TestExcommunications {
	private Player player;
	
	@Before
	public void setupPlayer()
	{
		this.player= new Player(PlayerColor.BLUE, new PlayerProperties(10,10,10,10,10,10,10));
	}

	@Test
	public void testActionExcommunication() {
		assertEquals(false, player.getModifiers().getActionMods().firstActionDelayed());
		assertEquals(false, player.getModifiers().getActionMods().marketActionForbidden());
		ActionExcommunication e = new ActionExcommunication(1, 1, true, false);
		assertEquals(1, e.getPeriod());
		System.out.println(e.toString());
		e.activate(player);
		assertEquals(true, player.getModifiers().getActionMods().firstActionDelayed());
		
		e=new ActionExcommunication(2, 1, false, true);
		e.activate(player);
		assertEquals(true, player.getModifiers().getActionMods().marketActionForbidden());
		
	}
	
	@Test
	public void testCardDiceExcommunication() {
		for(DevelopmentCardType cardType: DevelopmentCardType.values())
		{
			assertEquals(0, player.getModifiers().getDiceMods().getDiceMod(cardType).getValue());
		}
		
		CardDiceExcommunication e= new CardDiceExcommunication(3, 1, DevelopmentCardType.BUILDING, 2);
		e.activate(player);
		assertEquals(-2, player.getModifiers().getDiceMods().getDiceMod(DevelopmentCardType.BUILDING).getValue());
		System.out.println(e.toString());
	}
	
	@Test
	public void testDiceExcommunications() {
		DiceExcommunication e = new DiceExcommunication(3, 1, 1, 2, 0);
		e.activate(this.player);
		assertEquals(0,player.getFamily().getMember(MembersColor.WHITE).getValue());
		player.getFamily().getMember(MembersColor.ORANGE).setValue(4);
		assertEquals(2,player.getFamily().getMember(MembersColor.ORANGE).getValue());
		System.out.println(e.toString());
	}
	
	@Test
	public void testFinalCardVPointsExcommunication()
	{
		assertEquals(true, player.getModifiers().getFinalMods().getsCardsNumBonus(DevelopmentCardType.CHARACTER));
		FinalCardVPointsExcommunication e = new FinalCardVPointsExcommunication(4, 1, DevelopmentCardType.CHARACTER);
		e.activate(this.player);
		assertEquals(false, player.getModifiers().getFinalMods().getsCardsNumBonus(DevelopmentCardType.CHARACTER));
		System.out.println(e.toString());
	}
	
	@Test
	public void testFinalVPointsExcommunication()
	{
		assertEquals(0, player.getModifiers().getFinalMods().getVictoryPointsReductionDivisor());
		assertEquals(0, player.getModifiers().getFinalMods().getMilitaryDivisorVPointsReduction());
		assertEquals(0, player.getModifiers().getFinalMods().getvPointsReductionBuildingWoodDivisor());
		assertEquals(0, player.getModifiers().getFinalMods().getvPointsReductionBuildingStoneDivisor());
		assertEquals(0, player.getModifiers().getFinalMods().getvPointsReductionResDivisor());
		
		FinalVPointsExcommunication e = new FinalVPointsExcommunication(5, 1, 1, 2, 3, 4, 5);
		System.out.println(e.toString());
		e.activate(player);
		
		assertEquals(1, player.getModifiers().getFinalMods().getVictoryPointsReductionDivisor());
		assertEquals(2, player.getModifiers().getFinalMods().getMilitaryDivisorVPointsReduction());
		assertEquals(3, player.getModifiers().getFinalMods().getvPointsReductionBuildingWoodDivisor());
		assertEquals(4, player.getModifiers().getFinalMods().getvPointsReductionBuildingStoneDivisor());
		assertEquals(5, player.getModifiers().getFinalMods().getvPointsReductionResDivisor());
		
	}
	
	@Test
	public void testPropAdditionExcommunication()
	{
		PropAdditionExcommunication e = new PropAdditionExcommunication(6, 1, new ImmProperties(2,0,0,0,0,1,0));
		e.activate(player);
		System.out.println(e.toString());
		player.getProperties().getProperty(PropertiesId.COINS).addValue(3);
		player.getProperties().getProperty(PropertiesId.WOOD).addValue(4);
		assertEquals(11, player.getProperties().getProperty(PropertiesId.COINS).getValue());
		assertEquals(14, player.getProperties().getProperty(PropertiesId.WOOD).getValue());
		player.getProperties().getProperty(PropertiesId.COINS).addValue(1);
		assertEquals(11, player.getProperties().getProperty(PropertiesId.COINS).getValue());
		player.getProperties().getProperty(PropertiesId.MILITARYPOINTS).addValue(1);
		assertEquals(10, player.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue());
	}
	
	@Test
	public void testServantsValueExcommunication()
	{
		player.getFamily().getMember(MembersColor.BLACK).setValue(3);
		assertEquals(5, player.getFamily().getMemberValueWithServants(2, MembersColor.BLACK));
		ServantsValueExcommunication e= new ServantsValueExcommunication(7, 1, 2);
		System.out.println(e.toString());
		e.activate(player);
		assertEquals(4, player.getFamily().getMemberValueWithServants(2, MembersColor.BLACK));
	}
	
	@Test
	public void testVenturePointsExcommunication() {
		assertEquals(false, player.getModifiers().getFinalMods().getNoVentureCardsFinalVictoryPointsBonus());
		VenturePointsExcommunication e = new VenturePointsExcommunication(8, 1);
		System.out.println(e.toString());
		e.activate(player);
		assertEquals(true, player.getModifiers().getFinalMods().getNoVentureCardsFinalVictoryPointsBonus());
	}
	
	@Test
	public void testWorkExcommunications() {
		assertEquals(0, player.getModifiers().getWorkMods().getHarvMod());
		assertEquals(0, player.getModifiers().getWorkMods().getProdMod());
		WorkExcommunication e= new WorkExcommunication(9, 1, WorkType.HARVEST, 2);
		e.activate(player);
		System.out.println(e.toString());
		assertEquals(2, player.getModifiers().getWorkMods().getHarvMod());
	}

}
