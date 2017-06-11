package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
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
	 assert(checkInvalidMatchCreation());
	 assert(checkThrowDices());
	 assert(checkPlayerLoop());
	}
	
	private boolean checkPlayerLoop() {
		Player currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		testedMatch.setNextPlayer();
		if (currentPlayer == testedMatch.getCurrentPlayer()) return false;
		currentPlayer = testedMatch.getCurrentPlayer();
		return true;
	}

	private boolean checkThrowDices() {
		int dicesValues[] = testedMatch.getDices();
		for (int j=0; j<10; j++){
			for (int i: dicesValues)
				if (i<1 || i>6) return false;
			if (dicesValues == testedMatch.getDices()) return false;
			dicesValues = testedMatch.getDices();
		}
		return true;
		
	}

	private boolean checkInvalidMatchCreation() {
		try {
			new Match(invalidPlayers);
			return false;
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Error creating a valid match", e);
			return true;
		}
		
	}
	
	private boolean checkMatchCreation() {
		try {
			Match match = new Match(validPlayers);
			if (match.getPeriod()!=1) return false;
			if (match.getRound()!=1) return false;
			return true;
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Error creating a valid match", e);
			return false;
		}
		
	}

}
