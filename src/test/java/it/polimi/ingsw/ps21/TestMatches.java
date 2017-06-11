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
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class TestMatches {
	private final static Logger LOGGER = Logger.getLogger(TestMatches.class.getName());
	private PlayerColor invalidPlayers[] = {PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.RED};
	private PlayerColor validPlayers[] = {PlayerColor.BLUE, PlayerColor.RED, PlayerColor.YELLOW};
	@Test
	public void test() {
		//assert(true);
	 assert(checkMatchCreation());
	 assert(checkInvalidMatchCreation());
	}
	
	private boolean checkInvalidMatchCreation() {
		try {
			Match match = new Match(invalidPlayers);
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Error creating a valid match", e);
			return true;
		}
		return false;
	}
	
	private boolean checkMatchCreation() {
		try {
			Match match = new Match(validPlayers);
		} catch (InvalidIDException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Error creating a valid match", e);
			return false;
		}
		return true;
		
	}

}
