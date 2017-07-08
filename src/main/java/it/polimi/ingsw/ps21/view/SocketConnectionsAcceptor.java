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


public class SocketConnectionsAcceptor implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(SocketConnectionsAcceptor.class.getName());

	private static final int PORT = 7777; // TODO choose correct port
private ServerSocket serverSocket;
private ConcurrentLinkedQueue<Connection> stdConnections;
private ConcurrentLinkedQueue<Connection> advConnections;
private boolean acceptingConnections;
private ArrayList<String> names;
private ConcurrentHashMap<String, UserHandler> playingUsers;
private Semaphore stdSem;
private Semaphore advSem;

	
	public SocketConnectionsAcceptor(ConcurrentLinkedQueue<Connection> stdConnections, ConcurrentLinkedQueue<Connection> advConnections, ArrayList<String> names, ConcurrentHashMap<String, UserHandler> playingUsers, Semaphore stdSem, Semaphore advSem) {
		this.acceptingConnections=true;
		this.stdConnections=stdConnections;
		this.advConnections=advConnections;
		this.names=names;
		this.playingUsers=playingUsers;
		this.stdSem=stdSem;
		this.advSem= advSem;
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
