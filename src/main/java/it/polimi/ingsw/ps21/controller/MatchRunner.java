package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.polimi.ingsw.ps21.model.match.AdvancedMatch;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchRunner {
	private final static Logger LOGGER = Logger.getLogger(MatchRunner.class.getName());
	private UserHandler[] playerHandlers;
	private boolean isAdvanced;

	public MatchRunner(boolean isAdvanced, UserHandler... usersToAdd) {
		this.playerHandlers = usersToAdd.clone();
		this.isAdvanced=isAdvanced;
	}

	
	public ArrayList<String> run() {
		CountDownLatch latch= new CountDownLatch(1);
		try {
			Match match;
			PlayerColor[] playersIds= new PlayerColor[playerHandlers.length];
			for (int i=0; i<playersIds.length; i++) {

				playersIds[i]=playerHandlers[i].getPlayerId();
			}
			if(isAdvanced) match= new AdvancedMatch(playersIds);
			else  match = new SimpleMatch(playersIds);
			
			MatchController controller= new MatchController(match, latch, playerHandlers);
			(new Thread(controller)).start();
			
			
		} catch (InvalidIDException e) {
			LOGGER.log(Level.INFO, "Unable to add another player" , e);

		} catch (BuildingDeckException e) {
			LOGGER.log(Level.SEVERE, "Unable to create Match" , e);
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();

		}
		ArrayList<String> namesToRemove= new ArrayList<>();
		for(UserHandler user: playerHandlers)
		{
			namesToRemove.add(user.getName());
	
		}
		return namesToRemove;
		
		
		
		
		

	}

}
