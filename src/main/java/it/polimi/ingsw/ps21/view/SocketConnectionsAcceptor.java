package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.ConnectionsAcceptor;
import it.polimi.ingsw.ps21.model.match.MatchFactory;

public class SocketConnectionsAcceptor extends ConnectionsAcceptor implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(SocketConnectionsAcceptor.class.getName());
	private static final int PORT = 100; // TODO choose correct port
	private ServerSocket serverSocket;
	
	
	public SocketConnectionsAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue) {
		super(connectionsQueue);
	}

	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO Exception", e);
		}
		System.out.println("\nServer socket ready on port " + PORT);

		while (acceptingConnections) {
			try {
				Socket newSocket = serverSocket.accept();
				System.out.println("\nNew inbound connection detected. Source IP address: " + newSocket.getInetAddress());
				synchronized (connections) {
					
					SocketConnection newConnection= new SocketConnection(newSocket);
					connections.add(newConnection);
				}
				System.out.println("\nNew inbound connection added to the queue.");
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IO Exception", e);
			}

		}

	}
	
}
