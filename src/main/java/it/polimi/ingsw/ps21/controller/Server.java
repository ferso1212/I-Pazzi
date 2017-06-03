package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.UserHandler;

public class Server implements Runnable {

	private SocketConnectionsAcceptor socketAcceptor;
	private RMIConnectionsAcceptor rmiAcceptor;
	private final static int TIMEOUT = 60; // the seconds that the server will
											// wait once 2 players joined
	private final static int MAX_PLAYERS_NUM = 4; // The match is created if the
													// max number of players is
													// reached or if the timeout
													// expires.

	private ConcurrentLinkedQueue<UserHandler> connections = new ConcurrentLinkedQueue<UserHandler>();

	public Server() {
		this.socketAcceptor = new SocketConnectionsAcceptor();
		this.rmiAcceptor = new RMIConnectionsAcceptor();
		this.socketAcceptor.run();
		this.rmiAcceptor.run();

	}

	@Override
	public void run() {
		while (true) {
			long tStart = System.currentTimeMillis();
			long elapsedTime = 0;

			while (connections.size() < 4 && elapsedTime < 60000) {
				elapsedTime += System.currentTimeMillis();
			}

			int playersAdded = 0;
			synchronized (connections) {
				UserHandler[] usersToAdd = new UserHandler[connections.size()];
				while (playersAdded < MAX_PLAYERS_NUM && connections.size() > 0) {
						usersToAdd[i] = connections.remove();
				}
				(new MatchRunner(usersToAdd)).run();
			}
			
		}

	}

}
