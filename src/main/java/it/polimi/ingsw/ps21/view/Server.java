package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.ps21.controller.MatchRunner;
import it.polimi.ingsw.ps21.controller.TimeoutTask;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Server implements Runnable {

	//private SocketConnectionsAcceptor socketAcceptor;
	//private RMIConnectionsAcceptor rmiAcceptor;
	private final static long TIMEOUT = 60000;
		 // the milliseconds that the server will
											// wait once 2 players joined
	private final static int MAX_PLAYERS_NUM = 4; // The match is created if the
													// max number of players is
													// reached or if the timeout
													// expires.
	private final static int MIN_PLAYERS_NUM=2;

	private ConcurrentLinkedQueue<Connection> connections; //players who want to play a standard game
	private ConcurrentLinkedQueue<Connection> advConnections; //players who want to play with advanced rules
	private Boolean timeout;
	private boolean startedTimer = false;
	public Server() {
		this.connections= new ConcurrentLinkedQueue<Connection>();
		this.timeout=new Boolean(false);
		
		
		//this.socketAcceptor = new SocketConnectionsAcceptor(this.connections);
		//this.rmiAcceptor = new RMIConnectionsAcceptor(this.connections);
		
	}

	@Override
	public void run() {
		new Thread(new SocketConnectionsAcceptor(this.connections)).start();
		try {
			Registry locRegistry = LocateRegistry.createRegistry(5000);
			RMIConnectionAcceptor rmiAcceptor= new RMIConnectionAcceptor(this.connections);
			RMIConnectionCreator stubAcceptor= (RMIConnectionCreator) UnicastRemoteObject.exportObject(rmiAcceptor, 0);
			locRegistry.rebind("RMIConnectionCreator", stubAcceptor);
			// new Thread(rmiAcceptor).start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Server started and ready to receive connections.");
		while (true) {
			Timer timer = new Timer();
			TimeoutTask expired = new TimeoutTask();
while(connections.size()<MAX_PLAYERS_NUM && !expired.isExpired())
			{
				if(connections.size()>=MIN_PLAYERS_NUM && !startedTimer ) //the counter starts when at least 2 players have joined the lobby
					{
					
					timer.schedule(expired, TIMEOUT);
					startedTimer = true;
					System.out.println("Timeout started");
					}
				
			}
			timer.cancel();
			if(connections.size()>=MAX_PLAYERS_NUM) System.out.println("\nThere are enough connections in the queue to fulfill a match.");
			timeout = false;
			//Creates UserHandlers for each connection and and a new MatchRunner with those UserHandlers
			int playersAdded=0;
			synchronized (connections) {
				
				System.out.println("\nInitializing a new match with " + Math.min(MAX_PLAYERS_NUM, connections.size()) + " players.");
				UserHandler[] usersToAdd = new UserHandler[Math.min(connections.size(), MAX_PLAYERS_NUM)];
				while ((playersAdded < usersToAdd.length) && (connections.size() > 0)) {
						usersToAdd[playersAdded] = new UserHandler(PlayerColor.values()[playersAdded], connections.remove());
						playersAdded++;
				}
				new Thread((new MatchRunner(usersToAdd))).start();
				System.out.println("\nNew MatchRunner thread created with " + playersAdded + " players.");
			}
			
		}

	}

}
