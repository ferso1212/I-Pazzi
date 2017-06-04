package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.ps21.controller.ElapsedTimeCounter;
import it.polimi.ingsw.ps21.controller.MatchRunner;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Server implements Runnable {

	//private SocketConnectionsAcceptor socketAcceptor;
	//private RMIConnectionsAcceptor rmiAcceptor;
	private final static long TIMEOUT = 10000; // the milliseconds that the server will
											// wait once 2 players joined
	private final static int MAX_PLAYERS_NUM = 4; // The match is created if the
													// max number of players is
													// reached or if the timeout
													// expires.
	private final static int MIN_PLAYERS_NUM=2;

	private ConcurrentLinkedQueue<Connection> connections;
	private ElapsedTimeCounter elapsedTime;

	public Server() {
		this.connections= new ConcurrentLinkedQueue<Connection>();
		//this.socketAcceptor = new SocketConnectionsAcceptor(this.connections);
		//this.rmiAcceptor = new RMIConnectionsAcceptor(this.connections);
		
	}

	@Override
	public void run() {
		new Thread(new SocketConnectionsAcceptor(this.connections)).start();
		try {
			new Thread(new RMIConnectionAcceptor(this.connections)).start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.elapsedTime=new ElapsedTimeCounter();
		elapsedTime.start();
		while (true) {
			System.out.println("Server started and ready to receive connections.");
			while(connections.size()<MAX_PLAYERS_NUM && elapsedTime.getElapsedTime()<TIMEOUT)
			{
				if(connections.size()>=MIN_PLAYERS_NUM && !elapsedTime.isEnabled()) //the counter starts when at least 2 players have joined the lobby
					{
					elapsedTime.resetCounter();
					elapsedTime.startCounter();
					}
				if(elapsedTime.isEnabled()) System.out.println("Elapsed time since the second player joined the queue: " + elapsedTime.getElapsedTime() + " seconds.");
			}
			elapsedTime.stopCounter();
			if(elapsedTime.getElapsedTime()>TIMEOUT) System.out.println("\nTimeout expired: initializing new match with " + Math.max(MAX_PLAYERS_NUM, connections.size()) + " players.");
			else System.out.println("\nThere are enough connections in the queue to fulfill a match: initializing a new match with " + MAX_PLAYERS_NUM + " players.");
			
			//Creates UserHandlers for each connection and and a new MatchRunner with those UserHandlers
			int playersAdded = 0;
			synchronized (connections) {
				UserHandler[] usersToAdd = new UserHandler[Math.max(connections.size(), MAX_PLAYERS_NUM)];
				while ((playersAdded < usersToAdd.length) && (connections.size() > 0)) {
						usersToAdd[playersAdded] = new UserHandler(PlayerColor.values()[playersAdded], connections.remove());
				}
				new Thread((new MatchRunner(usersToAdd))).start();
				System.out.println("\nNew MatchRunner thread created with " + playersAdded + " players.");
			}
			
		}

	}

}
