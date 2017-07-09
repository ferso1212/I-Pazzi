package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.ExcommunicationMessage;
import it.polimi.ingsw.ps21.controller.LeaderChoice;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.TileChoice;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.LeaderChoiceAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.TileChoiceAction;
import it.polimi.ingsw.ps21.model.actions.VaticanAction;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.AdvancedMatch;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.match.VaticanRoundException;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.EndData;

public class TestMatches {
	private final static Logger LOGGER = Logger.getLogger(TestMatches.class.getName());
	private PlayerColor invalidPlayers[] = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED};
	private PlayerColor validPlayers[] = {PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW};
	private SimpleMatch testedSimpleMatch;
	private AdvancedMatch testedAdvancedMatch;
	
	@Before
	public void setUp(){
		try {
			testedSimpleMatch = new SimpleMatch(validPlayers);
			testedAdvancedMatch = new AdvancedMatch(validPlayers);
			// Setting default leaderCard
			while (testedAdvancedMatch.getRound() != RoundType.INITIAL_ROUND){
			Player player = testedAdvancedMatch.getCurrentPlayer();
			if (testedAdvancedMatch.getRound() == RoundType.LEADER_ROUND){
				LeaderChoice mess = new LeaderChoice(testedAdvancedMatch.getLeaderPossibilities(), player.getId(), 0);
				Action leaderAction = new LeaderChoiceAction(player.getId(), mess, 0);
				mess.setChosenCard(0);
				mess.setVisited();
				try {
					testedAdvancedMatch.doAction(leaderAction);
				} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException
						| VaticanRoundException e) {
					LOGGER.log(Level.WARNING, "Error executing test leader choice");
					fail("Error starting advancedMatch");
				}
			}
			if (testedAdvancedMatch.getRound() == RoundType.TILE_CHOICE){
				TileChoice mess2 = new TileChoice(player.getId(), testedAdvancedMatch.getPossibleTiles(), 0); 
				Action tileAction = new TileChoiceAction(player.getId(), mess2, 0);
				mess2.setChosen(0);
				mess2.setVisited();
				try {
					testedAdvancedMatch.doAction(tileAction);
				} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException
						| VaticanRoundException e) {
					LOGGER.log(Level.WARNING, "Error executing test tile choice");
					fail("Error starting advancedMatch");
				}
				
			}
				testedAdvancedMatch.setNextPlayer();
			}
			
		} catch (InvalidIDException | BuildingDeckException e) {
			fail("Error creating test match");
		} 
	}
	
	@Test
	public void test() {
		//assert(true);
	 assert(checkMatchCreation());
	 assert(checkNewRound());
	 setUp();
	 assert(checkInvalidMatchCreation());
	 assert(checkThrowDices(testedSimpleMatch));
	 assert(checkThrowDices(testedAdvancedMatch));
	 assert(checkPlayerLoop(testedSimpleMatch));
	 assert(checkPlayerLoop(testedAdvancedMatch));
	 assert(checkNotStandardPlayerLoop(testedSimpleMatch));
	 assert(checkNotStandardPlayerLoop(testedAdvancedMatch));
	 assert(checkCopingMatch());
	
	}
	
	/**
	 * This method check the order for new Round Creation when players have Delay First Action Modifier true
	 * @param testedMatch
	 * @return true if the order creation is ok, false otherwise
	 */
	
	private boolean checkNotStandardPlayerLoop(Match testedMatch) {
		testedMatch.getPlayers().forEach( p -> p.getModifiers().getActionMods().setDelayFirstAction(true));
		try {
			testedMatch.getBoard().getCouncilPalace().occupy(testedMatch.getCurrentPlayer(), testedMatch.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		} catch (NotOccupableException e) {
			e.printStackTrace();
		}
		Player currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		try {
			testedMatch.getBoard().getCouncilPalace().occupy(testedMatch.getCurrentPlayer(), testedMatch.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		} catch (NotOccupableException e) {
			e.printStackTrace();
		}
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		try {
			testedMatch.getBoard().getCouncilPalace().occupy(testedMatch.getCurrentPlayer(), testedMatch.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		} catch (NotOccupableException e) {
			e.printStackTrace();
		}
		
		while(testedMatch.getRound()!= RoundType.FINAL_ROUND){
			if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
			currentPlayer = testedMatch.getCurrentPlayer();
			testedMatch.setNextPlayer();
		}
		return true;
	}

	@Test
	public void vaticanTest(){
		assert(checkVaticanRound());
		assert(checkAdvancedVaticanRound());
	}
	
	@Test
	public void endTest(){
		assert(checkEndingMatch(testedSimpleMatch));
		assert(checkEndingMatch(testedAdvancedMatch));
	}
	
	private boolean checkAdvancedVaticanRound() {
		RoundType round = testedAdvancedMatch.getRound();
		Player player = testedAdvancedMatch.getCurrentPlayer();
		while(round!=RoundType.VATICAN_ROUND){
			round = testedAdvancedMatch.setNextPlayer();
		}
	while (round == RoundType.VATICAN_ROUND && !testedAdvancedMatch.isEnded()){
		player = testedAdvancedMatch.getCurrentPlayer();
		VaticanAction action = new VaticanAction(player.getId(), 0);
		Message message = action.update(player, testedAdvancedMatch);
		while (! (message instanceof ExcommunicationMessage) ){
			if (message instanceof RefusedAction) return false;
			else 
				if(message instanceof VaticanChoice){
					((VaticanChoice)message).setChosen(false);
					((VaticanChoice)message).setVisited();
				}
			message = action.update(player, testedAdvancedMatch);
		}
		try {
			action.activate(player, testedAdvancedMatch);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			LOGGER.log(Level.WARNING, "Error executing vatican action");
			return false;
			}
		round = testedAdvancedMatch.setNextPlayer();
		}		
		return true;
	}

	public boolean checkEndingMatch(Match test){
		Collection<Player> players = test.getPlayers();
		for (Player p: players){
			if (p.getId() == PlayerColor.BLUE) p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).setValue(10);
			if (p.getId() == PlayerColor.RED) p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).setValue(1);
			if (p.getId() == PlayerColor.YELLOW) p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).setValue(9);
		}
		while (!test.isEnded()){
		test.nextRound();
		}
		EndData testResult = testedSimpleMatch.getResult();
		for (PlayerColor c: testResult.getPlayersFinalPoints().keySet()){
			System.out.println(c + "\t" + testResult.getPlayersFinalPoints().get(c));
		}
		if(testResult.getPlayersFinalPoints().get(PlayerColor.BLUE) == 5
				&& testResult.getPlayersFinalPoints().get(PlayerColor.RED) == 0
				&& testResult.getPlayersFinalPoints().get(PlayerColor.YELLOW) == 2) return true;
		else return false;// Control the correct values for final points
	}
	

	
	private boolean checkVaticanRound() {
		RoundType round = testedSimpleMatch.getRound();
		while(round!=RoundType.VATICAN_ROUND){
			round = testedSimpleMatch.setNextPlayer();
		}
	while (round == RoundType.VATICAN_ROUND){
		Player player = testedSimpleMatch.getCurrentPlayer();
		VaticanAction action = new VaticanAction(player.getId(), 0);
		Message message = action.update(player, testedSimpleMatch);
		while (! (message instanceof ExcommunicationMessage) ){
			if (message instanceof RefusedAction) return false;
			else 
				if(message instanceof VaticanChoice){
					((VaticanChoice)message).setChosen(false);
					((VaticanChoice)message).setVisited();
				}
			message = action.update(player, testedSimpleMatch);
		}
		try {
			action.activate(player, testedSimpleMatch);
		} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException e) {
			LOGGER.log(Level.WARNING, "Error executing vatican action");
			return false;
			}
		round = testedSimpleMatch.setNextPlayer();
		}		
		return true;
	}

	private boolean checkNewRound() {
		PlayerColor oldOrder[]= testedSimpleMatch.getOrderQueue();
		testedSimpleMatch.nextRound();
		PlayerColor newOrder[] = testedSimpleMatch.getOrderQueue();
		if (!(testedSimpleMatch.getRound()!=RoundType.VATICAN_ROUND) || oldOrder.length != newOrder.length) return false; // vatican round implies different size
		for (int i=0; i<oldOrder.length; i++)
		{
			if (oldOrder[i]!=newOrder[i]) return false;
		}
		testedSimpleMatch.nextRound();
		if (testedSimpleMatch.getRound()!= RoundType.VATICAN_ROUND) return false;
		return true;
		
	}

	private boolean checkCopingMatch() {
		SimpleMatch testedSimpleMatch;
		try {
			testedSimpleMatch = new SimpleMatch(validPlayers);
		} catch (InvalidIDException | BuildingDeckException e1) {
			return false;
		}
		SimpleMatch copiedMatch = new SimpleMatch(testedSimpleMatch);
		if (!compareMatch(copiedMatch, testedSimpleMatch)) return false;
		try {
			SimpleMatch testCopy = testedSimpleMatch.getCopy();
			if (testCopy == testedSimpleMatch) return false;
		} catch (CloneNotSupportedException e) {
			return false;
		}
		AdvancedMatch advMatch;
		try {
			advMatch = new AdvancedMatch(validPlayers);
			AdvancedMatch copiedMatch2 = new AdvancedMatch(advMatch);
			if (!compareMatch(copiedMatch, testedSimpleMatch)) return false;
			if (!compareMatch(copiedMatch2, copiedMatch2.getCopy())) return false;
			return true;
		}
		
		catch (CloneNotSupportedException | BuildingDeckException | InvalidIDException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method is used to compare two different matches
	 * @param match1
	 * @param match2
	 * @return true if match1 and match2 have both same dices, same player order and same round, false otherwise
	 */
	private boolean compareMatch(Match match1, Match match2) {
		if (match1.getBlackDice() != match2.getBlackDice()) return false;
		if (match1.getOrangeDice() != match2.getOrangeDice()) return false;
		if (match1.getWhiteDice() != match2.getWhiteDice()) return false;
		if (match1.getRound() != match2.getRound()) return false;
		if (match1.isEnded() != match2.isEnded()) return false;
		PlayerColor[] firstOrder = match1.getOrderQueue();
		PlayerColor[] secondOrder = match1.getOrderQueue();
		if (secondOrder.length != firstOrder.length) return false;
		for (int i=0; i<firstOrder.length; i++ ){
			if (firstOrder[i]!= secondOrder[i]) return false;
		}
		return true;
		
	}

	private boolean checkPlayerLoop(Match testedMatch) {
		try {
			testedMatch.getBoard().getCouncilPalace().occupy(testedMatch.getCurrentPlayer(), testedMatch.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		} catch (NotOccupableException e) {
			e.printStackTrace();
		}
		Player currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		try {
			testedMatch.getBoard().getCouncilPalace().occupy(testedMatch.getCurrentPlayer(), testedMatch.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		} catch (NotOccupableException e) {
			e.printStackTrace();
		}
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		try {
			testedMatch.getBoard().getCouncilPalace().occupy(testedMatch.getCurrentPlayer(), testedMatch.getCurrentPlayer().getFamily().getMember(MembersColor.BLACK));
		} catch (NotOccupableException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<13; i++){
			if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
			currentPlayer = testedMatch.getCurrentPlayer();
			testedMatch.setNextPlayer();
		}
		if (testedMatch.getRound() != RoundType.FINAL_ROUND) return false;
		return true;
	}

	private boolean checkThrowDices(Match test) {
		test.throwDices();
		int equalValuesCounter = 0;
		int dicesValues[] = test.getDices();
		for (int j=0; j<10; j++){
			if (test.getBlackDice()< 1 || test.getBlackDice() > 6) return false;
		if (test.getWhiteDice()< 1 || test.getWhiteDice() > 6) return false;
		if (test.getOrangeDice()< 1 || test.getOrangeDice() > 6) return false;
		test.throwDices();
			int newDices[] = test.getDices();
			for (int i=0; i< newDices.length;i++) if (dicesValues[i] == newDices[i]) equalValuesCounter++;
			dicesValues = newDices;
		}
		if (equalValuesCounter <15) return true;
		else return false;
		
	}

	private boolean checkInvalidMatchCreation() {
		try {
			new AdvancedMatch(invalidPlayers);
			return false;
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.INFO, "Exception catched as expected, invalid match not created", e);
			return true;
		}
		
	}
	
	private boolean checkMatchCreation() {
		try {
			SimpleMatch match = new SimpleMatch(validPlayers);
			if (match.getPeriod()!=1) return false;
			if (match.getRound()!=RoundType.INITIAL_ROUND) return false;
			return true;
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Error creating a valid match", e);
			return false;
		}
		
	}

}
