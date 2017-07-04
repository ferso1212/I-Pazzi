package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.DevelopmentAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.ExtraWorkAction;
import it.polimi.ingsw.ps21.model.actions.MarketAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.match.AdvancedMatch;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class TestActions {
	private SimpleMatch match;
	private Deck deck;

	@Before
	public void setUp() {
		try {
			this.match = new SimpleMatch(PlayerColor.RED, PlayerColor.BLUE);
			this.deck=MatchFactory.instance().makeDeck();

		} catch (InvalidIDException | BuildingDeckException e) {
			fail();
		}
	}

	@Test
	public void testCouncilAction() {
		this.match.getCurrentPlayer().getFamily().getMember(MembersColor.NEUTRAL).setValue(6);
		{
			CouncilAction af = new CouncilAction(match.getCurrentPlayer().getId(),
					match.getCurrentPlayer().getFamily().getMember(MembersColor.NEUTRAL), 4);
			Message failMess = af.update(match.getCurrentPlayer(), match);
			if (!(failMess instanceof RefusedAction))
				fail();

			this.match.getCurrentPlayer().getFamily().getMember(MembersColor.ORANGE).setValue(0);
			af = new CouncilAction(match.getCurrentPlayer().getId(),
					match.getCurrentPlayer().getFamily().getMember(MembersColor.ORANGE), 0);
			failMess = af.update(match.getCurrentPlayer(), match);
			if (!(failMess instanceof RefusedAction))
				fail();

			this.match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK).setValue(5);
			CouncilAction a = new CouncilAction(match.getCurrentPlayer().getId(),
					match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK), 4);
			Message mess = a.update(match.getCurrentPlayer(), match);
			if (mess instanceof RefusedAction)
				fail();
			ImmProperties[] chosen = { new ImmProperties(1, 2, 3, 4, 5, 6, 7) };
			((CouncilChoice) mess).setPrivilegesChosen(chosen);
			((CouncilChoice) mess).setVisited();
			mess = a.update(match.getCurrentPlayer(), match);
			if (!(mess instanceof AcceptedAction))
				fail();
			try {
				a.activate(match.getCurrentPlayer(), match);
			} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
				fail();
			}
		}

	}
	
	@Test
	public void testDevelopmentAction()
	{
		for(PropertiesId prop: PropertiesId.values())
		{
			match.getCurrentPlayer().getProperties().getProperty(prop).setValue(0);
		}
		DevelopmentAction af= new DevelopmentAction(match.getCurrentPlayer().getId(), match.getCurrentPlayer().getFamily().getMember(MembersColor.NEUTRAL), 0, DevelopmentCardType.BUILDING, 3);
		if(!(af.update(match.getCurrentPlayer(), match) instanceof RefusedAction)) fail();
	}
	
	@Test
	public void testExtraWorkAction()
	{
		try {
			ExtraWorkAction a2 = new ExtraWorkAction(PlayerColor.BLUE, 8, WorkType.HARVEST);
			Message mess= a2.update(this.match.getCurrentPlayer(), match);
			if(!(mess instanceof AcceptedAction)) fail();
			
			this.match.getCurrentPlayer().getDeck().addCard(deck.getCard(1, DevelopmentCardType.TERRITORY));
		
		ExtraWorkAction a = new ExtraWorkAction(PlayerColor.BLUE, 8, WorkType.HARVEST);
		mess= a.update(this.match.getCurrentPlayer(), match);
		if(!(mess instanceof WorkMessage)) fail();
		WorkMessage workMess= (WorkMessage)mess;
		workMess.setChosenCardsAndEffects(new int[1]);
		mess= a.update(this.match.getCurrentPlayer(), match);
		if(!(mess instanceof AcceptedAction)) fail();
		a.activate(this.match.getCurrentPlayer(), match);
		
		} catch (RequirementNotMetException | IllegalCardException e) {
			fail();
			
		}
		
	}
	
	@Test
	public void testMarketAction()
	{	
		//tests if the action is refused when family member is not enough
		this.match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK).setValue(0);
		MarketAction a = new MarketAction(match.getCurrentPlayer().getId(), match.getBoard().getMarketSpace(0), 0, match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		if(!(a.update(match.getCurrentPlayer(), match) instanceof RefusedAction)) fail();
		
		//tests if the action is accepted when everything is ok
		a = new MarketAction(match.getCurrentPlayer().getId(), match.getBoard().getMarketSpace(0), 7, match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		if(!(a.update(match.getCurrentPlayer(), match) instanceof AcceptedAction)) fail();
		try {
			a.activate(this.match.getCurrentPlayer(), match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}
		
		//tests if the action is refused when the player has the excommunication that forbids to perform market actions
		match.getCurrentPlayer().getModifiers().getActionMods().forbidMarketAction();
		if(!(a.update(match.getCurrentPlayer(), match) instanceof RefusedAction)) fail();
		
		
	}
}
