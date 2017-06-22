package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps21.client.ActionRequestNetPacket;
import it.polimi.ingsw.ps21.client.ActionResponseNetPacket;
import it.polimi.ingsw.ps21.client.CostChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.CostChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.EffectChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.EffectChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.ExtraActionChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.ExtraActionChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.GenericStringNetPacket;
import it.polimi.ingsw.ps21.client.InitNetPacket;
import it.polimi.ingsw.ps21.client.MatchStartedNetPacket;
import it.polimi.ingsw.ps21.client.NameRequestNetPacket;
import it.polimi.ingsw.ps21.client.NameResponseNetPacket;
import it.polimi.ingsw.ps21.client.PacketType;
import it.polimi.ingsw.ps21.client.PlayerIdNetPacket;
import it.polimi.ingsw.ps21.client.PrivilegesChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.PrivilegesChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.VaticanChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.VaticanChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.ViewUpdateRequestNetPacket;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.effect.DiscountEffect;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.ExtraActionData;
import it.polimi.ingsw.ps21.view.ExtraActionType;
import it.polimi.ingsw.ps21.view.RulesChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.view.RulesChoiceResponseNetPacket;

public class NetPacketsTest {

	@Test
	public void testActionRequest() {
		// Action request
		ActionRequestNetPacket p = new ActionRequestNetPacket(1, 0);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.ACTION, p.getType());
	}

	@Test
	public void testCostChoiceRequest() {
		// Cost choice request
		ArrayList<ImmProperties> props = new ArrayList<>();
		props.add(new ImmProperties(0));
		CostChoiceRequestNetPacket p2 = new CostChoiceRequestNetPacket(1, props);
		assertEquals(1, p2.getNum());
		assertEquals(PacketType.COST_CHOICE, p2.getType());
		assertEquals(props, p2.getCostChoices());
	}

	@Test
	public void testEffectChoiceRequest() {
		// Effect choice request
		EffectSet effects;
		try {
			effects = new EffectSet(new DiscountEffect(new ImmProperties(0), DevelopmentCardType.TERRITORY));
			EffectSet[] arg = { effects };
			EffectChoiceRequestNetPacket p3 = new EffectChoiceRequestNetPacket(1, arg);
			assertEquals(1, p3.getNum());
			assertEquals(PacketType.EFFECT_CHOICE, p3.getType());
			assertEquals(arg[0], p3.getPossibleEffects()[0]);

		} catch (TooManyArgumentException e) {
			fail();
		}
	}

	@Test
	public void testExtraActionRequest() {
		// Extra action choice
		ExtraActionData[] arg = { new ExtraActionData(new NullAction(PlayerColor.BLUE)) };
		ExtraActionChoiceRequestNetPacket p4 = new ExtraActionChoiceRequestNetPacket(1, arg);
		assertEquals(1, p4.getNum());
		assertEquals(PacketType.EXTRA_ACTION_CHOICE, p4.getType());
		assertEquals(p4.getActions()[0].getType(), ExtraActionType.NULL_ACTION);
	}

	@Test
	public void testNameRequest() {
		// Name Request
		NameRequestNetPacket p5 = new NameRequestNetPacket(1);
		assertEquals(1, p5.getNum());
	}

	@Test
	public void testPrivilegesChoiceRequest() {
		// Privileges choice request
		ImmProperties[] arg6 = { new ImmProperties(0) };
		PrivilegesChoiceRequestNetPacket p6 = new PrivilegesChoiceRequestNetPacket(1, 2, arg6);
		assertEquals(1, p6.getNum());
		assertEquals(PacketType.PRIVILEGES_CHOICE, p6.getType());
		assertEquals(2, p6.getNumberOfAcquiredPrivileges());
		assertEquals(0, p6.getChoices()[0].getPropertyValue(PropertiesId.COINS));
	}

	@Test
	public void testVaticanChoiceRequest() {
		// Vatican Choice request
		VaticanChoiceRequestNetPacket p7 = new VaticanChoiceRequestNetPacket(1);
		assertEquals(1, p7.getNum());
		assertEquals(PacketType.VATICAN_CHOICE, p7.getType());
	}

	@Test
	public void testViewUpdate() {
		// View update request
		try {
			ViewUpdateRequestNetPacket p8 = new ViewUpdateRequestNetPacket(1,
					new MatchData(new SimpleMatch(PlayerColor.BLUE, PlayerColor.GREEN)));
			assertEquals(1, p8.getNum());
			assertEquals(PacketType.VIEW_UPDATE_REQUEST, p8.getType());
		} catch (InvalidIDException e) {
			fail();
		} catch (BuildingDeckException e) {
			fail();
		}
	}
	
	@Test
	public void testRulesChoiceRequest() {
		RulesChoiceRequestNetPacket p = new RulesChoiceRequestNetPacket(1);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.RULES_CHOICE, p.getType());
	}
	
	@Test
	public void testRulesChoiceResponse() {
		RulesChoiceResponseNetPacket p = new RulesChoiceResponseNetPacket(1, true);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.RULES_CHOICE, p.getType());
		assertEquals(true, p.wantsAdvanced());
		
	}
	
	@Test
	public void testVaticanChoiceResponse() {
		VaticanChoiceResponseNetPacket p = new VaticanChoiceResponseNetPacket(1, true);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.VATICAN_CHOICE, p.getType());
		assertEquals(p.supportsVatican(), true);
	}
	
	@Test
	public void privilegesChoiceResponse() {
		ImmProperties[] arg= {new ImmProperties(0)};
		PrivilegesChoiceResponseNetPacket p= new PrivilegesChoiceResponseNetPacket(1, arg);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.PRIVILEGES_CHOICE, p.getType());
		assertEquals(arg[0].getPropertyValue(PropertiesId.COINS), p.getChosenPrivileges()[0].getPropertyValue(PropertiesId.COINS));
	}
	
	@Test
	public void testNameResponse(){
		NameResponseNetPacket p = new NameResponseNetPacket(1, "Tony Effe");
		assertEquals(1, p.getNum());
		assertEquals(PacketType.NAME, p.getType());
		assertEquals("Tony Effe", p.getName());
	}
	
	@Test
	public void testGenericString() {
		GenericStringNetPacket p = new GenericStringNetPacket(1, "Bufu");
		assertEquals(1, p.getNum());
		assertEquals(PacketType.GENERIC_STRING, p.getType());
		assertEquals("Bufu", p.getStr());
	}
	
	@Test
	public void testCostChoiceResponse() {
		CostChoiceResponseNetPacket p = new CostChoiceResponseNetPacket(1, 1);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.COST_CHOICE, p.getType());
		assertEquals(1, p.getChosen());
	}
	
	@Test
	public void testEffectChoiceResponse() {
		EffectChoiceResponseNetPacket p = new EffectChoiceResponseNetPacket(1, 1);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.EFFECT_CHOICE, p.getType());
		assertEquals(1, p.getChosen());
	}
	
	@Test
	public void testExtraActionChoiceResponse() {
		ExtraActionChoiceResponseNetPacket p = new ExtraActionChoiceResponseNetPacket(1, 1);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.EXTRA_ACTION_CHOICE, p.getType());
		assertEquals(1, p.getChosen());
	}
	
	@Test
	public void testActionResponse() {
		ActionResponseNetPacket p = new ActionResponseNetPacket(1, new ActionData(ActionType.NULL, MembersColor.BLACK, 1,DevelopmentCardType.BUILDING , 1, 0));
		assertEquals(1, p.getNum());
		assertEquals(PacketType.ACTION, p.getType());
		assertEquals(1, p.getAction().getServants());
		assertEquals(ActionType.NULL, p.getAction().getType());
		assertEquals(MembersColor.BLACK, p.getAction().getFamilyMember());
		assertEquals(DevelopmentCardType.BUILDING, p.getAction().getTower());
	}
	
	@Test
	public void testInit() {
		InitNetPacket p = new InitNetPacket(1, false);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.INIT, p.getType());
		assertEquals(false, p.isNew());
	}
	
	@Test
	public void testMatchStarted() {
		MatchStartedNetPacket p = new MatchStartedNetPacket(1);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.MATCH_STARTED_NOTIFICATION, p.getType());
	}
	
	@Test
	public void testPlayerId() {
		PlayerIdNetPacket p = new PlayerIdNetPacket(1, PlayerColor.BLUE);
		assertEquals(1, p.getNum());
		assertEquals(PacketType.PLAYER_ID, p.getType());
		assertEquals(PlayerColor.BLUE, p.getId());
		
	}
}
