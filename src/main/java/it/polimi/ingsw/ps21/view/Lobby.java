package it.polimi.ingsw.ps21.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.ps21.controller.MatchRunner;
import it.polimi.ingsw.ps21.controller.TimeoutTask;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Lobby extends Thread{
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentHashMap<String, UserHandler> playingUsers;
	private ArrayList<String> names;
	private final long TIMEOUT=3000; //TODO change
	// the milliseconds that the server will
	// wait once 2 players joined
	private final static int MAX_PLAYERS_NUM = 4; // The match is created if the
													// max number of players is
													// reached or if the timeout
													// expires.
	private final static int MIN_PLAYERS_NUM = 2;
	
	private boolean timeoutExpired;
	private boolean startedTimer = false;
	private boolean isAdvanced;
	
	
	public Lobby(boolean type, ArrayList<String> names, ConcurrentHashMap<String, UserHandler> playingUsers)
	{
		//this.TIMEOUT= MatchFactory.instance().makeTimeoutServer();
		this.timeoutExpired=false;
		this.connectionsQueue= new ConcurrentLinkedQueue<>();
		this.isAdvanced=type;
		this.names=names;
		this.playingUsers=playingUsers;
		
	}
	
	public void run(){
	String chosenRules;
	if(isAdvanced) chosenRules=new String("advanced");
	else chosenRules=new String("standard");
	
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
		UserHandler[] usersToAdd;
		synchronized (connectionsQueue) {
			System.out.println("\nInitializing a new " + chosenRules + " match with " + Math.min(MAX_PLAYERS_NUM, connectionsQueue.size())
					+ " players.");
			usersToAdd = new UserHandler[Math.min(connectionsQueue.size(), MAX_PLAYERS_NUM)];
			while ((playersAdded < usersToAdd.length) && (connectionsQueue.size() > 0)) {
				UserHandler newUser=new UserHandler(PlayerColor.values()[playersAdded], connectionsQueue.poll());
				usersToAdd[playersAdded] = newUser;
				playingUsers.put(newUser.getName(), newUser);
				playersAdded++;
			}
		}
		new Thread((new MatchRunner(isAdvanced, usersToAdd))).start();
		System.out.println("\nNew MatchRunner thread created with " + playersAdded + " players.");
		
	}
	}
	
	public ConcurrentLinkedQueue getConnections()
	{
		return this.connectionsQueue;
	}
}
