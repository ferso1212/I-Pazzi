package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedAdvancedMatch;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;

public class TestMatches {
	private final static Logger LOGGER = Logger.getLogger(TestMatches.class.getName());
	private UnsettedAdvancedMatch testAdvancedMatch;
	private UnsettedMatch testMatch;
	
	@Test
	public void test() {
		//assert(true);
	 assert(checkMatchCreation());
	}
	private boolean checkMatchCreation() {
		try {
			testAdvancedMatch = new UnsettedAdvancedMatch();
			testMatch = new UnsettedMatch();
		} catch (BuildingDeckException e) {
			LOGGER.log(Level.WARNING, "Error creating deck", e);	
			return false;
		}		
		return true;
	}

}
