package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketConnectionAdder extends Thread {
	private final static Logger LOGGER = Logger.getLogger(SocketConnectionAdder.class.getName());
	private Socket socket;
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentLinkedQueue<Connection> advConnectionsQueue;
	private ArrayList<String> names;
	private ConcurrentHashMap<String, UserHandler> playingUsers;
	protected Semaphore stdSem;
	protected Semaphore advSem;

	public SocketConnectionAdder(Socket socket, ConcurrentLinkedQueue<Connection> connectionsQueue,
			ConcurrentLinkedQueue<Connection> advConnectionsQueue, ArrayList<String> names,
			ConcurrentHashMap<String, UserHandler> playingUsers, Semaphore stdSem, Semaphore advSem) {
		super();
		this.socket = socket;
		this.connectionsQueue = connectionsQueue;
		this.advConnectionsQueue = advConnectionsQueue;
		this.playingUsers = playingUsers;
		this.names = names;
		this.stdSem = stdSem;
		this.advSem = advSem;
	}

	public void run() {
		SocketConnection newInboundConnection = new SocketConnection(socket);

		String name;
		try {
			if (newInboundConnection.wantsNewMatch()) {
				boolean alreadyExists;

				do {
					name = newInboundConnection.reqName();
					if (name == null) {
						System.out.println("Connection interrupted because player canceled on name insertion");
						return;
					}
					synchronized (names) {
						alreadyExists = names.contains(name);
						if (!alreadyExists)
							names.add(name);
					}
					if (alreadyExists)
						newInboundConnection.sendMessage("This name already exists. Please choose another name. ");
					else
						addConnectionToQueue(newInboundConnection);
				} while (alreadyExists && newInboundConnection.isConnected());

			} else {
				boolean existsInMatches;
				do {
					name = newInboundConnection.reqName();
					synchronized (playingUsers) {
						existsInMatches = playingUsers.containsKey(name);
					}
					if (!existsInMatches)
						newInboundConnection.sendMessage("Name not found.");
				} while (!existsInMatches && newInboundConnection.isConnected());
				if (existsInMatches)
					rejoin(newInboundConnection);
			}

		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "User disconnected while requesting his name", e);
			return;
		}

	}

	private void addConnectionToQueue(SocketConnection newConnection) {
		boolean wantsAdvRules;
		try {
			wantsAdvRules = newConnection.reqWantsAdvRules();
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "User disconnected while requesting rules choice", e);
			return;
		}
		if (!wantsAdvRules) {
			synchronized (connectionsQueue) {
				this.connectionsQueue.add(newConnection);
			}
				stdSem.release();
				System.out.println("\n" + newConnection.getName()
						+ "'s inbound connection added to the standard lobby");
			
		}

		else {
			synchronized (advConnectionsQueue) {
				this.advConnectionsQueue.add(newConnection);
			}
				advSem.release();
				System.out.println("\n" + newConnection.getName()
						+ "'s inbound connection added to the advanced lobby");
			
		}
	}

	private void rejoin(SocketConnection newConnection) {
		UserHandler user = playingUsers.get(newConnection.getName());
		user.setConnection(newConnection);
		user.notifyReconnection();
	}

}
