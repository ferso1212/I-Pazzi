package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.CompleteMatchException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedAdvancedMatch;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchRunner implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(MatchRunner.class.getName());
	private UserHandler[] playerHandlers;
	private MatchController controller;
	private boolean isAdvanced;

	public MatchRunner(boolean isAdvanced, UserHandler... usersToAdd) {
		this.playerHandlers = usersToAdd.clone();
		this.isAdvanced=isAdvanced;
	}

	@Override
	public void run() {
		try {
			UnsettedMatch match;
			if(isAdvanced) match= new UnsettedAdvancedMatch();
			else  match = new UnsettedMatch();

			for (UserHandler player : playerHandlers) {

				match.addPlayer(player.getPlayerId());
			}
			this.controller = new MatchController(match, playerHandlers);
		} catch (CompleteMatchException | InvalidIDException e) {
			LOGGER.log(Level.INFO, "Unable to add another player" , e);

		} catch (ParserConfigurationException | BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Unable to create Match" , e);
		}

	}

}
