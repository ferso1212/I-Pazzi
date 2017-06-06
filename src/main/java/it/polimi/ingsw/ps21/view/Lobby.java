package it.polimi.ingsw.ps21.view;

import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.ps21.controller.MatchRunner;
import it.polimi.ingsw.ps21.controller.TimeoutTask;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Lobby extends Thread{
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	
	private final static long TIMEOUT = 60000;
	// the milliseconds that the server will
	// wait once 2 players joined
	private final static int MAX_PLAYERS_NUM = 4; // The match is created if the
													// max number of players is
													// reached or if the timeout
													// expires.
	private final static int MIN_PLAYERS_NUM = 2;
	
	private boolean timeoutExpired;
	private boolean startedTimer = false;
	private String chosenRules;

	public Lobby(String type)
	{
		this.timeoutExpired=false;
		this.connectionsQueue= new ConcurrentLinkedQueue<>();
		this.chosenRules=type;
	}
	
	public void run(){
	
	while (true) {
		Timer timer = new Timer();
		TimeoutTask expired = new TimeoutTask();
		while (connectionsQueue.size() < MAX_PLAYERS_NUM && !expired.isExpired()) {
			if (connectionsQueue.size() >= MIN_PLAYERS_NUM && !startedTimer) // the
																		// counter
																		// starts
																		// when
																		// at
																		// least
																		// 2
																		// players
																		// have
																		// joined
																		// the
																		// lobby
			{

				timer.schedule(expired, TIMEOUT);
				startedTimer = true;
				System.out.println("Timeout started");
			}

		}
		timer.cancel();
		if (connectionsQueue.size() >= MAX_PLAYERS_NUM)
			System.out.println("\nThere are enough connections in the queue to fulfill a match.");
		timeoutExpired = false;
		// Creates UserHandlers for each connection and and a new
		// MatchRunner with those UserHandlers
		int playersAdded = 0;
		synchronized (connectionsQueue) {

			System.out.println("\nInitializing a new " + this.chosenRules + " match with " + Math.min(MAX_PLAYERS_NUM, connectionsQueue.size())
					+ " players.");
			UserHandler[] usersToAdd = new UserHandler[Math.min(connectionsQueue.size(), MAX_PLAYERS_NUM)];
			while ((playersAdded < usersToAdd.length) && (connectionsQueue.size() > 0)) {
				usersToAdd[playersAdded] = new UserHandler(PlayerColor.values()[playersAdded],
						connectionsQueue.remove());
				playersAdded++;
			}
			new Thread((new MatchRunner(usersToAdd))).start();
			System.out.println("\nNew MatchRunner thread created with " + playersAdded + " players.");
		}

	}
	}
	
	public ConcurrentLinkedQueue getConnections()
	{
		return this.connectionsQueue;
	}
}
