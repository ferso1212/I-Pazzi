package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.match.CompleteMatchException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchRunner implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(MatchRunner.class.getName());
	private UserHandler[] playerHandlers;
	private MatchController controller;

	public MatchRunner(UserHandler... usersToAdd) {
		this.playerHandlers = usersToAdd.clone();
	}

	@Override
	public void run() {
		try {
			UnsettedMatch match = new UnsettedMatch();

			for (UserHandler player : playerHandlers) {

				match.addPlayer(player.getPlayerId());
				this.controller = new MatchController(match, playerHandlers);
			}
		} catch (CompleteMatchException | InvalidIDException e) {
			LOGGER.log(Level.INFO, "Unable to add another player" , e);

		} catch (ParserConfigurationException p) {
			LOGGER.log(Level.SEVERE, "Unable to set the match" , p);
		}

	}

}
