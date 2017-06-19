package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class TestMatches {
	private final static Logger LOGGER = Logger.getLogger(TestMatches.class.getName());
	private PlayerColor invalidPlayers[] = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED};
	private PlayerColor validPlayers[] = {PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW};
	private Match testedMatch;
	
	@Before
	public void setUp(){
		try {
			testedMatch = new Match(validPlayers);
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
	 assert(checkThrowDices());
	 assert(checkPlayerLoop());
	 assert(checkCopingMatch());
	
	}
	
	private boolean checkNewRound() {
		PlayerColor oldOrder[]= testedMatch.getOrderQueue();
		testedMatch.nextRound();
		PlayerColor newOrder[] = testedMatch.getOrderQueue();
		if (!(testedMatch.getRound()!=RoundType.VATICAN_ROUND) || oldOrder.length != newOrder.length) return false; // vatican round implies different size
		for (int i=0; i<oldOrder.length; i++)
		{
			if (oldOrder[i]!=newOrder[i]) return false;
		}
		testedMatch.nextRound();
		if (testedMatch.getRound()!= RoundType.VATICAN_ROUND) return false;
		return true;
		
	}

	private boolean checkCopingMatch() {
		Match previousMatch = new Match(testedMatch);
		if (!compareMatch(previousMatch, testedMatch)) return false;
		try {
			Match copiedMatch = testedMatch.getCopy();
			if (copiedMatch != testedMatch) return true;
			else return false;
		} catch (CloneNotSupportedException e) {
			return false;
		}
	}

	private boolean compareMatch(Match match1, Match match2) {
		if (match1.getBlackDice() != match2.getBlackDice()) return false;
		if (match1.getOrangeDice() != match2.getOrangeDice()) return false;
		if (match1.getWhiteDice() != match2.getWhiteDice()) return false;
		if (match1.getRound() != match2.getRound()) return false;
		if (match1.isEnded() != match2.isEnded()) return false;
		return true;
		
	}

	private boolean checkPlayerLoop() {
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

	private boolean checkThrowDices() {
		testedMatch.throwDices();
		int equalValuesCounter = 0;
		int dicesValues[] = testedMatch.getDices();
		for (int j=0; j<10; j++){
			if (testedMatch.getBlackDice()< 1 || testedMatch.getBlackDice() > 6) return false;
		if (testedMatch.getWhiteDice()< 1 || testedMatch.getWhiteDice() > 6) return false;
		if (testedMatch.getOrangeDice()< 1 || testedMatch.getOrangeDice() > 6) return false;
		testedMatch.throwDices();
			int newDices[] = testedMatch.getDices();
			for (int i=0; i< newDices.length;i++) if (dicesValues[i] == newDices[i]) equalValuesCounter++;
			dicesValues = newDices;
		}
		if (equalValuesCounter <10) return true;
		else return false;
		
	}

	private boolean checkInvalidMatchCreation() {
		try {
			new Match(invalidPlayers);
			return false;
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.INFO, "Exception catched as expected, invalid match not created", e);
			return true;
		}
		
	}
	
	private boolean checkMatchCreation() {
		try {
			Match match = new Match(validPlayers);
			if (match.getPeriod()!=1) return false;
			if (match.getRound()!=RoundType.INITIAL_ROUND) return false;
			return true;
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Error creating a valid match", e);
			return false;
		}
		
	}

}
