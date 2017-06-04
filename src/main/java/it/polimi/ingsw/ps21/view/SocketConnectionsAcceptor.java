package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.ConnectionsAcceptor;
import it.polimi.ingsw.ps21.model.match.MatchFactory;

public class SocketConnectionsAcceptor extends ConnectionsAcceptor implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(SocketConnectionsAcceptor.class.getName());

	private static final int PORT = 7777; // TODO choose correct port
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
				BufferedReader in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
				PrintWriter out = new PrintWriter(newSocket.getOutputStream());
				out.println("\nPlease insert your name: ");
				String newName=in.readLine();
				synchronized (connections) {
					
					SocketConnection newConnection= new SocketConnection(newName, newSocket);
					connections.add(newConnection);
					System.out.println("\n" + newName + "'s inbound connection added to the queue in position " + connections.size());
				}
				
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IO Exception", e);
			}

		}

	}
	
}
