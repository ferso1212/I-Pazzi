package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.ConnectionsAcceptor;


public class SocketConnectionsAcceptor extends ConnectionsAcceptor implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(SocketConnectionsAcceptor.class.getName());

	private static final int PORT = 7777; // TODO choose correct port
private ServerSocket serverSocket;

	
	public SocketConnectionsAcceptor(ConcurrentLinkedQueue<Connection> stdConnections, ConcurrentLinkedQueue<Connection> advConnections, ArrayList<String> names, ConcurrentHashMap<String, UserHandler> playingUsers, Semaphore stdSem, Semaphore advSem) {
		super(stdConnections, advConnections, names, playingUsers, stdSem, advSem);
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

			(new SocketConnectionAdder(newSocket, stdConnections, advConnections, names, playingUsers, stdSem, advSem)).start();
				
				
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IO Exception", e);
			}

		}

	}
	
}
