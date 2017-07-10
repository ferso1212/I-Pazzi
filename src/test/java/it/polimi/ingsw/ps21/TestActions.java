package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.ExcommunicationMessage;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.PickAnotherCardMessage;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.ServantsChoice;
import it.polimi.ingsw.ps21.controller.TileChoice;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.DevelopmentAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.ExtraWorkAction;
import it.polimi.ingsw.ps21.model.actions.MarketAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.PickAnotherCardAction;
import it.polimi.ingsw.ps21.model.actions.PlayLeaderCardAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.actions.TileChoiceAction;
import it.polimi.ingsw.ps21.model.actions.VaticanAction;
import it.polimi.ingsw.ps21.model.actions.WorkAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.effect.DiscountEffect;
import it.polimi.ingsw.ps21.model.effect.DiscountLeaderEffect;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.effect.InstantWorkEffect;
import it.polimi.ingsw.ps21.model.effect.PropEffect;
import it.polimi.ingsw.ps21.model.match.AdvancedMatch;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ExtraActionType;

public class TestActions {
	private SimpleMatch match;
	private Deck deck;
	private Player p;

	@Before
	public void setUp() {
		try {
			this.match = new SimpleMatch(PlayerColor.RED, PlayerColor.BLUE);
			// this.deck=MatchFactory.instance().makeDeck();
			p=match.getCurrentPlayer();

		} catch (InvalidIDException | BuildingDeckException e) {
			fail();
		}
	}

	@Test
	public void testCouncilAction() {
		p.getFamily().getMember(MembersColor.NEUTRAL).setValue(6);
		{
			CouncilAction af = new CouncilAction(match.getCurrentPlayer().getId(),
					match.getCurrentPlayer().getFamily().getMember(MembersColor.NEUTRAL), 4, 0);
			Message failMess = af.update(match.getCurrentPlayer(), match);
			if (!(failMess instanceof RefusedAction))
				fail();

			this.match.getCurrentPlayer().getFamily().getMember(MembersColor.ORANGE).setValue(0);
			af = new CouncilAction(match.getCurrentPlayer().getId(),
					match.getCurrentPlayer().getFamily().getMember(MembersColor.ORANGE), 0, 0);
			failMess = af.update(match.getCurrentPlayer(), match);
			if (!(failMess instanceof RefusedAction))
				fail();

			this.match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK).setValue(5);
			CouncilAction a = new CouncilAction(match.getCurrentPlayer().getId(),
					match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK), 4, 0);
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
			} catch (RequirementNotMetException | InsufficientPropsException e) {
				fail();
			}
		}

	}

	@Test
	public void testDevelopmentAction() {
		// tests if the action is Refused when the player hasn't enough
		// properties to pay any card's cost
		
		setAllPlayerProperties(0, p);
		DevelopmentAction af = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.NEUTRAL), 0,
				DevelopmentCardType.BUILDING, 3, 0);
		if (!(af.update(p, match) instanceof RefusedAction))
			fail();

		// tests if action is refused when space is already occupied
		setAllPlayerProperties(10, p);
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		try {
			match.getBoard().getTower(DevelopmentCardType.BUILDING).getTowerSpace(3).occupy(p,
					p.getFamily().getMember(MembersColor.BLACK));
			if (!(af.update(p, match) instanceof RefusedAction))
				fail();
		} catch (NotOccupableException e) {
			fail();
		}

		// tests if action is refused when the player tries to occupy an already
		// occupied tower and he hasn't enough coins to pay the malus
		setAllPlayerProperties(2, p);
		DevelopmentAction af2 = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.ORANGE), 5,
				DevelopmentCardType.BUILDING, 2, 0);
		if (!(af2.update(p, match) instanceof RefusedAction))
			fail();

		// tests if action is refused when the player has not enough cards
		RequirementAndCost[] cardReqAndCosts = { new RequirementAndCost(
				new Requirement(new CardsNumber(9, 9, 9, 9), new ImmProperties(0)), new ImmProperties(0)) };
		CharacterCard testCard = new CharacterCard("testCard", 1, cardReqAndCosts, new EffectSet(),
				new EffectSet(new PropEffect(new ImmProperties(0), new ImmProperties(1))));
		match.getBoard().getTower(DevelopmentCardType.CHARACTER).getTowerSpace(1).setCard(testCard);
		af2 = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.BLACK), 5,
				DevelopmentCardType.CHARACTER, 1, 0);
		if (!(af2.update(p, match) instanceof RefusedAction))
			fail();

		// tests if action is refused when family member value is not enough
		setAllPlayerProperties(10, p);
		p.getFamily().roundReset();
		cardReqAndCosts[0] = new RequirementAndCost(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0)),
				new ImmProperties(0));
		testCard = new CharacterCard("testCard", 1, cardReqAndCosts, new EffectSet(),
				new EffectSet(new PropEffect(new ImmProperties(0), new ImmProperties(1))));
		match.getBoard().getTower(DevelopmentCardType.CHARACTER).getTowerSpace(1).setCard(testCard);
		af2 = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.BLACK), 0,
				DevelopmentCardType.CHARACTER, 1, 0);
		if (!(af2.update(p, match) instanceof RefusedAction))
			fail();

		// tests if action is refused when family member is already used
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		p.getFamily().getMember(MembersColor.BLACK).setUsed(true);
		af2 = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.BLACK), 0,
				DevelopmentCardType.CHARACTER, 1, 0);
		if (!(af2.update(p, match) instanceof RefusedAction))
			fail();

		// TODO tests if action is refused when player doesn't meet adding card
		// requirements
		setAllPlayerProperties(10, p);

		// tests if choice is returned
		p.getFamily().roundReset();
		setAllPlayerProperties(10, p);
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		try {
			match.getBoard().getTower(DevelopmentCardType.CHARACTER).getTowerSpace(3).occupy(p,
					p.getFamily().getMember(MembersColor.ORANGE));
		} catch (IllegalArgumentException | NotOccupableException e) {
			fail();
		}
		DevelopmentAction a = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.BLACK), 5,
				DevelopmentCardType.CHARACTER, 1, 0);
		if (!(a.update(p, match) instanceof CostChoice))
			fail();

		match.getBoard().getTower(DevelopmentCardType.CHARACTER).getTowerSpace(3).reset();
		a = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.BLACK), 5,
				DevelopmentCardType.CHARACTER, 1, 0);
		Message mess = a.update(p, match);
		if (!(mess instanceof CostChoice))
			fail();

		((CostChoice) mess).setChosen(0);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof EffectChoice))
			fail();
		((EffectChoice) mess).setEffectChosen(((EffectChoice) mess).getPossibleEffects()[0]);
		mess = a.update(p, match);
		if (!(mess instanceof AcceptedAction))
			fail();
		try {
			a.activate(p, match);
		} catch (RequirementNotMetException | InsufficientPropsException | NotExecutableException e) {
			fail();
		}

		// test action execution with cards that are NOT Character Cards
		p.getFamily().roundReset();
		p.getFamily().getMember(MembersColor.ORANGE).setValue(10);
		TerritoryCard testCard2 = new TerritoryCard("testCard2", 1, 1, new EffectSet(),
				new EffectSet(new PropEffect(new ImmProperties(0), new ImmProperties(1))));
		match.getBoard().getTower(DevelopmentCardType.TERRITORY).getTowerSpace(2).setCard(testCard);
		DevelopmentAction a2 = new DevelopmentAction(p.getId(), p.getFamily().getMember(MembersColor.ORANGE), 5,
				DevelopmentCardType.TERRITORY, 2, 0);
		mess = a2.update(p, match);
		if (!(mess instanceof CostChoice))
			fail();
		((CostChoice) mess).setChosen(0);
		mess.setVisited();
		mess = a.update(p, match);
		mess = a.update(p, match);
		if (!(mess instanceof AcceptedAction))
			fail();
	}

	@Test
	public void testExtraWorkAction() {
		try {
			ExtraWorkAction a2 = new ExtraWorkAction(PlayerColor.BLUE, 8, WorkType.HARVEST);
			Message mess = a2.update(this.match.getCurrentPlayer(), match);
			if(!(mess instanceof ServantsChoice)) fail();
			((ServantsChoice)mess).setNumberOfServants(2);
			mess.setVisited();
			mess = a2.update(this.match.getCurrentPlayer(), match);
			if(!(mess instanceof WorkMessage)) fail();
			WorkMessage wmess=(WorkMessage)mess;
			int[] choices= new int[wmess.getcardsToActivateWithoutChoice().length];
			wmess.setChosenCardsWithoutCost(choices);
			choices=new int[wmess.getCardsWithCost().length];
			wmess.setChosenCardsWithCost(choices);
			mess.setVisited();
			mess = a2.update(this.match.getCurrentPlayer(), match);
			if (!(mess instanceof AcceptedAction))
				fail();

			// this.match.getCurrentPlayer().getDeck().addCard(deck.getCard(1,
			// DevelopmentCardType.TERRITORY));
			this.match.getCurrentPlayer().getDeck().addCard(new TerritoryCard("testCard", 1, 1, new EffectSet(),
					new EffectSet(new PropEffect(new ImmProperties(0), new ImmProperties(1)))));

			ExtraWorkAction a = new ExtraWorkAction(PlayerColor.BLUE, 8, WorkType.HARVEST);
			mess = a.update(this.match.getCurrentPlayer(), match);
			if(!(mess instanceof ServantsChoice)) fail();
			((ServantsChoice)mess).setNumberOfServants(2);
			mess.setVisited();
			mess = a.update(this.match.getCurrentPlayer(), match);
			if (!(mess instanceof WorkMessage)) fail();
			WorkMessage workMess = (WorkMessage) mess;
			workMess.setChosenCardsWithCost(new int[1]);
			workMess.setChosenCardsWithoutCost(new int[1]);
			mess.setVisited();
			mess = a.update(this.match.getCurrentPlayer(), match);
			if (!(mess instanceof AcceptedAction))
				fail();
			a.activate(this.match.getCurrentPlayer(), match);

		} catch (RequirementNotMetException e) {
			fail();

		}

	}

	@Test
	public void testMarketAction() {
		// tests if the action is refused when family member is not enough
		this.match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK).setValue(0);
		MarketAction a = new MarketAction(match.getCurrentPlayer().getId(), match.getBoard().getMarketSpace(0), 0,
				match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK), 0);
		if (!(a.update(match.getCurrentPlayer(), match) instanceof RefusedAction))
			fail();

		// tests if the action is accepted when everything is ok
		a = new MarketAction(match.getCurrentPlayer().getId(), match.getBoard().getMarketSpace(0), 7,
				match.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK), 0);
		if (!(a.update(match.getCurrentPlayer(), match) instanceof AcceptedAction))
			fail();
		try {
			a.activate(this.match.getCurrentPlayer(), match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}

		// tests if the action is refused when the player has the
		// excommunication that forbids to perform market actions
		match.getCurrentPlayer().getModifiers().getActionMods().forbidMarketAction();
		if (!(a.update(match.getCurrentPlayer(), match) instanceof RefusedAction))
			fail();

	}

	@Test
	public void testVaticanAction() {
		// tests when player chooses to support vatican
		VaticanAction a = new VaticanAction(p.getId(), 0);
		setAllPlayerProperties(15, p);
		Message mess = a.update(p, match);
		if (!(mess instanceof VaticanChoice))
			fail();
		((VaticanChoice) mess).setChosen(true);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof AcceptedAction))
			fail();
		try {
			a.activate(p, match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}

		// tests when player chooses to take the excommunication
		a = new VaticanAction(p.getId(), 0);
		setAllPlayerProperties(15, p);
		mess = a.update(p, match);
		if (!(mess instanceof VaticanChoice))
			fail();
		((VaticanChoice) mess).setChosen(false);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof ExcommunicationMessage))
			fail();
		try {
			a.activate(p, match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}

		// test if the action is refused when the message is not visited
		a = new VaticanAction(p.getId(), 0);
		setAllPlayerProperties(15, p);
		mess = a.update(p, match);
		if (!(mess instanceof VaticanChoice))
			fail();
		mess = a.update(p, match);
		if (!(mess instanceof RefusedAction))
			fail();
	}

	@Test
	public void testPickAnotherAction() {

		// test with character cards
		PickAnotherCardAction a = new PickAnotherCardAction(p, 5, DevelopmentCardType.CHARACTER);
		a.getData();
		assertEquals(a.getData().getType(), ExtraActionType.PICK_ANOTHER_CARD);
		Message mess = a.update(p, match);
		if (!(mess instanceof ServantsChoice))
			fail();
		((ServantsChoice) mess).setNumberOfServants(3);
		((ServantsChoice) mess).setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof PickAnotherCardMessage))
			fail();
		((PickAnotherCardMessage) mess).setCardChosen(0);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof CostChoice))
			fail();
		((CostChoice) mess).setChosen(0);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof EffectChoice))
			fail();
		((EffectChoice) mess).setEffectChosen(((EffectChoice) mess).getPossibleEffects()[0]);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof AcceptedAction))
			fail();
		try {
			a.activate(p, match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}

		// test with other different type of cards
		setAllPlayerProperties(20, p);
		a = new PickAnotherCardAction(p, 5, DevelopmentCardType.BUILDING);
		mess = a.update(p, match);
		if (!(mess instanceof ServantsChoice))
			fail();
		((ServantsChoice) mess).setNumberOfServants(3);
		((ServantsChoice) mess).setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof PickAnotherCardMessage))
			fail();
		((PickAnotherCardMessage) mess).setCardChosen(0);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof CostChoice))
			fail();
		((CostChoice) mess).setChosen(0);
		mess.setVisited();
		mess = a.update(p, match);
		if (!(mess instanceof AcceptedAction))
			fail();

	}
	
	@Test
	public void testWorkActions()
	{	//tests if action is refused when trying to use a member that was already used before
		setAllPlayerProperties(20, p);
		p.getFamily().roundReset();
		p.getFamily().getMember(MembersColor.BLACK).setUsed(true);
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		WorkAction swa = new WorkAction(p.getId(), match.getBoard().getSingleWorkSpace(WorkType.HARVEST), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
		Message mess= swa.update(p, match);
		if (!(mess instanceof RefusedAction)) fail();
		
		//test if action is refused when the space is already occupied
		p.getFamily().roundReset();
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		try {
			match.getBoard().getSingleWorkSpace(WorkType.HARVEST).occupy(p, p.getFamily().getMember(MembersColor.ORANGE));
		} catch (NotOccupableException e) {
			fail();
		}
		swa = new WorkAction(p.getId(), match.getBoard().getSingleWorkSpace(WorkType.HARVEST), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
		mess= swa.update(p, match);
		if (!(mess instanceof RefusedAction)) fail();
		
		//test activation of HARVEST
		p.getFamily().roundReset();
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		match.getBoard().getSingleWorkSpace(WorkType.HARVEST).reset();
		swa = new WorkAction(p.getId(), match.getBoard().getSingleWorkSpace(WorkType.HARVEST), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
		mess=swa.update(p, match);
		if (!(mess instanceof WorkMessage)) fail();
		int[] choices=new int[((WorkMessage)mess).getCardsWithCost().length];
		((WorkMessage)mess).setChosenCardsWithCost(choices);
		choices= new int[((WorkMessage)mess).getcardsToActivateWithoutChoice().length];
		for (int i=0; i< choices.length; i++) choices[i]=1;
		((WorkMessage)mess).setChosenCardsWithoutCost(choices);
		mess.setVisited();
		mess=swa.update(p, match);
		if (!(mess instanceof AcceptedAction)) fail();
		try {
			swa.activate(p, match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}
		
		//test with more than 3 players
		p.getFamily().roundReset();
		p.getFamily().getMember(MembersColor.BLACK).setValue(10);
		try {
			this.match=new SimpleMatch(PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW);
			match.getBoard().getMultipleWorkSpace(WorkType.HARVEST).occupy(p, p.getFamily().getMember(MembersColor.ORANGE));
			match.getBoard().getSingleWorkSpace(WorkType.HARVEST).reset();
			swa = new WorkAction(p.getId(), match.getBoard().getSingleWorkSpace(WorkType.HARVEST), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
			mess=swa.update(p, match);
			if (!(mess instanceof RefusedAction)) fail();
			
			p.getFamily().roundReset();
			p.getFamily().getMember(MembersColor.BLACK).setValue(10);
			this.match=new SimpleMatch(PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW);
			match.getBoard().getMultipleWorkSpace(WorkType.PRODUCTION).occupy(p, p.getFamily().getMember(MembersColor.ORANGE));
			match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION).reset();
			swa = new WorkAction(p.getId(), match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
			mess=swa.update(p, match);
			if (!(mess instanceof RefusedAction)) fail();
			
			p.getFamily().roundReset();
			p.getFamily().getMember(MembersColor.BLACK).setValue(10);
			this.match=new SimpleMatch(PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW);
			match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION).reset();
			match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION).occupy(p, p.getFamily().getMember(MembersColor.ORANGE));
			match.getBoard().getMultipleWorkSpace(WorkType.PRODUCTION).reset();
			swa = new WorkAction(p.getId(), match.getBoard().getMultipleWorkSpace(WorkType.PRODUCTION), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
			mess=swa.update(p, match);
			if (!(mess instanceof RefusedAction)) fail();
			
			p.getFamily().roundReset();
			p.getFamily().getMember(MembersColor.BLACK).setValue(10);
			this.match=new SimpleMatch(PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW);
			match.getBoard().getSingleWorkSpace(WorkType.HARVEST).reset();
			match.getBoard().getSingleWorkSpace(WorkType.HARVEST).occupy(p, p.getFamily().getMember(MembersColor.ORANGE));
			match.getBoard().getMultipleWorkSpace(WorkType.HARVEST).reset();
			swa = new WorkAction(p.getId(), match.getBoard().getMultipleWorkSpace(WorkType.HARVEST), p.getFamily().getMember(MembersColor.BLACK), 3, 0);
			mess=swa.update(p, match);
			if (!(mess instanceof RefusedAction)) fail();
		} catch (InvalidIDException | BuildingDeckException e) {
			fail();
		} catch (NotOccupableException e) {
			fail();
		}
	}
	
	@Test
	public void testTakePrivilegesAction()
	{
		TakePrivilegesAction a = new TakePrivilegesAction(p.getId(), 1);
		Message mess= a.update(p, match);
		if (!(mess instanceof CouncilChoice)) fail();
		ImmProperties[] chosen= {new ImmProperties(1,0)};
		((CouncilChoice)mess).setPrivilegesChosen(chosen);
		mess.setVisited();
		mess=a.update(p, match);
		if (!(mess instanceof AcceptedAction)) fail();
		try {
			a.activate(p, match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}
		
		a = new TakePrivilegesAction(p.getId(), 1);
		mess= a.update(p, match);
		if (!(mess instanceof CouncilChoice)) fail();
		ImmProperties[] chosen2= {new ImmProperties(1,0), new ImmProperties(0)};
		((CouncilChoice)mess).setPrivilegesChosen(chosen2);
		mess.setVisited();
		mess=a.update(p, match);
		if (!(mess instanceof RefusedAction)) fail();
	}
	
	@Test
	public void testPlayLeaderCardAction()
	{
		try {
			AdvancedMatch advM=new AdvancedMatch(PlayerColor.RED, PlayerColor.GREEN);
			Player ap= advM.getCurrentPlayer();
		
		setAllPlayerProperties(20, ap);
		Requirement[] reqs= {new Requirement(new CardsNumber(0), new ImmProperties(0))};
		LeaderCard card= new LeaderCard("leader", new DiscountLeaderEffect(reqs));
		PlayLeaderCardAction a = new PlayLeaderCardAction(ap.getId(), card, 0);
		if(!(a.update(ap, advM) instanceof AcceptedAction)) fail();
		try {
			a.activate(ap, advM);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}
		} catch (InvalidIDException | BuildingDeckException e1) {
			fail();
		}
	}
	
	@Test
	public void testNullAction()
	{
		NullAction a = new NullAction(p.getId());
		if (!(a.update(p, match) instanceof AcceptedAction)) fail();
		try {
			a.activate(p, match);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			fail();
		}
	}
	
	@Test
	public void testTileChoiceAction()
	{
		AdvancedMatch advM;
		try {
			advM = new AdvancedMatch(PlayerColor.RED, PlayerColor.GREEN);
			Player ap= advM.getCurrentPlayer();
			ArrayList<PersonalBonusTile> tiles= new ArrayList<>();
			tiles.add(new PersonalBonusTile(1, new ImmProperties(1), 1, new ImmProperties(1), 1));
			TileChoice choiceMess= new TileChoice(ap.getId(), tiles, 0);
			TileChoiceAction a = new TileChoiceAction(ap.getId(), choiceMess, 0);
			Message mess= a.update(ap, match);
			if (!(mess instanceof TileChoice)) fail();
			((TileChoice)mess).setChosen(0);
			mess.setVisited();
			mess=a.update(ap, match);
			if (!(mess instanceof AcceptedAction)) fail();
			a.activate(ap, match);
		} catch (InvalidIDException | BuildingDeckException | NotExecutableException | RequirementNotMetException | InsufficientPropsException e1) {
			
		}
		

	}

	private void setAllPlayerProperties(int i, Player p) {
		for (PropertiesId prop : PropertiesId.values()) {
			p.getProperties().getProperty(prop).setValue(i);
		}
	}
}
