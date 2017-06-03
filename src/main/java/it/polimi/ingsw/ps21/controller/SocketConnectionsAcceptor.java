package it.polimi.ingsw.ps21.controller;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.view.SocketConnection;
import it.polimi.ingsw.ps21.view.SocketUserHandler;
import it.polimi.ingsw.ps21.view.UserHandler;

public class SocketConnectionsAcceptor extends ConnectionsAcceptor implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(SocketConnectionsAcceptor.class.getName());
	private static final int PORT = 100; // TODO choose correct port
	private ServerSocket serverSocket;
	private ConcurrentLinkedQueue<UserHandler> connections;
	private boolean awaitingConnections;
	public SocketConnectionsAcceptor() {

	}

	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO Exception", e);
		}
		System.out.println("Server socket ready on port " + PORT);

		while (awaitingConnections) {
			try {
				Socket newConnection = serverSocket.accept();
				synchronized (connections) {
					
					SocketUserHandler newUserHandler= new SocketUserHandler(newConnection);
					connections.add(newUserHandler);
					newUserHandler.run();
				}
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IO Exception", e);
			}

		}

	}
}
