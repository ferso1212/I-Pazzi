package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.match.AdvancedMatch;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.CompleteMatchException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchRunner {
	private final static Logger LOGGER = Logger.getLogger(MatchRunner.class.getName());
	private UserHandler[] playerHandlers;
	private MatchController controller;
	private boolean isAdvanced;

	public MatchRunner(boolean isAdvanced, UserHandler... usersToAdd) {
		this.playerHandlers = usersToAdd.clone();
		this.isAdvanced=isAdvanced;
	}

	
	public ArrayList<String> run() {
		try {
			Match match;
			PlayerColor[] playersIds= new PlayerColor[playerHandlers.length];
			for (int i=0; i<playersIds.length; i++) {

				playersIds[i]=playerHandlers[i].getPlayerId();
			}
			if(isAdvanced) match= new AdvancedMatch(playersIds);
			else  match = new SimpleMatch(playersIds);
			
			this.controller = new MatchController(match, playerHandlers);
			
			
		} catch (InvalidIDException e) {
			LOGGER.log(Level.INFO, "Unable to add another player" , e);

		} catch (BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Unable to create Match" , e);
		}
		
		ArrayList<String> namesToRemove= new ArrayList<>();
		for(UserHandler user: playerHandlers)
		{
			namesToRemove.add(user.getName());
	
		}
		return namesToRemove;
		
		
		
		
		

	}

}
