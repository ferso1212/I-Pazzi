package it.polimi.ingsw.ps21.view;

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

	private SocketConnectionsAcceptor socketAcceptor;
	private RMIConnectionsAcceptor rmiAcceptor;
	private final static long TIMEOUT = 60000; // the milliseconds that the server will
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
		this.socketAcceptor = new SocketConnectionsAcceptor(this.connections);
		this.rmiAcceptor = new RMIConnectionsAcceptor(this.connections);
		
	}

	@Override
	public void run() {
		this.socketAcceptor.run();
		this.rmiAcceptor.run();
		this.elapsedTime=new ElapsedTimeCounter();
		elapsedTime.start();
		while (true) {
			elapsedTime.resetCounter();
			System.out.println("Server started and ready to receive connections.");
			while(connections.size()<MAX_PLAYERS_NUM && elapsedTime.getElapsedTime()<TIMEOUT)
			{
				if(connections.size()>MIN_PLAYERS_NUM && !elapsedTime.isEnabled()) //the counter starts when at least 2 players have joined the lobby
					{
					elapsedTime.startCounter();
					}
			}
			elapsedTime.stopCounter();
			
			//Creates UserHandlers for each connection and and a new MatchRunner with those UserHandlers
			int playersAdded = 0;
			synchronized (connections) {
				UserHandler[] usersToAdd = new UserHandler[Math.max(connections.size(), MAX_PLAYERS_NUM)];
				while ((playersAdded < usersToAdd.length) && (connections.size() > 0)) {
						usersToAdd[playersAdded] = new UserHandler(PlayerColor.values()[playersAdded], connections.remove());
				}
				new Thread((new MatchRunner(usersToAdd))).start();;
			}
			
		}

	}

}
